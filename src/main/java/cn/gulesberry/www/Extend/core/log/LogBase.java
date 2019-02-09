package cn.gulesberry.www.Extend.core.log;


import java.io.File;
import java.io.FileOutputStream;

public class LogBase {
    public static FileOutputStream getWriter(File file) throws Exception{
        FileOutputStream writer = new FileOutputStream(file,true);
        return writer;
    }
    public static String getLogFolder(String file){
        return "./plugins/"+file+"/log";
    }
}
