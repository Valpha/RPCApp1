package com.valpha.rpcapp.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valpha.rpcapp.R;
import com.valpha.rpcapp.View.FmseekerAdapter;
import com.valpha.rpcapp.View.RecyclerViewClickListener2;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.SharedPrefs;

import java.util.HashSet;
import java.util.Set;

public class TunerActivity extends AppCompatActivity {

    private static final String TAG = "TunerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AnyPref.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);
        final RecyclerView favorList = findViewById(R.id.rv_favorlist);
        favorList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        favorList.setAdapter(new FavorListAdapter());
        final DiscreteScrollView seeker = findViewById(R.id.seeker);
        FmseekerAdapter adapter = new FmseekerAdapter();
        seeker.setAdapter(adapter);
        DiscreteScrollView.OnItemChangedListener<FmseekerAdapter.VH> listener = new DiscreteScrollView.OnItemChangedListener<FmseekerAdapter.VH>() {
            @Override
            public void onCurrentItemChanged(@Nullable FmseekerAdapter.VH vh, int i) {
                int freq = i + 875;
                Log.d(TAG, "onCurrentItemChanged: " + freq);
            }
        };
        seeker.addOnItemChangedListener(listener);
        seeker.addOnItemTouchListener(new RecyclerViewClickListener2(this, seeker,
                new RecyclerViewClickListener2.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int freq = position + 875;
                        Log.d(TAG, "onItemClick: freq is " + freq);
                        seeker.scrollToPosition(position);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        int freq = position + 875;
                        boolean activatedFlag = view.isActivated();
                        view.setActivated(!activatedFlag);
                        updateFavorList(freq);
                        Log.d(TAG, "onItemLongClick: freq is " + freq);
                    }

                    @SuppressLint("NewApi")
                    private void updateFavorList(int freq) {
                        String freqStr = String.valueOf(freq);
                        SharedPrefs prefs = AnyPref.getPrefs("FavorList");
                        Set<String> set = prefs.getStringSet("favors", new HashSet<String>());
                        if (set.contains(freqStr)) {
                            set.remove(freqStr);
                            Log.d(TAG, "updateFavorList: add " + freqStr);
                        } else {
                            set.add(freqStr);
                            Log.d(TAG, "updateFavorList: remove" + freqStr);
                        }
                        Log.d(TAG, "updateFavorList: set is" + set);
                        prefs.clear().putStringSet("favors", set).commit();
                        FavorListAdapter mAdapter = (FavorListAdapter) favorList.getAdapter();
                        mAdapter.getFavorList();
                        mAdapter.notifyDataSetChanged();
                    }
                }));

    }


}
