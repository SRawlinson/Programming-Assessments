
public class SlowTrain extends Train  {

	public SlowTrain(String name, Railway starter) {
		super(name, starter);
	}

	@Override
	void setSpeed() {
		this.speed = 100;		
	}

}
