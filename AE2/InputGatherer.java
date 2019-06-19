import java.util.Scanner;
public class InputGatherer {
static	Scanner encoder = new Scanner(System.in);
	
	/*This clas deals with inputs from the user
	 * Checking for appropriate filenames is done here, although this results in a delay between entering filename
	 * and any file not found exceptions
	 */
	public static String askForFileName() {
		System.out.println("Please enter the name of the file you'd like to encrypt/decrypt");
		String fileName = "";
		while(true) {
			fileName = encoder.next();
			if( (fileName.charAt(fileName.length() - 1)) == 'C' || 
					fileName.charAt(fileName.length() - 1) == 'P' ) {
				return fileName;
			} else {
				System.out.println("The filename must end in 'C' or 'P': ");
				encoder.nextLine();
			}
			}
		}
	
	//Askes for the encryption key - calls methods which check for duplicate letters and that it's all in caps
	public static String askForKeyword() {
		while(true) {
	
		System.out.println("Please enter the encryption key:");
		String keyword = encoder.next();
		if(checkForDuplicates(keyword)) {		
			System.out.println("Encryption key must not have duplicates: ");
			encoder.nextLine();
		} else if (checkForCapitals(keyword)){
			System.out.println("Encryption key must be all capital letters: ");
			encoder.nextLine();
		} else { 
			return keyword;
		}
		}
	}
	public static boolean checkForCapitals(String word) {
		boolean capitals = false;
		char[] wordArray = word.toCharArray();
		for(int i = 0; i < word.length(); i++) {
			if( !((int)(wordArray[i]) > 64 && (int)(wordArray[i]) < 91 )) {
				capitals = true;
				break;
				}		
		} return capitals;

	}
	
	public static boolean checkForDuplicates(String word) {
		boolean duplicates = false;
		char[] wordArray = word.toCharArray();
		for(int i = 0; i < word.length(); i++) {
			for(int j = i+1; j < word.length(); j++) {
				if(wordArray[i] == wordArray[j]) {
					duplicates = true;
				}
			}
		}
		return duplicates;
	}
		
		
	
	//for testing
	public String askForAString() {
		System.out.println("Please enter the text you would like encoded:");
		return encoder.next();
	}

}
