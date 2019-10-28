package wget;
import java.util.*;
 
/**
 * Writer.java
 * Este hilo agrega aleatoriamente un elemento a un ArrayList compartido
 */
public class Writer extends Thread {
    private ReadWriteList<String> sharedList;
 
    public Writer(ReadWriteList<String> sharedList) {
        this.sharedList = sharedList;
    }
 
    public void run() {
        Random random = new Random();
        int number = random.nextInt(100);
        sharedList.add("S"+number+"");
 
        try {
            Thread.sleep(100);
            System.out.println(getName() + " -> put: " + number);
        } catch (InterruptedException ie ) { ie.printStackTrace(); }
        
        System.out.println("");
    }
}