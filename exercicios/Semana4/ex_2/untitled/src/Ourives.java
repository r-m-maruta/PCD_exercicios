public class Ourives extends Thread{

    private final Balanca balanca;
    private volatile boolean ativo = true;
    private int lingotes = 0;

    public Ourives(Balanca balanca){
        this.balanca = balanca;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName() + " - Iniciando...");
        try {
            while(ativo){
                double ouro = balanca.remover();
                lingotes++;
                System.out.printf("%s - Recolheu %.2f kg para fazer lingote #%d%n",
                        Thread.currentThread(), ouro, lingotes);
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " - depois de sleep (lingote feito).");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " - antes de tratar InterruptedException.");
        } finally{
            System.out.println(Thread.currentThread().getName() + " - fim do run.");
            System.out.println("Total de lingotes criados: " + lingotes);
        }
    }

    public void parar() {
        ativo = false;
        interrupt();
    }

}
