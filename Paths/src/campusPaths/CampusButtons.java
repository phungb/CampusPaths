package campusPaths;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cParser.Building;
import cParser.CampusParser;
import cParser.CampusParser.MalformedDataException;

/**
 * 
 * This panel contains the buttons for the Campus GUI
 * buttons allow user to reset and find paths
 * @author Bill
 *
 */
public class CampusButtons extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	//start building input options
	JComboBox<String> startInput;
	
	//end building input options
	JComboBox<String> endInput;
	
	//view of map
	private CampusView view;
	
	/**
	 * Construct buttons to add to GUI
	 * 
	 * @param view the map that CampusPaths is located on
	 * @throws MalformedDataException if Malformed data is used
	 */
	public CampusButtons(CampusView view) throws MalformedDataException {
		
		this.view = view;
		
		List<Building> buildings = new ArrayList<>();
		CampusParser.buildingParser("src/cParser/data/campus_buildings.dat", buildings);
		String[] names = getBuildingLongNames(buildings);
		
		Dimension size = getPreferredSize();
		size.height = 100;
		setPreferredSize(size);
		
		JLabel startLoc = new JLabel("Starting Location: ");
		JLabel endLoc = new JLabel("End Location: ");
		
		startInput = new JComboBox<String>(names);
		endInput = new JComboBox<String>(names);
		
		JButton rst = new JButton("Reset");
		JButton find = new JButton("Find Path");
		find.addActionListener(new NewActionListener());
		rst.addActionListener(new NewActionListener());
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		//// First column ///////////////////////////
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		this.add(startLoc, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		this.add(startInput, gc);
		
		//// Second column //////////////////////
		gc.gridx = 1;
		gc.gridy = 0;
		
		this.add(endLoc, gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		
		this.add(endInput, gc);
		
		//// Third Column ////////////////////////	
		gc.gridx = 2;
		gc.gridy = 0;
		
		this.add(find, gc);
		
		gc.gridx = 2;
		gc.gridy = 1;
		
		this.add(rst, gc);
	}
	
	/**
	 * Overrides ActionListener to update buttons to either find the shortest route or reset the GUI
	 */
	private class NewActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = e.getActionCommand();
			if (name.equals("Find Path")) {
				String startBuilding = startInput.getSelectedItem().toString();
				String endBuilding = endInput.getSelectedItem().toString();
				view.getPath(startBuilding, endBuilding);
			} else {
				startInput.setSelectedIndex(0);
				endInput.setSelectedIndex(0);
				view.reset();
			}
		}
	}
	
	/**
	 * Get all Long names of the buildings on map
	 * 
	 * @param buildings list of buildings that are on the map
	 * @return an array of long names of the buildings on the map
	 */
	private String[] getBuildingLongNames(List<Building> buildings) {
		String[] names = new String[buildings.size()];
		for(int i = 0; i < buildings.size(); i++) {
			names[i] = buildings.get(i).getLongName();
		}
		return names;
	}
}
