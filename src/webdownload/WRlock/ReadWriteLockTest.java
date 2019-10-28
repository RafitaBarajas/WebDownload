package wget;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadWriteLockTest 
{
    
    private final int poolSize = 5;
    static final int READER_SIZE = 10;
    static final int WRITER_SIZE = 5;
        
    public static void main(String[] args) 
    {
        String[] initialElements = {"S1","S2","S3","S4"};        
        ReadWriteList<String> sharedList = new ReadWriteList<>(initialElements);
        
        ExecutorService ex = Executors.newFixedThreadPool(1);
        ExecutorService ex1 = Executors.newFixedThreadPool(5);
        
        for (int i = 0; i < WRITER_SIZE; i++) {
            ex.execute(new Writer(sharedList));
            
        }
        
        for (int i = 0; i < READER_SIZE; i++) {
            ex1.execute(new Reader(sharedList));
        }
 
    }
}