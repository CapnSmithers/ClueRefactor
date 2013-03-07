package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		error();
	}

	public void error() {
		try {
			FileWriter fstream = new FileWriter("ErrorLog.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("An input file is in an invalid format. Check the documents.");
			out.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public BadConfigFormatException(String name) {
		error(name);
	}
	public void error(String name) {
		try {
			FileWriter fstream = new FileWriter("ErrorLog.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(name + " is in an invalid format. Check the document.");
			out.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
