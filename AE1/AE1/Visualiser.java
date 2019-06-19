
/**
 * The Visualiser prints to the console the railway 'network', including each element's name, capacity and the trains currently travelling on them via
 * each's toString. It is set to do this every half second. 
 * 
 * The nature of the network would make it hard to gather all the relevant data if multiple paths were to be made in an extended version of this program. Perhaps each 'path' 
 * or even each segment would have to be given its own Visualiser class
 * @author srawl
 *
 */
public class Visualiser implements Runnable {
	private Railway firstStation;

	
	public Visualiser(Railway firstStation) {
		this.firstStation = firstStation;
	}

	@Override
	public void run() {
		printToConsole();
	}
	
	
	public void printToConsole() {
		while(true) {
			String visuals = "";
			Railway visStation = firstStation;
			while(visStation.hasNext()) {
				visuals += visStation;
				visStation = visStation.getNext();
			}
			
			visuals += "\n";
			System.out.print(visuals);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
		}
		}
	}
}
