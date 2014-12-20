package ca.qc.cstj.android.inox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.models.Rune;
import ca.qc.cstj.android.inox.R;
import ca.qc.cstj.android.inox.services.ServicesURI;


/**
 * Created by 1159099 on 2014-11-10.
 */
public class RuneAdapter extends ArrayAdapter<Rune> {

    private LayoutInflater layoutInflater;
    private ArrayList<Rune> rune;

    public RuneAdapter(Context context, LayoutInflater layoutInflater,  ArrayList<Rune> listeRunes) {
        super(context, R.layout.row_rune,listeRunes);
        this.rune = listeRunes;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RuneViewHolder runeViewHolder;

        //Est-ce que la view n'existe pas
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_rune,null);
            runeViewHolder = new RuneViewHolder();
            runeViewHolder.ImgRune = (ImageView) convertView.findViewById(R.id.row_ImgRune);
            runeViewHolder.rune = (TextView) convertView.findViewById(R.id.row_rune);

            convertView.setTag(runeViewHolder);
        } else {
            runeViewHolder = (RuneViewHolder) convertView.getTag();
        }

        Rune rune = getItem(position);

        StringBuilder SB = new StringBuilder();
        runeViewHolder.rune.setText(SB.append(rune.getType()).append(": ").append(rune.getNbrRune()));

        StringBuilder image = new StringBuilder();
        //ION
        Ion.with(runeViewHolder.ImgRune)
                .placeholder(R.drawable.spinner_white_76)
                .error(R.drawable.error_48)
                .load(image.append(ServicesURI.IMAGE_SERVICE_URI).append(rune.getType()).append(".png").toString());


        return convertView;
    }

    private static class RuneViewHolder {
        public ImageView ImgRune;
        public TextView rune;
    }
}


