package com.valpha.rpcapp.Controller;

import android.util.ArraySet;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.valpha.rpcapp.Contract.SharePrefs;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TunerController {
    private static final TunerController ourInstance = new TunerController();
    private static final String TAG = TunerController.class.getSimpleName();
    private int freq;
    private String freqStr;

    public static TunerController getInstance() {
        return ourInstance;
    }

    private List<String> mDatas;
    private Comparator<String> freqSortRule = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {

            return Float.compare(Float.valueOf(o1), Float.valueOf(o2));
        }
    };

    private TunerController() {
        this.mDatas = new ArrayList<>();
        this.mAdapterList = new ArrayList<>();
        updateFavorList();
        notifyAdapters();
    }

    private void updateFavorList() {
        SharedPrefs prefs = AnyPref.getPrefs(SharePrefs.FAVORLIST_NAME);
        Set<String> set = prefs.getStringSet(SharePrefs.FAVORLIST_SET, new ArraySet<String>());
        mDatas = new ArrayList<>(set);
        Collections.sort(mDatas, freqSortRule);
        Log.d(TAG, "updateFavorList: DataList was updated!----new data size is " + mDatas.size());
    }

    public void notifyAdapters() {
        for (RecyclerView.Adapter adapter : mAdapterList) {
            adapter.notifyDataSetChanged();
        }
    }

    public List<String> getFavorList() {
        Log.d(TAG, "getFavorList: return data size is " + mDatas.size());
        return mDatas;
    }

    private List<RecyclerView.Adapter> mAdapterList;

    public void addAdapter(RecyclerView.Adapter adapter) {
        this.mAdapterList.add(adapter);
        Log.d(TAG, "addAdapter: this adapter is \"" + adapter.getClass().getSimpleName() + "\"");
        Log.d(TAG, "addAdapter: now the adapterList is " + Arrays.toString(mAdapterList.toArray()));
    }

    public void updateFavorList(int freq) {
        SharedPrefs prefs = AnyPref.getPrefs(SharePrefs.FAVORLIST_NAME);
        Set<String> set = prefs.getStringSet(SharePrefs.FAVORLIST_SET, new ArraySet<String>());
        if (set.contains(String.valueOf(freq))) {
            set.remove(String.valueOf(freq));
        } else {
            set.add(String.valueOf(freq));
        }
        mDatas = new ArrayList<>(set);
        Collections.sort(mDatas, freqSortRule);
        prefs.clear().putStringSet(SharePrefs.FAVORLIST_SET, set).commit();
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
        this.freqStr = String.valueOf(freq);
    }

    public String getFreqStr() {
        return freqStr;
    }


    public boolean isFavorate(int freq) {
        return mDatas.contains(String.valueOf(freq));
    }

    public int getNextFreq() {
        if (mDatas.size() == 0) {
            return -1;
        }
        for (String data : this.mDatas) {
            if (Integer.valueOf(data) > freq) {
                return Integer.valueOf(data);
            }
        }
        return 0;
    }

    public int getPreviousFreq() {
        if (mDatas.size() == 0) {
            return -1;
        }
        for (int i = 0; i < mDatas.size(); i++) {
            if (Integer.valueOf(mDatas.get(i)) >= freq) {
                if (i == 0) {
                    return 0;
                } else {
                    return Integer.valueOf(mDatas.get(i - 1));
                }
            }
        }
        return 0;
    }

    public int getFirstFavor() {
        return Integer.valueOf(this.mDatas.get(0));
    }

    public int getLastFavor() {
        return Integer.valueOf(this.mDatas.get(this.mDatas.size() - 1));
    }
}
