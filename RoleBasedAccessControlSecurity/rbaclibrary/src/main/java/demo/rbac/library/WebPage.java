package demo.rbac.library;

import java.util.ArrayList;
import java.util.List;

public class WebPage {

    private Integer _id;            // this appears in other tables
    public Integer getId() {
        return _id;
    }
    public void setId(Integer value) {
        this._id = value;
    }

    private String _url;
    public String getUrl() {
        return _url;
    }
    public void setUrl(String value) {
        this._url = value;
    }

    public WebPage(Integer pId, String pUrl) {
        setId(pId);
        setUrl(pUrl);
    }

    public static List<WebPage> listWebPages = new ArrayList<>();

}
