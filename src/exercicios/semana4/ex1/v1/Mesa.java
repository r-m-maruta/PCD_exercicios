package exercicios.semana4.ex1.v1;

import java.util.LinkedList;
import java.util.List;

public class Mesa {
    //recurso partilhado

    private final List<Javali> javalis = new LinkedList<>();

    //sem o throws, tinha que ter o try catch
    synchronized void putJavali(Javali javali) throws InterruptedException {
        //porque while e nao if, relacionado com o gpu, pois jvm ou sys acorda threads
        while(javalis.size() > 10)
            wait();

        javalis.add(javali);
        notifyAll();
    }

    //void removeJavali(versão1.Javali javali) throws InterruptedException {}

    //synchronized == lock, unlock;
    public synchronized Javali removeJavali() throws InterruptedException {
        while(javalis.isEmpty())
            wait();

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

        List<Cozinheiro> cozinheiros  = new LinkedList<>();
        for(int i = 0; i < 5; i++){
            cozinheiros.add(new Cozinheiro(mesa, "Walter - " + i));
        }

        List<Gaules> gauleses  = new LinkedList<>();
        for(int i = 0; i < 10; i++){
            gauleses.add(new Gaules(mesa, "Jesse - " + i));
        }

        cozinheiros.forEach(Thread::start); //explicar
        gauleses.forEach(Thread::start);
        //gauleses.forEach(t -> t.start()); //igual ao de cime
    }
}
