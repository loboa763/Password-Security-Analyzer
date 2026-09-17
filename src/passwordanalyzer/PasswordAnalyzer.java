// A package is basically a way of organizing related Java classes
package passwordanalyzer;

//Imports the scanner class so we can get keyboard input from the user.
import java.util.Scanner;

 
public class PasswordAnalyzer {
	
	//The main method where the program starts running
	public static void main(String[] args) {
		
		//creates a scanner called input
		//System.in means we want to read input from the keyboard
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your password: ");
		//Reads the users input and stores it in the password variable
		String password = input.nextLine();
		System.out.println("===== PASSWORD ANALYSIS =====");
		int length = password.length();
		if(length<8) {
			System.out.println("Password length: " + length+ " (too short)");
		}
		else {
			System.out.println("Password length: " + length);
		}
		
		boolean hasUppercase = false;
		boolean hasLowercase = false;
		boolean hasNumber = false;
		boolean hasSpecialCharacter = false;
		
		for(int i = 0; i<password.length(); i++) {
			char c = password.charAt(i);
			if(Character.isUpperCase(c)) {
				hasUppercase = true;
			}
			if(Character.isLowerCase(c)) {
				hasLowercase = true;
			}
			if(Character.isDigit(c)) {
				hasNumber = true;
			}
			if(!Character.isDigit(c) && !Character.isLetter(c)) {
				hasSpecialCharacter = true;
			}
			
			if(hasUppercase && hasLowercase && hasNumber && hasSpecialCharacter) {
				break;
			}
		}
		
		if(hasUppercase) {
			System.out.println("Uppercase: Yes");
		}
		else{
			System.out.println("Uppercase: No");
		}
		
		
		if(hasLowercase) {
			System.out.println("Lowercase: Yes");
		}
		else {
			System.out.println("Lowercase: No");
		}
		
		if(hasNumber) {
			System.out.println("Number: Yes");
		}
		else {
			System.out.println("Number: No");
		}
		
		if(hasSpecialCharacter) {
			System.out.println("Special character: Yes");
		}
		else {
			System.out.println("Special character: No");
		}
		
		int score = 0;
		if(length>=8) {
			score++;
		}
		if(hasUppercase) {
			score++;
		}
		if(hasLowercase) {
			score++;
		}
		if(hasNumber) {
			score++;
		}
		if(hasSpecialCharacter) {
			score++;
		}
		
		System.out.println("Password score: " + score + "/5");
		
		String passwordStrength = "";
		
		if(score <= 1) {
			passwordStrength = "Very Weak";
		}
		else if(score == 2) {
			passwordStrength = "Weak";
		}
		else if(score == 3) {
			passwordStrength = "Medium";
		}
		else if (score == 4) {
			passwordStrength = "Strong";
		}
		else{
			passwordStrength = "Very Strong";
		}
		
		System.out.println("Password Strength: " + passwordStrength);
		
		int poolSize = 0;
		if(hasNumber) {
			poolSize += 10;
		}
		if(hasUppercase) {
			poolSize += 26;
		}
		if(hasLowercase) {
			poolSize +=26;
		}
		if(hasSpecialCharacter) {
			poolSize +=32;
		}
		
		double entropy = 0;
		
		if(poolSize>0) {
			entropy = length * (Math.log(poolSize) / Math.log(2));
		}
		
		System.out.printf("Password entropy: %.2f bits%n", entropy);
		
		String entropyStrength = "";
		
		if(entropy < 28) {
			entropyStrength = "Weak";
		}
		else if(entropy <50) {
			entropyStrength = "Moderate";
		}
		else{
			entropyStrength = "Strong";
		}
		
		System.out.println("Entropy Strength: " + entropyStrength);
		
		input.close();
		
	}

}
