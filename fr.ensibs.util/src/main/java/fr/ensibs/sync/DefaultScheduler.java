package fr.ensibs.sync;

public class DefaultScheduler extends Scheduler {
    /**
     * Constructor
     *
     * @param period the delay between each notification
     */

    SchedulerThread t;


    public DefaultScheduler(long period) {
        super(period);
        t = new SchedulerThread();
    }

    @Override
    public void start() {
        t.start();
    }

    @Override
    public void stop() {
        t.interrupt();
    }


    private class SchedulerThread extends Thread{

        public synchronized void run(){
            while(!this.isInterrupted()){
                DefaultScheduler.this.notifyObservers();
                try {
                    this.sleep(DefaultScheduler.this.getPeriod());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
