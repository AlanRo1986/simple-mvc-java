package com.lanxinbase.system.provider.handler;


import com.lanxinbase.system.provider.basic.IStoreFile;

import java.io.*;

/**
 * Created by alan.luo on 2017/8/4.
 */
public class FileHandler implements IStoreFile {

    @Override
    public String get(String path) {
        if (exist(path)){
            BufferedReader bufferedReader = null;
            String data = "";
            try {
                bufferedReader = new BufferedReader(new FileReader(path));
                String str = "";
                while ((str = bufferedReader.readLine()) != null){
                    data += str;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return data;
        }
        return null;
    }

    @Override
    public void put(String path, String content) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String path, String content) {
        if (exist(path)){
            remove(path);
        }
        put(path,content);
    }

    @Override
    public boolean remove(String path) {
        File file = new File(path);
        return file.delete();
    }

    @Override
    public boolean exist(String path) {
        File file = new File(path);
        return file.exists();
    }
}
