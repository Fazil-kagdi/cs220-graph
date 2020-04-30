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
		Map<INode,Integer> res = new HashMap<>();
        PriorityQueue<Path> qu = new PriorityQueue<>();
        qu.add(new Path(getOrCreateNode(sourceNode),0));
        while ( res.size() < getAllNodes().size()) {
        	Path n = qu.poll();
        	INode Node1 = n.name;
        	if (res.containsKey(Node1))
        		continue;
        	int cost = n.cost;
        	res.put(Node1, cost);
        	for(INode temp : Node1.getNeighbors()) {
        		qu.add(new Path(temp, Node1.getWeight(temp)+cost));
        	}	
        }
        return res; 
	}

	@Override
	public IGraph primJarnik() {
		IGraph res = new Graph();
		INode start = (INode) this.getAllNodes().toArray()[0]; //Thanks to Choudry
		PriorityQueue<edge> qu = new PriorityQueue<>();
		for(INode temp : start.getNeighbors()) {
			qu.add(new edge(start,temp,start.getWeight(temp)));
		}
		while(res.getAllNodes().size()!=this.getAllNodes().size()) {
			edge e = qu.poll();
			INode node1 = e.n1;
			INode node2 = e.n2;
			if(res.containsNode(node1.getName()) && res.containsNode(node2.getName()))
				continue;
			INode first = res.getOrCreateNode(node1.getName());
			INode second = res.getOrCreateNode(node2.getName());
			first.addUndirectedEdgeToNode(second,e.weight);
			for(INode temp : node2.getNeighbors()) {
        		if (!temp.equals(node1)) {
        		qu.add(new edge(node2,temp,node2.getWeight(temp)));
        		}
        	}
		}
		return res;
	}
}