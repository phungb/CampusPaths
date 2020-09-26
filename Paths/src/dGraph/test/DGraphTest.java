package hw5.test;

import org.junit.*; 
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import hw5.DGraph;

/**
 * This class contains a set of test cases that can be used to test the
 * the implementation of the DGraph class. 
 */

public class DGraphTest {

	private DGraph<String,String> dGraph;
	
	private final String NODE_X = "x";
	private final String NODE_Y = "y";
	private final String NODE_Z = "z";
	private final String EDGE_XY = "XY";
	private final String EDGE_XZ = "XZ";
	
	
	@Before
	public void setUp() throws Exception {
		dGraph = new DGraph<String,String>();
	}
	
	@Test
	public void testSizeWhenConstructed() {
		assertEquals(0, dGraph.size());
	}
	
	@Test
	public void testisEmptyWhenConstructed() {
		assertTrue(dGraph.isEmpty());
	}
	
	@Test
	public void testisEmptyAfterAddNode() {
		dGraph.addNode(NODE_X);
		assertTrue(!dGraph.isEmpty());
	}
	
	@Test
	public void testSizeAfterAddDuplicateNode() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_X);
		assertEquals(1, dGraph.size());
	}
	
	@Test
	public void testAddDuplicateNode() {
		dGraph.addNode(NODE_X);
		assertTrue(!dGraph.addNode(NODE_X));
	}
	
	@Test
	public void testAddMultipleNodes() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		assertTrue(dGraph.addNode(NODE_Z));
	}
	
	@Test
	public void testSizeAfterAddMultipleNodes() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addNode(NODE_Z);
		assertEquals(3, dGraph.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddEdgeToNonExistingNode() {
		dGraph.addNode(NODE_X);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
	}
	
	@Test
	public void testAddOnExistingNodes() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		assertTrue(dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY));
	}
	
	public void testAddEdgeOnExistingNodesMultipleTimes() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		assertTrue(!dGraph.addEdge(NODE_X, NODE_Y, EDGE_XZ));
	}
	
	@Test
	public void testGetLabelOfExistingEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		assertTrue(dGraph.containsEdge(NODE_X, NODE_Y));
	}
	
	@Test
	public void testGetLabelOfRemovedEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		dGraph.removeEdge(NODE_X, NODE_Y);
		assertTrue(!dGraph.containsEdge(NODE_X, NODE_Y));
	}
	
	public void testGetLabelOfExistingEdgeAfterAddEdgeMultipleTimes() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XZ);
		assertTrue(dGraph.containsEdge(NODE_X, NODE_Y));
	}
	
	@Test
	public void testGetLabelOfNonExistingEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		assertNull(dGraph.getLabel(NODE_X, NODE_Y));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetLabelOfNonExistingNode() {
		dGraph.addNode(NODE_X);
		dGraph.getLabel(NODE_X, NODE_Y);
	}
	
	@Test
	public void testContainsExistingEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		assertTrue(dGraph.containsEdge(NODE_X, NODE_Y));
	}
	
	public void testContainsRemovedEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		dGraph.removeEdge(NODE_X, NODE_Y);
		assertTrue(!dGraph.containsEdge(NODE_X, NODE_Y));
	}
	
	@Test
	public void testContainsNonExistingEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		assertTrue(!dGraph.containsEdge(NODE_X, NODE_Y));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testContainsEdgeOnNonExistingNode() {
		dGraph.addNode(NODE_X);
		dGraph.containsEdge(NODE_X, NODE_Y);
	}
	
	@Test
	public void testNoChildren() {
		dGraph.addNode(NODE_X);
		assertTrue(dGraph.getChildren(NODE_X).isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetChildrenOnNonExistingNode() {
		dGraph.getChildren(NODE_X);
	}
	
	@Test
	public void testOneChild() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		List<String> expected = new ArrayList<String>();
		expected.add(NODE_Y);
		assertEquals(expected, dGraph.getChildren(NODE_X));
	}
	
	@Test
	public void testMultipleChildren() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.addNode(NODE_Z);
		dGraph.addEdge(NODE_X, NODE_Y, EDGE_XY);
		dGraph.addEdge(NODE_X, NODE_Z, EDGE_XZ);
		List<String> expected = new ArrayList<String>();
		expected.add(NODE_Y);
		expected.add(NODE_Z);
		assertEquals(expected, dGraph.getChildren(NODE_X));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveEdgeToNonExistingNode() {
		dGraph.removeEdge(NODE_X, NODE_Y);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveEdgeToNonExistingEdge() {
		dGraph.addNode(NODE_X);
		dGraph.addNode(NODE_Y);
		dGraph.removeEdge(NODE_X, NODE_Y);
	}

}
