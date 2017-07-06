package com.angeloparenteapp.kmk_kissmarrykill;

/**
 * Created by Angelo on 12/05/2017.
 */

public class Person {
    private int kiss;
    private int marry;
    private int kill;
    private String name;
    private String url;

    public Person(){
    }

    public Person(int kiss, int marry, int kill, String name, String url) {
        this.kiss = kiss;
        this.marry = marry;
        this.kill = kill;
        this.name = name;
        this.url = url;
    }

    public void setKiss(int kiss) {
        this.kiss = kiss;
    }

    public int getKiss() {
        return kiss;
    }

    public void setMarry(int marry) {
        this.marry = marry;
    }

    public int getMarry() {
        return marry;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getKill() {
        return kill;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
