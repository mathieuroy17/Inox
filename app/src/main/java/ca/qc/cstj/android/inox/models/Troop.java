package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class Troop {

    private String href;
    private String imageUrl;
    private String name;
    private String uuid;
    private List<Rune> kernel;
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

            int size = JsonObject.entrySet().size();
            Object object[] = JsonObject.entrySet().toArray();

            for (int i = 0; i < size; i++)
            {
                Rune rune = new Rune();
                String tmp = object[i].toString();
                int index = tmp.indexOf("=");
                String type = tmp.substring(0, index);
                int nbrRune = Integer.parseInt(tmp.substring(index + 1, tmp.length()));

                rune.setType(type);
                rune.setNbrRune(nbrRune);
                kernel.add(rune);
            }
        }
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

    public List<Rune> getKernel() {
        return kernel;
    }

    public void setKernel(List<Rune> kernel) {
        this.kernel = kernel;
    }
}
