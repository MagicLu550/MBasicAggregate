package net.noyark.www.game.minecraft.web;

import java.io.File;
import java.io.FileOutputStream;

public class WebSetting {
    public static void set(){
        try {
            File folder = new File("webapps/root");
            folder.mkdirs();
            File file = new File("webapps/root/readme.txt");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            out.write("１．请在root创建一个404.html文件　2.网页放在在webapps　3.增加业务网页时．servlet组件要继承com.webserver.servlet.Servlet 4.开发者开发业务网页时参考com.webserver.servlet.ShowAllUserServlet".getBytes("UTF-8"));
            out.write("5.webserver未来推出开发文档[web.noyark.net]".getBytes());
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
