
package webdownload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import webdownload.WRlock.ReadWriteList;

public class WebDownload {

    static ReadWriteList<String> sharedList = new ReadWriteList<>();
    
    public static void main(String[] args) {
        try {
            URL url = new URL("http://148.204.58.221/axel/aplicaciones/");
            
            ExecutorService ex = Executors.newFixedThreadPool(5);
            ex.execute(new Handler(sharedList, url));
            
            //Handler h = new Handler(sharedList, url);
            //h.start();
            
            
        } catch (Exception e) {
           e.printStackTrace();
        }
        
    }
}
