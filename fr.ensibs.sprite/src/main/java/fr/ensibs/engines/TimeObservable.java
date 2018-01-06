package fr.ensibs.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TimeObservable{

    private static TimeObservable instance;
    private List<Observer> observers;


    public static TimeObservable getInstance(){
        if(instance == null){
            instance = new TimeObservable();
        }
        return instance;
    }

    private TimeObservable(){
        observers = new ArrayList<>();
    }

    public void addObserver(Observer o){
        observers.add(o);
    }

    public void notifyObservers(TimeEvent te) {
        for(int i = 0;i<observers.size();i++){
           observers.get(i).update(null,te);
        }
    }
}
