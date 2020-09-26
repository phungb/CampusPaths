package hw8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import dGraph.DGraph;
import hw8.CampusParser.MalformedDataException;

public class CampusPaths {

	public static void main(String[] args) {
		try {
			Scanner console = new Scanner(System.in);

			List<Building> buildings = new ArrayList<Building>();
			Map<Building, Map<Building, Set<Double>>> paths = new HashMap<Building, Map<Building, Set<Double>>>();
			CampusParser.buildingParser("src/hw8/data/campus_buildings.dat", buildings);
			CampusParser.pathsParser("src/hw8/data/campus_paths.dat", paths);
			DGraph<Building, Double> graph = makeGraph("src/hw8/data/campus_paths.dat");
			menu();
			System.out.println();
			while(true) {

				System.out.print("Enter an option ('m' to see the menu): ");
				String input = console.nextLine();

				while(input.startsWith("#") || input.length() == 0) {
					System.out.println(input);
					input = console.nextLine();
				}

				if (input.equals("b")) {
					printBuildings(buildings);
				} else if (input.equals("q")) {
					break;
				} else if (input.equals("m")) {
					menu();
				} else if (input.equals("r")) {
					System.out.print("Abbreviated name of starting building: ");
					String s = console.nextLine();
					Building start = findBuilding(buildings, s);
					System.out.print("Abbreviated name of ending building: ");
					String e = console.nextLine();
					Building end = findBuilding(buildings, e);

					if(start == null && end == null) {
						System.out.println("Unknown building " + s);
						System.out.println("Unknown building " + e);
					} else if (start == null) {
						System.out.println("Unknown building " + s);
					} else if (end == null) {
						System.out.println("Unknown building " + e);
					} else {
						System.out.println("Path from " + start.getLongName() + " to " + end.getLongName() + ":");
						List<Building> path = new ArrayList<Building>();			
						shortestRoute(buildings, graph, s, e, path);
						pathToString(graph, path);
					}
				} else {
					System.out.println("Unknown Option");
				}
				System.out.println();
			}
			console.close();
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}	
	}

	/**
	 * given a list of buildings prints them out
	 * 
	 * @param buildings list of buildings to print out
	 * @required buildings !=null;
	 */
	private static void printBuildings(List<Building> buildings) {
		System.out.println("Buildings:");
		Set<Building> temp = new TreeSet<Building>(
				new Comparator<Building>() {
					public int compare(Building a, Building b) {
						if(a.getShortName().equals(b.getShortName())) {
							return a.getLongName().compareTo(b.getLongName());
						} else {
							return a.getShortName().compareTo(b.getShortName());
						}
					}
				});
		temp.addAll(buildings);
		for(Building b : temp) {
			System.out.println("\t" + b.getShortName() + ": " + b.getLongName());
		}

	}

	/**
	 * Builds a graph from a given file
	 * @param filename 
	 *   		file to retrieve the information to input into the graph
	 * @requires filename != null 
	 * @return the graph containing the information of the given file
	 * @throws MalformedDataException if file is not in the right format
	 */
	public static DGraph<Building, Double> makeGraph(String filename) throws MalformedDataException {
		DGraph<Building, Double> graph = new DGraph<Building, Double>();
		Map<Building, Map<Building, Set<Double>>> paths = new HashMap<Building, Map<Building, Set<Double>>>();
		CampusParser.pathsParser(filename, paths);

		for(Building start : paths.keySet()) {
			graph.addNode(start);
		}

		for(Building start : paths.keySet()) {
			for(Building endLocation : paths.get(start).keySet()) {
				for(Double cost : paths.get(start).get(endLocation)) {
					graph.addEdge(start, endLocation, cost);
				}
			}
		}
		return graph;
	}

	/**
	 * Finds the shortest path from one building to another building if a path exists
	 * 
	 * @param graph 
	 * 			the graph to find the path on
	 * @Param buildings
	 * 		  	list of buildings from which to seach path to and from
	 * @param start 
	 * 			start node, the beginning of the path
	 * @param end 
	 * 			end node, the end of the path
	 * @param shortestPath
	 * 			list to put the path in if path exists
	 * @requires graph != null && start != null && end != null && shortestPath != null && building != null
	 * @modifies shortestPath
	 * @effects adds the shortest path from start to end to shortestPath
	 * @return true if there is a path from start node to end node
	 * @throws IllegalArgumentException if either start or end is 
	 * not in the graph
	 */
	public static boolean shortestRoute(List<Building> buildings, DGraph<Building, Double> graph, 
			String start, String end, List<Building> shortestPath) {
		if(graph == null) {
			throw new IllegalArgumentException("graph cannot be null");
		}

		if(start == null || end == null) {
			throw new IllegalArgumentException("start or end cannot be null");
		}

		if(findBuilding(buildings, start) == null || findBuilding(buildings, end) == null) {
			throw new IllegalArgumentException("start or end is not a valid building");
		}

		Set<Building> finished = new HashSet<Building>();
		Map<Building, Double> updatedCost = new HashMap<Building, Double>();
		PriorityQueue<List<Building>> active = new PriorityQueue<List<Building>>(pathCompare(graph, updatedCost));
		List<Building> startList = new ArrayList<Building>();

		Building endB = findBuilding(buildings, end);
		Building tempEndB = new Building("", "", endB.getX(), endB.getY());

		Building startB = findBuilding(buildings, start);
		Building tempStart = new Building("", "", startB.getX(), startB.getY());

		updatedCost.put(tempStart, 0.0);
		startList.add(tempStart);

		active.add(startList);
		while(!active.isEmpty()) {
			List<Building> minPath = active.remove();
			Building minDest = minPath.get(minPath.size() - 1);
			if(minDest.equals(tempEndB)) {
				shortestPath.clear();
				for(int i = 0; i < minPath.size(); i++) {
					shortestPath.add(minPath.get(i));
				}
				return true;

			} 

			if(finished.contains(minDest)) {
				continue;
			}

			if((minPath.size() >= 2)) {
				Double addCost = graph.getLabel(minPath.get(minPath.size() - 2), minDest).get(0); 
				Double soFar = updatedCost.get(minPath.get(minPath.size() - 2));
				updatedCost.put(minDest, addCost + soFar);
			}

			finished.add(minDest);
			List<Building> newDests = graph.getChildren(minDest);
			for(Building newDest : newDests) {
				if(!finished.contains(newDest)) {
					List<Building> temp = new ArrayList<Building>(minPath);
					temp.add(newDest);
					active.add(temp);
				}
			}

		}
		return false;
	}

