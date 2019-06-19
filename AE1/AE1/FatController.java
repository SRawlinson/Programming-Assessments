import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
/**
 * The FatController class is responsible for the set up of the program, creating the railway, starting the Visualiser class
 * and starting the train making process. 
 * 
 * It also holds the 'destination' arrayList, a holding place for trains once they have completed their journey. If the program 
 * were to be extended, this might be left altogether or become a method for storing past trains to be used again after a certain period. 
 * 
 *  * NB: The 'notificationMode' is set to false in the list of global variables. If set to true, it prints helpful notifications 
 *  about the movement of trains that I found to be more helpful than the debugger when dealing with multiple threads. Rather than 
 *  remove it afterward, I attempted to adapt it to be something close to the 'tags' from the Group Project or what we were currently 
 *  learning about in Software Engineering.  
 * @author srawl
 *
 */
public class FatController implements Runnable {
	protected Random rand;
	protected int numOfStations;
	protected int trackNum = 0;
	protected ArrayList<String> stationNames;
	private static ArrayList<Train> destination;
	protected boolean notificationMode = false;
	protected Railway firstStation;
	/**
	 * The Constructor for this class makes the 'Railway' ArrayList as well as creating and starting the Visualiser Thread. 
	 * 
	 * Details of each of these elements can be found in their respective methods. 
	 *
	 */
	public FatController() {
		rand = new Random();
		makeARailway();
		destination = new ArrayList<Train>();
		System.out.println("Here we go.... (Choo! Choo!)");
		
		Visualiser vis = new Visualiser(firstStation);
		Thread visThread = new Thread(vis);
		visThread.setName("VisThread");
		visThread.start();
	}
	
	/**
	 * Running the fatControoler thread simply starts the makerOfTrains one. This is a relic from when the functionality of makerOfTrains was 
	 * in the fatController class, but also ensures the trains don't get started until everything else has been made. 
	 */
	public void run() {
		TrainMaker makerOfTrains = new TrainMaker(firstStation);
		makerOfTrains.start();
	}
	
	/**
	 * This creates an arraylist of names for the program to select from.
	 * 
	 * It simply creates an ArrayList from a pre-existing txt files of station names. If the program were to be expanded 
	 * to allow the user to create a custom track, the 'NamesLoader' class could perhaps be changed to allow for 
	 * user input instead. 
	 */
	private void makeStationNames() {
		String names = "stationNames.txt";
		stationNames = NamesLoader.loadNames(names);
		
	}

	/**
	 * This method creates enough Railway objects to create railway for the trains to traverse. 
	 * 
	 * Currently the length of the 'network' is assigned randomly, although it could be quickly modified to allow 
	 * for user input to dictate its length. I decided to make it randomly to avoid hard-coding in the length or even capacity of each segment, 
	 * simply to demonstrate the flexibility of the program. The result is a little over-complicated with the array to ensure the flow of the track, 
	 * but this was a result of the randomness and again could be modified easily if there was a set path for the 'network' to follow. 
	 * 
	 * +2 is added when deciding the number of stations so that there is always at least two, with a single track between them. It 
	 * was assumed that the format for the railway was always 'Station - Track - Station' in order to prevent generating 
	 * one long track or a series of stations. 
	 * 
	 * As stated the capacity for each station is also set randomly, but the constructors of the trains could also be extended easily to feature
	 * a method call asking for user input. For now, it is always at least 2 to demonstrate the 'overtaking' function of the fast trains. 
	 * 
	 * 
	 */
	public void makeARailway() {
		makeStationNames();
		numOfStations = rand.nextInt(4) + 2;
		
		firstStation = new Station(pickAName());
		
		Railway[] tempArray = new Railway[numOfStations + (numOfStations - 1)];
		tempArray[0] = firstStation;
		for (int i = 1; i < tempArray.length; i++) {
			if ( i % 2 == 0) {
				Railway newRailway = new Station(pickAName());
				tempArray[i] = newRailway;
			} else if ( i % 2 != 0) {
				Railway newRailway = new Track(pickATrackName());
				tempArray[i] = newRailway;
			}
		}
		for (int i = 0; i < tempArray.length - 1; i++) {
			tempArray[i].addNext(tempArray[i+1]);
		}
		
		tempArray[tempArray.length-1].addNext(new Destination("Destination"));
		
	}
	
	/**
	 * This selects a station name at random from the available ArrayList.
	 * 
	 * I decided to use a mutable collection in order to remove chosen names so as to avoid repetition. I chose 
	 * an ArrayList over a Stack or Queue in order continue the random selection.
	 * @return A Station name
	 */
	public String pickAName() {
		int numb = rand.nextInt(stationNames.size());
		String name = stationNames.get(numb);
		stationNames.remove(numb);
		return name;
	}
	
	/**
	 * This simply returns a String Value of the number of the track in the line. 
	 * @return
	 */
	public String pickATrackName() {
		trackNum++;
		return "Track " + String.valueOf(trackNum);
	}	
	
	public static void reachDestination(Train train) {
		destination.add(train);
		//System.out.println(train + " has eached destination!");
	}

}
