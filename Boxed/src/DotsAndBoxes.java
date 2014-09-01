import java.util.ArrayList;
import java.util.List;


public class DotsAndBoxes
{
	Graph g;
	int rows;
	int cols;
	int[] playerScores=new int[10];
	
	public DotsAndBoxes(int rows, int columns)
	{
		g=new Graph((rows+1)*(columns+1));
		
		this.rows=rows;
		this.cols=columns;
		for(int j=1; j<this.cols; j++)
		{
			for(int i=1; i<this.rows; i++)
			{
				g.addEdge(i+((this.rows+1)*j), i+((this.rows+1)*j)+1, 1);
				g.addEdge(i+((this.rows+1)*j), i+((this.rows+1)*j)-1,1);
				g.addEdge(i+((this.rows+1)*j), i+((this.rows+1)*(j-1)), 1);
				g.addEdge(i+((this.rows+1)*j), i+((this.rows+1)*(j+1)), 1);
			}
		}
	}
	
	public int drawLine(int player, int x1, int y1, int x2, int y2)
	{
		int ret=0;
		int v = 0;
		int w=0;
		if(y1==y2)
		{
			if(x1>x2)
			{
				v=(this.rows+1)*y1+x1;
				
				w=v+(this.rows+1);
				
				if(g.isEdge(v,w))
				{	
					g.removeEdge(v, w);
					
					if(g.degree(v)==0 && v>this.rows && isNotFringe(v))
						ret++;
					if(g.degree(w)==0 && w>this.rows && isNotFringe(w))
						ret++;
				}
			}
			else
			{
				v=y1*(this.rows+1)+x2;
				
				w=v+(this.rows+1);
				
				if(g.isEdge(v,w))
				{	
					g.removeEdge(v, w);
					
					if(g.degree(v)==0 && v>this.rows && isNotFringe(v))
						ret++;
					if(g.degree(w)==0 && w>this.rows && isNotFringe(w))
						ret++;
				}
			}
		}
		else if(x1==x2)
		{
			if(y1>y2)
			{
				v=y1*(this.rows+1)+x1;
				
				w=v+1;
				
				if(g.isEdge(v,w))
				{	
					g.removeEdge(v, w);
					
					if(g.degree(v)==0 && v>this.rows && isNotFringe(v))
						ret++;
					if(g.degree(w)==0 && w>this.rows && isNotFringe(w))
						ret++;
				}
			}
			else
			{
				v=y2*(this.rows+1)+x1;
				
				w=v+1;
				
				if(g.isEdge(v,w))
				{
					g.removeEdge(v, w);
					
					if(g.degree(v)==0 && v>this.rows && isNotFringe(v))
						ret++;
					if(g.degree(w)==0 && w>this.rows && isNotFringe(w))
						ret++;
				}
			}
		}
		
		playerScores[player]+=ret;
		
		return ret;
	}
	
	public int score(int player)
	{
		return playerScores[player];
	}
	
	public boolean areMovesLeft()
	{
		for(int i=0; i<g.vcount(); i++)
		{
			if(g.degree(i)>0)
				return true;
		}
		
		return false;
	}
	
	public int countDoubleCrosses()
	{
		int ret=0;
		
		DfsGraphTraversal bfs=new DfsGraphTraversal();
		
		List<List<Integer>> ForestThingy=new ArrayList<List<Integer>>();
		
		ForestThingy=bfs.traverse(g);
		
		for(List<Integer> i : ForestThingy)
		{
			if(i.size()==2)
				ret++;
		}
		
		return ret;
	}
	
	public int countCycles()
	{
		int ret=0;
		
		DfsGraphTraversal bfs=new DfsGraphTraversal();
		
		List<List<Integer>> ForestThingy=new ArrayList<List<Integer>>();
		
		ForestThingy=bfs.traverse(g);
		
		for(List<Integer> i : ForestThingy)
		{
			if(i.size()>=4)
			{
				boolean cycle=true;
				for(Integer j : i)
				{
					if(g.degree(j)!=2)
						cycle=false;
				}
			
				if(cycle)
				{
					ret++;
				}
			}
		}
		
		return ret;
	}
	
	public int countOpenChains()
	{
		int ret=0;
		BfsGraphTraversal bfs=new BfsGraphTraversal();
		
		List<List<Integer>> ForestThingy=bfs.traverse(g);
		
		for(List<Integer> i : ForestThingy)
		{
			if(i.size()>=3)
			{
				ret+=helpCountOpenChains(i);
			}
		}
		
		return ret;
	}
	
	private int helpCountOpenChains(List<Integer> i)
	{
		int ret=0;
		for(Integer j : i)
		{
			if(g.degree(j)==2 && g.getMark(j)==0)
			{
				for(int a=g.first(j); a<g.vcount(); a=g.next(j, a))
				{
					if(g.degree(a)>2 || !isNotFringe(a))
					{
						g.removeEdge(j, a);
						DfsGraphTraversal dfs=new DfsGraphTraversal();
						List<Integer> listThingy=dfs.doTraverse(g, j);
						
						for(Integer b : listThingy)
						{
							g.setMark(b, 0);
						}
						
						g.addEdge(j, a, 1);
						
						int currentChainSize=0;
						boolean foundChain=false;
						
						for(int k=0; k<listThingy.size() && !foundChain; k++)
						{
							if(g.degree(listThingy.get(k))==2)
							{
								currentChainSize++;
								g.setMark(listThingy.get(k), 1);
							}
							else if(currentChainSize>=3 && (g.degree(listThingy.get(k))>2 || !isNotFringe(listThingy.get(k))))
							{
								ret++;
								foundChain=true;
							}
						}
					}
				}
			}
		}
		
		return ret;
	}
	
	private boolean isNotFringe(int v)
	{
		return (v>this.rows && v<(this.rows+1)*this.cols && v%(this.rows+1)!=0 && (v+1)%(this.rows+1)!=v);
	}
}
