package com.valpha.rpcapp.Fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.valpha.rpcapp.Contract.RpcApp;
import com.valpha.rpcapp.Contract.RpcApp.Communication;
import com.valpha.rpcapp.Contract.SharePrefs;
import com.valpha.rpcapp.R;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.SharedPrefs;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RpcAppFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RpcAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RpcAppFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mRpcComMode;

    private OnFragmentInteractionListener mListener;
    private Communication.Uart mUartSettings;
    private View spiLayout;
    private View uartLayout;
    private View canLayout;
    private SharedPrefs sharedPrefs;

    public RpcAppFragment() {
        // Required empty public constructor
    }


    public static RpcAppFragment newInstance(String comMode) {
        RpcAppFragment fragment = new RpcAppFragment();
        Bundle args = new Bundle();

        args.putString(Communication.MODE, comMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = AnyPref.getPrefs(SharePrefs.RPC_APP_STATE);


        if (getArguments() != null) {
            mRpcComMode = sharedPrefs.getString(Communication.MODE, "");
            if (mRpcComMode.equals("")) {
                mRpcComMode = getArguments().getString(Communication.MODE);
            }
            switch (mRpcComMode) {
                case Communication.MODE_UART:
                    mUartSettings = AnyPref.get(Communication.Uart.class, Communication.MODE_UART);
                    break;
                case Communication.MODE_SPI:
                    //TODO:补充SPI
                    break;
                case Communication.MODE_CAN:
                    //TODO: 补充CAN
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rpc_app, container, false);
        uartLayout = view.findViewById(R.id.layout_uart);
        spiLayout = view.findViewById(R.id.layout_spi);
        canLayout = view.findViewById(R.id.layout_can);
        Spinner spnRpcMode = view.findViewById(R.id.spn_rpc_mode);
        spnRpcMode.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_rpc_mode, R.id.tv_item_rpc_mode, Communication.MODE_LIST));
        switch (mRpcComMode) {
            case Communication.MODE_UART:
                uartLayout.setVisibility(View.VISIBLE);
                spiLayout.setVisibility(View.GONE);
                canLayout.setVisibility(View.GONE);
                createUartView(uartLayout);
                break;
            case Communication.MODE_SPI:
                //TODO:补充SPI
                break;
            case Communication.MODE_CAN:
                //TODO: 补充CAN
                break;
            default:
                break;
        }
        return view;
    }

    private void createUartView(View view) {
        Spinner spnUartBaudrate = (Spinner) view.findViewById(R.id.spn_baudrate);
        spnUartBaudrate.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.item_rpc_spn,
                R.id.tv_item_spn_default, Communication.Default.Uart.BAUDRATE_LIST));

        Spinner spnUartdatabits = (Spinner) view.findViewById(R.id.spn_databits);
        spnUartdatabits.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.item_rpc_spn,
                R.id.tv_item_spn_default, Communication.Default.Uart.DATABITS_LIST));
        Spinner spnUartstopbits = (Spinner) view.findViewById(R.id.spn_stopbits);
        spnUartstopbits.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.item_rpc_spn,
                R.id.tv_item_spn_default, Communication.Default.Uart.STOPBIT_LIST));
        Spinner spnUartparity = (Spinner) view.findViewById(R.id.spn_parity);
        spnUartparity.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.item_rpc_spn,
                R.id.tv_item_spn_default, Communication.Default.Uart.PARITY_LIST));


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
        sharedPrefs.putString(Communication.MODE, mRpcComMode);
        sharedPrefs.commit();

        switch (mRpcComMode) {
            case Communication.MODE_UART:
                AnyPref.put(mUartSettings,Communication.MODE_UART);
                break;
            case Communication.MODE_SPI:
                //TODO:补充SPI
                break;
            case Communication.MODE_CAN:
                //TODO: 补充CAN
                break;
            default:
                break;
        }


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
