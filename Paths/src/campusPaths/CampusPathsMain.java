package campusPaths;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


import cParser.CampusParser.MalformedDataException;

/**
 *
 * Main class to run Campus paths with a GUI
 * @author Bill Phung
 *
 */
public class CampusPathsMain {
	
	public static void main(String[] args) throws MalformedDataException {
		JFrame frame = new JFrame("Campus Shortest Path Finder");
		frame.setPreferredSize(new Dimension(1314, 900));
		
		CampusView view = new CampusView();
		CampusButtons buttons = new CampusButtons(view);

		frame.add(view);
		frame.add(buttons, BorderLayout.NORTH);
		frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
}
