package graph.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import graph.INode;

/**
 * Class to represent a single node (or vertex) of a graph.
 * 
 * Node can be used for either directed or undirected graphs, as well as
 * for weighted or unweighted graphs. For unweighted graphs, use something like 1 for all 
 * of the weights. For undirected graphs, add a directed edge in both directions.
 * 
 * You want to make as many operations O(1) as possible, which means you will
 * probably use a lot of Maps.
 * 
 * Side note: You can tell that I come from a networking background and not a mathematical
 * background because I almost always use the term "node" instead of "vertex".
 * 
 * @author jspacco
 *
 */
public class Node implements INode
{
    
	private String name;
	private Map<INode, Integer> neighbors = new HashMap<>();

	public Node(String n) { 
		this.name = n;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<INode> getNeighbors() {
		return neighbors.keySet();

	}

	@Override
	public void addDirectedEdgeToNode(INode neighbor, int weight) {
		neighbors.put(neighbor, weight);
		
	}

	@Override
	public void addUndirectedEdgeToNode(INode neighbor, int weight) {
		addDirectedEdgeToNode(neighbor, weight);
	    neighbor.addDirectedEdgeToNode(this, weight);
	}

	@Override
	public void removeDirectedEdgeToNode(INode neighbor) {
		neighbors.remove(neighbor);	
		
	}

	@Override
	public void removeUndirectedEdgeToNode(INode neighbor) {
		removeDirectedEdgeToNode(neighbor);
		neighbor.removeDirectedEdgeToNode(this);
	}

	@Override
	public boolean hasEdge(INode node) {
		if(neighbors.containsKey(node)) {
			return true;
		}
		return false;
	}

	@Override
	public int getWeight(INode node) {
		return neighbors.get(node);
	}
}
