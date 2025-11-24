package exercicios.semana5.ex4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> {

    private Queue<E> queue = new LinkedList<E>();
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity)
                notFull.await();
            queue.offer(e);
            notEmpty.signalAll();
        } finally{
            lock.unlock();
        }
    }

    public E take () throws InterruptedException {
        lock.lock();
        try{
            while (queue.isEmpty())
                notEmpty.await();
            E e = queue.poll();
            notFull.signalAll();
            return e;
        } finally {
            lock.unlock();
        }
    }



}
