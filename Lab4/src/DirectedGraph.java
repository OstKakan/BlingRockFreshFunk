
import java.util.*;

public class DirectedGraph<E extends Edge> {
	private List<E>[] nodes;
	private PriorityQueue<E> pq;
	private Vector<LinkedList<E>> nodeVector;

	public DirectedGraph(int noOfNodes) {
		System.out.println("Number of nodes: " + noOfNodes);
		nodes = (List<E>[]) new List[noOfNodes];
		for(int i = 0 ; i< noOfNodes ; i++){ 					//O(n)
			nodes[i] = new LinkedList<E>();						//O(1)
		}
	}

	public void addEdge(E e) {
		System.out.println("Source is: " + e.getSource());
		nodes[e.getSource()].add(e);							//O(1)
		//pq.add(e);

	}

	public Iterator<E> shortestPath(int from, int to) {
		return null;
	}
		
	public Iterator<E> minimumSpanningTree() {
		LinkedList<E> shorterList, longerList;
		pq = new PriorityQueue<E>(new CompKruskalEdge());
		nodeVector = new Vector<>(); //Vector with list of edges, this will be used for a return;

		for(int i = 0 ; i < nodes.length ; i++){ //Off by one?
			nodeVector.add(new LinkedList<E>());
		}

		for(List<E> list : nodes){
			for(E e : list){
				pq.add(e);
			}
		}

		mainloop:
		while(!pq.isEmpty()){
			E e;
			do{
				e = pq.poll();
				if(e == null){
					break mainloop;
				}

			}while(nodeVector.get(e.getSource()) == nodeVector.get(e.getDest()));

			longerList = nodeVector.get(e.getDest());
			shorterList = nodeVector.get(e.getSource());
			if(nodeVector.get(e.getSource()).size() > nodeVector.get(e.getDest()).size()){
				longerList = nodeVector.get(e.getSource());
				shorterList = nodeVector.get(e.getDest());
			}

			longerList.add(e);
			nodeVector.set(e.getDest(), longerList);
			nodeVector.set(e.getSource(), longerList);
			longerList.addAll(shorterList);
			for(E edges : shorterList){
				nodeVector.set(edges.getSource(), longerList);
				nodeVector.set(edges.getDest(), longerList);
			}

		}

		return nodeVector.get(1).iterator();
	}

}
  
