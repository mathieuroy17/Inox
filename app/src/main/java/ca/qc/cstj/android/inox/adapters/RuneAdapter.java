package ca.qc.cstj.android.inox.adapters;

import android.content.Context;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.models.Rune;

/**
 * Created by 1159099 on 2014-11-07.
 */
public class RuneAdapter  extends GenericArrayAdapter<Rune> {

    public RuneAdapter(Context context, int layoutRes, ArrayList<Rune> runes) {
        super(context,layoutRes,runes);
    }



    @Override
    public void update(TextView textView, Rune object) {
        String rune = object.getType();
        textView.setText(rune);

    }



}