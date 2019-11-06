package com.valpha.rpcapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valpha.rpcapp.Contract.RpcApp;
import com.valpha.rpcapp.Contract.SharePrefs;
import com.valpha.rpcapp.R;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RpcAppFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RpcAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RpcAppFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mRpcComMode;

    private OnFragmentInteractionListener mListener;

    public RpcAppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RpcAppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RpcAppFragment newInstance(String comMode) {
        RpcAppFragment fragment = new RpcAppFragment();
        Bundle args = new Bundle();
        args.putString(RpcApp.Communication.MODE, comMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRpcComMode = getArguments().getString(RpcApp.Communication.MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rpc_app, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPrefs sharedPrefs = AnyPref.getPrefs(SharePrefs.RPC_APP_STATE);
        sharedPrefs.putString(RpcApp.Communication.MODE, mRpcComMode);
        sharedPrefs.commit();

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
        void onFragmentInteraction(Uri uri);
    }
}
