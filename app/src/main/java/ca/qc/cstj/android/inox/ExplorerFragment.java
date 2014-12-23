package ca.qc.cstj.android.inox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.util.ArrayList;

import ca.qc.cstj.android.inox.models.Exploration;
import ca.qc.cstj.android.inox.models.Fonction;
import ca.qc.cstj.android.inox.models.Rune;
import ca.qc.cstj.android.inox.models.Troop;
import ca.qc.cstj.android.inox.models.UtilisateurConnecter;
import ca.qc.cstj.android.inox.services.ServicesURI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExplorerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExplorerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ExplorerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Cle";

    // TODO: Rename and change types of parameters
    private String cle;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cle Parameter 1.
     * @return A new instance of fragment ExplorerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplorerFragment newInstance(String cle) {
        ExplorerFragment fragment = new ExplorerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, cle);
        fragment.setArguments(args);
        return fragment;
    }
    public ExplorerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cle = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explorer,container, false);



    }

    @Override
    public void onStart() {
        super.onStart();

        loadExplorer();

    }


    private void loadExplorer()
    {

        StringBuilder href = new StringBuilder();
        href.append(ServicesURI.PORTAL_SERVICE_URI).append(cle);

        //pour l'Exploration du portal
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Exploration en cours");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //pour les runes
        final ProgressDialog progressDialogRune;
        progressDialogRune = new ProgressDialog(getActivity());
        progressDialogRune.setMessage("Chargement des runes en cours");
        progressDialogRune.setIndeterminate(true);
        progressDialogRune.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //Pour aller à la liste d'exploration après
        final FragmentManager fragmentManager = getFragmentManager();



        Ion.with(getActivity())
                .load(href.toString())
                .progressDialog(progressDialog)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> Response) {

                        if (Response.getHeaders().getResponseCode() == HttpStatus.SC_OK)
                        {
                            final Exploration exploration = new Exploration(Response.getResult());

                            TextView rune = (TextView) getActivity().findViewById(R.id.runeRecu);
                            StringBuilder SBRune = new StringBuilder();
                            if(exploration.getLstRunes().size()>0) {
                                SBRune.append("rune recu\n").append(Fonction.RuneToString(exploration.getLstRunes()));
                            }
                            else
                            {
                                SBRune.append("aucune rune n'ont été trouvé");
                            }

                            rune.setText(SBRune.toString());

                            if ((exploration.getTroop()).getName() != null) {
                                StringBuilder hrefRune = new StringBuilder();
                                hrefRune.append(ServicesURI.RUNES_SERVICE_URI).append("?token=").append(UtilisateurConnecter.getToken());

                                Ion.with(getActivity())
                                        .load(hrefRune.toString())
                                        .progressDialog(progressDialogRune)
                                        .asJsonObject()
                                        .withResponse()
                                        .setCallback(new FutureCallback<Response<JsonObject>>() {
                                            @Override
                                            public void onCompleted(Exception e, Response<JsonObject> response) {

                                                if (response.getHeaders().getResponseCode() == HttpStatus.SC_OK) {
                                                    JsonObject JsonObject = response.getResult();
                                                    boolean assezDeRune;

                                                    ArrayList<Rune> runes = Fonction.FormatRune(JsonObject);
                                                    assezDeRune = Fonction.EnoughRune((exploration.getTroop().getKernel()), runes);

                                                    if (assezDeRune == true)
                                                    {
                                                        //donner choix acheter troop
                                                        TextView troop = (TextView) getActivity().findViewById(R.id.TroopTrouver);
                                                        TextView prix = (TextView) getActivity().findViewById(R.id.prix);

                                                        StringBuilder SBTroop= new StringBuilder();
                                                        SBTroop.append(exploration.getTroop().getName()).append("\n attack: ").append(exploration.getTroop().getAttack()).append("\n defense :")
                                                                .append(exploration.getTroop().getDefense()).append("\n speed :").append(exploration.getTroop().getSpeed());

                                                        StringBuilder SBCout = new StringBuilder();
                                                        SBCout.append("Prix :").append(Fonction.RuneToString(exploration.getTroop().getKernel()));



                                                        troop.setText(SBTroop.toString());
                                                        prix.setText(SBCout.toString());

                                                        final Button Add = (Button)getActivity().findViewById(R.id.buttonAjouter);
                                                        Add.setOnClickListener(new View.OnClickListener() {
                                                            public void onClick(View v) {
                                                                exploration.setLstRunes(Fonction.PayRune(exploration.getTroop().getKernel(), exploration.getLstRunes()));
                                                                Fonction.addExploration(getActivity(), exploration, fragmentManager);
                                                            }
                                                        });
                                                        final Button dontAdd = (Button)getActivity().findViewById(R.id.buttonNePasAjouter);
                                                        dontAdd.setOnClickListener(new View.OnClickListener() {
                                                            public void onClick(View v) {
                                                                Troop troop = new Troop();
                                                                exploration.setTroop(troop);
                                                                Fonction.addExploration(getActivity(), exploration, fragmentManager);
                                                            }
                                                        });


                                                    }
                                                    else {
                                                        Troop troop = new Troop();
                                                        exploration.setTroop(troop);
                                                        final Button Add = (Button)getActivity().findViewById(R.id.buttonAjouter);
                                                        Add.setOnClickListener(new View.OnClickListener() {
                                                            public void onClick(View v) {
                                                                // 1. Instantiate an AlertDialog.Builder with its constructor
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                                                // 2. Chain together various setter methods to set the dialog characteristics
                                                                builder.setMessage(R.string.dialog_message)
                                                                        .setTitle(R.string.dialog_title);

                                                                // 3. Get the AlertDialog from create()
                                                                AlertDialog dialog = builder.create();
                                                            }
                                                        });
                                                        final Button dontAdd = (Button)getActivity().findViewById(R.id.buttonNePasAjouter);
                                                        dontAdd.setOnClickListener(new View.OnClickListener() {
                                                            public void onClick(View v) {
                                                                Fonction.addExploration(getActivity(), exploration, fragmentManager);
                                                                return;
                                                            }
                                                        });
                                                    }

                                                } else {
                                                    //erreur get runes
                                                    JsonObject JsonObject = response.getResult();
                                                    Fonction.AffichageErreur(getActivity(),JsonObject);
                                                }
                                            }
                                        });
                            } else {
                                TextView troop = (TextView) getActivity().findViewById(R.id.TroopTrouver);
                                TextView prix = (TextView) getActivity().findViewById(R.id.prix);

                                StringBuilder SBTroop= new StringBuilder();
                                SBTroop.append("Aucune troop n'a été trouvé");

                                StringBuilder SBCout = new StringBuilder();
                                SBCout.append("Cout de 0 cliquer sur ajouter pour continuer");



                                troop.setText(SBTroop.toString());
                                prix.setText(SBCout.toString());

                                final Button Add = (Button)getActivity().findViewById(R.id.buttonAjouter);
                                Add.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Fonction.addExploration(getActivity(), exploration, fragmentManager);
                                    }
                                });

                                final Button dontAdd = (Button)getActivity().findViewById(R.id.buttonNePasAjouter);
                                dontAdd.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Fonction.addExploration(getActivity(), exploration, fragmentManager);
                                    }
                                });

                            }

                        } else {
                            //erreur explorer
                            JsonObject JsonObject = Response.getResult();
                            Fonction.AffichageErreur(getActivity(),JsonObject);
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
