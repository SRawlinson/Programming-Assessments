/*
 * This class deals with the analysis of letter frequencies. Mainly through holding all information in arrays 26 elements 
 * long with a toString method looping through each. 
 */
public class LetterAnalyser {
	private char[] letters = new char[26];
	private int[] frequencies = new int[26];
	private double[] frequencyPercentages = new double[26];
	private double[] diffValues = new double[26];
	private double[] averageFrequencies = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
		       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
			   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
	private int totalCapitals = 0;
	private double max = 0;
	private char mostFrequent = 0;
	
	public LetterAnalyser(String message) {
		//populating letters array with alphabet
		for(int i = 0; i < 26; i++) {
			letters[i] = (char)(i + 65);
		}
		
		//populating frequency array while getting the total number of capital letters in the message for the percentages calculation
		for(int i = 0; i < 26; i++) {
			int frequency = 0;
			for(int j = 0; j < message.length(); j++) {
				if(letters[i] == message.charAt(j) ) {
					frequency++;					
				}
			} frequencies[i] = frequency;
			totalCapitals += frequency;
		}
		
		//populating frequency percentages array and records the largest frequency percentage
		for(int i = 0; i < 26; i++) {			
			frequencyPercentages[i] = ((double)(frequencies[i])/totalCapitals)*100;
			if(frequencyPercentages[i] > max) {
				max = frequencyPercentages[i];
				mostFrequent = letters[i];
			}
		}
		
		//populating diff array
		for(int i = 0; i < 26; i++) {
			diffValues[i] = frequencyPercentages[i] - averageFrequencies[i];
		}
		
	}
	
	public String returnAnArray(int i) {
		return "" + String.format("%-10s", letters[i]) + '\t' + formatInts(frequencies[i]) + '\t' + '\t' 
				+ formatDoubles(frequencyPercentages[i]) + '\t' + '\t' + averageFrequencies[i] + '\t' + '\t' +
				String.format("%3.1f", diffValues[i]) + '\n';	
	}
	
	//ToString method iterates over each array, shown above in returnAnArray(). 
	public String toString() {
		String[] analysis = new String[26];
		String bigLine = String.format("LETTER ANALYSIS: " + '\n' + "%-6s" + '\t' + "%-6s" + '\t' + "%-6s" +
							'\t' + "%-6s" + '\t' + "%-6s" + '\n', "Letter", "Freq", "Freq%", "AvFreq%", "Diff");
		for(int i = 0; i < 26; i++) {
			analysis[i] = returnAnArray(i);
			bigLine+=analysis[i];
		} bigLine += "The most frequent letter is " + mostFrequent + " at " + formatDoubles(max);
		return bigLine;
		
	}
	//Created this to help format doubles.
	public String formatDoubles(double value) {
		return String.format("%-5.1f", value);
	}
	
	public String formatInts(int value) {
		return String.format("%-5d", value);
	}
	
	

}
