package com.dreamfly.timeschedule.view.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;

/**
 * Created by jayden on 15-10-5.
 * Reference http://blog.csdn.net/ff20081528/article/details/17121911
 */
public class EditTextWithDel extends EditText {

    private Drawable imgAble;
    private Context mContext;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        imgAble = mContext.getResources().getDrawable(R.drawable.ic_delete);
        setPadding(10,0,10,0);
        visibleImgDel(false);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    private void setDrawable() {
        if(length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
            visibleImgDel(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int)event.getRawX();
            int eventY = (int)event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if(rect.contains(eventX, eventY)) {
                visibleImgDel(true);
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    // Something error...
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogPrint.Debug("===>>>> EditTextWithDel==>> keyCode === " + keyCode);
        return super.onKeyDown(keyCode, event);
    }

    private void visibleImgDel(boolean flag) {
        imgAble.setVisible(flag, true);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
