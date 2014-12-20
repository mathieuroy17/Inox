package ca.qc.cstj.android.inox;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import ca.qc.cstj.android.inox.models.UtilisateurConnecter;
import ca.qc.cstj.android.inox.services.ServicesURI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConnexionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConnexionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ConnexionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "section_number";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment ConnexionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnexionFragment newInstance(int position) {
        ConnexionFragment fragment = new ConnexionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }
    public ConnexionFragment() {
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
        return inflater.inflate(R.layout.fragment_connexion, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        final Button creation = (Button)getActivity().findViewById(R.id.buttonCréationDeCompte);
        creation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, CreationDeCompteFragment.newInstance(0))
                        .commit();

            }
        });

        final Button connexion = (Button)getActivity().findViewById(R.id.buttonConnexion);
        connexion.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Connexion en cours");
                progressDialog.setIndeterminate(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                //Trouve les informations
                final EditText editText = (EditText)getActivity().findViewById(R.id.utilisateur);
                final String nom = editText.getText().toString();

                final EditText textPseudo = (EditText)getActivity().findViewById(R.id.motDePasse);
                String password = textPseudo.getText().toString();

                StringBuilder href = new StringBuilder();
                href.append(ServicesURI.EXPLORATEURS_SERVICE_URI).append("?utilisateur=").append(nom).append("&password=").append(password);

                Ion.with(getActivity())
                        .load(href.toString())
                        .progressDialog(progressDialog)
                        .asJsonObject()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<JsonObject>>()
                        {
                            @Override
                            public void onCompleted(Exception e, Response<JsonObject> Response)
                            {

                                if (Response.getHeaders().getResponseCode() == HttpStatus.SC_OK) {
                                    JsonObject jsonObject=Response.getResult();

                                    UtilisateurConnecter.setToken(jsonObject.getAsJsonPrimitive("token").getAsString());
                                    UtilisateurConnecter.setNom(jsonObject.getAsJsonPrimitive("user").getAsString());
                                    UtilisateurConnecter.setExpiration(jsonObject.getAsJsonPrimitive("expires").getAsInt());

                                    FragmentManager fragmentManager = getFragmentManager();

                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, RuneFragment.newInstance(0))
                                            .commit();
                                }

                            }
                        });
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
