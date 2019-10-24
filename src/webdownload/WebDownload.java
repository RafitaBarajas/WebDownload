
package webdownload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WebDownload {
    
    static String path = "C:\\Users\\grafi_000\\Desktop\\";
    static int MAX_DOWNLOADS = 5;
    static ArrayList<String> downloaded;                //RECURSO COMPARTIDO

    public static void main(String[] args) {

        downloaded = new ArrayList<String>();
        
        try {
            
            URL url = new URL("https://ipn.mx/");
            
            download(url);
            
        } catch (Exception e) {
           e.printStackTrace();
        }
        
    }
    
    public static void download(URL url){
        
        
        String html = "";
        String line;
        String name;
        String newurl;
        int index;
        boolean flag = false;
        
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            name = connection.getURL().toString();
            //System.out.println(name);
            name = name.replace("/", "");
            name = name.replace(":", "");
            //System.out.println(name);
            
            while((line = br.readLine()) != null) {
                html = html + line + "\n";
            }
            
            for (int i = 0; i < downloaded.size(); i++) {
                if (downloaded.get(i).equals(name)) {
                    flag = true;
                }
            }
                        
            if(!flag){
                downloaded.add(name);
                
                //System.out.println(name);
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(path + name));

                if(html.length() > 65530){
                    while (html.getBytes().length > 60000){
                        dos.writeUTF(html.substring(0, 60000));
                        dos.flush();
                        
                        html = html.substring(60001);
                    }
                    dos.writeUTF(html);
                    dos.flush();
                    
                } else {
                    dos.writeUTF(html);
                    dos.flush();
                }

                dos.close();
            }
 
            while(html.contains("href=\"http")){
            
                index = html.indexOf("href=\"http") + 6;

                newurl = html.substring(index, html.indexOf("\"", index));
                //System.out.println(newurl);
                
                download(new URL(newurl));
                
                html = html.substring(index);
            }
            
            
        } catch(Exception e){
            //System.out.println(url.g));
            e.printStackTrace();
        }
        
    }
    
}
