package com.github.omadahealth.lollipin.lib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.omadahealth.lollipin.lib.R;
import com.github.omadahealth.lollipin.lib.interfaces.KeyboardButtonClickedListener;

/**
 * Created by stoyan and oliviergoutay on 1/13/15.
 */
public class KeyboardButtonView extends RelativeLayout {

    private KeyboardButtonClickedListener mKeyboardButtonClickedListener;

    private Context mContext;

    public KeyboardButtonView(Context context) {
        this(context, null);
    }

    public KeyboardButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        initializeView(attrs, defStyleAttr);
    }

    void needBackground(boolean isDigits) {
        findViewById(R.id.keyboard_button_bg).setVisibility(isDigits ? View.INVISIBLE : View.VISIBLE);
    }

    private void initializeView(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null && !isInEditMode()) {
            final TypedArray attributes = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.KeyboardButtonView,
                    defStyleAttr, 0);
            String text = attributes.getString(R.styleable.KeyboardButtonView_lp_keyboard_button_text);
            Drawable image = attributes.getDrawable(R.styleable.KeyboardButtonView_lp_keyboard_button_image);
            boolean rippleEnabled = attributes.getBoolean(R.styleable.KeyboardButtonView_lp_keyboard_button_ripple_enabled, true);

            attributes.recycle();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            KeyboardButtonView view = (KeyboardButtonView) inflater.inflate(R.layout.view_keyboard_button, this);

            if (text != null) {
                TextView textView = (TextView) view.findViewById(R.id.keyboard_button_textview);
                if (textView != null) {
                    textView.setText(text);
                }
            }
            if (image != null) {
                ImageView imageView = (ImageView) view.findViewById(R.id.keyboard_button_imageview);
                if (imageView != null) {
                    imageView.setImageDrawable(image);
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            View rootView = findViewById(R.id.pin_code_keyboard_button_ripple);
            if (rootView != null) {
                rootView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mKeyboardButtonClickedListener != null) {
                            mKeyboardButtonClickedListener.onRippleAnimationEnd();
                        }
                    }
                });
            }

        }
    }

    /**
     * Set by {@link com.github.omadahealth.lollipin.lib.views.KeyboardView} to returns events to
     * {@link com.github.omadahealth.lollipin.lib.managers.AppLockActivity}
     */
    public void setOnRippleAnimationEndListener(KeyboardButtonClickedListener keyboardButtonClickedListener) {
        mKeyboardButtonClickedListener = keyboardButtonClickedListener;
    }

/*
    @Override
    public void onRippleAnimationEnd() {
        if (mKeyboardButtonClickedListener != null) {
            mKeyboardButtonClickedListener.onRippleAnimationEnd();
        }
    }
*/

    /**
     * Otherwise views above will not have the event.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        onTouchEvent(event);
        return false;
    }

}
