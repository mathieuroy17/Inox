package ca.qc.cstj.android.inox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.adapters.RuneAdapter;
import ca.qc.cstj.android.inox.adapters.TroopAdapter;
import ca.qc.cstj.android.inox.models.Rune;
import ca.qc.cstj.android.inox.models.Troop;
import ca.qc.cstj.android.inox.services.ServicesURI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TroopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TroopFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TroopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "section_number";
    private ListView lstTroop;
    private ProgressDialog progressDialog;
    private TroopAdapter troopAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment TroopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TroopFragment newInstance(int position) {
        TroopFragment fragment = new TroopFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }
    public TroopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_troop, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        lstTroop= (ListView) getActivity().findViewById(R.id.list_troop);
        loadTroops();
    }

    private void loadTroops() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("En Chargement");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        String tmp = new String();
        tmp = ServicesURI.TROOPS_SERVICE_URI+"?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXRoIiwiZXhwaXJlcyI6MTQxOTYzMjQ5NDMwNH0.UdvkJ1V-IfPvf7-oMVMSGJoSW49o1qiM6XF7wSBYRU4";

        Ion.with(getActivity())
                .load(tmp)
                .progressDialog(progressDialog)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> response) {

                        if(response.getHeaders().getResponseCode() == HttpStatus.SC_OK)
                        {
                            JsonArray jsonArray= response.getResult();
                            ArrayList<Troop> troops = new ArrayList<Troop>();

                            for (JsonElement element : jsonArray) {

                                troops.add(new Troop(element.getAsJsonObject()));
                            }

                            troopAdapter = new TroopAdapter(getActivity(),getActivity().getLayoutInflater(),troops);
                            lstTroop.setAdapter(troopAdapter);
                        }
                        else{
                            //erreur 404
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
