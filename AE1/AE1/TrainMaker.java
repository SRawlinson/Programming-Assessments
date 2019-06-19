import java.util.Random;

public class TrainMaker extends Thread {
	private Railway firstStation;
	private Random rand;
	private int slowCounter;
	private int fastCounter;
	
	public TrainMaker(Railway station) {
		this.firstStation = station;
		rand = new Random();
		slowCounter = 0;
		fastCounter = 0;
		
	}
	
	/**
	 * The run method is what begins the process of creating the trains, after the constructor has made the rest of what
	 * is necessary for the program. 
	 */
	@Override
	public void run() {
		while(true) {
			makeTrains();
		}	
	}

	/**
	 * This method makes trains after a couple of set delays. Which train it makes is decided randomly. 
	 * 
	 * If re-designed, this aspect could be easily modified to follow a schedule of sorts, sending specific train types at regular intervals. 
	 * 
	 * The initial wait was implemented last, as it was found that the program stopped making trains after a certain point 
	 * without it. I'm still not entirely certain why.
	 * 
	 * After that, the system currently waits up to five seconds more (to introduce more 'randomness') before making an instance of a train.
	 */
	private void makeTrains() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int entryTrains = firstStation.getCounter();
		int entryCapacity = firstStation.getCapacity();
		if (entryTrains < entryCapacity) {
			try {
				Thread.sleep(rand.nextInt(5000) + 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			int trainDecider = rand.nextInt(2);
			if (trainDecider == 0) {
				makeASlowTrain();				
			} else if (trainDecider == 1) {
				makeAFastTrain();
			} else {
				System.out.println("Something went wrong!");
			}
		}

	}	
	
	
	/**
	 * This creates an instance of a 'Slow' train object, and starts its thread. 
	 * 
	 * A global variable was created in order to help name each successive train created. I thought it best to separate 
	 * these names based on the type of train.
	 */
	public void makeASlowTrain(){
		slowCounter ++;
		String slowName = "";
		slowName = "S" + String.valueOf(slowCounter);
		SlowTrain slow = new SlowTrain(slowName, firstStation);
		slow.start();
	}
	
	/**
	 * This creates an instance of a 'Fast' train object, and starts its thread.
	 */
	public void makeAFastTrain() {
		fastCounter ++;
		String fastName = "";
		fastName = "F" + String.valueOf(fastCounter);
		FastTrain fast = new FastTrain(fastName, firstStation);
		fast.start();
	}
}
