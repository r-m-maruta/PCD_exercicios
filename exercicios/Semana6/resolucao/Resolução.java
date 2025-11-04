import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainBarreira {
    static final int NUM_BLOCKS_TO_BE_SEARCHED=10000;
    static final int STRING_LENGTH=1024;
    static final String STRING_TO_BE_FOUND="huik";

    public static void main(String[] args) {
        final long initTime=System.currentTimeMillis();
        SearcherTask[] tasks=new SearcherTask[NUM_BLOCKS_TO_BE_SEARCHED];
        CountDownLatch latch = new CountDownLatch(NUM_BLOCKS_TO_BE_SEARCHED);
        ThreadPoolRS threadPool = new ThreadPoolRS(5);
        RandomString rs=new RandomString(STRING_LENGTH);
        for(int i=0; i!=NUM_BLOCKS_TO_BE_SEARCHED;i++){
            tasks[i]=new SearcherTask(rs.nextString(),
                    STRING_TO_BE_FOUND , latch);
            threadPool.submit(tasks[i]);

        }
	/*	CyclicBarrier barrier= new CyclicBarrier(NUM_BLOCKS_TO_BE_SEARCHED,new Runnable() {
	
			@Override
			public void run() {
				int count=0;
				for(SearcherThread t:threads)
					if(t.getResult()!=-1){
						System.out.println("Found at "+t.getResult());//+" in "+t.getMyText());
						count++;
					}
				System.out.println("Search DONE. Found:"+count+" Time:"+
					(System.currentTimeMillis()-initTime));
				
			}
		});
		*/


        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int count=0;
        for(SearcherTask t:tasks)
            if(t.getResult()!=-1){
                System.out.println("Found at "+t.getResult());//+" in "+t.getMyText());
                count++;
            }
        System.out.println("Search DONE. Found:"+count+" Time:"+
                (System.currentTimeMillis()-initTime));





    }
}


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class SearcherThread extends Thread{
    private String myText;
    private String textToFind;
    private CountDownLatch latch;
    private int result=-1;

    public SearcherThread(String myText, String textToFind,
                          CountDownLatch latch) {
        this.myText = myText;
        this.textToFind = textToFind;
        this.latch = latch;
    }

    public String getMyText() {
        return myText;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        result=myText.indexOf(textToFind);
        latch.countDown();
        System.err.println("Thread finishing at:"+System.currentTimeMillis());

    }


}


import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    //public static final String digits = "0123456789";

    public static final String alphanum = /*upper +*/ lower ;//+ digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomString() {
        this(21);
    }

}

import java.util.ArrayList;

public class ThreadPoolRS {

    ArrayList<Runnable> tasks;


    public ThreadPoolRS(int numWorkers) {
        tasks = new ArrayList<Runnable>();
        for(int i = 0 ; i< numWorkers ; i++) {
            WorkerThread worker = new WorkerThread(this);
            worker.start();
        }

    }

    public synchronized void submit(Runnable taksToAdd) {
        tasks.add(taksToAdd);
        notifyAll();
    }

    public synchronized Runnable getTask() throws InterruptedException {
        while(tasks.isEmpty()) {
            wait();
        }
        return tasks.remove(0);
    }
}



public class WorkerThread extends Thread{

    private ThreadPoolRS threadPoolRS;

    public WorkerThread(ThreadPoolRS threadPoolRS) {
        this.threadPoolRS = threadPoolRS;
    }

    @Override
    public void run() {
        while(!interrupted()) {
            Runnable taskToRun;
            try {
                taskToRun = threadPoolRS.getTask();
            } catch (InterruptedException e) {
                System.out.println("Fui interrompido vou parar");
                return;
            }

            taskToRun.run();

        }
    }

}


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class SearcherTask implements Runnable{
    private String myText;
    private String textToFind;

    private int result=-1;
    private CountDownLatch latch;

    public SearcherTask(String myText, String textToFind , CountDownLatch latch) {
        this.myText = myText;
        this.textToFind = textToFind;
        this.latch = latch;

    }

    public String getMyText() {
        return myText;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        result=myText.indexOf(textToFind);
        latch.countDown();

    }


}



//Barreira - quando todos terminaram algo é feito (por exemplo, quando todos acabrem de responder)
//
//
//CountDownLatch - Quando todos terminarem, as threas vao a vida delas, e eu divirto me a fazer outra coisa (por exemplo apresentar os resultados)
// Por exemplo, avisar que faltam x para um record ..
//
//ThreadPool -
//
//
//Semaforo - para x pessoas entrar numa sala por exemplo