import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileReaderWriter {
	//File reading and writing happened often enough to warrant it's own class

	public static void exportFile(String a, String fileName) {
		String title = fileName + ".txt";
		FileWriter fw = null;
		try {
			fw = new FileWriter(title);	
			fw.write(a);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Attempt to close the file
			if(fw!=null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
			}
		}
	}
	public static String readFileAndTurnToString(String fileName) {
		String fileContents = "";
		FileReader fr = null;
		try {
			fr = new FileReader(fileName + ".txt");
			Scanner scanner = new Scanner(fr);
			while(scanner.hasNext()) {
			scanner.useDelimiter(" ");
			fileContents += scanner.next() + " ";
			}		
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		finally { 
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	} return fileContents;	
	}


}
