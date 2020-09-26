package hw5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * This class represents a mutable, directed graph
 *
 * @author Bill Phung
 */

public class DGraph<E, T extends Comparable<T>> {
	//Rep invariant: Every Node in the graph is not null
	//if a node is contained in an edge in the graph that node
	//must also be in the graph
	
	//Abstraction function: AF(this) = a mutable directed weighted graph that 
	//								   takes in nodes and edges
	//                                 the graph is empty if there are no nodes
	
	//directed graph
	private HashMap<E, HashMap<E, Set<T>>> dGraph;
	
	/**
	 * Creates an empty directed graph.
	 * 
	 * @effect constructs an empty directed graph
	 */
	public DGraph() {
		dGraph = new HashMap<E, HashMap<E, Set<T>>>();
		checkRep();
	}
	
	/**
	 * Constructs an edge between two nodes
	 * 
	 * @param start
	 * 			Edge starting node
	 * @param end
	 * 			node that is directly reachable from start
	 * @param label
	 * 			label of the edge
	 * @requires start, end ,label != null
	 * @modifies this.dGraph
	 * @effect adds edge from start to end to the graph if the edge a edge
	 * has not already been established
	 * @throws IllegalArgumentException if either start or end is not in the graph
	 * @return returns true if edge was successfully added to the graph
	 */
	public boolean addEdge(E start, E end, T label) {
		checkRep();
		if(start.equals(null) || end.equals(null) || label.equals(null)) {
			throw new IllegalArgumentException("no null entries in the graph");
		}
		
		if(!dGraph.containsKey(start)) { 
			throw new IllegalArgumentException("node is not contained in graph" + start.toString());
		}
		
		if (!dGraph.containsKey(end)) {
			throw new IllegalArgumentException("node is not contained in graph" + end.toString());
		}
		if(dGraph.get(start).containsKey(end) && dGraph.get(start).get(end).contains(label)) {
			return false;
		} else {
			if(!dGraph.get(start).containsKey(end)) {
				dGraph.get(start).put(end, new TreeSet<T>());
			}
			
			dGraph.get(start).get(end).add(label);
			checkRep();
			return true;
		}
	}
	
	/**
	 * Adds node into the graph
	 * 
	 * @param node
	 * 			node to be added into the graph
	 * @modifies this.dGraph
	 * @requires node != null
	 * @effect adds node into the graph if node was not previously
	 * there
	 * @returns returns true if node was succeessfully added into the graph
	 */
	public boolean addNode(E node) {
		checkRep();
		
		if(node == null) {
			throw new IllegalArgumentException("graph cannot contain null nodes");
		}
		
		if(dGraph.containsKey(node)) {
			return false;
		}
		
		dGraph.put(node, new HashMap<E, Set<T>>());
		
		return true;
	}
	
	/**
	 * Checks to see if a node is contained in a graph
	 * 
	 * @param node
	 * 			node to checked
	 * @requires node != null
	 * @returns returns true if node is contained in dGraph.keySet()
	 */
	public boolean containsNode(E node) {
		checkRep();
		return dGraph.containsKey(node);
	}
	
	/**
	 * Checks to see if there is an edge between two nodes in the graph
	 * 
	 * @param start
	 * 			start node of edge to checked
	 * @param end 
	 * 			end node of edge to be checked
	 * @requires node != null
	 * @throws IllegalArgumentException if start or end is not contained in the graph
	 * @returns returns true if node is contained in edge is contained in the graph
	 * 			that is returns true if this.dGraph.keySet().contains(start) && this.dGraph.get(start).keySet().contains(end)
	 */
	public boolean containsEdge(E start, E end) {
		checkRep();
		if(start.equals(null) || end.equals(null)) {
			throw new IllegalArgumentException("Nodes in graph cannot be null");
		}
		
		if(!this.containsNode(start) || !this.containsNode(end)) {
			throw new IllegalArgumentException("Nodes are not contained in the graph");
		}
		
		return dGraph.get(start).containsKey(end);
	}
	
	/**
	 * Returns the list of labels associated with an edges between two nodes
	 * 
	 * @param start
	 * 			start node of edge to checked
	 * @param end 
	 * 			end node of edge to be checked
	 * @requires start, end != null
	 * @throws IllegalArgumentException if start or end is not contained in the graph
	 * @returns returns labels associated with edges between two nodes returns null otherwise
	 */
	public List<T> getLabel(E start, E end) {
		checkRep();
		
		if(this.containsEdge(start, end)) {
			List<T> labels = new ArrayList<T>();
			labels.addAll(dGraph.get(start).get(end));
			return labels;
		}
		
		return null;
	}
	
	/**
	 * Returns a set of the nodes directly reachable from a given node
	 * 
	 * @param node
	 * 			parent node
	 * @requires node != null
	 * @throws IllegalArgumentException if node is not in the graph
	 * @returns returns a List of nodes directly reachable from node
	 */
	public List<E> getChildren(E node) {
		checkRep();
		if(node.equals(null)) {
			throw new IllegalArgumentException("No null nodes in graph");
		}
		
		if(!this.containsNode(node)) {
			throw new IllegalArgumentException("Node is not in the graph:" + node);
		}
		
		return new ArrayList<E>(dGraph.get(node).keySet());
	}
	
	/**
	 * Removes all edges from two nodes from the graph
	 * 
	 * @param start
	 * 			start node of edge to be removed
	 * @param end 
	 * 			end node of edge to be removed
	 * @modifies this.dGraph
	 * @requires start, end != null
	 * @effect removes edge with starting node start and ending node end from the graph if
	 * 					the graph contains the edge
	 * @throws IllegalArgumentException if edge or start node or end node is not contained in the graph
	 * @returns returns true if removal of edge was successful. Returns false otherwise
	 */
	public void removeEdge(E start, E end) {
		checkRep();
		if(start.equals(null) || end.equals(null)) {
			throw new IllegalArgumentException("No null Nodes in Graph");
		}
		
		if(!this.containsNode(start) || !this.containsNode(end) || !this.containsEdge(start, end)) {
			throw new IllegalArgumentException("Edge Not Found");
		}
		
		dGraph.get(start).remove(end);
		checkRep();
	}
	
	/**
	 * Returns a set of the nodes that are in the graph.
	 * 
	 * @return returns a set of nodes that are in the graph
	 */
	public Set<E> getNodes() {
		checkRep();
		return new HashSet<E>(dGraph.keySet());
	}
	
	/**
	 * Returns how many nodes are in the graph.
	 * 
	 * @return returns how many nodes are in the graph
	 */
	public int size() {
		checkRep();
		return dGraph.size();
	}
	
	/**
	 * Checks if the graph is empty
	 * 
	 * @return returns true if the graph is empty
	 */
	public boolean isEmpty() {
		checkRep();
		return dGraph.isEmpty();
	}
	
	@Test(timeout = 10)
	private void checkRep() {
		assert(dGraph != null);
		assert(!dGraph.keySet().contains(null));
	}
}
