package graph.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;

/**
 * A basic representation of a graph that can perform BFS, DFS, Dijkstra,
 * and Prim-Jarnik's algorithm for a minimum spanning tree.
 * 
 * @author jspacco
 *
 */
public class Graph implements IGraph
{
    
	private Map<String, INode> g = new HashMap<>();
	private LinkedList<INode> visited= new LinkedList<INode>();
	@Override
	public INode getOrCreateNode(String name) {
		if(this.containsNode(name)) {
			return g.get(name);
		}
		g.put(name, new Node(name));
		return g.get(name);
	}

	@Override
	public boolean containsNode(String name) {
		if(g.containsKey(name)) {
			return true;
		}
		return false;
	}

	@Override
	public Collection<INode> getAllNodes() {
		Collection<INode> c = new HashSet<INode>(); 
		Iterator<String> itr = g.keySet().iterator();
		while (itr.hasNext()) {
			c.add(g.get(itr.next()));
		}
		return c;
	}

	@Override
	public void breadthFirstSearch(String startNode, NodeVisitor v) {
		Queue<INode> q = new LinkedList<INode>();
	    LinkedList<INode> l = new LinkedList<INode>();
	
		q.add(g.get(startNode));
		v.visit(g.get(startNode));
		l.add(g.get(startNode));
		
		while(q.size() != 0) {
			INode e = q.poll();
			Collection<INode> c =  e.getNeighbors();
			Iterator<INode> iterator = c.iterator();
		    while (iterator.hasNext()) {
		    	 INode n =iterator.next();
		    	 if(!l.contains(n)) {
		    		q.add(n);
		        	l.add(n);
		        	v.visit(n);
		        }
		     }
		}
	}

	@Override
	public void depthFirstSearch(String startNode, NodeVisitor v) {
		visited.add(g.get(startNode));
		v.visit(g.get(startNode));
		
		Collection<INode> c = g.get(startNode).getNeighbors(); 
		Iterator<INode> itr = c.iterator();
		while(itr.hasNext()) {
			INode n =itr.next();
			if(!visited.contains(n)) {
				depthFirstSearch(n.getName(),v);
			}
		}
	}

	@Override
	public Map<INode, Integer> dijkstra(String sourceNode) {	
		Map<INode,Integer> result = new HashMap<>();
        PriorityQueue<Path> toDo = new PriorityQueue<>();
        toDo.add(new Path(getOrCreateNode(sourceNode),0));
        while ( result.size() < getAllNodes().size()) {
        	Path nextpath = toDo.poll();
        	INode Node1 = nextpath.destination;
        	if (result.containsKey(Node1))
        		continue;
        	int cost = nextpath.cost;
        	result.put(Node1, cost);
        	for(INode temp : Node1.getNeighbors()) {
        		toDo.add(new Path(temp, Node1.getWeight(temp)+cost));
        	}	
        }
        return result; 
	}

	@Override
	public IGraph primJarnik() {
		// TODO Auto-generated method stub
		return null;
	}
}