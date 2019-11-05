package com.valpha.rpcapp.View;

import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valpha.rpcapp.Contract.SharePrefs;
import com.valpha.rpcapp.Controller.TunerController;
import com.valpha.rpcapp.R;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.SharedPrefs;

import java.util.List;
import java.util.Set;

public class FmseekerAdapter extends RecyclerView.Adapter<FmseekerAdapter.VH> {

    private final TunerController mController;

    public FmseekerAdapter(TunerController controller) {
        this.mController = controller;
        mController.addAdapter(this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scale_layout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        List<String> set = mController.getFavorList();
        int freq = position + 875;
        String freqStr = String.valueOf(freq);
        if (set.contains(freqStr)) {
            holder.ivScale.setBlinking(true);
        } else {
            holder.ivScale.setBlinking(false);
        }
        if (freq % 5 == 0) {
            if (freq % 10 == 0) {
                holder.tvNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setText(String.valueOf(freq / 10));
                holder.ivScale.setScaleY(1.0f);
            } else {
                holder.tvNumber.setVisibility(View.INVISIBLE);
                holder.ivScale.setScaleY(0.8f);
            }
        } else {
            holder.tvNumber.setVisibility(View.INVISIBLE);
            holder.ivScale.setScaleY(0.7f);
        }
    }

    @Override
    public int getItemCount() {
        return 205;
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView2State ivScale;
        TextView tvNumber;

        VH(@NonNull View itemView) {
            super(itemView);
            ivScale = itemView.findViewById(R.id.iv_scale);
            tvNumber = itemView.findViewById(R.id.tv_scale_number);
        }
    }
}
