package exercicios.semana6.ex1.v3;

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



