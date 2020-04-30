package graph.impl;

import graph.INode;

public class edge implements Comparable<edge>{
	INode n1;
	INode n2;
	int weight;
	
	public edge(INode n1, INode n2, int weight) {
		this.n1 = n1;
		this.n2 = n2;
		this.weight = weight;
	}

	@Override
	public int compareTo(edge o) {
		return this.weight - o.weight;
	}
}
