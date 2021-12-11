package it.giuugcola.oop.metadata;

import java.util.ArrayList;

public class FileMinAvgMax {

    ArrayList<String> minAvgMaxFile = new ArrayList<>(); //min (String), avg(Double), max(string) di file
    ArrayList<String> minAvgMaxPhoto = new ArrayList<>();
    ArrayList<String> minAvgMaxVideo = new ArrayList<>();

    public ArrayList<String> getMinAvgMaxFile() {
        return minAvgMaxFile;
    }

    public ArrayList<String> getMinAvgMaxPhoto() {
        return minAvgMaxPhoto;
    }

    public ArrayList<String> getMinAvgMaxVideo() {
        return minAvgMaxVideo;
    }

}
