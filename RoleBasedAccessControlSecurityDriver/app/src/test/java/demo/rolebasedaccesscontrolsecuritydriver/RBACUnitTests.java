package demo.rolebasedaccesscontrolsecuritydriver;

import android.support.v4.app.INotificationSideChannel;

import org.junit.Test;

import static org.junit.Assert.*;

import demo.rbac.library.*;                        // packaged AAR module for library

public class RBACUnitTests {

    @Test
    public void getNextId_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> getNextId_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        Integer before = operations.nextId;
        operations.getNextId();
        Integer after = operations.nextId;
        assertTrue("after should be 1 greater than before", (after - before == 1));
    }

    @Test
    public void getIdForWebPage_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> getIdForWebPage_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addWebPage("news.html");
        operations.addWebPage("weather.html");
        operations.addWebPage("sports.html");

        Integer webPageID1 = operations.getIDForWebPage("news.html");
        assertTrue("webPageID1 should equal 1", (webPageID1 == 1));

        Integer webPageID2 = operations.getIDForWebPage("weather.html");
        assertTrue("webPageID1 should equal 2", (webPageID2 == 2));

        Integer webPageID3 = operations.getIDForWebPage("sports.html");
        assertTrue("webPageID1 should equal 3", (webPageID3 == 3));
    }

    @Test
    public void getIdForRole_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> getIdForRole_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addWebPage("news.html");
        operations.addWebPage("weather.html");
        operations.addWebPage("sports.html");

        operations.addRole("Creator");
        operations.addRole("Viewer");

        assertTrue("Role Creator should have roleID of 4", (Role.listRoles.get(0).getId() == 4));
        assertTrue("User Viewer should have userID of 5", (Role.listRoles.get(1).getId() == 5));

        Integer roleID1 = operations.getIDForRole("Creator");
        assertTrue("roleID1 should equal 4", (roleID1 == 4));

        Integer roleID2 = operations.getIDForRole("Viewer");
        assertTrue("roleID1 should equal 5", (roleID2 == 5));
    }

    @Test
    public void getIdForUser_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> getIdForUser_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addWebPage("news.html");
        operations.addWebPage("weather.html");
        operations.addWebPage("sports.html");

        operations.addRole("Creator");
        operations.addRole("Viewer");

        operations.addUser("Andy");
        operations.addUser("Mitch");

        assertTrue("User Andy should have userID of 6", (User.listUsers.get(0).getId() == 6));
        assertTrue("User Mitch should have userID of 7", (User.listUsers.get(1).getId() == 7));

        Integer userID1 = operations.getIDForUser("Andy");
        assertTrue("roleID1 should equal 6", (userID1 == 6));

        Integer userID2 = operations.getIDForUser("Mitch");
        assertTrue("roleID2 should equal 7", (userID2 == 7));
    }

    @Test
    public void webPages_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> webPages_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addWebPage("news.html");
        operations.addWebPage("weather.html");
        operations.addWebPage("sports.html");

        Integer count = WebPage.listWebPages.size();
        assertTrue("listWebPages should have 3 elements", (count == 3));
    }

    @Test
    public void roles_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> roles_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addRole("Creator");
        operations.addRole("Viewer");

        Integer count = Role.listRoles.size();
        assertTrue("listRoles should have 2 elements", (count == 2));
    }

    @Test
    public void users_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> users_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addUser("Andy");
        operations.addUser("Mitch");

        Integer count = User.listUsers.size();
        assertTrue("listUsers should have 2 elements", (count == 2));
    }

    @Test
    public void complete_test1() {
        System.out.println(">>>*********************************");
        System.out.println(">>> complete_test1()");
        System.out.println(">>>*********************************");

        resetEnvironment();

        operations.addWebPage("news.html");
        operations.addWebPage("weather.html");
        operations.addWebPage("sports.html");

        operations.addRole("Creator");
        operations.addRole("Viewer");

        operations.addUser("Andy");
        operations.addUser("Mitch");

        operations.assignRoleToUser("Andy", "Creator");
        operations.assignRoleToUser("Mitch", "Viewer");
        System.out.println(">>> users created");

        User user0 = User.listUsers.get(0);
        assertTrue("User Andy should have RoleID = 4", (user0.getRoleId() == 4));

        User user1 = User.listUsers.get(1);
        assertTrue("User Mitch should have RoleID = 5", (user1.getRoleId() == 5));


        //*************************
        // Permissions
        //*************************
        operations.definePermission("Creator", "news.html", true);
        operations.definePermission("Creator", "weather.html", true);
        operations.definePermission("Creator", "sports.html", true);

        Permission perm2 = Permission.listPermissions.get(2);
        assertTrue("Permission Creator/sports.html should have hasPermission = true",
                (perm2.getHasPermission() == true));

        operations.definePermission("Viewer", "news.html", false);
        operations.definePermission("Viewer", "weather.html", false);
        operations.definePermission("Viewer", "sports.html", false);

        Permission perm4 = Permission.listPermissions.get(4);
        assertTrue("Permission Viewer/weather.html should have hasPermission = false",
                (perm4.getHasPermission() == false));

        System.out.println(">>> permissions created");

        operations.printData();

        //*************************
        // Operation
        //*************************

        Boolean AndyNewsPerm = Permission.canAccess(6, 1);
        assertTrue("Andy's permission for news.html should be true",
                (AndyNewsPerm == true));

        Boolean MitchSportsPerm = Permission.canAccess(7, 3);
        assertTrue("Mitch's permission for sports.html should be false",
                (MitchSportsPerm == false));


        //*************************
        // Removals
        //*************************

        Boolean found;

        operations.deleteUser("Mitch");
        found = false;
        for (User u : User.listUsers) {
            if (u.getName() == "Mitch") {
                found = true;
            }
        }
        assertTrue("User Mitch should have been deleted", (found == false));

        operations.deleteWebPage("sports.html");
        found = false;
        for (WebPage w : WebPage.listWebPages) {
            if (w.getUrl() == "sports.html") {
                found = true;
            }
        }
        assertTrue("WebPage sports.html should have been deleted", (found == false));

        operations.deletePermission("Viewer", "sports.html");
        found = false;
        for (Permission p : Permission.listPermissions) {
            if ( (p.getRoleID() == 5) && (p.getWebPageID() == 3 ) ) {
                found = true;
            }
        }
        assertTrue("Permission Viewer/sports.html should have been deleted", (found == false));

        operations.deletePermission("Creator", "editorial.html");

        operations.deleteRole("Viewer");
        found = false;
        for (Role r : Role.listRoles) {
            if (r.getName() == "Viewer") {
                found = true;
            }
        }
        assertTrue("Role Viewer should have been deleted", (found == false));

        operations.deleteUser("Craig");

        operations.printData();

    }

    private void resetEnvironment() {
        operations.nextId = 0;
        WebPage.listWebPages.clear();
        Role.listRoles.clear();
        User.listUsers.clear();
        Permission.listPermissions.clear();
    }

}