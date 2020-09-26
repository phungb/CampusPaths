package hw8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Parser utility to load the campus dataset.
 */
public class CampusParser {
	
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
	public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * Reads the campus buildings dataset
	 * Each line of the input file contains a building abreviated name, full name and coordinates
     * 
     * @requires filename is a valid path and buildings != null
     * @param filename the file that will be read
     * @param buildings list in which all buildings will be stored
     * @effects fills buildings with a list of all buildings
     * @throws MalformedDataException if the file is not well-formed:
     */
    public static void buildingParser(String filename, List<Building> buildings) throws MalformedDataException {
    	if (buildings == null) {
    		throw new IllegalArgumentException();
    	}
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new FileReader(filename));
    		String inputLine;
    		while ((inputLine = reader.readLine()) != null) {
    			
                // Ignore comment lines.
                if (inputLine.startsWith("#")) {
                    continue;
                }

                String[] tokens = inputLine.split("\t");
                if (tokens.length != 4) {
                    throw new MalformedDataException("Line should contain exactly three tabs: "
                                                     + inputLine);
                }
                
                String shortName = tokens[0];
                String longName = tokens[1];
                double x = Double.parseDouble(tokens[2]);
                double y = Double.parseDouble(tokens[3]);
                Building building = new Building(shortName, longName, x, y);
                
                buildings.add(building);
    		}
    	} catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
    }
    
    /**
     * Reads the campus paths dataset
     * Input file contains start and end locations and the distance between them
     * if a path is there.
     * 
     * @requires filename is a valid path and paths != null
     * @param filename the file that will be read
     * @param paths map in which locations will be mapped to other locations and the distance of the path
     * @effects fills paths with a map from each start location to corresponding end locations and the
     * 			distance of the path between them
     * @throws MalformedDataException if the file is not well-formed:
     */
    public static void pathsParser(String filename, Map<Building, Map<Building, Set<Double>>> paths) throws MalformedDataException {
    	if (paths == null) {
    		throw new IllegalArgumentException();
    	}
    	BufferedReader reader = null;
    	
    	try {
    		reader = new BufferedReader(new FileReader(filename));
    		
    		String startLine = reader.readLine();
    		while (startLine != null) {
    			
                // Ignore comment lines.
                if (startLine.startsWith("#")) {
                    continue;
                }
                
                String[] tokens = startLine.split(",");
                if (tokens.length != 2) {
                    throw new MalformedDataException("Line should contain exactly one comma; "
                                                     + startLine);
                }
                
                double x = Double.parseDouble(tokens[0]);
                double y = Double.parseDouble(tokens[1]);
                Building start = new Building("", "", x, y);
                
                paths.put(start, new HashMap<Building, Set<Double>>());
                
                String destString = reader.readLine();
                while(destString != null && destString.startsWith("\t")) {
                	String[] destData = destString.split(": ");
                	
                    if (destData.length != 2) {
                        throw new MalformedDataException("Line should contain exactly one colon; "
                                                         + destString);
                    }
                    
                	destData[0] = destData[0].trim();
                	
                	String[] destCoord = destData[0].split(",");
                	
                    if (destCoord.length != 2) {
                        throw new MalformedDataException("Line should contain exactly one comma; "
                                                         + destString);
                    }
                	
                	double distance = Double.parseDouble(destData[1]);
                	double endX = Double.parseDouble(destCoord[0]);
                	double endY = Double.parseDouble(destCoord[1]);
                	
                	Building endBuilding = new Building("","", endX, endY);
                	
                	if(!paths.get(start).containsKey(endBuilding)) {
                		Set<Double> cost = new TreeSet<Double>();
                		cost.add(distance);
                		paths.get(start).put(endBuilding, cost);
                	} else {
                		Set<Double> cost = paths.get(start).get(endBuilding);
                		cost.add(distance);
                	}
                	destString = reader.readLine();
                }
                startLine = destString;
    		}
    	} catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
