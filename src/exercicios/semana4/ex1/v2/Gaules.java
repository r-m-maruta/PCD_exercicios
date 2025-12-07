package exercicios.semana4.ex1.v2;

import exercicios.semana4.ex1.v2.Javali;
import exercicios.semana4.ex1.v2.Mesa;

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
