
public class Destination extends Railway {

	/**
	 * Creating a 'fake' station seemed the best way for the program to handle leaving the railway network. It's simply a mediation between 
	 * the 'while' loop of the train object and the reachDestination method. The capacity is set deliberately high to avoid any locking errors when trains arrive. 
	 * 
	 * If extended, the program might need to factor the same trains remaining on the network and being sent to different locations, or back 
	 * the way they came, and so this class and general solution would have to be modified or abandoned. 
	 * @param name
	 */
	public Destination(String name) {
		super(name);
	}

	@Override
	protected void setCapacity() {
		this.capacity = 100000000;
	}

	@Override
	public String toString() {
		String finishedTrains = "";
		for (int i = 0; i < trains.size(); i++) {
			finishedTrains += trains.get(i) + ", ";
		}
		return finishedTrains;
	}

	@Override
	protected void setLength() {
		this.length = 0;
	}

	@Override
	protected void setWaitTime() {
		this.waitTime = 0;
	}
}
