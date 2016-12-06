package caesarcipher;

public class CaesarEncrypt {

	private int shift = 0;
	public int getShift(){
		return shift;
	}
	public void setShift(int newShift){
		shift = newShift;
	}
	
	private String encrypt(String text, int shiftVal){
		char[] textChars = text.toCharArray();
		char[] encryptedTextChars = new char[textChars.length];
		
		for (int i = 0; i < textChars.length ; i++){
			int actualShift = shiftVal;

			if(textChars[i] >= 'A' && textChars[i] <= 'Z') {
				if (textChars[i] + actualShift > 'Z'){
					actualShift = actualShift - 26;
				}
				
				char c = (char)(textChars[i] + actualShift);
				
				if(c < 'A') {
					c = (char)(c + 26);
				}
				
				encryptedTextChars[i] = c; 
			}
			else {
				encryptedTextChars[i] = textChars[i];
			}				
		}
		
		String retVal = new String(encryptedTextChars);
		return retVal;
	}
	
	public String encrypt(String text){
		return encrypt(text, shift);
	}
	
	public String decrypt(String encryptedText){
		return encrypt(encryptedText, -shift);
	}
	
}
