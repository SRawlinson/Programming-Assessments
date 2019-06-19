import java.util.ArrayList;

public class Track extends Railway {
	
	public Track(String name) {
		super(name);
	}

	
	@Override
	protected void setCapacity() {
		this.capacity = 1;
	}

	@Override
	protected void setWaitTime() {
		this.waitTime = 0;
	}

	@Override
	protected void setLength() {
		this.length = 1000;
	}
}
