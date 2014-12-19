package ca.qc.cstj.android.inox.models;

import com.google.gson.JsonObject;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class Explorateur {
    private String nom;
    private String utilisateur;
    private String password;

    public Explorateur(JsonObject jsonObject) {
        if(jsonObject.has("nom")) {
            nom = jsonObject.getAsJsonPrimitive("nom").getAsString();
        }
        if(jsonObject.has("utilisateur")) {
            utilisateur = jsonObject.getAsJsonPrimitive("utilisateur").getAsString();
        }
        if(jsonObject.has("password")) {
            password = jsonObject.getAsJsonPrimitive("password").getAsString();
        }

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
