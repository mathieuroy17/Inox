package ca.qc.cstj.android.inox;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cstj.android.inox.adapters.RuneAdapter;
import ca.qc.cstj.android.inox.models.Rune;
import ca.qc.cstj.android.inox.services.ServicesURI;

    import ca.qc.cstj.android.inox.adapters.RuneAdapter;


public class RuneFragment extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "section_number";
        private ListView lstRune;
        private ProgressDialog progressDialog;
        private RuneAdapter runeAdapter;
        // TODO: Rename and change types of parameters
        private String mParam1;


        private OnFragmentInteractionListener mListener;

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param position Parameter 1.
         *
         * @return A new instance of fragment FilmFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static RuneFragment newInstance(int position) {
            RuneFragment fragment = new RuneFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_PARAM1, position);
            fragment.setArguments(args);
            return fragment;
        }
        public RuneFragment() {
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
            return inflater.inflate(R.layout.fragment_runes, container, false);
        }

        @Override
        public void onStart() {
            super.onStart();

            lstRune = (ListView) getActivity().findViewById(R.id.list_runes);
            loadRunes();

            lstRune.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String href = runeAdapter.getItem(position).getHref();


                }
            });



        }

        private void loadRunes() {

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("En Chargement");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            Ion.with(getActivity())
                    .load(ServicesURI.RUNES_SERVICE_URI)
                    .progressDialog(progressDialog)
                    .asJsonArray()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonArray>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonArray> response) {

                            if(response.getHeaders().getResponseCode() == HttpStatus.SC_OK)
                            {
                                JsonArray jsonArray = response.getResult();
                                ArrayList<Rune> runes = new ArrayList<Rune>();

                                for(JsonElement element : jsonArray)
                                {
                                    runes.add(new Rune(element.getAsJsonObject()));
                                }

                                runeAdapter = new RuneAdapter(getActivity(),android.R.layout.simple_list_item_1,runes);
                                lstRune.setAdapter(runeAdapter);
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


