package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("An input file is in an invalid format. Check the documents.");
		logError();
	}
	
	public BadConfigFormatException(String fileName) {
		super(fileName + " is in an invalid format. Check the document.");
		logError();
	}

	public void logError() {
		try {
			FileWriter fstream = new FileWriter("ErrorLog.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getMessage());
			out.close();
		} 
		catch (Exception e) {
			System.out.println("Error in writing to log: " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
