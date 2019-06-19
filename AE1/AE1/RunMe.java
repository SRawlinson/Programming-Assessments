
public class RunMe {
	/**
	 * The RunMe class simply starts off the instance of the FatController class controlling the program. 
	 * 
	 * If we had not been using thread I think I would have included most of the functionality of the FatController
	 * class in here. As it was, I was a little uncertain if classes could call '.start()' on themselves or from a main
	 * within their class or whether that might complicate what was already a multi-staged process. 
	 * 
	 * NB: The specification does ask for the RunMe class to make the railways etc, but I felt that moving that to the FatController
	 * and even allowing that class to create further ones for these jobs made for a more modular program. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Chooooo! Chooooooo!");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Welcome to my Assessed Exercise!");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FatController fatController = new FatController();
		Thread controllerThread = new Thread(fatController);
		controllerThread.start();
	}
	
}
