package com.yulan233.inputmethondemo;

import android.inputmethodservice.InputMethodService;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yulan233.inputmethondemo.chineseEngine.PinyinEngine;
import com.yulan233.inputmethondemo.chineseEngine.engineLib.searchedHanZi;
import com.yulan233.inputmethondemo.databinding.LayoutKeyboardNumberBinding;
import com.yulan233.inputmethondemo.databinding.LayoutKeyboardQwertyBinding;
import com.yulan233.inputmethondemo.databinding.LayoutMainkeybaordBinding;
import com.yulan233.inputmethondemo.databinding.LayoutTextviewBinding;
import com.yulan233.inputmethondemo.keyboard.CandidatedHanZi;
import com.yulan233.inputmethondemo.keyboard.KeyboardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class imputeMethodIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private InputConnection ic;
    private KeyboardView keyboardView;

//    根键盘
    private LayoutMainkeybaordBinding layoutMainkeybaordBinding;
    private LayoutKeyboardNumberBinding layoutKeyboardNumberBinding;
    private LayoutKeyboardQwertyBinding layoutKeyboardQwertyBinding;

//    拼音候选项
    private TextView pinyin;
    private LayoutTextviewBinding layoutTextviewBinding;

    private PinyinEngine pinyinEngine;

    private List<String> pinyinCandidates;
    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public View onCreateInputView() {
        keyboardView = new KeyboardView(getApplicationContext());
        layoutMainkeybaordBinding=LayoutMainkeybaordBinding.inflate(getLayoutInflater());

        layoutKeyboardNumberBinding=LayoutKeyboardNumberBinding.inflate(getLayoutInflater());
        layoutKeyboardQwertyBinding=LayoutKeyboardQwertyBinding.inflate(getLayoutInflater());

        layoutMainkeybaordBinding.getRoot().addView(layoutKeyboardNumberBinding.getRoot());
        layoutMainkeybaordBinding.getRoot().addView(layoutKeyboardQwertyBinding.getRoot());

//        keyboardView.setKeyboard(layoutKeyboardNumberBinding.getRoot());
        keyboardView.setKeyboard(layoutKeyboardNumberBinding,"number");
        keyboardView.setKeyboard(layoutKeyboardQwertyBinding,"qwerty");
        keyboardView.setMainKeyboard("qwerty");
        keyboardView.setOnKeyboardActionListener(this);
        //在此处导入键盘布局
//        Keyboard keyboard = new Keyboard(getApplicationContext(), R.layout.layout_keyboard_number);
//        keyboardView.setKeyboard(keyboard);
//        keyboardView.setOnKeyboardActionListener(this);
//        return layoutKeyboardNumberBinding.getRoot();
        return layoutMainkeybaordBinding.getRoot();
    }

    @Override
    public View onCreateCandidatesView() {
        layoutTextviewBinding=LayoutTextviewBinding.inflate(getLayoutInflater());
        pinyin=(TextView) layoutTextviewBinding.getRoot().getChildAt(0);
        return layoutTextviewBinding.getRoot();
    }
    private void setPinyin(String a){
        String b=(String) pinyin.getText();
        b=b+a;
        pinyin.setText((CharSequence) b);
    }
    private void putCandidates(){
        RecyclerView recyclerView= layoutKeyboardQwertyBinding.someText;
        List<searchedHanZi> l1=pinyinEngine.getSortedHanZi();
        List<searchedHanZi> l2;
        if (l1.size()>30) {
            l2 = l1.subList(l1.size() - 30, l1.size());
        }
        else{
            l2 = l1;
        }
        CandidatedHanZi candidatedHanZi=new CandidatedHanZi(l2,this,keyboardView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(candidatedHanZi);

    }

    @Override
    public void onWindowShown() {
        super.onWindowShown();
//         进入此回调时，用户将开始输入
//         在此时保存InputConnection，用于后续的输入操作
        ic = getCurrentInputConnection();
    }

    @Override
    public void onWindowHidden() {
        super.onWindowHidden();
//        进入此回调时，用户已结束输入
//         在此时清理InputConnection
        ic = null;
    }
    @Override
    public void onPress(CharSequence primaryCode) {
//        此处的primaryCode，即键盘布局中的code属性，可以根据code来commit需要输入的文字
//         第二个参数是输入的位置，注意是从右往左，0代表向右边插入一个字符
        if (pinyinEngine!=null&&primaryCode.toString().matches("[a-zA-Z]")){
            setPinyin(primaryCode.toString());
            pinyinEngine.setPinyin(pinyin.getText().toString());
            pinyinEngine.searchPinYin();
            pinyin.setText((CharSequence) pinyinEngine.getPinyin());
            putCandidates();

        }
        ic.commitText("" + primaryCode, 1);

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
//        判断是哪个按键
//        -5是退格，-6切换数字键，-7返回键
        if (primaryCode==-5&&pinyinEngine==null){
//            CharSequence selectText= ic.getSelectedText(0);
//            判断是否有选择的内容
            if(ic.getSelectedText(0)!=null){
                ic.setComposingText("",1);
//                删除所有选择的内容
            }else{
//                退格一个字符
                ic.deleteSurroundingText(1,0);
            }
        }else if(primaryCode == -5){
            if(pinyin.getText().equals("")){
                if(ic.getSelectedText(0)!=null){
                    ic.setComposingText("",1);
//                删除所有选择的内容
                }else{
//                退格一个字符
                    ic.deleteSurroundingText(1,0);
                }
            }else {
                String a=(String) pinyin.getText();
                String[] b=a.split("");
                a="";
                if(b.length<2){
                    a="";
                }else{
                    if(b[b.length-2].equals("'")){
                        for (int i = 0; i < b.length - 2; i++) {
                            a=a+b[i];
                        }
                    }else{
                        for (int i = 0; i < b.length - 1; i++) {
                            a=a+b[i];
                        }
                    }
                }
                pinyin.setText((CharSequence)a);
            }
        }
        if (primaryCode==-6){
            keyboardView.switchMainKeyboard("number");
            setCandidatesViewShown(false);
        }
        if (primaryCode==-7){
            keyboardView.switchMainKeyboard("qwerty");
            if(pinyinEngine!=null){
                setCandidatesViewShown(true);
            }
        }
        if (primaryCode==-8){
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));

        }
//        中英切换
        if (primaryCode==-9&&pinyinEngine==null){
            pinyinEngine=new PinyinEngine();
            pinyinEngine.setContext(getApplicationContext());
            setCandidatesViewShown(true);
        }else if(primaryCode==-9){
            pinyinEngine=null;
            setCandidatesViewShown(false);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
