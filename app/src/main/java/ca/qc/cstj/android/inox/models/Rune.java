package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by 1159099 on 2014-11-04.
 */
public class Rune {
    private String href;
    private String type;
    private int nbrRune;

    public Rune(JsonObject jsonObject) {
        if(jsonObject.has("href")) {
            href = jsonObject.getAsJsonPrimitive("href").getAsString();
        }
        /*if(jsonObject.has("type")) {
            type = jsonObject.getAsJsonPrimitive("type").getAsString();
        }*/
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setTitre(String type) {
        this.type = type;
    }


}
