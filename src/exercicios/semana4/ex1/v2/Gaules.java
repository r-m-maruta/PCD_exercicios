package exercicios.semana4.ex1.v2;

import versão2.Javali;
import versão2.Mesa;

public class Gaules extends Thread{


    private final Mesa mesa;


    public Gaules(Mesa mesa, String nome) {
        super(nome);
        this.mesa = mesa;
        //String é sempre final
    }

    @Override
    public void run() {
        while(true){
            try {
                //versão1.Javali javali ??
                Javali javali = mesa.removeJavali();
                System.out.println(getName() + "comeu " + javali);
            } catch (InterruptedException e){
                break;
            }
        }
    }
}
