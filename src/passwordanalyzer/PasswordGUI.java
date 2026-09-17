package passwordanalyzer;

import javax.swing.JFrame;
//A JFrame is basically a window.(Square application window).
import javax.swing.JLabel;
//A JLabel is simply text displayed in the GUI.
import javax.swing.JPasswordField;

import java.awt.GridLayout;

import javax.swing.JButton;
// A JButton is a clickable button in the GUI;

import javax.swing.JProgressBar;


//PasswordGUI inherits from JFrame (Parent class/Child class) inheritance relationship is-a. Class gets access to JFrame functionality such as setTitle("Password Security Analyzer"); setSize(450, 300); setVisible(true); setLocationRelativeTo(null);
public class PasswordGUI extends JFrame{
	public static void main(String args[]) {
		
		PasswordGUI window = new PasswordGUI();
		window.setTitle("Password Security Analyzer");
		window.setSize(450,300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//When the X is clicked, completely terminate the operation
		window.setLocationRelativeTo(null); //center on the screen, not another component
		window.setLayout(new GridLayout(0,1));
		//GridLayout organizes components into a grid of rows and columns.
		JLabel passwordLabel = new JLabel("Enter password: ");
		window.add(passwordLabel);
		
		JPasswordField passwordField = new JPasswordField();
		window.add(passwordField);
		
	
		JButton analyzeButton = new JButton("Analyze");
		window.add(analyzeButton);
		
		JLabel resultLabel = new JLabel("Results will appear here");
		window.add(resultLabel);
		
		JProgressBar strengthBar = new JProgressBar(0,5);
		strengthBar.setStringPainted(true);
		window.add(strengthBar);
		
		JLabel entropyLabel = new JLabel("Entropy: ");
		window.add(entropyLabel);
		
		JLabel crackTimeLabel = new JLabel("Estimated Crack Time: ");
		window.add(crackTimeLabel);
		
		JLabel hashLabel = new JLabel("SHA-256 Hash: ");
		window.add(hashLabel);
		
		
		
		analyzeButton.addActionListener(e -> {
			//An ActionListener basically waits/listens for an action to happen to a button.
			//Attach something to analyzeButton that listens for the button being clicked.
			//Button gets clicked e-> run whatever is inside { } - Get password Analyze password, Calculate score, Calculate entropy, Display results
			
			char[] passwordCharacters = passwordField.getPassword();
			String password = new String(passwordCharacters);
			String strength = PasswordAnalyzer.analyzePassword(password);
			double entropy = PasswordAnalyzer.calculateEntropy(password);
			double crackTime = PasswordAnalyzer.calculateCrackTime(entropy);
			String formattedCrackTime = PasswordAnalyzer.formatCrackTime(crackTime);
			String passwordHash = PasswordAnalyzer.hashPassword(password);
			//This is the connection between your two Java files: PasswordGUI.java "Hello123#" -> passwordField.getPassword(); -> String password = "Hello123!" ->  PasswordAnalyzer.analyzePassword(password) -> PasswordAnalyzer.java - runs all the logic you already wrote
			resultLabel.setText("Password Strength: " + strength);
			//actually using a return value between two classes: PasswordGUI(password)->PasswordAnalyzer(return "Very Strong")->PasswordGUI(JLabel displays "Password Strength: Very Strong"
			
			if(strength.equals("Very Weak")) {
				strengthBar.setValue(1);
			}
			
			else if(strength.equals("Weak")) {
				strengthBar.setValue(2);
			}
			
			else if(strength.equals("Medium")) {
				strengthBar.setValue(3);
			}
			
			else if(strength.equals("Strong")) {
				strengthBar.setValue(4);
			}
			
			else {
				strengthBar.setValue(5);
			}
			
			
			strengthBar.setString(strength);
			
			entropyLabel.setText(String.format("Entropy: %.2f bits", entropy));
			
			crackTimeLabel.setText("Estimated Crack Time: "+ formattedCrackTime);
			
			hashLabel.setText("SHA-256 Hash: " + passwordHash);
		});
		
			

		
		window.setVisible(true);
	}
	
}
