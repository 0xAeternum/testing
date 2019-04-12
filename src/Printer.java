import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Printer implements InkOperator{
	
	private boolean loggedAsUser;
	private boolean loggedAsEmp;
	
	private int ink;
	private ArrayList<ColoredInk> inkColors;
	
	public Printer(int ink, ArrayList<ColoredInk> inkColors) {
		loggedAsUser = false;
		loggedAsEmp = false;
		this.ink = ink;
		this.inkColors = inkColors;
	}
	
	public boolean loginUser() {
		return loggedAsUser = true;
	}
	
	public boolean loginEmployee() {
		return loggedAsEmp = true;
	}
	
	public boolean isLoggedAsUser() {
		return loggedAsUser;
	}
	
	public boolean isLoggedAsEmp() {
		return loggedAsEmp;
	}
	
	public int getInk() {
		return ink;
	}
	
	public void loadInk() {
		if (loggedAsEmp) {
			ink = 100;
		}
	}
	
	public void logout() {
		loggedAsEmp = false;
		loggedAsUser = false;
	}
	
	public boolean isInkLow() {
		if (ink < 50) {
			return true;
		}
		
		return false;
	}
	
	public boolean print(File file) {
		boolean isPrintPossible = loggedAsEmp && ink > 10 || loggedAsUser && ink > 10;
		if (isPrintPossible) {
			ink -= 10;
		}
		
		return isPrintPossible;
	}
	
	public boolean scan(File file) {
		return loggedAsEmp || loggedAsUser;
	}
	
	public String getFileContents(File file) throws IOException {
		String result = "";
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		reader.close();
		
		return result;
	}
	

}
