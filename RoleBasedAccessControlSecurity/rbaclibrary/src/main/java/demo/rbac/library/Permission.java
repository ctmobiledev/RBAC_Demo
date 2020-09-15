package demo.rbac.library;

import java.util.ArrayList;
import java.util.List;

import static demo.rbac.library.User.*;
import static demo.rbac.library.Permission.*;

public class Permission {

    private Integer _id;
    public Integer getId() {
        return _id;
    }
    public void setId(Integer value) {
        this._id = value;
    }

    private Integer _roleID;
    public Integer getRoleID() {
        return _roleID;
    }
    public void setRoleID(Integer value) {
        this._roleID = value;
    }

    private Integer _webPageID;
    public Integer getWebPageID() {
        return _webPageID;
    }
    public void setWebPageID(Integer value) {
        this._webPageID = value;
    }

    private boolean _hasPermission;
    public boolean getHasPermission() {
        return _hasPermission;
    }
    public void setHasPermission(boolean value) {
        this._hasPermission = value;
    }

    public static boolean canAccess(Integer userID, Integer webPageID) {

        // Find roleID for the userID
        Integer foundRoleID = -1;
        for (User u : listUsers) {
            if (userID == u.getId()) {
                foundRoleID = u.getRoleId();
                break;
            }
        }
        //System.out.println(">>> foundUserID = " + foundUserID);
        //System.out.println(">>> foundRoleID = " + foundRoleID);

        // Find permission for the roleID
        Integer foundPermissionID = -1;
        boolean foundHasPermission = false;
        for (Permission p : listPermissions) {
            if ( (foundRoleID == p.getRoleID()) && (webPageID == p.getWebPageID()) ) {
                foundPermissionID = p.getId();
                foundHasPermission = p.getHasPermission();
                break;
            }
        }
        System.out.println(">>> foundPermissionID = " + foundPermissionID);
        System.out.println(">>> foundHasPermission = " + foundHasPermission);

        return foundHasPermission;

    }

    public Permission(Integer pId, Integer pRoleId, Integer pWebPageId, boolean pHasPermission) {
        setId(pId);
        setRoleID(pRoleId);
        setWebPageID(pWebPageId);
        setHasPermission(pHasPermission);
    }

    public static List<Permission> listPermissions = new ArrayList<>();

}
