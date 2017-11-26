package com.khezet.GUI;

import javax.swing.*;
import java.net.URL;

public class Utils {

    public static String getFileExtension(String fileName) {

        int pointPosition = fileName.lastIndexOf(".");

        if(pointPosition == -1) return null;

        if(pointPosition == fileName.length()-1) return null;

        return fileName.substring(pointPosition+1, fileName.length());

    }


    public static ImageIcon createIcon(String path) {
        URL url = System.class.getResource(path);//get a url of a string(path)
        if (url == null) System.err.println("path undefined"+url);

        return new ImageIcon(url, "icon of each btn");

    }

}
