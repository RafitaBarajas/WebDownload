package webdownload.WRlock;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Reader.java
 * Este hilo lee aleatoriamente un elemento a un ArrayList compartido
 */

public class Reader extends Thread{    
    private ReadWriteList<String> sharedList;    
    private String path;
    private DataOutputStream output;
    
    public Reader(ReadWriteList<String> sharedList, String path, OutputStream output){              
        this.sharedList = sharedList;
        this.path = path;
        this.output = new DataOutputStream(output);
    }
 
    public void run() {
        int esta = sharedList.exists(path);
        
        try{
            
            output.writeInt(esta);
            output.flush();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}