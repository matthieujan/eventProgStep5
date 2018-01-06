package fr.ensibs.engines;

import fr.ensibs.collections.FIFO;
import fr.ensibs.collections.ObjectHolder;
import fr.ensibs.sprite.Movie;

public class SpriteEngine extends Thread implements Engine {

    private Movie movie;

    public SpriteEngine(Movie movie){
        this.movie = movie;
    }

    public void run(){
        TimeEvent te;
        while(!this.isInterrupted()){
            if(!FIFO.getInstance().isEmpty()){
                te = (TimeEvent) FIFO.getInstance().getFirst();
                FIFO.getInstance().remove(0);
                movie.setCurrentTime(te.getTime()*100);
                ObjectHolder.push(movie.getSnapshot());
            }
        }
    }
}
