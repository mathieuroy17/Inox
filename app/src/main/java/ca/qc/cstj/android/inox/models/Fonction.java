package ca.qc.cstj.android.inox.models;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.ExplorationFragment;
import ca.qc.cstj.android.inox.R;
import ca.qc.cstj.android.inox.services.ServicesURI;

/**
 * Created by Mathieu on 2014-12-22.
 */
public class Fonction
{
   public static ArrayList<Rune> FormatRune(JsonObject jsonObject)
   {
       ArrayList<Rune> runes = new ArrayList<Rune>();

       int size = jsonObject.entrySet().size();
       Object object[] = jsonObject.entrySet().toArray();

       for (int i = 0; i < size; i++) {
           Rune rune = new Rune();
           String tmp = object[i].toString();
           int index = tmp.indexOf("=");
           String type = tmp.substring(0, index);
           int nbrRune = Integer.parseInt(tmp.substring(index + 1, tmp.length()));

           rune.setType(type);
           rune.setNbrRune(nbrRune);
           runes.add(rune);

       }
       return runes;
   }

   public static boolean EnoughRune(ArrayList<Rune> kernel,ArrayList<Rune> MesRunes)
   {
       boolean assezDeRune = true;

       for(Rune element : kernel)
       {
              for(Rune rune : MesRunes)
              {
                if(rune.getType() == element.getType())
                {
                    if(rune.getNbrRune() < element.getNbrRune())
                    {
                        assezDeRune = false;
                    }
                }
              }
       }
       return  assezDeRune;
   }
    public static StringBuilder RuneToString(ArrayList<Rune> runes)
    {
        StringBuilder SB = new StringBuilder();

        for(Rune element : runes)
        {
            SB.append(element.getType()).append(":").append(element.getNbrRune()).append("\n");
        }
        return SB;
    }

   public static void addExploration(Context Activity,Exploration exploration,final FragmentManager fragmentManager)
   {
       ProgressDialog progressDialog = new ProgressDialog(Activity);
       progressDialog.setMessage("Connexion en cours");
       progressDialog.setIndeterminate(true);
       progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

       JsonObject jsonObject = exploration.getExplorationAsJson();

       StringBuilder href= new StringBuilder();
       href.append(ServicesURI.EXPLORATIONS_SERVICE_URI).append("?token=").append(UtilisateurConnecter.getToken());

       Ion.with(Activity)
               .load(href.toString())
               .progressDialog(progressDialog)
               .setHeader("Content-Type", "application/json")
               .setJsonObjectBody(jsonObject)
               .asJsonObject()
               .withResponse()
               .setCallback(new FutureCallback<Response<JsonObject>>() {
                   @Override
                   public void onCompleted(Exception e, Response<JsonObject> Response) {

                       if (Response.getHeaders().getResponseCode() == HttpStatus.SC_CREATED) {

                           fragmentManager.beginTransaction()
                                   .replace(R.id.container, ExplorationFragment.newInstance(0))
                                   .commit();
                       } else {
                           //erreur
                       }

                   }
               });
   }
}
