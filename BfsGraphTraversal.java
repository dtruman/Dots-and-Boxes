import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BfsGraphTraversal
{
	int UNVISITED=0;
	int VISITED=1;
	
	List<List<Integer>> traverse(Graph g)
	{
		int v;
		List<List<Integer>> forest=new ArrayList<List<Integer>>();
		
		for(v=0; v<g.vcount(); v++)
		{
			g.setMark(v, UNVISITED);
		}
		for(v=0; v<g.vcount(); v++)
		{
			if(g.getMark(v)==UNVISITED)
			{
				forest.add(doTraverse(g, v));
			}
		}
		
		g.resetMarks();
		return forest;
	}
	
	public List<Integer> doTraverse(Graph G, int start)
	{
		List<Integer> tree=new ArrayList<Integer>();
		PriorityQueue<Integer> Q=new PriorityQueue<Integer>();
		Q.add(start);
		G.setMark(start,  VISITED);
		tree.add(start);
		while(Q.size()>0)
		{
			int v=Q.poll();
			for(int w=G.first(v); w<G.vcount(); w=G.next(v, w))
			{
				if(G.getMark(w)==UNVISITED)
				{
					G.setMark(w, VISITED);
					tree.add(w);
					Q.add(w);
				}
			}
		}
		
		return tree;
	}
}
