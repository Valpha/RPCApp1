package com.valpha.rpcapp.Activity;

import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valpha.rpcapp.R;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.SharedPrefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

class FavorListAdapter extends RecyclerView.Adapter<FavorListAdapter.VH> {
    private static final String TAG = FavorListAdapter.class.getSimpleName();
    List<String> mDatas = new ArrayList<>();

    FavorListAdapter() {
        getFavorList();
    }

    void getFavorList() {
        mDatas.clear();
        SharedPrefs prefs = AnyPref.getPrefs("FavorList");
        Set<String> set = prefs.getStringSet("favors", new ArraySet<String>());
        mDatas.addAll(set);
        Collections.sort(mDatas);
        Log.d(TAG, "onCreate: datas size is " + mDatas.size());
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorlist_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String data = mDatas.get(position);
        holder.tvFreq.setText(data.substring(0, data.length() - 1).concat(".").concat(String.valueOf(data.charAt(data.length() - 1))));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvFreq;
        ImageButton btFavor;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvFreq = (TextView) itemView.findViewById(R.id.tv_frequency);
            btFavor = (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }
}
