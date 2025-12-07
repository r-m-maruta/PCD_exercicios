package exercicios.semana4.ex1.v2;

import exercicios.semana4.ex1.v2.Cozinheiro;
import exercicios.semana4.ex1.v2.Gaules;
import exercicios.semana4.ex1.v2.Javali;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Mesa {
    //recurso partilhado

    private final List<Javali> javalis = new LinkedList<>();

    //sem o throws, tinha que ter o try catch
    synchronized void putJavali(Javali javali) throws InterruptedException {
        //porque while e nao if, relacionado com o gpu, pois jvm ou sys acorda threads
        while(javalis.size() > 10){
            System.out.println(Thread.currentThread().getName() + "DORMIU");
            wait();
            System.out.println(Thread.currentThread().getName() + "ACORDOU");
        }

        javalis.add(javali);
        notifyAll();
    }

    //void removeJavali(versão1.Javali javali) throws InterruptedException {}

    //synchronized == lock, unlock;
    synchronized Javali removeJavali() throws InterruptedException {
        while(javalis.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + "DORMIU");
            wait();
            System.out.println(Thread.currentThread().getName() + "ACORDOU");
        }

            try{
            return javalis.removeFirst();
        } finally {
            notifyAll();
        }
    }

    public static void main(String[] args){
        Mesa mesa = new Mesa();
        //ver o map do Scala e funções anonimas
        //explicar esta linha toda
        //List<versão1.Cozinheiro> cozinheiros = IntStream.range(0,5)
        //        .mapToObj(i -> new versão1.Cozinheiro(mesa, "Walter - " + i )).toList();

        List<Thread> threads = new LinkedList<>();
        for(int i = 0; i < 5; i++){
            threads.add(new Cozinheiro(mesa, "Walter - " + i));
        }

        for(int i = 0; i < 10; i++){
            threads.add(new Gaules(mesa, "Jesse - " + i));
        }

        threads.forEach(Thread::start); //explicar
        //gauleses.forEach(t -> t.start()); //igual ao de cima

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e){
            System.out.println(e.getLocalizedMessage());
            e.getStackTrace();
        }

        threads.forEach(Thread::interrupt);

        threads.forEach(t -> {
            try{
                t.join();
            } catch (InterruptedException e){

            }
        });

    }
}
