package exercicios.semana7.ex1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Comparable<Account> {

    double balance;
    Lock lock = new ReentrantLock();
    private int id;

    public Account(double balance, int id) {
        this.balance = balance;
        this.id = id;
    }

    @Override
    public int compareTo(Account other) {
        return Integer.compare(this.id, other.id);
    }

    void withdraw(double amount) {
        balance -= amount;
    }

    void deposit(double amount) {
        balance += amount;
    }

    void transfer(Account to, double amount) {
        Account first = this.compareTo(to) < 0 ? this : to;
        Account second = this.compareTo(to) < 0 ? to : this;

        first.lock.lock();
        second.lock.lock();

        try {
            this.withdraw(amount);
            to.deposit(amount);
        } finally {
            second.lock.unlock();
            first.lock.unlock();
        }
    }

    private class Transferer extends Thread {
        private Account to;

        public Transferer(Account to) {
            this.to = to;
        }

        @Override
        public void run() {
            for (int i = 0; i != NUM_TRANSFERENCIAS; i++)
                transfer(to, 1);
        }
    }

    private static final int NUM_TRANSFERENCIAS = 1000000;

    public static void main(String[] args) throws InterruptedException {

        Account a1 = new Account(NUM_TRANSFERENCIAS + 1, 1);
        Account a2 = new Account(NUM_TRANSFERENCIAS + 1, 2);

        Thread t1 = a1.new Transferer(a2);
        Thread t2 = a2.new Transferer(a1);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Saldos: " + a1.balance + ":" + a2.balance);
    }
}
