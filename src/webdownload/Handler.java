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
import java.util.logging.Level;
import java.util.logging.Logger;
import webdownload.WRlock.ReadWriteList;
import static webdownload.WebDownload.sharedList;

public class Handler extends Thread{
    
    private ReadWriteList<String> sharedList;
    private URL url;
    static String path = "C:\\Users\\chistopher\\Desktop\\webDownload\\";
    
    public Handler(ReadWriteList<String> sharedList,URL url ){
        this.sharedList = sharedList;
        this.url = url;
    }
    
    public void run(){
        try {
            download(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void download(URL url) throws IOException{
        
        String line;
        String name;
        String subpath;
        String firstsubpath;
        String auxname;
        int index;
        boolean flag = false;
        
        try{
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            name = connection.getURL().getHost() + connection.getURL().getFile();
            //System.out.println(name);
            
            switch(connection.getResponseCode()){
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return;
            }
            
            if(sharedList.exists(name) == 1){
                flag = true;
            }
            
            if(!flag){
                //downloaded.add(name);
                
                auxname = name.replace(".doc", "");
                auxname = auxname.replace(".docx", "");
                auxname = auxname.replace(".txt", "");
                auxname = auxname.replace(".gz", "");
                auxname = auxname.replace(".tar", "");
                auxname = auxname.replace(".tgz", "");
                auxname = auxname.replace(".c", "");
                auxname = auxname.replace(".java", "");
                auxname = auxname.replace(".pptx", "");
                auxname = auxname.replace(".gif", "");
                auxname = auxname.replace(".png", "");
                auxname = auxname.replace(".pdf", "");
                auxname = auxname.replace(".jpg", "");
                auxname = auxname.replace(".css", "");
                auxname = auxname.replace(".zip", "");
                auxname = auxname.replace(".mht", "");
                auxname = auxname.replace(".html", "");
                auxname = auxname.replace(".ico", "");
                auxname = auxname.replace(".x", "");
                auxname = auxname.replace(".swf", "");
                auxname = auxname.replace(".ppt", "");
                auxname = auxname.replace(".form", "");
                auxname = auxname.replace(".jar", "");
                
                if(!auxname.equals(name)){
                    auxname = auxname.substring(0, auxname.lastIndexOf("/"));
                    
                    File f = new File(path + auxname);
                    f.mkdirs();
                    f.setWritable(true);
                
                } else {
                    File f = new File(path + name);
                    f.mkdirs();
                    f.setWritable(true);
                }
                                
                if(connection.getContentType().contains("text/html")){                    
                    
                    if(!name.contains(".html")){
                        if(name.endsWith("/")){
                            name = name.substring(0, name.length() - 1);
                        }
                        name = name + ".html";
                    }
                    
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    //DataOutputStream dos = new DataOutputStream(new FileOutputStream(path + name));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + name)));

                    while((line = br.readLine()) != null) {
                        line = line + "\n";

                        if(line.contains("href=\"")){
                            //System.out.println("");
                            //System.out.print(line);
                            
                            index = line.indexOf("href=") + 6;
                            subpath = line.substring(index, line.indexOf("\"", index));
                            
                            firstsubpath = subpath;
          
                            //System.out.println(subpath);                      
                            
                            if(!subpath.contains("?") && !subpath.contains("@")){
                                if(subpath.startsWith("/")){
                                    subpath = url.getProtocol() + "://" + url.getHost() + subpath;
                                } else {
                                    subpath = url.getProtocol() + "://" + url.getHost() + url.getFile() + subpath;
                                }
                                System.out.println(subpath);
                                
                                download(new URL(subpath));
                            }
                            
                            line  = line.replace("https://", path);
                            line  = line.replace("http://", path);
                            
                            if(!firstsubpath.startsWith("/")){
                                line  = line.replace("href=\"", "href=\"" + path + url.getHost() + url.getFile() + "/");
                            }
                            
                        }
                        
                        if(line.contains("src=\"")){
                            index = line.indexOf("src") + 5;
                            subpath = line.substring(index, line.indexOf("\"", index));
                        
                            if(!subpath.contains("?")){
                                if(subpath.startsWith("/")){
                                    subpath = url.getProtocol() + "://" + url.getHost() + subpath;
                                } else {
                                    subpath = url.getProtocol() + "://" + url.getHost() + url.getFile() + subpath;
                                }
                                System.out.println(subpath);
                                
                                download(new URL(subpath));
                            }
                            
                            line  = line.replace("https://", path);
                            line  = line.replace("http://", path);
                            line  = line.replace("/icons/", "../icons/");
                            
                        }
                                                
                        bw.write(line);
                        bw.newLine();
                        //dos.flush();
                    }
                    
                    //dos.close();
                    br.close();
                    bw.close();
                
                } else {
                                        
                    DataInputStream dis = new DataInputStream(connection.getInputStream());
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(path + name));

                    long recibidos = 0;
                    int n;

                    while(recibidos < connection.getContentLengthLong()){
                        byte[] b = new byte[2000];
                        n = dis.read(b);
                        recibidos = recibidos + n;

                        dos.write(b, 0, n);
                        dos.flush();
                    }

                    dis.close();
                    dos.close();

                }
            
            }
        } catch(Exception e){
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("connection.getResponseCode()" + connection.getResponseCode());
            e.printStackTrace();
        }
        
    }
}
