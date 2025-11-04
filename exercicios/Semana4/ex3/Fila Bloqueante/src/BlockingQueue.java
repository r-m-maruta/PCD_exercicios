import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue <T> {

    private Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public BlockingQueue() {
        this.capacity = 0;
    }

    public BlockingQueue(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
    }

    public synchronized void put (T item) throws InterruptedException {
        while(capacity > 0 && queue.size() >= capacity) {
            System.out.println(Thread.currentThread() + "Queue is full");
            wait();
        }

        queue.add(item);
        System.out.println(Thread.currentThread() + " - Inseriu: " + item + " | Tamanho = " + queue.size());

        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while(queue.isEmpty()) {
            System.out.println(Thread.currentThread() + "Queue is empty");
            wait();
        }

        T item = queue.poll();
        System.out.println(Thread.currentThread() + " - Retirou: " + item + " | Tamanho = " + queue.size());

        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized void clear() {
        queue.clear();
        notifyAll();
    }

}
