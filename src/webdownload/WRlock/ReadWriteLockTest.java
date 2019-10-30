package webdownload.WRlock;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadWriteLockTest 
{
    
    private final int poolSize = 5;
    static final int READER_SIZE = 10;
    static final int WRITER_SIZE = 5;
        
    public static void main(String[] args) throws IOException 
    {
        String[] initialElements = {"S1","S2","S3","S4"};        
        ReadWriteList<String> sharedList = new ReadWriteList<>(initialElements);
        
        PipedOutputStream pout = new PipedOutputStream();
        PipedInputStream pin = new PipedInputStream(pout);
        DataInputStream dis = new DataInputStream(pin);
        
        ExecutorService ex = Executors.newFixedThreadPool(1);
        ExecutorService ex1 = Executors.newFixedThreadPool(5);
        
        for (int i = 0; i < WRITER_SIZE; i++) {
            ex.execute(new Writer(sharedList, "Página"+i));
            
        }
        
        ex.execute(new Reader(sharedList, "Page1", pout));
        System.out.println("Existe "+dis.readInt());
        
        
 
    }
}