package com.cristichi.lifepointscounter.obj;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    public static List<List<Integer>> buttons;
    public static float soundVolume;
    public static float musicVolume;

    static {
        Init();
    }

    public static void Init(){
        buttons = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l3 = new ArrayList<>();
        List<Integer> l4 = new ArrayList<>();
        buttons.add(l1);
        buttons.add(l2);
        buttons.add(l3);
        buttons.add(l4);

        l1.add(50);
        l1.add(100);

        l2.add(500);
        l2.add(1000);

        l3.add(-50);
        l3.add(-100);

        l4.add(-500);
        l4.add(-1000);

        soundVolume = 0.5f;
        musicVolume = 0.5f;
    }
}
