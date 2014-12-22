package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class Troop {

    private String href;
    private String imageUrl;
    private String name;
    private String uuid;
    private ArrayList<Rune> kernel;
    private int attack;
    private int defense;
    private int speed;

    public Troop(JsonObject jsonObject) {
        if(jsonObject.has("href")) {
            href = jsonObject.getAsJsonPrimitive("href").getAsString();
        }
        if(jsonObject.has("imageUrl")) {
            imageUrl = jsonObject.getAsJsonPrimitive("imageUrl").getAsString();
        }
        if(jsonObject.has("name")) {
            name = jsonObject.getAsJsonPrimitive("name").getAsString();
        }
        if(jsonObject.has("attack")) {
            attack = jsonObject.getAsJsonPrimitive("attack").getAsInt();
        }
        if(jsonObject.has("defense")) {
            defense = jsonObject.getAsJsonPrimitive("defense").getAsInt();
        }
        if(jsonObject.has("speed")) {
            speed = jsonObject.getAsJsonPrimitive("speed").getAsInt();
        }
        if(jsonObject.has("uuid"))
        {
            uuid = jsonObject.getAsJsonPrimitive("uuid").getAsString();
        }
        if(jsonObject.has("kernel"))
        {
            JsonObject JsonObject = jsonObject.getAsJsonObject("kernel");
            kernel=Fonction.FormatRune(JsonObject);
        }
    }
    public Troop()
    {

    }

    public JsonObject getTroopAsJson()
    {
        JsonObject jsonObject= new JsonObject();
        //vérifie si la classe est vide
        if(name != null) {
            jsonObject.addProperty("name",name);
            jsonObject.addProperty("attack",attack);
            jsonObject.addProperty("defense",defense);
            jsonObject.addProperty("speed",speed);
            jsonObject.addProperty("imageUrl",imageUrl);
            jsonObject.addProperty("uuid",uuid);
        }
        return jsonObject;

    }
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public ArrayList<Rune> getKernel() {
        return kernel;
    }

    public void setKernel(ArrayList<Rune> kernel) {
        this.kernel = kernel;
    }
}
