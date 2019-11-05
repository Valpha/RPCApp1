package com.valpha.rpcapp.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.valpha.rpcapp.R;

public class ImageView2State extends AppCompatImageView {
    private static final String TAG = "ImageView";
    final int[] STATE_BLINKING = {R.attr.state_blinking};

    public ImageView2State(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean mIsBlinking = false;

    public void setBlinking(boolean mIsBlinking) {
        this.mIsBlinking = mIsBlinking;
        Log.d(TAG, "setBlinking: now the Blinging state is " + mIsBlinking);
        refreshDrawableState();
    }

    public boolean ismIsBlinking() {
        return mIsBlinking;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (mIsBlinking) {
            mergeDrawableStates(drawableState, STATE_BLINKING);
        }
        return drawableState;
    }
}
