package fr.ensibs.collections;

import java.util.LinkedList;

public class FIFO<T> extends LinkedList {

    private static FIFO f = null;

    public static FIFO getInstance(){
        if(f == null){
            f = new FIFO();
        }
        return f;
    }

    private FIFO(){
        super();
    }
}
