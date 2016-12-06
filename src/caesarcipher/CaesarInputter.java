package caesarcipher;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CaesarInputter {
	
	private Scanner kb = new Scanner(System.in);
	
	private String getTextInput(String prompt){
		System.out.print(prompt);
		
		Pattern oldDelimiter = kb.delimiter();
		kb.useDelimiter("\n");
		String input = kb.next().toUpperCase();
		kb.useDelimiter(oldDelimiter);
		return input;
	}
	
	public String getEncryptedTextInput(){
		return getTextInput("Please type a word to be encrypted: " );
	}
	
	public String getDecryptedTextInput(){
		return getTextInput("Please type a word to be decrypted: " );
	}
	
	public int getShiftInput(){
		int shiftNum;
		
		do {
			System.out.print("How many numbers would you like to shift over: ");
			if(kb.hasNextInt()) {
				shiftNum = kb.nextInt();
				if(shiftNum < 1 || shiftNum > 25) {
					System.out.println("Shift values must be in the range 1-25.");	
				}
				else {
					return shiftNum;
				}
			}
			else {
				kb.next(); // throw away the invalid string
				System.out.println("Invalid choice. Try again.");
			}
		} while(true);
	}

	public String getModeInput() {
		char inputVal;
		
		do {
			System.out.print("Do you want to (E)ncrypt or (D)ecrypt: ");
			inputVal = kb.next().toUpperCase().charAt(0);
			
			if(inputVal == 'E') {
				return "ENCRYPT";	
			}
			else if(inputVal == 'D') {
				return "DECRYPT";
			}
			else {
				System.out.println("Not a valid choice. Try again.");
			}
		} while(true);
	}
}
