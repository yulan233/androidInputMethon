package com.yulan233.inputmethondemo.chineseEngine;

import android.content.Context;

import com.yulan233.inputmethondemo.R;
import com.yulan233.inputmethondemo.chineseEngine.engineLib.searchedHanZi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PinyinEngine {
//    拼音
    private String Pinyin;
    private Context context;
//    存放检索到的拼音数据
    Map<String, List<searchedHanZi>> map=new HashMap<>();
//    存放检索到的拼音
    List<String> pinyinList=new ArrayList<>();
    List<searchedHanZi> sortedHanZi;
    public void toSortHanZi(){
        sortedHanZi=new ArrayList<>();
        for (int i = 0; i < pinyinList.size(); i++) {
            sortedHanZi.addAll(Objects.requireNonNull(map.get(pinyinList.get(i))));
        }
        Collections.sort(sortedHanZi);
    }

    public List<searchedHanZi> getSortedHanZi() {
        return sortedHanZi;
    }

    public Map<String, List<searchedHanZi>> getMap() {
        return map;
    }

    public List<String> getPinyinList() {
        return pinyinList;
    }

    public void setPinyin(String Pinyin){
//        this.Pinyin=Pinyin;
//        判断拼音分词是否正确，并对分词进行操作
        if(cheackPinYin(Pinyin)){
            String[] pinyin2=Pinyin.split("");
            Pinyin="";
            for (int i = 0; i < pinyin2.length; i++) {
                if (i== pinyin2.length-1){
                    Pinyin=Pinyin+"'"+pinyin2[i];
                    continue;
                }
                Pinyin=Pinyin+pinyin2[i];
            }
        }
        this.Pinyin=Pinyin;
    }
    public String getPinyin(){
        return Pinyin;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    //检索拼音，并返回检索到的数据
    public void searchPinYin(){
        searchPinYin(Pinyin);
    }
    private void searchPinYin(String pinyin) {
        String[] pinyin2=pinyin.split("'");
        pinyin="^";
        for (int i = 0; i < pinyin2.length; i++) {
            pinyin=pinyin+pinyin2[i]+"[a-z]*";
            if (i== pinyin2.length-1){
                continue;
            }
            pinyin=pinyin+"\\s{1}";
        }
        map=new HashMap<>();
        pinyinList=new ArrayList<>();
        try {
            InputStream is=context.getResources().openRawResource(R.raw.pinyin_simpdict);
            boolean flag = true;
            String pinyinKey = null;
            int i = 0;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            while (flag) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] line2 = line.split("\t");
                if (line2[1].matches(pinyin)&&line2[1].equals(pinyinKey)){
//                    System.out.println(line2[0]+","+line2[1]+","+i);
                    searchedHanZi hanZi=new searchedHanZi(Integer.parseInt(line2[2]),line2[0]);
                    map.get(line2[1]).add(hanZi);
                }else if (line2[1].matches(pinyin)){
                    pinyinKey=line2[1];
                    pinyinList.add(line2[1]);
                    map.put(line2[1],new ArrayList<searchedHanZi>());
                    searchedHanZi hanZi=new searchedHanZi(Integer.parseInt(line2[2]),line2[0]);
                    map.get(line2[1]).add(hanZi);
                }
//                System.out.println(line2[1]+","+i);
                i++;
            }
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        toSortHanZi();
    }
    //    检查拼音是否需要分词
    public boolean cheackPinYin(String pinyin){
        boolean flag=true;
        String[] line2=pinyin.split("'");
        try {
            InputStream is=context.getResources().openRawResource(R.raw.pinyin);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line=bufferedReader.readLine();
            while (line!=null){
                if (line.matches("^"+line2[line2.length-1]+"[A-Za-z]*")){
                    flag=false;
                    return flag;
                }
                line=bufferedReader.readLine();
            }
        } catch(IOException ioException){
            ioException.printStackTrace();
        }
        return flag;
    }


}
