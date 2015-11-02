package DecisionTrees.DecisionTreesArtifact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tree {

	public int totalLines;
	public double shannonEntropy;
	public Map<String,String> previousNodesAttributes=new HashMap<String,String>();
	public boolean isLeaf;
	public ArrayList<Tree> innerBranches;
	public String attrValue;
	public String attrCat;
	public Map<String, Double> leafValues = new HashMap<String, Double>();

	public void showAll() {
		System.out.println("totalLines:" + totalLines);
		System.out.println("shannonEntropy:" + shannonEntropy);
		if (previousNodesAttributes != null)
			System.out.println("previousNodesAttributes:" + previousNodesAttributes.toString());
		System.out.println("isLeaf:" + isLeaf);
		System.out.println("attrValue:" + attrValue);
		System.out.println("attrCat:" + attrCat);
		if (innerBranches != null) {
			Iterator it = innerBranches.iterator();
			while (it.hasNext()) {
				Tree newTree = (Tree) it.next();
				newTree.showAll();
			}
		}

		System.out.println(leafValues.toString());
		;

	}
}
