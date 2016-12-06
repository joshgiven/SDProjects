package caesarcipher;

public class CaesarCipher {

	public static void main(String[] args) {
		CaesarInputter inputter = new CaesarInputter();	
		CaesarOutputter outputter = new CaesarOutputter();
		
		String mode = inputter.getModeInput();
		
		String userinput;
		if(mode.equals("ENCRYPT")) {
			userinput = inputter.getEncryptedTextInput();
		}
		else {
			userinput = inputter.getDecryptedTextInput();			
		}
		
		int shiftNum = inputter.getShiftInput();
	
		CaesarEncrypt encrypter = new CaesarEncrypt();
		encrypter.setShift(shiftNum);

		if(mode.equals("ENCRYPT")) {
			String encryptedText = encrypter.encrypt(userinput);
			outputter.printEncryptedData(encryptedText);
		}
		else {
			String decryptedText = encrypter.decrypt(userinput);
			outputter.printDecryptedData(decryptedText);
		}
	}

}
