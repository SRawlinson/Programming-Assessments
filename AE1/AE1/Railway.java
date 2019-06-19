import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Multiple instances of the Railway class come together to form a network for the trains to traverse. In order to develop many types of 
 * Railway object, this class was kept abstract with the relevant settings called from the constructor. THese could be added to in an extended program.
 * 
 * Originally the Railway objects were held in an ArrayList, but it was pointed out that this was not very flexible if the system were to be extended. Instead, 
 * the Railway network operates similarly to a Linked-List, with each element only knowing about the next in the network. This was necessary for the trains to 
 * travel, and could possibly be extended to allow for multiple paths or destinations through a network by adding in more 'next'-style references. When combined 
 * with data from the train, the system should then support multiple pathways through the network while maintaining the integrity of the locking system. 
 * @author srawl
 *
 */
abstract class Railway {
	protected int length;
	protected int capacity;
	protected String name;
	boolean full = false; 
	protected ArrayList<Train> trains;
	protected int counter = 0;	
	protected ReentrantLock signalMaster = new ReentrantLock();
	protected Condition fullRailway = signalMaster.newCondition();
	protected boolean notifications = false;
	protected Railway next;
	protected int waitTime;
	
	/**
	 * The constructor calls the relevant setters for each class extending Railway and instantiates the ArrayList for holding trains. 
	 * @param name
	 */
	public Railway(String name) {
		this.name = name;
		setLength();		
		setCapacity();
		setWaitTime();
		
		trains = new ArrayList<Train>();
	}
	
	/**
	 * The network is constructed through linking the trains - if extended, this method could be adapted to add multiple, pathways for a train to follow, 
	 * with a conditional statement added to the 'addTrain' method below to dictate which it would follow.
	 * @param next
	 */
	public void addNext(Railway next) {
		this.next = next;
	}
	
	/**
	 * This method adds a train to the arrayList 'trains', but only after ensuring there is space, based on the capacity of the object.
	 * It then calls the 'exitCurrentSegment' method, which in turn calls the 'removeTrain' method below on the Railway object previous 
	 * to the current one in the network. Both add and remove methods update the relevant counter and call the method ensuring the isFull
	 * value is correct, utilising a lock and Condition to halt the process of trains accordingly. This means that the Trains can maintain 
	 * the agency of moving forward, but are held up by the status of the railway. 
	 * @param train
	 */
	public void addTrain(Train train) {
		signalMaster.lock();
		while (this.isFull()) {
			try {
				if (notifications) {
					System.out.println(getName() + " is full! " + train.toString() + " is waiting!");
				}
				fullRailway.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		train.exitCurrentSegment();
		trains.add(train);
		train.railway = this;
		increaseCounter();
		if (notifications) {
			System.out.println(train.toString() + " is in " + this.getName());
		}
		checkCapacity();
		signalMaster.unlock();

		try {
			train.sleep((this.length / train.getSpeed())*1000 + waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 

	}
	
	/**
	 * This method removes a train and, if the Railway was full, signals any waiting trains there is now room available. It is called from the 
	 * train object currently calling addTrain in the next object in the network - while confusing, this complicated dependency seemed necessary to 
	 * ensure the trains didn't leave stations before they were able to move onto the next one. 
	 * @param train
	 * @throws NoTrainException
	 */
	public void removeTrain(Train train) throws NoTrainException {
		if (trains != null) {
			signalMaster.lock();
				if (trains.contains(train)) {
					trains.remove(train);
					if (this.isFull()) {
						fullRailway.signalAll();
						if (notifications) {
							System.out.println(getName() + " is full no longer! Full steam ahead!");
						}
					}
					decreaseCounter();
					if (notifications) {
						System.out.println(train.toString() + " has left " + this.getName());
					}
					checkCapacity();
				} else {
					throw new NoTrainException();
				
			} signalMaster.unlock();
		}
	}
	
	/**
	 * This is called regularly and is synchronised to ensure the Condition maintains its integrity. 
	 */
	public void checkCapacity() {
		synchronized(this) {
		if (capacity == counter) {
			full = true;
		} else {
			full = false;
		}
		}
	}
	
	public String toString() {
		String trainNames = "";
			for (int i = 0; i < trains.size(); i++) {
				trainNames += trains.get(i).toString() + " ";
			}		
		return "|----" + name + "--"+ "(" +trains.size()+"/"+capacity+") " + "--" + trainNames + "--|";
	}
	
	public boolean hasNext() {
		if (this.next != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Railway getNext() {
		return this.next;
	}
	
	protected abstract void setLength();
	
	protected abstract void setWaitTime();
		
	protected abstract void setCapacity();
	
	public int getLength() {
		return length;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isFull() {
		return full;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void increaseCounter() {
		counter++;
	}
	
	public void decreaseCounter() {
		counter--;
	}


}
