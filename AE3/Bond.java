/*
 * This class describes the Bond Objects made from the input file and contained in the ArrayList used to plot the graph. 
 * 
 * Each variable from the input file is held as a String in order to move freely between the integers and doubles - the Getters 
 * for each variable converts the strings to doubles for the same reason. 
 * 
 * The ToString method was designed with the need for a description of each bond in mind. 
 */
public class Bond {
	protected String x;
	protected String y;
	protected String z;
	protected String temp;
	protected String firstVariable;
	protected String secondVariable;
	protected String thirdVariable;
	
	public Bond(String xString, String yString, String zString, String firstVariableName, String secondVariableName, String thirdVariableName) {
		x = xString;
		y = yString;
		z = zString;
		firstVariable = firstVariableName;
		secondVariable = secondVariableName;
		thirdVariable = thirdVariableName;
		temp = "";
	}
	
	//The ToString() was written with the detailsField in mind, so that no additional formatting would be required, hence the need for variable names
	public String toString() {
		String bondItem = firstVariable + ": " + x + ", " + secondVariable + ": " + y + ", " + thirdVariable + ": " + z;
		return bondItem;
	}
	
	//The variables change order to maintain flexibility - same as the combo boxes. 
	public void swapXForZ() {
		temp = x;
		x = z;
		z = temp;
		
		//The strings of each variable name must be swapped with the variables themselves in order to keep the data coherent. 
		swapFirstForThird();
	}
	
	public void swapYForZ() {
		temp = y;
		y = z; 
		z = temp;
		swapSecondForThird();
	}
	
	public void swapFirstForThird() {
		temp = firstVariable;
		firstVariable = thirdVariable;
		thirdVariable = temp;
	}
	
	public void swapSecondForThird() {
		temp = secondVariable;
		secondVariable = thirdVariable;
		thirdVariable = temp;
	}
	
	//the Getters for x and y are both set to doubles to make the graph plotting run smoothly when moving between the integer and double values. 
	public Double getX() {
		return Double.valueOf(x);
	}

	public void setX(String x) {
		this.x = x;
	}

	public Double getY() {
		return Double.valueOf(y);
	}

	public void setY(String y) {
		this.y = y;
	}

	public Double getZ() {
		return Double.valueOf(z);
	}

	public void setZ(String z) {
		this.z = z;
	}
}
