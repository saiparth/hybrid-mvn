package lab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;

public class frames {
	public static void main(String[] args) {
		/*JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("A window frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(new Point(10, 50));
		frame.setSize(new Dimension(300, 120));
		frame.setTitle("A frame");
		frame.setVisible(true);*/
		 JFrame frame = new JFrame(); 
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
         frame.setSize(new Dimension(300, 100)); 
         frame.setTitle("A frame"); 
		JButton button1 = new JButton();
		button1.setText("I'm a button.");
		button1.setBackground(Color.BLUE);
		frame.add(button1);
		JButton button2 = new JButton();
		button2.setText("Click me!");
		button2.setBackground(Color.RED);
		frame.add(button2);
		frame.setVisible(true);
	}
}
