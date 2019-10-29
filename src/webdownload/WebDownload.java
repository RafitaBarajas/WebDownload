
package webdownload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import webdownload.WRlock.ReadWriteList;

public class WebDownload {
    
    static String path = "C:\\Users\\grafi_000\\Desktop\\webDownload\\";
    static int MAX_DOWNLOADS = 1;
    //ReadWriteList<String> downloaded = new ReadWriteList<>();         //RECURSO COMPARTIDO
    static ArrayList<String> downloaded = new ArrayList<>();
    
    public static void main(String[] args) {
        try {
            
            URL url = new URL("https://ipn.mx");
            
            download(url);
            
        } catch (Exception e) {
           e.printStackTrace();
        }
        
    }
    
    public static void download(URL url){
        
        String line;
        String name;
        String subpath;
        int index;
        boolean flag = false;
        
        try{
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            //System.out.println(connection.getContentType());
            
            if(connection.getContentType().contains("text/html")){
            
                name = connection.getURL().getHost() + connection.getURL().getFile();
                System.out.println(name);

                for (int i = 0; i < downloaded.size(); i++) {
                    if (downloaded.get(i).equals(name)) {
                        flag = true;
                    }
                }

                if(!flag){
                    downloaded.add(name);
                    
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(path + name + ".html"));

                    while((line = br.readLine()) != null) {
                        line = line + "\n";

                        if(line.contains("href")){
                            System.out.println(line);
                            index = line.indexOf("href") + 6;
                            subpath = line.substring(index, line.indexOf("\"", index));
                            System.out.println(subpath);
                            //remplazar path
                            //mkdirs
                        }
                        
                        if(line.contains("src")){
                            
                        }
                        
                        dos.writeUTF(line);
                        dos.flush();
                    }
                    
                    dos.close();
                }

                /*while(html.contains("href=\"http")){

                    index = html.indexOf("href=\"http") + 6;

                    newurl = html.substring(index, html.indexOf("\"", index));
                    //System.out.println(newurl);

                    download(new URL(newurl));

                    html = html.substring(index);
                }*/
            }
            
        } catch(Exception e){
            //System.out.println(url.g));
            e.printStackTrace();
        }
        
    }
    
}
