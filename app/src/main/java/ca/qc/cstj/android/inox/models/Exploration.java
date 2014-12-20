package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import ca.qc.cstj.android.inox.helpers.DateParser;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class Exploration {
    private String href;
    private String locationDepart;
    private String locationArriver;
    private DateTime dateExploration;

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
            if(location.has("Fin"))
            {
                locationArriver = location.getAsJsonPrimitive("Fin").getAsString();
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
}
