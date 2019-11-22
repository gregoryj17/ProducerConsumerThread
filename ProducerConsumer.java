// Jackson Gregory - 010819623

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private static int BUFFER_SIZE = 5;
    private static Random rand = new Random();
    private static int sleep = 20, prod = 5, cons = 1;
    
    // create the semaphore set to numInCriticalSection;
    private static Semaphore empty = new Semaphore(BUFFER_SIZE);
    private static Semaphore full = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);

    private static ArrayList<Integer> buffer = new ArrayList<Integer>();

    static class ProducerThread extends Thread {

        private String name = "";
        private long start = System.nanoTime();

        public ProducerThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for(int i = 0; i < 100 && ((System.nanoTime()-start)/Math.pow(10,9))<sleep; i++){
                    Thread.sleep(rand.nextInt(500));

                    // acquire semaphores
                    empty.acquire();
                    mutex.acquire();

                    // Put something in the buffer
                    int r = Math.abs(rand.nextInt());
                    buffer.add(r);
                    System.out.println("Producer produced " + r);

                    // release semaphores
                    mutex.release();
                    full.release();
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        @Override
        public void interrupt(){
            try{
                super.interrupt();
            } catch(Exception e){

            }
        }
    }

    static class ConsumerThread extends Thread {

        private String name = "";
        private long start = System.nanoTime();

        public ConsumerThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for(int i = 0; i < 100 && ((System.nanoTime()-start)/Math.pow(10,9))<sleep; i++){
                    Thread.sleep(rand.nextInt(500));

                    // acquire semaphores
                    full.acquire();
                    mutex.acquire();

                    // Consume something from the buffer
                    System.out.println("        Consumer consumed " + buffer.remove(0));

                    // release semaphores
                    mutex.release();
                    empty.release();
                }

            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        @Override
        public void interrupt(){
            try{
                super.interrupt();
            } catch(Exception e){

            }
        }

    }

    public static void main(String[] args) {
        try{
            sleep = Integer.parseInt(args[0]);
            prod = Integer.parseInt(args[1]);
            cons = Integer.parseInt(args[2]);
            System.out.println("Using arguments from command line");
        } catch (Exception e){
            sleep = 20;
            prod = 5;
            cons = 1;
            System.out.println("Invalid command line arguments. Defaulting to 20, 5, 1.");
        }
        System.out.println("Sleep time = " + sleep);
        System.out.println("Producer threads = " + prod);
        System.out.println("Consumer threads = " + cons);

        ArrayList<Thread> threads = new ArrayList<>();

        for(int i = 0; i < prod; i++){
            ProducerThread pt = new ProducerThread("Producer Thread "+i);
            pt.start();
            threads.add(pt);
        }

        for(int i = 0; i < cons; i++){
            ConsumerThread ct = new ConsumerThread("Consumer Thread "+i);
            ct.start();
            threads.add(ct);
        }

        long start = System.nanoTime();

        while(((System.nanoTime()-start)/Math.pow(10,9))<sleep){
            System.out.print("");
        }

        for(Thread t : threads){
            if(t.isAlive())t.interrupt();
        }

    }
}
