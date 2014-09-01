import java.util.ArrayList;
import java.util.List;

public class DfsGraphTraversal
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
	
	public List<Integer> doTraverse(Graph G, int v)
	{
		List<Integer> tree=new ArrayList<Integer>();
	
		G.setMark(v, VISITED);
		tree.add(v);
	
		for(int w=G.first(v); w<G.vcount(); w=G.next(v, w))
		{
			if(G.getMark(w)==UNVISITED)
			{
				tree.addAll(doTraverse(G, w));
			}
		}
		
		return tree;
	}
}
