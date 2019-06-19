import java.util.Arrays;

/*
 * This class creates the Monoalphabet cipher encodes/decodes strings passed to it
*/
public class MonoAlphabet {
	char[][] cipher = new char[2][26];
	
	public MonoAlphabet(String keyword) {
		//populates both arrays in the matrix with the alphabet
		for(int i = 0; i < 26; i++) {
			cipher[0][i] = (char)(i+65);
			cipher[1][i] = (char)(i+65);
		}
		
		//turns all chars with the same value as those in the keyword to zero
		for(int i = 0; i < 26; i++) {
			for(int j = 0; j < keyword.length(); j++)
			if(cipher[1][i] == keyword.charAt(j)) {
				cipher[1][i] = 0;
			}
		}
		//sorts the array in numeric order, beginning with the zeroes representing the keyword.
		Arrays.sort(cipher[1]);
		
		//inserts the keyword chars into the start of the array. 
		for(int i = 0; i < keyword.length(); i++) {
			cipher[1][i] = keyword.charAt(i);
		}
	}
	
	
	public String encodeString(String a) {
		String line = "";
		for(int i = 0; i < a.length(); i++) {
			line += encodeCharacter(a.charAt(i));
		}
		return line;
	}
	
	public String decodeString(String a) {
		String line = "";
		for(int i = 0; i < a.length(); i++) {
			line += decodeCharacter(a.charAt(i));
		}
		return line;
	}
		
	public char encodeCharacter(char a) {
		for(int i = 0; i < 26; i++) {
			if(a == cipher[0][i]) {
				a = cipher[1][i];
				break;
			}
		}
		return a;
	}
	
	public char decodeCharacter(char a) {
		for(int i = 0; i < 26; i++) {
			if(a == cipher[1][i]) {
				a = cipher[0][i];
				break;
			}
		} return a;
	}
	
	
	public String toString() {
		String line = "";
		//for loop to print the two arrays. 
		for (int i=0; i < 2;i++) {
			for(int j = 0; j< 26;j++) {
			line += this.cipher[i][j]+ " ";
			}
		line +=	'\n';
		}
		return line;
	}
}
