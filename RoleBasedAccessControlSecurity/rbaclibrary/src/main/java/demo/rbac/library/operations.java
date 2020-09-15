package demo.rbac.library;

/*
***********************************************************************************************
*       Role-Based Access Control Security Model Challenge
*       Charles Tatum
*       charlestatumuiuc@outlook.com
*
*       Version 1.0
***********************************************************************************************
*/

import static demo.rbac.library.User.*;
import static demo.rbac.library.Role.*;
import static demo.rbac.library.WebPage.*;
import static demo.rbac.library.Permission.*;

public class operations {

    //*****************************************
    // Programmer's Notes
    //*****************************************

    // Proper order of defining data:
    //      1. Web Page = give a URL
    //      2. Role = give a name
    //      3. Permission = a combination of a Role, a WebPage, and a boolean indicating canAccess
    //      4. User = give a name, and then assign to a Role


    public static Integer nextId = 0;
    public static Integer getNextId() {
        nextId += 1;
        //System.out.println(">>> getNextId - nextId is " + nextId);
        return nextId;
    }

    public static Integer getIDForUser(String name) {
        Integer result = -1;
        for (User u : listUsers) {
            if (name == u.getName()) {
                result = u.getId();
                break;
            }
        }
        //System.out.println(">>> getIDForUser - name = " + name + ": " + result);
        if (result == -1) { System.out.println(">>> name " + name + " does not exist"); }
        return result;
    }

    public static Integer getRoleIDForUser(String userName) {
        Integer result = -1;
        for (User u : listUsers) {
            if (userName == u.getName()) {
                result = u.getRoleId();
                break;
            }
        }
        //System.out.println(">>> getRoleIDForUser - name = " + userName + " has a role ID of " + result);
        if (result == -1) { System.out.println(">>> name " + userName + " has not been assigned a role"); }
        return result;
    }

    public static Integer getIDForRole(String name) {
        Integer result = -1;
        for (Role u : listRoles) {
            if (name == u.getName()) {
                result = u.getId();
                break;
            }
        }
        //System.out.println(">>> getIDForRole - name = " + name + ": " + result);
        if (result == -1) { System.out.println(">>> role " + name + " does not exist"); }
        return result;
    }

    public static Integer getIDForWebPage(String name) {
        Integer result = -1;
        for (WebPage u : listWebPages) {
            if (name == u.getUrl()) {
                result = u.getId();
                break;
            }
        }
        //System.out.println(">>> getIDForWebPage - url = " + name + ": " + result);
        if (result == -1) { System.out.println(">>> url " + name + " does not exist"); }
        return result;
    }

    public static void addUser(String newValue) {
        User newUser = new User(getNextId(), newValue);         // role assigned separately
        listUsers.add(newUser);
        System.out.println(">>> addUser - added name " + newValue + " with ID = " + newUser.getId());
    }

    public static void addRole(String newValue) {
        Role newRole = new Role(getNextId(), newValue);
        listRoles.add(newRole);
        System.out.println(">>> addRole - added name " + newValue + " with ID = " + newRole.getId());
    }

    public static void addWebPage(String newValue) {
        WebPage newWebPage = new WebPage(getNextId(), newValue);
        listWebPages.add(newWebPage);
        System.out.println(">>> addWebPage - added url " + newValue + " with ID = " + newWebPage.getId());
    }

    public static void assignRoleToUser(String userName, String roleName) {

        User userToAssign = new User();
        User foundUser = new User();

        //System.out.println(">>> assignRoleToUser - starting ");

        Integer userID = getIDForUser(userName);
        Integer roleID = getIDForRole(roleName);

        //System.out.println(">>> assignRoleToUser - userID = " + userID);
        //System.out.println(">>> assignRoleToUser - roleID = " + roleID);

        if ( (userID != -1) && (roleID != -1) ) {
            userToAssign.setId(userID);
            userToAssign.setName(userName);
            userToAssign.setRoleId(roleID);

            Integer posInList = -1;
            for (Integer n = 0; n < listUsers.size(); n++) {
                if (userID == listUsers.get(n).getId()) {
                    posInList = n;
                    break;
                }
            }
            //System.out.println(">>> assignRoleToUser - posInList = " + posInList);

            if (posInList != -1) {
                foundUser = listUsers.get(posInList);
                listUsers.get(posInList).setRoleId(roleID);

                // Now, find the user in listUsers because its roleID value is null at this point
                System.out.println(">>> assignRoleToUser - user " + userName + " has been assigned role of " +
                        roleName);
            }
        } else {
            System.out.println(">>> assignRoleToUser error: " +
                    "user " + userName + (userID == -1 ? " does not exist " : " exists while ") +
                    "role " + userName + (userID == -1 ? " does not exist " : " exists")
            );
        }
    }

