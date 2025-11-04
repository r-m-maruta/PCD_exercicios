package versao3;

public class Cozinheiro extends Thread{

    private final Mesa mesa;
    private String nome; //String é sempre final

    public Cozinheiro(Mesa mesa, String nome) {
        //nome da Thread, ver isto do super como deve de ser
        super(nome);
        this.mesa = mesa;
        this.nome = nome;
    }

    @Override
    public void run(){
        for(int i = 0; i <100; i++){
            //porque o try catch ??
            try {
                //Thread.currentThread().getName() como é uma Thread podemos usar logo o getName() ?? ver bem essa situação
                Javali javali = new Javali(getName(), i++);

                //System.out.println(getName() + " produziu - " + javali);
                mesa.putJavali(javali);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


}
