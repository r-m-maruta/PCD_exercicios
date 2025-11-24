package exercicios.semana5.ex3_2;

import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Track extends JComponent implements Observer{
	private static final boolean SHOW_WINNER = true;
	private static final boolean STOP_OTHER_CARS = true;
	private int numCars;
	private int numSteps;
	private int[] carPositions;
	private ImageIcon icon = new ImageIcon("/home/rmpgm/IdeaProjects/PCD/exercicios/Semana5/ex3_2/azul.gif");
	private boolean raceHasEnded=false;
	private List<Thread> cars;

	public Track(int numCars, int numSteps) {
		this.numCars = numCars;
		this.numSteps = numSteps;
		carPositions = new int[numCars];
	}

	public void moveCar(int car, int position) {
		if (car < 0 || car >= numCars)
			throw new IllegalArgumentException("invalid car index: " + car);
		if (position < 0 || position > numSteps)
			throw new IllegalArgumentException("invalid position: " + position);
		carPositions[car] = position;
		repaint();
	}

	public void updateCarPositions(int[] positions) {
		if(carPositions.length!=positions.length)
			throw new IllegalArgumentException("wrong array length: "+ positions.length);

		for(int i = 0; i < positions.length; i++)
			carPositions[i] = positions[i];

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double deltaY = ((double) getHeight()) / (numCars + 1);
		double deltaX = ((double) getWidth() - icon.getIconWidth()) / numSteps;
		for (int i = 0; i < numCars; i++) {
			g.drawLine(0, (int) (deltaY * (i + 1)), getWidth(),
					(int) (deltaY * (i + 1)));
			g.drawImage(icon.getImage(), (int) (carPositions[i] * deltaX),
					(int) (deltaY * (i + 1)) - icon.getIconHeight(), null);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Car updatedCar=(Car)arg0;
		moveCar(updatedCar.getId(), updatedCar.getPosition());

		// Check if car ended race
		if(updatedCar.getPosition()==numSteps){
			if(SHOW_WINNER && !raceHasEnded){
				raceHasEnded=true;
				if(STOP_OTHER_CARS)
					for(Thread c: cars)
						c.interrupt();
				JOptionPane.showMessageDialog(this, "O vencedor foi o carro "+updatedCar.getId());
			}
			
		}
		// Redraw everything!
		invalidate();


	}

	public void setAllCars(List<Thread> cars) {
		this.cars=cars;
	}
}
