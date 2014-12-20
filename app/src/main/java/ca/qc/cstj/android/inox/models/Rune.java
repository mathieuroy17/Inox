package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by 1159099 on 2014-11-04.
 */
public class Rune {
    private String type;
    private int nbrRune;

    public Rune() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbrRune() {
        return nbrRune;
    }

    public void setNbrRune(int nbrRune) {
        this.nbrRune = nbrRune;
    }
}
