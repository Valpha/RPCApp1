package com.valpha.rpcapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valpha.rpcapp.Controller.TunerController;
import com.valpha.rpcapp.R;
import com.valpha.rpcapp.View.FavorListAdapter;
import com.valpha.rpcapp.View.FmseekerAdapter;
import com.valpha.rpcapp.View.ImageView2State;
import com.valpha.rpcapp.View.RecyclerViewClickListener2;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import net.nashlegend.anypref.AnyPref;

public class TunerActivity extends AppCompatActivity {

    private static final String TAG = "TunerActivity";
    private TunerController mController;
    private TextView tvCurrentFrequency;
    private ImageButton btPrev;
    private ImageButton btnext;
    private ImageView2State btfavor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AnyPref.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);
        mController = TunerController.getInstance();

        RecyclerView favorList = findViewById(R.id.rv_favorlist);
        favorList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        favorList.setAdapter(new FavorListAdapter(mController));

        final DiscreteScrollView seeker = findViewById(R.id.seeker);
        mController.bindFmSeeker(seeker);
        seeker.setAdapter(new FmseekerAdapter(mController));
        tvCurrentFrequency = findViewById(R.id.tv_curfreq);

        final DiscreteScrollView.OnItemChangedListener<FmseekerAdapter.VH> listener;
        listener = new DiscreteScrollView.OnItemChangedListener<FmseekerAdapter.VH>() {
            @Override
            public void onCurrentItemChanged(@Nullable FmseekerAdapter.VH vh, int i) {
                int freq = i + 875;
                mController.setFreq(freq);
                String text = "当前频率为" + freq / 10.0 + "MHz";
                tvCurrentFrequency.setText(text);
                btfavor.setBlinking(mController.isFavorate(freq));
                Log.d(TAG, "onCurrentItemChanged: " + freq);
            }
        };
        seeker.addOnItemChangedListener(listener);


        final RecyclerViewClickListener2 seekerPressListener = new RecyclerViewClickListener2(this, seeker,
                new RecyclerViewClickListener2.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView recyclerView, View view, int position) {
                        int freq = position + 875;
                        mController.setFreq(freq);
                        Log.d(TAG, "onItemClick: freq is " + freq);
                        recyclerView.smoothScrollToPosition(position);
                    }

                    @Override
                    public void onItemLongClick(RecyclerView recyclerView, View view, int position) {
                        int freq = position + 875;
                        Log.d(TAG, "onItemLongClick: freq is " + freq);
                        ImageView2State ivScale = view.findViewById(R.id.iv_scale);
                        boolean isBlinking = ivScale.ismIsBlinking();
                        Log.d(TAG, "onItemLongClick: activityFlag is " + isBlinking + " now");
                        ivScale.setBlinking(!isBlinking);
                        mController.updateFavorList(freq);
                        mController.notifyAdapters();
                    }
                });
        seeker.addOnItemTouchListener(seekerPressListener);

        seeker.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        recyclerView.addOnItemTouchListener(seekerPressListener);
                        break;
                    default:
                        recyclerView.removeOnItemTouchListener(seekerPressListener);
                        break;
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        btPrev = findViewById(R.id.bt_prev);
        btnext = findViewById(R.id.bt_next);
        btfavor = findViewById(R.id.bt_favor);

        btfavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isBlinking = ((ImageView2State) v).ismIsBlinking();
                ((ImageView2State) v).setBlinking(!isBlinking);
                int freq = mController.getFreq();
                mController.updateFavorList(freq);
                mController.notifyAdapters();
            }
        });

        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextFreq = mController.getNextFreq();
                Log.d("ButtonTAG", "onClick: nextFreq = " + nextFreq);
                if (nextFreq == -1) {
                    return;
                }
                if (nextFreq == 0) {
                    int firstFreq = mController.getFirstFavor();
                    Log.d("ButtonTAG", "onClick: firstFreq =" + firstFreq);
                    if (firstFreq == 0) {
                        return;
                    } else {

                        mController.getFmSeeker().smoothScrollToPosition(firstFreq - 875);
                    }
                } else {
                    mController.getFmSeeker().smoothScrollToPosition(nextFreq - 875);
                }

            }
        });

        btPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevFreq = mController.getPreviousFreq();
                Log.d("ButtonTAG", "onClick: prevFreq =" + prevFreq);
                if (prevFreq == -1) {
                    return;
                }
                if (prevFreq == 0) {
                    int lastFreq = mController.getLastFavor();
                    Log.d("ButtonTAG", "onClick: lastFreq =" + lastFreq);
                    if (lastFreq == 0) {
                        return;
                    } else {

                        mController.getFmSeeker().smoothScrollToPosition(lastFreq - 875);
                    }

                } else {
                    mController.getFmSeeker().smoothScrollToPosition(prevFreq - 875);
                }
            }
        });

    }
}
