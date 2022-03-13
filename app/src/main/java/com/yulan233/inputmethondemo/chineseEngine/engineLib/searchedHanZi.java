package com.yulan233.inputmethondemo.chineseEngine.engineLib;

public class searchedHanZi implements Comparable<searchedHanZi>{
    private Integer WordFrequency;
    private String Hanzi;
    public searchedHanZi(){

    }
    public searchedHanZi(int wordFrequency, String hanzi) {
        WordFrequency = wordFrequency;
        Hanzi = hanzi;
    }

    @Override
    public int compareTo(searchedHanZi o) {
        if(null == this.WordFrequency) {
            return -1;
        }
        if(null == o.getWordFrequency()) {
            return 1;
        }
        return this.WordFrequency.compareTo(o.WordFrequency);
    }

    public Integer getWordFrequency() {
        return WordFrequency;
    }

    public void setWordFrequency(Integer wordFrequency) {
        WordFrequency = wordFrequency;
    }

    public String getHanzi() {
        return Hanzi;
    }

    public void setHanzi(String hanzi) {
        Hanzi = hanzi;
    }

    @Override
    public String toString() {
        return "{" +
                "WordFrequency=" + WordFrequency +
                ", Hanzi='" + Hanzi + '\'' +
                '}';
    }
}
