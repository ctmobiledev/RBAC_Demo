package demo.rbac.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Role {

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

    public Role(Integer pId, String pName) {
        setId(pId);
        setName(pName);
    }

    public static List<Role> listRoles = new ArrayList<>();

}