    public static void definePermission(String roleName, String webPageName, boolean permValue) {
        Integer roleID = getIDForRole(roleName);
        Integer webPageID = getIDForWebPage(webPageName);

        // Permissions are driven by the roleID, not the permission row's own "ID"
        listPermissions.add(new Permission(getNextId(), roleID, webPageID, permValue));
        System.out.println(">>> definePermission : role = " + roleName + " includes webPageName = " + webPageName +
                " with canAccess value of " + permValue);
    }

    public static void queryUserForURL(String userName, String webPageName) {
        Integer userID = getIDForUser(userName);
        Integer webPageID = getIDForWebPage(webPageName);

        System.out.println(">>> queryUserForURL : user = " + userName + ", webPageName = " + webPageName);

        if (userID == -1) {
            System.out.println(">>> user = " + userName + " does not exist; exiting");
            System.out.println(" ");
            return;
        }
        if (webPageID == -1) {
            System.out.println(">>> web page = " + webPageName + " does not exist; exiting");
            System.out.println(" ");
            return;
        }

        boolean userHasAccess = Permission.canAccess(userID, webPageID);

        if (userHasAccess) {
            System.out.println(">>> user HAS ACCESS");
            System.out.println(" ");
        } else {
            System.out.println(">>> user DOES NOT HAVE ACCESS");
            System.out.println(" ");
        }
    }

    public static void deleteUser(String name) {
        // When a User is deleted, there's no impact on roles, web pages or permissions.

        System.out.println(">>> deleteUser - deleting user " + name);
        Integer userID = getIDForUser(name);
        Integer posInList = -1;
        for (Integer n = 0; n < listUsers.size(); n++) {
            if (userID == listUsers.get(n).getId()) {
                posInList = n;
                break;
            }
        }
        if (posInList > -1) {
            User objToRemove = listUsers.get(posInList);
            listUsers.remove(objToRemove);
            System.out.println(">>> deletion successful");
        } else {
            System.out.println(">>> that user does not exist");
        }

    }

    public static void deleteRole(String name) {
        // When a Role is deleted, there's impacts on users (since they can be associated with one),
        // and with permissions (since it's one of the properties of one).

        System.out.println(">>> deleteRole - deleting role " + name);

        Integer roleID = getIDForRole(name);
        Integer posInList = -1;
        for (Integer n = 0; n < listRoles.size(); n++) {
            if (roleID == listRoles.get(n).getId()) {
                posInList = n;
                break;
            }
        }
        if (posInList > -1) {
            Role objToRemove = listRoles.get(posInList);
            listRoles.remove(objToRemove);

            // We must now also change any users' roleID values to nulls
            for (User u : listUsers) {
                if (u.getRoleId() == roleID) {
                    u.setRoleId(null);
                    System.out.println(">>> user " + u.getName() + " now has NO ROLE ASSIGNED (null)");
                }
            }

            System.out.println(">>> deletion successful");
        } else {
            System.out.println(">>> that role does not exist");
        }

    }

    public static void deleteWebPage(String name) {
        // When a WebPage is deleted, there's impacts on definitions of a Permission.

        System.out.println(">>> deleteWebPage - deleting web page " + name);

        Integer webPageID = getIDForWebPage(name);
        Integer posInList = -1;
        for (Integer n = 0; n < listWebPages.size(); n++) {
            if (webPageID == listWebPages.get(n).getId()) {
                posInList = n;
                break;
            }
        }

        if (posInList > -1) {
            WebPage objToRemove = listWebPages.get(posInList);
            listWebPages.remove(objToRemove);

            // We must now also cascade-delete any permission definitions' since webPageID doesn't exist anymore
            for (Integer n = 0; n < listPermissions.size(); n++) {
                if (listPermissions.get(n).getWebPageID() == webPageID) {
                    Permission permToRemove = listPermissions.get(n);
                    listPermissions.remove(permToRemove);
                    System.out.println(">>> permission ID " + n + " HAS BEEN DELETED (cascading)");
                }
            }

            System.out.println(">>> deletion successful");
        } else {
            System.out.println(">>> that web page does not exist");
        }

    }

