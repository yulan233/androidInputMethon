package com.yulan233.inputmethondemo.keyboard;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.yulan233.inputmethondemo.databinding.LayoutKeyboardNumberBinding;
import com.yulan233.inputmethondemo.databinding.LayoutKeyboardQwertyBinding;

import java.util.ArrayList;
import java.util.List;

public class initKeyboard {
//    获取前一个类对象，用于添加监听
    private final KeyboardView mKeyboardView;
//    定义一堆List，用于方便添加监听和其他使用
//    qwerty键盘
    private List<TextView> keyboardQwertyCommon=new ArrayList<>();
    private List<TextView> keyboardQwertySpecial=new ArrayList<>();
//    number键盘
    private List<TextView> keyboardNumberCommon=new ArrayList<>();
    private List<TextView> keyboardNumberSpecial=new ArrayList<>();
//    构造函数，用于获取上一个类对象
    public initKeyboard(KeyboardView view){
        mKeyboardView=view;
    }
    /**
     * 公开的初始化按键监听的方法
     * @param keyboardType 一个字符串类型的布局类型
     * @param binding 布局对象
    * */
    public void initKeyboardListener(ViewBinding binding,String keyboardType){
        switch (keyboardType){
            case "qwerty":
                setQwertyKeyboard((LayoutKeyboardQwertyBinding) binding);
                break;
            case "number":
                setNumberKeyboard((LayoutKeyboardNumberBinding) binding);
                break;
            case "zhCNPinYin":
                break;
            case "zhCNWuBi":
                break;
            default:
                break;
        }
        initKeyboardListener(keyboardType);
    }
//    将按键放入数组好管理
    private void setQwertyKeyboard(@NonNull LayoutKeyboardQwertyBinding binding){
        keyboardQwertyCommon.add(binding.qwertyQ);
        keyboardQwertyCommon.add(binding.qwertyW);
        keyboardQwertyCommon.add(binding.qwertyE);
        keyboardQwertyCommon.add(binding.qwertyR);
        keyboardQwertyCommon.add(binding.qwertyT);
        keyboardQwertyCommon.add(binding.qwertyY);
        keyboardQwertyCommon.add(binding.qwertyU);
        keyboardQwertyCommon.add(binding.qwertyI);
        keyboardQwertyCommon.add(binding.qwertyO);
        keyboardQwertyCommon.add(binding.qwertyP);
        keyboardQwertyCommon.add(binding.qwertyA);
        keyboardQwertyCommon.add(binding.qwertyS);
        keyboardQwertyCommon.add(binding.qwertyD);
        keyboardQwertyCommon.add(binding.qwertyF);
        keyboardQwertyCommon.add(binding.qwertyG);
        keyboardQwertyCommon.add(binding.qwertyH);
        keyboardQwertyCommon.add(binding.qwertyJ);
        keyboardQwertyCommon.add(binding.qwertyK);
        keyboardQwertyCommon.add(binding.qwertyL);
        keyboardQwertyCommon.add(binding.qwertyZ);
        keyboardQwertyCommon.add(binding.qwertyX);
        keyboardQwertyCommon.add(binding.qwertyC);
        keyboardQwertyCommon.add(binding.qwertyV);
        keyboardQwertyCommon.add(binding.qwertyB);
        keyboardQwertyCommon.add(binding.qwertyN);
        keyboardQwertyCommon.add(binding.qwertyM);
        keyboardQwertyCommon.add(binding.fuSpace);
        keyboardQwertyCommon.add(binding.fuDian);

        keyboardQwertySpecial.add(binding.backspace);
        keyboardQwertySpecial.add(binding.switchKeyboardCNorUS);
        keyboardQwertySpecial.add(binding.switchWidget);
        keyboardQwertySpecial.add(binding.fuHao);
        keyboardQwertySpecial.add(binding.send);
        keyboardQwertySpecial.add(binding.switchNumberKeyboard);
    }
    private void setNumberKeyboard(@NonNull LayoutKeyboardNumberBinding binding){
        keyboardNumberCommon.add(binding.number0);
        keyboardNumberCommon.add(binding.number1);
        keyboardNumberCommon.add(binding.number2);
        keyboardNumberCommon.add(binding.number3);
        keyboardNumberCommon.add(binding.number4);
        keyboardNumberCommon.add(binding.number5);
        keyboardNumberCommon.add(binding.number6);
        keyboardNumberCommon.add(binding.number7);
        keyboardNumberCommon.add(binding.number8);
        keyboardNumberCommon.add(binding.number9);
        keyboardNumberCommon.add(binding.fuSpace);
        keyboardNumberCommon.add(binding.fuDian);
        keyboardNumberCommon.add(binding.fuAt);

        keyboardNumberSpecial.add(binding.fuHao);
        keyboardNumberSpecial.add(binding.backClick);
        keyboardNumberSpecial.add(binding.backspace);
        keyboardNumberSpecial.add(binding.send);
    }
//    获得键盘的绑定对象
    public List<TextView> getKeyboardCommon(String keyboardType){
        List<TextView> list = null;
        switch (keyboardType){
            case "qwerty":
                list=keyboardQwertyCommon;
                break;
            case "number":
                list=keyboardNumberCommon;
                break;
            case "zhCNPinYin":
                break;
            case "zhCNWuBi":
                break;
            default:
                break;
        }
        return list;
    }
    public List<TextView> getKeyboardSpecial(String keyboardType){
        List<TextView> list = null;
        switch (keyboardType){
            case "qwerty":
                list=keyboardQwertySpecial;
                break;
            case "number":
                list=keyboardNumberSpecial;
                break;
            case "zhCNPinYin":
                break;
            case "zhCNWuBi":
                break;
            default:
                break;
        }
        return list;
    }


    //    初始化按键的监听
    private void initKeyboardListener(String keyboardType){
        switch (keyboardType){
            case "qwerty":
                for (int i = 0; i < keyboardQwertyCommon.size(); i++) {
                    TextView t=keyboardQwertyCommon.get(i);
                    t.setOnClickListener(mKeyboardView);
                }
                for (int i = 0; i < keyboardQwertySpecial.size(); i++) {
                    TextView t=keyboardQwertySpecial.get(i);
                    t.setOnClickListener(mKeyboardView);
                }
                break;
            case "number":
                for (int i = 0; i < keyboardNumberCommon.size(); i++) {
                    TextView t=keyboardNumberCommon.get(i);
                    t.setOnClickListener(mKeyboardView);
                }
                for (int i = 0; i < keyboardNumberSpecial.size(); i++) {
                    TextView t=keyboardNumberSpecial.get(i);
                    t.setOnClickListener(mKeyboardView);
                }
                break;
            case "zhCNPinYin":
                break;
            case "zhCNWuBi":
                break;
            default:
                break;
        }
    }
}
