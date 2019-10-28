package wget;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Reader.java
 * Este hilo lee aleatoriamente un elemento a un ArrayList compartido
 */

public class Reader extends Thread
{
    
    
    private ReadWriteList<String> sharedList;    
    
    
    public Reader(ReadWriteList<String> sharedList)
    {              
        this.sharedList = sharedList;
    }
 
    public void run() {
        Random random = new Random();
        int index = random.nextInt(sharedList.size());
        String number = sharedList.get(index);
 
        System.out.println(getName() + " -> get: " + number);
 
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie ) { ie.printStackTrace(); }
 
    }
}