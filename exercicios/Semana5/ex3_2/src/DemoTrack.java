
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

public class DemoTrack {
	private static final int NUM_CARS=5;
    private static final int PERMITS = 2;

	public static void main(String[] args) {
		// GUI usage example... Change to suit exercise
		JFrame frame = new JFrame("Demo Track");
		Track track = new Track(NUM_CARS, 100);
		List<Thread> cars=new ArrayList<>();

        Semaphore semaphore = new Semaphore(PERMITS);

        for (int i = 0; i != NUM_CARS; i++) {
            Car c = new Car(i, 100, semaphore);
            c.addObserver(track);
            Thread t = new Thread(c);
            cars.add(t);
            t.start();
        }


		track.setAllCars(cars);
		
		frame.add(track);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}

}
