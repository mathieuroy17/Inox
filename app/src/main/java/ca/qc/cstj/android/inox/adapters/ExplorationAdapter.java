package ca.qc.cstj.android.inox.adapters;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.models.Exploration;

/**
 * Created by 1159099 on 2014-11-07.
 */
public class ExplorationAdapter  extends GenericArrayAdapter<Exploration> {

    public ExplorationAdapter(Context context, int layoutRes, ArrayList<Exploration> Explorations) {
        super(context,layoutRes,Explorations);
    }



    @Override
    public void update(TextView textView, Exploration object) {
        String Arriver = object.getLocationArriver();
        textView.setText(Arriver);

    }



}