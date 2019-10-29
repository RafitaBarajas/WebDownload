package webdownload.WRlock;
import java.util.*;
 
/**
 * Writer.java
 * Este hilo agrega aleatoriamente un elemento a un ArrayList compartido
 */
public class Writer extends Thread {
    private ReadWriteList<String> sharedList;
    private String path;
 
    public Writer(ReadWriteList<String> sharedList, String path) {
        this.sharedList = sharedList;
        this.path = path;
    }
 
    public void run() {
        sharedList.add(path);
 
        System.out.println(getName() + " -> added: " + path);
        
        System.out.println("");
    }
}