package hw8.test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hw8.Building;

/**
 * This class contains a set of test cases that can be used to test the
 * the implementation of the CampusPaths class. 
 */
public class BuildingTests {
	
	@Test
	public void getYOnNeg() {
		Building b = new Building("b", "barnacles", 2, -1);
		assertEquals(-1.0, b.getY(), 0.001);
	}
	
	@Test
	public void getXOnNeg() {
		Building b = new Building("b", "barnacles", -2, -1);
		assertEquals(-2.0, b.getX(), 0.001);
	}

	@Test
	public void getYOnPos() {
		Building b = new Building("b", "barnacles", 2, 1);
		assertEquals(1.0, b.getY(), 0.001);
	}

	@Test
	public void getXOnPos() {
		Building b = new Building("b", "barnacles", 2, 1);
		assertEquals(2.0, b.getX(), 0.001);
	}
	
	@Test 
	public void getShortName() {
		Building b = new Building("b", "barnacles", 2, 1);
		assertEquals("b", b.getShortName());
	}
	
	@Test 
	public void getLongName() {
		Building b = new Building("b", "barnacles", 2, 1);
		assertEquals("barnacles", b.getLongName());
	}
}
