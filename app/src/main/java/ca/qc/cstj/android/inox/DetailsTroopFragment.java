package ca.qc.cstj.android.inox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import ca.qc.cstj.android.inox.models.Troop;
import ca.qc.cstj.android.inox.models.UtilisateurConnecter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsTroopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsTroopFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DetailsTroopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Troop_href";

    // TODO: Rename and change types of parameters
    private String mHref;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param href Parameter 1.
     * @return A new instance of fragment DetailsTroopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsTroopFragment newInstance(String href) {
        DetailsTroopFragment fragment = new DetailsTroopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, href);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailsTroopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHref = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viewlive =  inflater.inflate(R.layout.fragment_details_troop,container, false);
        loadTroop(viewlive);

        return  viewlive;
    }

    private void loadTroop(final View viewlive) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Chargement d'une troop");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        StringBuilder href = new StringBuilder();
        href.append(mHref).append("?token=").append(UtilisateurConnecter.getToken());

        Ion.with(getActivity())
                .load(href.toString())
                .progressDialog(progressDialog)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> Response) {

                        if (Response.getHeaders().getResponseCode() == HttpStatus.SC_OK) {
                            Troop troop = new Troop(Response.getResult());

                            ImageView imgTroop = (ImageView) viewlive.findViewById(R.id.imgTroop);

                            //ION
                            Ion.with(imgTroop)
                                    .placeholder(R.drawable.spinner_white_76)
                                    .error(R.drawable.error_48)
                                    .load(troop.getImageUrl());

                            TextView name = (TextView) viewlive.findViewById(R.id.rows_nom);
                            name.setText(troop.getName());

                            StringBuilder SB = new StringBuilder();

                            SB.append("Attaque :").append(troop.getAttack()).append("\n").append("Defense :").append(troop.getDefense())
                               .append("\n").append("Vitesse :").append(troop.getSpeed());

                            TextView statistique = (TextView) viewlive.findViewById(R.id.rows_stat);
                            statistique.setText(SB.toString());

                        }
                        else
                        {

                        }
                    }
                });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
