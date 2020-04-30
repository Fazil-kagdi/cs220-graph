package graph.impl;

import graph.INode;

public class Path implements Comparable<Path>{
	 
	  INode name;
	  int cost;

	  public Path(INode name, int cost){
	    this.name = name;
	    this.cost = cost;
	  }

	  public int compareTo(Path other){ // This thing is dope. Like it's simple yet so elegant
	    return this.cost - other.cost;
	  }
}