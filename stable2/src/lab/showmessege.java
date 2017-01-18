package lab;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class showmessege {
	//@Test
	public static boolean condition(){
		int a=1,b=2;
		int c=(a>b)?a:b;
		System.out.println(c);
		return true;
		
	}
	//@Test
	public  void logical(){
		System.out.println("Main Method starts");
		int a = 10;
		int b = 20;
		boolean res = a>=10 && condition();
		Logger log=Logger.getLogger(showmessege.class);
		 PropertyConfigurator.configure("log4j.properties");
		log.info("helloworld");
		log.debug("Sample debug message");
		log.info("Sample info message");
		log.warn("Sample warn message");
		log.error("Sample error message");
		log.fatal("Sample fatal message");
		System.out.println("res = "+res);
		System.out.println("Main Method Ends");
	}
	public static void main(String[] args) {
		
	//}
	//public static void optionpane() {
		// read the user's name graphically
		String name = JOptionPane.showInputDialog(null, "What is your name?");
		// ask the user a yes/no question 12
		int choice = JOptionPane.showConfirmDialog(null, "Do you like cake, " + name + "?");
		// show different response depending on answer 16
		
		if (choice == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Of course! Who doesn't?");
		
		} else {
			// choice == NO_OPTION or CANCEL_OPTION 20
			JOptionPane.showMessageDialog(null, "We'll have to agree to disagree.");
		}
	}
}
