package fr.ensibs.collections;

public class ObjectHolder {
    private static Object t = null;

    public static void push(Object t){
        ObjectHolder.t = t;
    }

    public static Object pop(){
        Object toPop = t;
        t = null;
        return toPop;
    }
}
