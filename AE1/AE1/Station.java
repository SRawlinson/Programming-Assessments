import java.util.ArrayList;
import java.util.Random;

public class Station extends Railway {
	
	public Station(String name) {
		super(name);
	}	

	@Override
	protected void setCapacity() {
		Random rand = new Random();
		this.capacity = rand.nextInt(4) + 2;
		
	}

	@Override
	protected void setWaitTime() {
		// TODO Auto-generated method stub
		this.waitTime = 5000;
	}

	@Override
	protected void setLength() {
		this.length = 100;
	}
}
