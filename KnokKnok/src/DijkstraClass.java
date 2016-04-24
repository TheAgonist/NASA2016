
import java.util.*;
import java.util.Comparator;

public class DijkstraClass{
	public int INF = 9999;
	private int[] d = new int[100];
	public int[] Dijkstra(int graph[][], int s, int n){
		Arrays.fill(d, INF);
		d[s] = 0;
		Comparator<Pair<Integer,Integer>> comparator = new PairComparator();
		PriorityQueue<Pair<Integer,Integer>> q = new PriorityQueue<Pair<Integer,Integer>>(100,comparator);
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(s,0); 
		q.add(temp);
		while(!q.isEmpty()){
			Pair<Integer,Integer> top = new Pair<Integer,Integer>(temp.getLeft(),temp.getRight());
			q.remove();
			for(int i = 0; i < n; i++){
				if(graph[top.getLeft()][i] != 0){
					int curVert = i;
					int curDist = graph[top.getLeft()][i];
					if(d[curVert] > d[top.getLeft()] + curDist){
						d[curVert] = d[top.getLeft()] + curDist;
						Pair<Integer,Integer> temp1 = new Pair<Integer,Integer>(curVert, d[top.getLeft()] + curDist);
						q.add(temp1);
					}
				}
			}
		}
		return d;
	}
}
