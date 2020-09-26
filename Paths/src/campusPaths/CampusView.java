package campusPaths;

import cParser.*;
import cParser.CampusParser.MalformedDataException;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import dGraph.DGraph;

public class CampusView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	//list that holds the path to highlight on the map
	private List<Building> path;
	
	//list to hold all buildings on the map
	private List<Building> buildings;
	
	//graph to represent the paths on the map
	private DGraph<Building, Double> graph;
	
	//image of map
	private BufferedImage mapImage;
	
	/**
	 * Constructs a GUI of CampusPaths
	 * 
	 * @throws MalformedDataException
	 */
	public CampusView() throws MalformedDataException {
		buildings = new ArrayList<>();
		path = new ArrayList<>();
		CampusParser.buildingParser("src/cParser/data/campus_buildings.dat", buildings);
		this.graph = CampusPaths.makeGraph("src/cParser/data/campus_paths.dat");
        
		try {
			mapImage = ImageIO.read(new File("src/cParser/data/campus_map.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Paints the map with a path highlighted if there is one
	 * 
	 * @param g the graphics context where the painting should take place
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
        
        int mapWidth = getWidth();
        int mapHeight = getHeight();
        
        double scaleX = (double)mapWidth / mapImage.getWidth();
        double scaleY = (double)mapHeight / mapImage.getHeight();
        
		g2.drawImage(mapImage, 0, 0, mapWidth, mapHeight, 0, 0, mapImage.getWidth(), 
				mapImage.getHeight(), null);
		
		
		if (!path.isEmpty()) {
			int startX = (int) (path.get(0).getX()*scaleX);
			int startY = (int) (path.get(0).getY()*scaleY);
			
			int endX = (int) (path.get(path.size()-1).getX()*scaleX);
			int endY = (int) (path.get(path.size()-1).getY()*scaleY);
			
            int currentX = startX;
            int currentY = startY;
            
			g2.setColor(Color.MAGENTA);
			g2.setStroke(new BasicStroke(3));
			for(int i = 0; i < path.size() ; i++) {
				int nextX = (int)Math.round(path.get(i).getX()*scaleX);
				int nextY = (int)Math.round(path.get(i).getY()*scaleY);
				g2.drawLine(currentX, currentY, nextX, nextY);
                currentX = nextX;
                currentY = nextY;
			}
			
			g2.setColor(Color.RED);
			
			g2.fillRect(startX, startY, 10, 10);
			g2.fillRect(endX, endY, 10, 10);
		}
	}
	
	/**
	 * paints path to two buildings
	 * 
	 * @param start long name of start building
	 * @param end long name of end building
	 * @modifies path if path is found
	 * @effects path contains the shortest path between start and end if a path is found
	 */
	public void getPath(String start, String end) {
		String startAbr = "";
		String endAbr = "";
		
		for(Building building : buildings) {
			if (building.getLongName().equals(start)) {
				startAbr = building.getShortName();
			}
			if (building.getLongName().equals(end)) {
				endAbr = building.getShortName();
			}
		}
		try { CampusPaths.shortestRoute(buildings, graph, startAbr, endAbr, path);
		} catch (IllegalArgumentException e) {
		}
		repaint();
	}
	
	/**
	 * resets GUI
	 * 
	 * @modifies path
	 * @effects clear the path
	 */
	public void reset() {
		path.clear();
		repaint();
	}
}
