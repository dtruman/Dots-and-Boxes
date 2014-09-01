import java.util.ArrayList;
import java.util.List;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g=new Graph(7);
		
		g.addEdge(0,1,1);
		g.addEdge(0, 2, 1);
		g.addEdge(1,3,1);
		g.addEdge(3,4,1);
		g.addEdge(2,5,1);
		g.addEdge(5,6,1);
		
		DotsAndBoxes dab=new DotsAndBoxes(5,5);
		
		System.out.println();
		
		dab.drawLine(1, 0, 1, 0, 2);
		
		Tests t=new Tests();
		
		//t.testOwner();
		//t.testDoubleCrosses();
		//t.testCycle();
		//t.testChains();
		t.testInternalChain();
		//t.testInternalChains();
		//t.testNotAChain();
	}
}
