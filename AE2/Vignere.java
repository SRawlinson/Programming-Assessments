
public class Vignere {
	
/*	
 * This class creates the vignere cipher and encodes or decodes strings passed to it
*/	
	char[] alphabet = new char[26];
	char[][] cipher;
	String keyword;
	
	public Vignere(String key) {
		keyword = key;
		//populates alphabet key
		for(int i = 0; i < 26; i++) {
			alphabet[i] = (char)(i +65);
		}
		
			char[][] temporaryCipher = new char[keyword.length()][26];
			cipher = temporaryCipher;
	
		//populates cipher based on keyword. 
		for(int i = 0; i < keyword.length(); i++) {
			for(int j = 0; j < 26; j++ ) {
				cipher[i][j] = (char)((int)(keyword.charAt(i) + j));
				if((int)(cipher[i][j]) > 90) {
					cipher[i][j] = (char)((int)(cipher[i][j] - 26));
				}
			}
		}		
	}
	//encodes string - calls method to encode char
	public String encodeString(String message) {
		String secretMessage = "";
		int row = 0;
		for(int i = 0; i < message.length(); i++) {
			if(row == keyword.length()) { 
				row = 0;
			}
			if( message.charAt(i)> 64 && message.charAt(i) < 91 ) {
			secretMessage += encodeChar(message.charAt(i), row);
				row++;
			
		} else { 
			secretMessage += message.charAt(i);
		}
		}
		return secretMessage;
	}
	
	public char encodeChar(char a, int row) {
		for(int i = 0; i < 26; i++) {
			if(a == alphabet[i]) {
				a = cipher[row][i];
				break;
		}			
		}
		return a;	
	}
	//decodes string - calls method to decode char
	public String decodeString(String message) {
		String secretMessage = "";
		int row = 0;
		for(int i = 0; i < message.length(); i++) {
			if(row == keyword.length()) { 
				row = 0;
			}
			if( message.charAt(i)> 64 && message.charAt(i) < 91 ) {
			secretMessage += decodeChar(message.charAt(i), row);
				row++;
			} else {
				secretMessage += message.charAt(i);
			}
		}
		 return secretMessage;
	}
	
	public char decodeChar(char a, int row) {
		for(int i = 0; i < 26; i++) {
			if(a == cipher[row][i]) {
				a = alphabet[i];
				break;
			}
		} return a;
	}
	//ToString method for testing
	public String toString() {
		String line = "";
		for (int i=0; i < keyword.length();i++) {
			for(int j = 0; j< 26;j++) {
			line += cipher[i][j]+ " ";
			}
		line +=	'\n';
		}
		return line;
	}
}
