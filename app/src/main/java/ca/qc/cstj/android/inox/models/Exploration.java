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
    private List<Rune> lstRunes;

    public Exploration(JsonObject jsonObject) {
        if(jsonObject.has("href")) {
            href = jsonObject.getAsJsonPrimitive("href").getAsString();
        }
        if(jsonObject.has("dateExploration")) {
            dateExploration = DateParser.ParseIso(jsonObject.getAsJsonPrimitive("dateExploration").getAsString());
        }
        if(jsonObject.has("Locations"))
        {
            JsonObject location = jsonObject.getAsJsonObject("Locations");
            if(location.has("Depart"))
            {
                locationDepart = location.getAsJsonPrimitive("Depart").getAsString();
            }
            else if(location.has("start"))
            {
                locationDepart = location.getAsJsonPrimitive("Depart").getAsString();
            }

            if(location.has("Fin"))
            {
                locationArriver = location.getAsJsonPrimitive("Fin").getAsString();
            }
            else if(location.has("end"))
            {
                locationDepart = location.getAsJsonPrimitive("end").getAsString();
            }

        }
        if(jsonObject.has("troop"))
        {
            troop = new Troop(jsonObject.getAsJsonObject("troop"));
        }
        if(jsonObject.has("runes"))
        {
            JsonObject JsonObject= jsonObject.getAsJsonObject("runes");

            int size= JsonObject.entrySet().size();
            Object object[]= JsonObject.entrySet().toArray();

            for(int i=0;i<size;i++)
            {
                Rune rune = new Rune();
                String tmp= object[i].toString();
                int index =tmp.indexOf("=");
                String type = tmp.substring(0,index);
                int nbrRune = Integer.parseInt(tmp.substring(index+1,tmp.length()));

                rune.setType(type);
                rune.setNbrRune(nbrRune);
                lstRunes.add(rune);
            }
        }
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

    public List<Rune> getLstRunes() {
        return lstRunes;
    }

    public void setLstRunes(List<Rune> lstRunes) {
        this.lstRunes = lstRunes;
    }
}
