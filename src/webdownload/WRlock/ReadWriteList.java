package webdownload.WRlock;

import java.util.*;
import java.util.concurrent.locks.*;
 
public class ReadWriteList<E> {
    private List<E> list = new ArrayList<>();
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    
    public ReadWriteList(){
    }
 
    public ReadWriteList(E... initialElements) { //genérico:Clase, Interfaz        
        list.addAll(Arrays.asList(initialElements));
    }
 
    public void add(E element) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock(); 
        try {
            list.add(element);
        } finally {
            writeLock.unlock();
        }
    }
 
    public E get(int index) {
        Lock readLock = rwLock.readLock();
        readLock.lock(); 
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }
    
    public int exists(String path){
        
        int aux = 0;
        
        Lock readLock = rwLock.readLock();
        readLock.lock(); 
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(path)){
                aux = 1;
            }
        }
        
        if(aux == 0){
            list.add((E) path);
        }
        
        try {
            return aux;
        } finally {
            readLock.unlock();
        }
    }
 
    public int size() {
        Lock readLock = rwLock.readLock();
        readLock.lock(); 
        try {
            return list.size();
        } finally {
            readLock.unlock();
        }
    }
 
}