	/**
	 * Finds a building from a abbreviated name
	 * 
	 * @param buildings list of buildings to find from
	 * @param name abbreviated name of building
	 * @requires angle != null && buildings != null
	 * @return null if building is not contained in buildings. The building associated with the abbreviated
	 * 			name otherwise
	 */
	public static Building findBuilding (List<Building> buildings, String name) {
		if(name == null) {
			throw new IllegalArgumentException();
		}
		for (Building b : buildings) {
			if(b.getShortName().equals(name)) {
				return b; 
			}
		}
		return null;
	}

	/**
	 * prints out a compass direction from a given angle
	 * 
	 * @requires angle != null
	 * @param angle angle to find the compass direction of
	 * @return compass direction of angle
	 */
	public static String getDirection(Double angle) {
		double pi = Math.PI;
		if (angle < (7*pi/8) && angle > (5*pi/8)) {
			return "SW";
		} else if (angle <= 5*pi/8 && angle >= 3*pi/8) {
			return "S"; 
		} else if (angle < 3*pi/8 && angle > pi/8) {
			return "SE";
		} else if (angle <= pi/8 && angle >= -pi/8) {
			return "E";
		} else if (angle < -pi/8 && angle > -3*pi/8) {
			return "NE";
		} else if (angle <= -3*pi/8 && angle >= -5*pi/8) {
			return "N";
		} else if (angle < -5*pi/8 && angle > -7*pi/8) {
			return "NW";
		} else {
			return "W"; 
		}
	}

	/**
	 * Prints out a path from a given list and end location
	 * 
	 * @param graph 
	 * 			the graph to find the path on
	 * @param path
	 * 			list that represents a path
	 * @param end
	 * 			end node of the path
	 * @requires graph != null && path != null && end != null
	 */
	public static void pathToString(DGraph<Building,Double> graph, List<Building> path) {
		double total = 0; 
		for(int i = 0; i < path.size() - 1; i++) {
			Building first = path.get(i);
			Building second = path.get(i + 1);
			total += graph.getLabel(first, second).get(0);
			Double angle = Math.atan2(second.getY() - first.getY(), second.getX() - first.getX());
			String direction = getDirection(angle);
			System.out.println("\tWalk " + Math.round(graph.getLabel(first, second).get(0)) +  " feet " + direction + 
					" to (" + Math.round(second.getX()) + ", " + Math.round(second.getY()) + ")" );
		}
		System.out.println("Total distance: " + Math.round(total) + " feet");
	}

	/**
	 * Prints out a menu of options
	 * 
	 */
	public static void menu() {
		System.out.println("Menu:");
		System.out.println("\t" + "r to find a route");
		System.out.println("\t" + "b to see a list of all buildings");
		System.out.println("\t" + "q to quit");
	}

	/**
	 * A comparator used to compare two paths
	 * 
	 * @param graph
	 * 			graph in which path costs can be calculated
	 * @return A comparator that can be used to compare the costs of two paths
	 */
	private static Comparator<List<Building>> pathCompare(DGraph<Building, Double> graph, Map<Building, Double> updatedCost) {
		Comparator<List<Building>> comp = new Comparator<List<Building>>() {

			@Override
			public int compare(List<Building> p1, List<Building> p2) {
				Double p1Cost = 0.0;
				Double p2Cost = 0.0;

				if (updatedCost.containsKey(p1.get(p1.size() - 1))) {
					p1Cost = updatedCost.get(p1.get(p1.size() - 1));
				} else {
					p1Cost = updatedCost.get(p1.get(p1.size() - 2)) + graph.getLabel(p1.get(p1.size() - 2), p1.get(p1.size() - 1)).get(0);
				}

				if (updatedCost.containsKey(p2.get(p2.size() - 1))) {
					p2Cost = updatedCost.get(p2.get(p2.size() - 1));
				} else {
					p2Cost = updatedCost.get(p2.get(p2.size() - 2)) + graph.getLabel(p2.get(p2.size() - 2), p2.get(p2.size() - 1)).get(0);
				}

				return p1Cost.compareTo(p2Cost);

			}
		};
		return comp;
	}
}