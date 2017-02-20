package lab;

import org.testng.annotations.Test;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;


public class listner {
	@Test
	 public void actionPerformed() {
		 JOptionPane.showMessageDialog(null,"You clicked the button!"); 
		 System.out.println("done ");
		// attach a listener to handle events on this button 
		 //button1.addActionListener(new MessageListener());
		 }
 }

