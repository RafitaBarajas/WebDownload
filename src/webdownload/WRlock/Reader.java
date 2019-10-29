package webdownload.WRlock;
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
    private int index;
    
    public Reader(ReadWriteList<String> sharedList, int index){              
        this.sharedList = sharedList;
        this.index = index;
    }
 
    public void run() {
        String number = sharedList.get(index);
 
        System.out.println(getName() + " -> get: " + number);
    }
    
}