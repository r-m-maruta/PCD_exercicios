public class Escavadora extends Thread {

    private final Balanca balanca;
    private volatile boolean ativo = true;

    public Escavadora(Balanca balanca) {
        this.balanca = balanca;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " iniciando...");
        try {
            while (ativo) {
                double pedaco = Math.random();
                balanca.adicionar(pedaco);
                System.out.println(Thread.currentThread().getName() + " -antes do sleep");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " - depois do sleep");
            }
        } catch (InterruptedException e){
            System.out.println(Thread.currentThread().getName() + " - antes de tratar InterruptedException.");
        } finally {
            System.out.println(Thread.currentThread().getName() + " finalizando...");
        }
    }

    public void parar(){
        ativo = false;
        interrupt();
    }
}
