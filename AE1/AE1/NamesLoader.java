import java.util.ArrayList;
import java.io.*;
public class NamesLoader {
	public static ArrayList<String> loadNames(String filename) {
		ArrayList<String> names = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String name;
			while((name = reader.readLine())!=null) {
				names.add(name);
			}
			reader.close();
			return names;
		}catch(IOException e) {}
		return names;
	}
}
