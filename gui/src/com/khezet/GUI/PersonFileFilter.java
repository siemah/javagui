package com.khezet.GUI;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class PersonFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {

        String fileName = file.getName();
        String extension = Utils.getFileExtension(fileName);

        if(file.isDirectory()) return true;
        else if(extension == null) return false;
        else if( extension.equals("per") ) return true;

        return false;
    }

    @Override
    public String getDescription() {
        return "Person Data File (*.per)";
    }
}
