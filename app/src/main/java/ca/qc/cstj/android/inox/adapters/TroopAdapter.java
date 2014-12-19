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

import ca.qc.cstj.android.inox.models.Troop;
import ca.qc.cstj.android.inox.R;


/**
 * Created by 1159099 on 2014-11-10.
 */
public class TroopAdapter extends ArrayAdapter<Troop> {

    private LayoutInflater layoutInflater;
    private ArrayList<Troop> troops;

    public TroopAdapter(Context context, LayoutInflater layoutInflater,  ArrayList<Troop> listeTroops) {
        super(context, R.layout.row_troop,listeTroops);
        this.troops = listeTroops;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TroopViewHolder troopViewHolder;

        //Est-ce que la view n'existe pas
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_troop,null);
            troopViewHolder = new TroopViewHolder();
            troopViewHolder.ImgTroop = (ImageView) convertView.findViewById(R.id.row_ImgTroop);
            troopViewHolder.name = (TextView) convertView.findViewById(R.id.row_troop);

            convertView.setTag(troopViewHolder);
        } else {
            troopViewHolder = (TroopViewHolder) convertView.getTag();
        }

        Troop troop = getItem(position);

        troopViewHolder.name.setText(troop.getName());

        //ION
        Ion.with(troopViewHolder.ImgTroop)
                .placeholder(R.drawable.spinner_white_76)
                .error(R.drawable.error_48)
                .load(troop.getImageUrl());


        return convertView;

    }

    private static class TroopViewHolder {
        public ImageView ImgTroop;
        public TextView name;
    }
}


