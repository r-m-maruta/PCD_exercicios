package exercicios.semana7.ex2;

public class Fork {
	private int id; 
	private boolean inUse; 
	public Fork(int id){
		this.id=id; 
		inUse=false;
	} 
	public synchronized void up(){
		while(inUse){ 
			System.out.println("Going to sleep waiting ("+id+")");
			try{ 
				wait();
			}catch(InterruptedException e){e.printStackTrace();} 
		}
//		System.out.println("Fork("+id+") up"); 
		inUse=true;
	} 
	public synchronized void down(){
//		System.out.println("Fork("+id+") down"); 
		inUse=false; 
		notifyAll();
	} 
}
