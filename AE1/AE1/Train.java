import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Train class represents the trains going through the network of Railway objects made by the FatController. 
 * 
 *  This is kept as an abstract class, in order to allow for further extensions. Fast and Slow trains are made by this 
 *  system but these could conceivably be split into further types of speed, or even allow for user input of speed. 
 *  
 *  Currently the train simply moves forward, based on the 'next' value contained in the Railway object. If the program 
 *  were to be extended, having multiple paths of travel would seem to be a logical conclusion. In conjunction with adapting 
 *  the Railway objects, perhaps the trains could be given a 'destination' variable, which interacts with the Railway to 
 *  decide which of each Station's 'next' segments it should follow. 
 * @author srawl
 *
 */
abstract class Train extends Thread {
	protected int speed;
	protected String name;
	protected Railway railway;

	
	public Train (String name, Railway starter) {
		this.name = name;
		this.railway = starter;
		setSpeed();
	}
	/**
	 * The train simply adds itself to each railway in turn until there are none left. Once there, it is added to an ArrayList at the 'destination' object
	 * in order to keep track of a successful journey. 
	 */
	public void run() {
		railway.addTrain(this);
		while(railway.hasNext()) {
			railway.next.addTrain(this);
		}
		
		FatController.reachDestination(this);
		
	}
	/**
	 * The setSpeed of each train is kept abstract and called from the constructor so that the Train might be easily extended to accommodate multiple speed types. 
	 *  If a future iteration involved larger trains or a preference for routes, that would probably be handled similarly. 
	 */
	abstract void setSpeed();

	
	/**
	 * This method is not the most efficient, as rather than throw an error to signify something going wrong, the 'else' path is
	 * left empty. This was to avoid re-writing the addTrain method to excise the exitCurrentSegment call when the process begins - when a train 
	 * starts it has no original station to exit. This somewhat undermines the exception handling written earlier, and would have to be changed 
	 * in the future. 
	 */
	protected void exitCurrentSegment() {
		if (railway.trains.contains(this)) {
		try {
			railway.removeTrain(this);
		} catch (NoTrainException e) {
			System.out.println("No train in " + railway.getName());
		} 
		
		} else {
			//System.out.println("Something went wrong!");
		}
	}
	
	public int getSpeed() {
		return speed;
	}

	public String toString() {
		return name;
	}

}
