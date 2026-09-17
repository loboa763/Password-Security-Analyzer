// A package is basically a way of organizing related Java classes
package passwordanalyzer;

//Imports the scanner class so we can get keyboard input from the user.
import java.util.Scanner;

import java.security.MessageDigest;
//MessageDigest does the hashing
import java.security.NoSuchAlgorithmException;
//NoSuchAlgorithmException handles the possibility that java can't find SHA-256
import java.nio.charset.StandardCharsets;
//StandardCharsets converts the password into bytes in a consistent format



 
public class PasswordAnalyzer {
	
	//The main method where the program starts running
	public static void main(String[] args) {
		
		//creates a scanner called input
		//System.in means we want to read input from the keyboard
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter your password: ");
		//Reads the users input and stores it in the password variable
		String password = input.nextLine();
		
		analyzePassword(password);
		
		input.close();	
		
				
	}
	
	public static String analyzePassword(String password) {
		
		
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
		
		return passwordStrength;
		
		
	}
	
	public static double calculateEntropy(String password) {
		
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
		
		if(poolSize==0) {
			return 0;
		}
		
		return password.length() * (Math.log(poolSize) / Math.log(2));
		
		
		
	}
	
	public static double calculateCrackTime(double entropy) {
		
		//entropy-> 2^entropy-> possible password combinations -> divide by 1 billion guesses/second -> estimated seconds to brute force
		
		double combinations = Math.pow(2, entropy);
		double guessesPerSecond = 1_000_000_000;
		
		double seconds = combinations/guessesPerSecond;
		
		return seconds;
		
		
	}
	
	public static String formatCrackTime(double seconds) {
		
		if(seconds < 60) {
			return String.format("%.2f seconds", seconds);
		}
		
		else if(seconds < 3600) {
			return String.format("%.2f minutes", seconds / 60);
		}
		
		else if(seconds < 86400) {
			return String.format("%.2f hours", seconds / 3600);
		}
		
		else if(seconds < 31536000) {
			return String.format("%.2f days", seconds / 86400);
		}
		else {
			return String.format("%.2f years", seconds / 31536000);
		}
	}
	
	public static String hashPassword(String password) {
		//password String-> convert to bytes-> SHA-256 processes bytes-> 32-byte hash -> for loop converts bytes to hexadecimal -> 64-character String

	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");

	        byte[] hashBytes = digest.digest(
	                password.getBytes(StandardCharsets.UTF_8)
	        );

	        StringBuilder hash = new StringBuilder();

	        for(int i = 0; i < hashBytes.length; i++) {
	            hash.append(String.format("%02x", hashBytes[i]));
	        }

	        return hash.toString();
	    }
	    catch(NoSuchAlgorithmException e) {
	        return "Hash unavailable";
	    }
	}
	
	

}
