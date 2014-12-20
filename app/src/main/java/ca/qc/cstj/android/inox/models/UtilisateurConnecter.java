package ca.qc.cstj.android.inox.models;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class UtilisateurConnecter {
    private static String token;
    private static String nom;
    private static int expiration;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UtilisateurConnecter.token = token;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        UtilisateurConnecter.nom = nom;
    }

    public static int getExpiration() {
        return expiration;
    }

    public static void setExpiration(int expiration) {
        UtilisateurConnecter.expiration = expiration;
    }
}
