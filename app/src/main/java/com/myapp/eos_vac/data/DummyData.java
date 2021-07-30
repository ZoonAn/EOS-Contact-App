package com.myapp.eos_vac.data;

import java.util.ArrayList;

public class DummyData {
    public static ArrayList<Profile> dummyList;
    private static int page = 0;

    static{
        dummyList = new ArrayList<>();
        dummyList.add(new Profile("안준범", "010-7322-8902", "ahnzoon@gmail.com"));
        dummyList.add(new Profile("asdf", "010-3077-8902", "asdf@hanmail.com"));

        dummyList.add(new Profile("곽용우", "010-3744-0834", "asdf@hanmail.com"));

        dummyList.add(new Profile("박근원", "010-9880-6010", "asdf@hanmail.com"));
    }

    public static int getPage(){
        return page;
    }

    public static void pageUp(){
        page++;
    }
    public static void pageDown(){
        page--;
    }
}
