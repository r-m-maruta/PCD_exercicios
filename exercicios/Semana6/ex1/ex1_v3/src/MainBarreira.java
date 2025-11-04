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