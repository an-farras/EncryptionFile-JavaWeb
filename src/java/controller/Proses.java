/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 *
 * @author Farras Ahmad
 */
public class Proses {
    
    public static void getPath() {
        
    }
    
    public static void copyFolder(File src, File dest, long key, char mode)
            throws IOException {

        if (src.isDirectory()) {

            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdirs();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile, key, mode);
            }

        } else {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            byte[] cipher = new byte[1024];
            
            //long key = 184;
            int length;
            //copy the file content in bytes 
            while ((length = in.read(buffer)) > 0) {
                //out.write(buffer, 0, length);
                for(int k = 0; k < length; k++) {
                    if(mode == 'e') cipher[k] = doEnkripsi(key, buffer[k]);
                    else if (mode == 'd') cipher[k] = doDekripsi(key, buffer[k]);
                }
                out.write(cipher, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }
    
    public static byte doEnkripsi(long key, byte msg) {
        Random rng = new Random(key);
        int randomNum = rng.nextInt(256);
        byte cipher = (byte)((byte)randomNum ^ msg);
        return cipher;
    }
    
    public static byte doDekripsi(long key, byte msg) {
        Random rng = new Random(key);
        int randomNum = rng.nextInt(256);
        byte plain = (byte)((byte)randomNum ^ msg);
        return plain;
    }
}
