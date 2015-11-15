package reParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class reParser {
	public static void main(String[] args) throws IOException {
		String sCurrentLine;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("test"));
		String s = "";
		while ((sCurrentLine = br.readLine()) != null) {
			// split   ¡A ()
			String[] tokens = sCurrentLine.split("¡A| |(|)");
			for(String token : tokens){
				
			}
		}
	}
}