    public static void deletePermission(String roleName, String webPage) {
        // When a Permission is deleted, it does changes the definition of the role, but it does
        // not require the listRoles to be modified.

        System.out.println(">>> deletePermissions - deleting permission for role " + roleName + " and web page " +
                webPage);

        Integer roleID = getIDForRole(roleName);
        Integer webPageID = getIDForWebPage(webPage);
        Integer deleteCount = 0;

        for (Integer n = 0; n < listPermissions.size(); n++) {
            if ( (listPermissions.get(n).getRoleID() == roleID) &&
                    (listPermissions.get(n).getWebPageID() == webPageID) ) {
                Permission objToRemove = listPermissions.get(n);
                listPermissions.remove(objToRemove);
                System.out.println(">>> permission ID " + n + " HAS BEEN DELETED");
                deleteCount += 1;
            }
        }

        if (deleteCount > 0) {
            System.out.println(">>> deletion successful - data rows removed: " + deleteCount);
        } else {
            System.out.println(">>> no matching data rows found");
        }

    }

    public static void runInternalTest() {

        System.out.println(">>> runInternalTest() fired");

        System.out.println(">>> ************************************************");
        System.out.println(">>> **************** START OF TEST *****************");
        System.out.println(">>> ************************************************");
        System.out.println(" ");

        //*************************
        // Input Data
        //*************************

        addWebPage("news.html");
        addWebPage("weather.html");
        addWebPage("sports.html");
        System.out.println(">>> web pages created");
        printData();

        addRole("Creator");
        addRole("Viewer");
        System.out.println(">>> roles created");
        printData();

        addUser("Andy");
        addUser("Mitch");
        System.out.println(">>> users created");
        printData();

        assignRoleToUser("Andy", "Creator");
        assignRoleToUser("Mitch", "Viewer");
        System.out.println(">>> users created");
        printData();

        //*************************
        // Permissions
        //*************************
        definePermission("Creator", "news.html", true);
        definePermission("Creator", "weather.html", true);
        definePermission("Creator", "sports.html", true);

        definePermission("Viewer", "news.html", false);
        definePermission("Viewer", "weather.html", false);
        definePermission("Viewer", "sports.html", false);

        System.out.println(">>> permissions created");
        printData();

        //*************************
        // Operation
        //*************************

        queryUserForURL("Andy", "news.html");
        queryUserForURL("Andy", "weather.html");
        queryUserForURL("Andy", "sports.html");

        queryUserForURL("Mitch", "news.html");
        queryUserForURL("Mitch", "weather.html");
        queryUserForURL("Mitch", "sports.html");

        queryUserForURL("Craig", "news.html");
        queryUserForURL("Philippe", "business.html");
        queryUserForURL("Andy", "entertainment.html");

        //*************************
        // Removals
        //*************************

        deleteUser("Mitch");
        printData();

        deleteWebPage("sports.html");
        printData();

        deleteWebPage("entertainment.html");
        printData();

        deletePermission("Viewer", "sports.html");
        printData();

        deletePermission("Creator", "editorial.html");
        printData();

        deleteRole("Viewer");
        printData();

        deleteUser("Craig");
        printData();

        deleteUser("Andy");
        printData();

        deleteRole("Management");
        printData();

        System.out.println(" ");
        System.out.println(">>> ************************************************");
        System.out.println(">>> ****************  END OF TEST  *****************");
        System.out.println(">>> ************************************************");

    }

    public static void printData() {

        System.out.println(" ");
        System.out.println(">>>*************************************************************************");
        System.out.println(">>> CURRENT DATA STATE:");
        System.out.println(">>>*************************************************************************");
        System.out.println(">>> Web Pages:");
        for (WebPage w : listWebPages) {
            System.out.println(">>>   " + w.getId() + ": " + w.getUrl());
        }
        System.out.println(">>> Roles:");
        for (Role r : listRoles) {
            System.out.println(">>>   " + r.getId() + ": " + r.getName());
        }
        System.out.println(">>> Users:");
        for (User u : listUsers) {
            System.out.println(">>>   " + u.getId() + ": " + u.getName() + ", RoleID: " + u.getRoleId());
        }
        System.out.println(">>> Permissions:");
        for (Permission p : listPermissions) {
            System.out.println(">>>   " + p.getId() +
                    ": RoleID = " + p.getRoleID().toString() +
                    ", WebPageID = " + p.getWebPageID().toString() +
                    ", HasPermission = " + p.getHasPermission());
        }
        System.out.println(">>>*************************************************************************");
        System.out.println(" ");

    }

}
