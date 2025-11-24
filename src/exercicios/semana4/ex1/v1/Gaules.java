package exercicios.semana4.ex1.v1;

public class Gaules extends Thread{


    private final Mesa mesa;
    private String nome;


    public Gaules(Mesa mesa, String nome) {
        super(nome);
        this.mesa = mesa;
        this.nome = nome;
        //String é sempre final
    }

    @Override
    public void run() {
        for(int i = 0; i<5; i++){
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
