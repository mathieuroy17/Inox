package ca.qc.cstj.android.inox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.R;

/**
 * Created by Ycharron on 2014-10-24.
 */
public abstract class GenericArrayAdapter<T> extends ArrayAdapter<T> {

    private LayoutInflater mInflater;
    private int mLayoutRes;

    public GenericArrayAdapter(Context context, int layoutRes, ArrayList<T> liste) {
        super(context,layoutRes,liste);
        mLayoutRes = layoutRes;
        init(context);
    }



    public abstract void update(TextView textView, T object);

    private void init(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GenericViewHolder viewHolder;

        if(convertView == null) {

            convertView = mInflater.inflate(mLayoutRes,parent,false);
            viewHolder = new GenericViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (GenericViewHolder) convertView.getTag();
        }

        update(viewHolder.textView,getItem(position));

        return convertView;

    }

    private static class GenericViewHolder {
        private TextView textView;

        private GenericViewHolder(View rootView) {
            textView = (TextView) rootView.findViewById(android.R.id.text1);
        }
    }



}
