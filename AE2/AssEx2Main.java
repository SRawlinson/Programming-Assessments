/*
 *  16/11 It works! Need to go through and comment and refactor methods where possible - maybe look at main?
 *  
 *  Then look at interfaces and inheritance. 
 *   
 */
public class AssEx2Main {
	public static void main(String[] args) {
		
		String fileName = InputGatherer.askForFileName();
		String secretMessage = FileReaderWriter.readFileAndTurnToString(fileName);			

		//Cipher created here - before the if statement.
		MonoAlphabet cipher = new MonoAlphabet(InputGatherer.askForKeyword());
//		Vignere cipher = new Vignere(InputGatherer.askForKeyword());
		
		//If statement to decide whether to encode or decode.
		char decider = fileName.charAt(fileName.length() - 1);
		if(decider == 'P') {
			//encode
			secretMessage = cipher.encodeString(secretMessage);
			//Also adapts fileName for the newly en/decoded file
			fileName = changeFileName(fileName, 'C');
			
		} else if(decider == 'C') {
			//decode
			secretMessage = cipher.decodeString(secretMessage);
			
			fileName = changeFileName(fileName, 'D');
			
		}
		//Exports encoded/decoded file
		FileReaderWriter.exportFile(secretMessage, fileName);
		
		//Changes filename again and analyses the output
		fileName = changeFileName(fileName, 'F');
		LetterAnalyser letterAnalyser = new LetterAnalyser(secretMessage);

		//Exports analysis output using new filename
		FileReaderWriter.exportFile(letterAnalyser.toString(), fileName);
		
		//ToString Method for testing the array
//		System.out.println(cipher);
		
	}
	
	public static String changeFileName(String fileName, char a) {
		char[] fileNameArray = fileName.toCharArray();
		fileNameArray[fileName.length() - 1] = a;
		String newFileName = new String(fileNameArray);
		fileName = newFileName;
		return fileName;
	}
	
	
}
