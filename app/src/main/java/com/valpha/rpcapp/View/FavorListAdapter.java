package com.valpha.rpcapp.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valpha.rpcapp.Controller.TunerController;
import com.valpha.rpcapp.R;

/**
 * @author Valpha
 */
public class FavorListAdapter extends RecyclerView.Adapter<FavorListAdapter.VH> {
    private static final String TAG = FavorListAdapter.class.getSimpleName();
    private final TunerController mController;

    public FavorListAdapter(TunerController controller) {
        this.mController = controller;
        mController.addAdapter(this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorlist_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        final String data = mController.getFavorList().get(position);
        holder.tvFreq.setText(data.substring(0, data.length() - 1).concat(".").concat(String.valueOf(data.charAt(data.length() - 1))));
        holder.btFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.getFmSeeker().smoothScrollToPosition(Integer.valueOf(data)-875);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mController.getFavorList().size();
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
