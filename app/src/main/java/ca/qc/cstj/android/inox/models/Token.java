package ca.qc.cstj.android.inox.models;

/**
 * Created by Mathieu on 2014-12-19.
 */
public class Token {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Token.token = token;
    }
}
