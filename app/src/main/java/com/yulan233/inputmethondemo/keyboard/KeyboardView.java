package com.yulan233.inputmethondemo.keyboard;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.yulan233.inputmethondemo.databinding.LayoutKeyboardNumberBinding;
import com.yulan233.inputmethondemo.databinding.LayoutKeyboardQwertyBinding;

import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yulan233.inputmethondemo.R;

public class KeyboardView extends View implements View.OnClickListener,View.OnTouchListener {


    //    键盘监听
    public interface OnKeyboardActionListener {

        /**
         * 当用户按下一个键时调用。这是在调用 {@link #onKey} 之前发送的。对于重复的键，这只会被调用一次。
         *
         * @param primaryCode 被按下的键的 unicode。如果触摸不在有效键上，则该值将为零。
         */
        void onPress(CharSequence primaryCode);

        /**
         * 当用户释放一个键时调用。这是在调用 {@link #onKey} 之后发送的。对于重复的键，这只会被调用一次。
         *
         * @param primaryCode 被释放的密钥的代码
         */
        void onRelease(int primaryCode);

        /**
         * 向侦听器发送按键。
         *
         * @param primaryCode 这是按下的键
         * @param keyCodes    所有可能的替代键的代码，主代码是第一个。
         *                    如果主键代码是单个字符，例如字母、数字或符号，
         *                    则替代项将包括可能位于同一键或相邻键上的其他字符。
         *                    这些代码可用于纠正意外按下与预期键相邻的键。
         */
        void onKey(int primaryCode, int[] keyCodes);

        /**
         * 向侦听器发送一系列字符。
         *
         * @param text 要显示的字符序列。
         */
        void onText(CharSequence text);

        /**
         * 当用户从右到左快速移动手指时调用。
         */
        void swipeLeft();

        /**
         * 当用户快速从左向右移动手指时调用。
         */
        void swipeRight();

        /**
         * 当用户快速从上到下移动手指时调用。
         */
        void swipeDown();

        /**
         * 当用户快速将手指从下向上移动时调用。
         */
        void swipeUp();
    }

    /** 监听 {@link KeyboardView.OnKeyboardActionListener}. */
    private OnKeyboardActionListener mKeyboardActionListener;
//    各种键盘键盘
    private LayoutKeyboardNumberBinding keyboardNumberBinding;
    private LayoutKeyboardQwertyBinding keyboardQwertyBinding;
    private List<View> allKeyboard=new ArrayList<>();
//    private Map<String,View> allKeyboard1=new HashMap<>();
//    键盘初始化类
    private initKeyboard mInitKeyboard=new initKeyboard(this);
//    主键盘
    private ViewBinding mKeyboardBinding;
    private View mKeyboard;


    public KeyboardView(Context context) {
        super(context);
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
//    设置上层的监听
    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        mKeyboardActionListener = listener;
    }
//获得监听事件的对象
    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        return mKeyboardActionListener;
    }
    List<TextView> Keys;
//    设置主键盘
    public void setMainKeyboard(String keyboardType){
        hiddenAllKeyboard();
        switch (keyboardType){
            case "number":
                mKeyboard=keyboardNumberBinding.getRoot();
                mKeyboard.setVisibility(VISIBLE);
                break;
            case "qwerty":
                mKeyboard=keyboardQwertyBinding.getRoot();
                mKeyboard.setVisibility(VISIBLE);
                break;
        }
    }
//    切换主键盘
    public void switchMainKeyboard(String keyboardType){
        switch (keyboardType){
            case "number":
                mKeyboard=keyboardNumberBinding.getRoot();
                break;
            case "qwerty":
                mKeyboard=keyboardQwertyBinding.getRoot();
                break;
        }
        hiddenAllKeyboard();
        mKeyboard.setVisibility(VISIBLE);
    }
    private void hiddenAllKeyboard(){
        for (View keyboard: allKeyboard) {
            keyboard.setVisibility(GONE);
        }
    }
//设置各种键盘和初始化键盘
    public void setKeyboard(ViewBinding keyboard,String keyboardType){
        switch (keyboardType){
            case "number":
                keyboardNumberBinding=(LayoutKeyboardNumberBinding) keyboard;
                mInitKeyboard.initKeyboardListener(keyboard,keyboardType);
                allKeyboard.add(keyboardNumberBinding.getRoot());
                break;
            case "qwerty":
                keyboardQwertyBinding=(LayoutKeyboardQwertyBinding) keyboard;
                mInitKeyboard.initKeyboardListener(keyboard,keyboardType);
                allKeyboard.add(keyboardQwertyBinding.getRoot());
                break;
        }
    }
    public View getKeyboard(){
        return mKeyboard;
    }

    @Override
    public void onClick(View v) {
//        获取id确的是哪个按钮
        if(v.getId()==R.id.hanzi){
            TextView view=(TextView)v;
            mKeyboardActionListener.onPress(view.getText());
            return;
        }
        TextView view=mKeyboard.findViewById(v.getId());
        if(view.getText().equals("退格")){
            mKeyboardActionListener.onKey(-5,null);
            return;
        }
        if(view.getText().equals("123")){
            mKeyboardActionListener.onKey(-6,null);
            return;
        }
        if(view.getText().equals("返回")){
            mKeyboardActionListener.onKey(-7,null);
            return;
        }
        if(view.getText().equals("发送")){
            mKeyboardActionListener.onKey(-8,null);
            return;
        }
        if (view.getText().equals("英to中")||view.getText().equals("中to英")){
            mKeyboardActionListener.onKey(-9,null);
            if (view.getText().equals("英to中")){
                view.setText((CharSequence) "中to英");
            }else{
                view.setText((CharSequence) "英to中");
            }

            return;
        }
//        调用这个已经实现的onPress()

        if(view.getText().equals("空格")){
            mKeyboardActionListener.onPress(" ");
            return;
        }
        mKeyboardActionListener.onPress(view.getText());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
