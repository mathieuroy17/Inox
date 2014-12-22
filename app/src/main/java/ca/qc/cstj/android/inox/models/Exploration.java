package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cstj.android.inox.helpers.DateParser;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class Exploration {
    private String href;
    private String locationDepart;
    private String locationArriver;
    private DateTime dateExploration;
    private Troop troop;
    private ArrayList<Rune> lstRunes;

    public Exploration(JsonObject jsonObject) {
        if(jsonObject.has("href")) {
            href = jsonObject.getAsJsonPrimitive("href").getAsString();
        }
        if(jsonObject.has("dateExploration")) {
            dateExploration = DateParser.ParseIso(jsonObject.getAsJsonPrimitive("dateExploration").getAsString());
        }
        if(jsonObject.has("locations"))
        {
            JsonObject location = jsonObject.getAsJsonObject("locations");
            if(location.has("start"))
            {
                locationDepart = location.getAsJsonPrimitive("start").getAsString();
            }
            if(location.has("end"))
            {
                locationArriver = location.getAsJsonPrimitive("end").getAsString();
            }

        }
        if(jsonObject.has("troop"))
        {
            troop = new Troop(jsonObject.getAsJsonObject("troop"));
        }
        if(jsonObject.has("runes"))
        {
            JsonObject JsonObject= jsonObject.getAsJsonObject("runes");

            lstRunes=Fonction.FormatRune(JsonObject);
        }
    }

    public JsonObject ListRuneToJson()
    {
        JsonObject jsonObject = new JsonObject();

        for(Rune element : lstRunes)
        {
           jsonObject.addProperty(element.getType(),element.getNbrRune());
        }
        return jsonObject;
    }

    public JsonObject getExplorationAsJson()
    {
        JsonObject jsonObject = new JsonObject();
        JsonObject location = new JsonObject();
        JsonObject rune = new JsonObject();

        jsonObject.addProperty("dateExploration",dateExploration.toString());

        location.addProperty("start",locationDepart);
        location.addProperty("end",locationArriver);

        jsonObject.add("locations",location);
        jsonObject.add("troop",troop.getTroopAsJson());
        jsonObject.add("runes",ListRuneToJson());

        return jsonObject;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getLocationDepart() {
        return locationDepart;
    }

    public void setLocationDepart(String locationDepart) {
        this.locationDepart = locationDepart;
    }

    public String getLocationArriver() {
        return locationArriver;
    }

    public void setLocationArriver(String locationArriver) {
        this.locationArriver = locationArriver;
    }

    public DateTime getDateExploration() {
        return dateExploration;
    }

    public void setDateExploration(DateTime dateExploration) {
        this.dateExploration = dateExploration;
    }

    public Troop getTroop() {
        return troop;
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }


    public ArrayList<Rune> getLstRunes() {
        return lstRunes;
    }

    public void setLstRunes(ArrayList<Rune> lstRunes) {
        this.lstRunes = lstRunes;
    }
}
