
public class FastTrain extends Train {
	
	public FastTrain(String name, Railway starter) {
		super(name, starter);
	}

	@Override
	void setSpeed() {
		this.speed = 500;
	}
	

}
