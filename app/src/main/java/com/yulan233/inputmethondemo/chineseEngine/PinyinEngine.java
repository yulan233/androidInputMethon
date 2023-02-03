package com.yulan233.inputmethondemo.chineseEngine;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yulan233.inputmethondemo.R;
import com.yulan233.inputmethondemo.chineseEngine.engineLib.searchedHanZi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    //对搜索到的汉字进行热度排序
    public void toSortHanZi(){
        sortedHanZi=new ArrayList<>();
        for (int i =0; i < pinyinList.size(); i++) {
            //Objects,requireNonNull是防止为空
            sortedHanZi.addAll(Objects.requireNonNull(map.get(pinyinList.get(i))));
        }
        Collections.sort(sortedHanZi);
    }

    public List<searchedHanZi> getSortedHanZi() {
        return sortedHanZi;
    }
    public void setNullHanzi(){
        sortedHanZi=new ArrayList<>();
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
        searchPinYinSQL(Pinyin);
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
    //新的用sqlite实现的pinyin搜索
    private void searchPinYinSQL(String pinyin) {
        String[] pinyin2=pinyin.split("'");

        map=new HashMap<>();
        pinyinList=new ArrayList<>();
//        打开数据库

        File file = context.getDatabasePath("database").getParentFile();

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file.toString()+File.separator+"pinyin_dist.db",null);

//        dateBase dateBase=new dateBase(context,"pinyin_dist2.db",null,3);
//        dateBase.onUpgrade(sqLiteDatabase,3,3);
        if(sqLiteDatabase.isOpen()){
            //一个like表达式
            String pinyin3="";
            for (int i = 0; i < pinyin2.length-1; i++) {
                pinyin3+=pinyin2[i]+"% ";
            }
            pinyin3+=pinyin2[pinyin2.length-1]+"%";
            //sql查询语句
            String searchSql="select * from sdf_txt where pinyin like '"+pinyin3+"';";
            //查询结果
            Cursor cursor=sqLiteDatabase.rawQuery(searchSql,null);
            //遍历查询结果，如果为空则不遍历
            int HanziNumber=pinyin2.length;
            String pinyinKey=null;
            if(cursor!=null){
                cursor.moveToFirst();
//                pinyinKey=cursor.getString(cursor.getColumnIndex("pinyin"));
//                map.put(pinyinKey,new ArrayList<searchedHanZi>());
                try{
                    do{
                        String pinyin_cursor=cursor.getString(cursor.getColumnIndex("pinyin"));
                        String hot_cursor=cursor.getString(cursor.getColumnIndex("hot"));
                        String hanzi_cursor=cursor.getString(cursor.getColumnIndex("hanzi"));
                        if(HanziNumber!=hanzi_cursor.length()&&HanziNumber<3){
                            continue;
                        }
                        if (pinyin_cursor.equals(pinyinKey)){
                            map.get(pinyinKey).add(new searchedHanZi(Integer.parseInt(hot_cursor),hanzi_cursor));
                        }else {
                            pinyinKey=pinyin_cursor;
                            pinyinList.add(pinyin_cursor);
                            map.put(pinyinKey,new ArrayList<searchedHanZi>());
                            map.get(pinyinKey).add(new searchedHanZi(Integer.parseInt(hot_cursor),hanzi_cursor));
                        }
                    }while (cursor.moveToNext());
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(cursor.getColumnCount());
                }

                cursor.close();
            }
            sqLiteDatabase.close();
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
    public void imporDatabase() {
        //存放数据库的目录
        String dirPath="/data/data/com.yulan233.inputmethondemo/databases";
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        //数据库文件
        File file = new File(dir, "pinyin_dist.db");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            //加载需要导入的数据库
            InputStream is = context.getResources().openRawResource(R.raw.pinyin_dist);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffere=new byte[is.available()];
            is.read(buffere);
            fos.write(buffere);
            is.close();
            fos.close();

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


}
