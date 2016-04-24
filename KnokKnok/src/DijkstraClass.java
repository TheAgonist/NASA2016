
import java.util.*;

public class DijkstraClass{
	public int INF = 9999;
	private int[] d = new int[100];
	public int[] Dijkstra(int[][] graph, int s, int n){
		Arrays.fill(d, INF);
		d[s] = 0;
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(100);
		q.add(s);
		int time = 1;
		while(!q.isEmpty()){
			int top = q.element();
			q.remove();
			for(int i = 1; i <= n; i++){
				if(graph[top][i] != 0){
					int curVert = i;
					int curDist = graph[top][i];
					if(d[curVert] > d[top] + curDist){
						d[curVert] = d[top] + curDist;
						int temp1 = curVert;
						q.add(temp1);
					}
				}
			}
		}
		/*for(int i = 0; i < v.length; i++){
			System.out.print(v[i] + " ");
		}*/
		return d;
	}
}
