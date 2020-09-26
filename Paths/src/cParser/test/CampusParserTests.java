package hw8.test;

import hw8.Building;
import hw8.CampusParser;
import hw8.CampusParser.MalformedDataException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.*; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class CampusParserTests {
	private List<Building> buildings;
	private Map<Building, Map<Building, Set<Double>>> paths;
	
	@Before
	public void setUp() {
		buildings = new ArrayList<Building>();
		paths = new HashMap<Building, Map<Building, Set<Double>>>();
	}
	
	@Test
	public void buildingTest() throws MalformedDataException {
		CampusParser.buildingParser("src/hw8/data/four_buildings.dat", buildings);
		
		Building cse = new Building("CSE", "Paul G. Allen Center for Computer Science & Engineering" ,
				1.0,	4.0);
		Set<Building> temp = new HashSet<Building>(buildings);
		assertTrue(temp.contains(cse));
	}
	
	@Test
	public void testParsePaths() throws MalformedDataException {
		CampusParser.pathsParser("src/hw8/data/paths.dat", paths);
		Building a = new Building("","",1.0,1.0);
		Building b = new Building("","",1.0,2.0);
		Set<Double> cost = new HashSet<Double>();
		cost.add(1.0);
		assertTrue(paths.get(a).get(b).equals(cost));
	}
	
	@Test(expected = MalformedDataException.class)
	public void testParseBadFormatedBuilding() throws MalformedDataException {
		CampusParser.pathsParser("src/hw8/data/bad_building.dat", paths);
	}
	
	@Test(expected = MalformedDataException.class)
	public void testParseBadFormatedPaths() throws MalformedDataException {
		CampusParser.pathsParser("src/hw8/data/bad_paths.dat", paths);
	}
	
	@Test(expected = MalformedDataException.class)
	public void testParseBadFormatedPaths2() throws MalformedDataException {
		CampusParser.pathsParser("src/hw8/data/bad_paths2.dat", paths);
	}
}
