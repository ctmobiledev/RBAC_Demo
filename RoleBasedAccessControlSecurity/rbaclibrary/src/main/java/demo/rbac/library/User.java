package demo.rbac.library;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Integer _id;
    public Integer getId() {
        return _id;
    }
    public void setId(Integer value) {
        this._id = value;
    }

    private String _name;
    public String getName() {
        return _name;
    }
    public void setName(String value) {
        this._name = value;
    }

    // foreign key - user gets only one role
    private Integer _roleId;
    public Integer getRoleId() {
        return _roleId;
    }
    public void setRoleId(Integer value) {
        this._roleId = value;
    }

    public User() {
        // allows setting of properties later
    }

    public User(Integer pId, String pName) {
        setId(pId);
        setName(pName);
    }

    public User(Integer pId, String pName, Integer pRoleId) {
        setId(pId);
        setName(pName);
        setRoleId(pRoleId);
    }

    public static List<User> listUsers = new ArrayList<>();

}
