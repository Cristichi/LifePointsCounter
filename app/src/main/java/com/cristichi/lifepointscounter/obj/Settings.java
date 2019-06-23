package com.cristichi.lifepointscounter.obj;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Settings implements Parcelable, Serializable {
    private static final int VERSION = 3;

    public static final String FILE = "settings.bin";

    public static Settings current = new Settings();

    public List<List<Integer>> buttons;
    public float soundVolume;
    public float musicVolume;
    public int lp;
    public String player1;
    public String player2;

    public Settings() {
        buttons = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l3 = new ArrayList<>();
        List<Integer> l4 = new ArrayList<>();

        l1.add(50);
        l1.add(100);

        l2.add(500);
        l2.add(1000);

        l3.add(-50);
        l3.add(-100);

        l4.add(-500);
        l4.add(-1000);

        buttons.add(l3);
        buttons.add(l4);
        buttons.add(l1);
        buttons.add(l2);

        soundVolume = 0.5f;
        musicVolume = 0.5f;

        lp = 8000;
        player1 = "";
        player2 = "";
    }

    protected Settings(Parcel in) {
        in.setDataPosition(0);
        int version = in.readInt();
        if (version==VERSION){
            lp = in.readInt();
            player1 = in.readString();
            player2 = in.readString();
            soundVolume = in.readFloat();
            musicVolume = in.readFloat();
            int buttonsSize = in.readInt();
            buttons = new ArrayList<>(buttonsSize);
            for (int i=0; i<buttonsSize; i++){
                int listSize = in.readInt();
                List<Integer> list  = new ArrayList<>(listSize);
                for (int j=0; j<listSize; j++){
                    list.add(in.readInt());
                }
                buttons.add(list);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.setDataPosition(0);
        dest.writeInt(VERSION);
        dest.writeInt(lp);
        dest.writeString(player1);
        dest.writeString(player2);
        dest.writeFloat(soundVolume);
        dest.writeFloat(musicVolume);
        dest.writeInt(buttons.size());
        for (List<Integer> list : buttons){
            dest.writeInt(list.size());
            for (Integer integer : list){
                dest.writeInt(integer);
            }
        }
    }

    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public boolean write(DataOutputStream writer){
        try{
            writer.writeInt(VERSION);
            writer.writeInt(lp);
            writer.writeUTF(player1);
            writer.writeUTF(player2);
            writer.writeFloat(soundVolume);
            writer.writeFloat(musicVolume);
            writer.writeInt(buttons.size());
            for (List<Integer> list : buttons){
                writer.writeInt(list.size());
                for (Integer integer : list){
                    writer.writeInt(integer);
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean read(DataInputStream reader) {
        try{
            int version = reader.readInt();
            if (version==VERSION) {
                lp = reader.readInt();
                player1 = reader.readUTF();
                player2 = reader.readUTF();
                soundVolume = reader.readFloat();
                musicVolume = reader.readFloat();
                int buttonsSize = reader.readInt();
                buttons = new ArrayList<>(buttonsSize);
                for (int i = 0; i < buttonsSize; i++) {
                    int listSize = reader.readInt();
                    List<Integer> list = new ArrayList<>(listSize);
                    for (int j = 0; j < listSize; j++) {
                        list.add(reader.readInt());
                    }
                    buttons.add(list);
                }
                return true;
            }
        }catch (Exception e){
        }
        return false;
    }
}
