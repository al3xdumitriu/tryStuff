package DecisionTrees.DecisionTreesArtifact;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */

public class App {
	Tree beautifullTree;
	Map<String, HashMap<String, ArrayList<Double>>> memoredAttr = new HashMap<String, HashMap<String, ArrayList<Double>>>();
	String[] bestAtributesNames;
	// Map<String, HashMap<String, ArrayList<Double>>> foundNodes = new
	// HashMap<String, HashMap<String, ArrayList<Double>>>();
	//Map<Integer, Map<String, ArrayList<Double>>> foundNodes = new HashMap<Integer, Map<String, ArrayList<Double>>>();

	public static void main(String[] args) {

		Attributes.setAttr();
		System.out.println(Attributes.attributes.toString());
		System.out.println(Attributes.classes.toString());
		App obj = new App();
		obj.run();

	}

	public void run() {

		String csvFile = "car.data";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] car = { "123", "234", "543" };

		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, ArrayList<Double>> addedMap = new HashMap<String, ArrayList<Double>>();
		int initialLinesNo;
		int nrLinie;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			initialLinesNo = 0;
			map = new HashMap<String, Integer>();
			while ((line = br.readLine()) != null) {
				initialLinesNo++;

				// use comma as separator
				car = line.split(cvsSplitBy);
				if (bestAtributesNames == null) {
					bestAtributesNames = new String[car.length];
				}
				// count the occurrences of each value
				// for (String sequence : values) {
				if (!map.containsKey(car[car.length - 1])) {
					map.put(car[car.length - 1], 0);
				}
				map.put(car[car.length - 1], map.get(car[car.length - 1]) + 1);
				// }

				// calculate the entropy
			}
			Double result = 0.0;
			System.out.println(map.toString());
			for (String sequence : map.keySet()) {
				Double frequency = (double) map.get(sequence) / (double) initialLinesNo;
				result -= frequency * (Math.log(frequency) / Math.log(2));

			}
			// double ceva=0.0;
			// ceva-=(9.0/14.0) * (Math.log(9.0/14.0) / Math.log(2));
			// ceva-=(5.0/14.0) * (Math.log(5.0/14.0) / Math.log(2));
			// System.out.println("Rezultat:"+ceva);

			System.out.println(result);
			br.close();

			br = null;
			Map<Integer, Map<String, ArrayList<Double>>> total = new HashMap<Integer, Map<String, ArrayList<Double>>>();
			for (int i = 0; i < car.length - 1; i++) {

				br = new BufferedReader(new FileReader(csvFile));
				nrLinie = 0;
				map = new HashMap<String, Integer>();
				addedMap = new HashMap<String, ArrayList<Double>>();
				while ((line = br.readLine()) != null) {
					nrLinie++;

					// use comma as separator
					car = line.split(cvsSplitBy);

					// count the occurrences of each value
					// for (String sequence : values) {
					if (!addedMap.containsKey(car[i])) {
						ArrayList<Double> temp = new ArrayList<Double>();
						for (int k = 0; k < Attributes.classes.size(); k++) {
							temp.add(0.0);
						}
						// temp.add(0.0);
						// temp.add(0.0);//!!!!!ADAUG MAI MULTE PR FIECARE
						// VARIABILA
						addedMap.put(car[i], temp);
					}

					for (int k = 0; k < Attributes.classes.size(); k++) {
						if (car[car.length - 1].equals(Attributes.classes.get(k))) {
							double tempNr = (double) ((ArrayList) addedMap.get(car[i])).get(k);
							((ArrayList) addedMap.get(car[i])).remove(k);
							tempNr++;
							((ArrayList) addedMap.get(car[i])).add(k, tempNr);

							// ((ArrayList)addedMap.get(car[i])).set(0,(int)(((ArrayList)addedMap.get(car[i])).get(1))+1);
							//

						}
					}

					// calculate the entropy
				}
				result = 0.0;
				// System.out.println(addedMap.toString());
				Iterator it = addedMap.keySet().iterator();
				while (it.hasNext()) {
					String sequence = it.next().toString();
					result = 0.0;
					int totalSum = 0;
					for (int k = 0; k < Attributes.classes.size(); k++) {

						totalSum += (double) ((ArrayList) addedMap.get(sequence)).get(k);

					}
					Double frequency;
					for (int k = 0; k < Attributes.classes.size(); k++) {

						frequency = (double) ((ArrayList) addedMap.get(sequence)).get(k) / (double) totalSum;
						if (frequency != 0)
							result -= frequency * (Math.log(frequency) / Math.log(2));

					}

					addedMap.get(sequence).add(Attributes.classes.size(), result);
					// System.out.println(result);
				}
				total.put(i, addedMap);

				br.close();

				br = null;

			}
			System.out.println(total.toString());

			// CALCULAREA GAIN-ului
			calculateMaxGain(null,total, initialLinesNo);

			// apeleazaRecursiv(1);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

	public void calculateMaxGain(Tree node,Map<Integer, Map<String, ArrayList<Double>>> total, int initialLinesNo) {
		double minSumEntropy = -1;
		Iterator it = total.keySet().iterator();
		int nodMemorat = 0;
		int it2 = 0;
		while (it.hasNext()) {
			it2 = (int) it.next();
			Iterator innerMap = total.get(it2).keySet().iterator();
			double localSumEntropy = 0.0;
			while (innerMap.hasNext()) {
				String sequence = innerMap.next().toString();
				if ((total.get(it2).get(sequence).get(total.get(it2).get(sequence).size() - 1)) != 0.0)
					localSumEntropy -= (total.get(it2).get(sequence).get(0) / initialLinesNo)
							* (total.get(it2).get(sequence).get(total.get(it2).get(sequence).size() - 1));

			}
			System.out.println("localSum:" + localSumEntropy);
			if (minSumEntropy < localSumEntropy) {
				minSumEntropy = localSumEntropy;
				nodMemorat = it2;
			}

		}

		System.out.println("final" + minSumEntropy);
//		foundNodes.put(nodMemorat, total.get(nodMemorat));
//		System.out.println(foundNodes.toString());

		createNode(node,total.get(nodMemorat), nodMemorat);
	}

	public void createNode(Tree nodeInitial,Map<String, ArrayList<Double>> node, int index) {
		System.out.println(node.toString());
		if (beautifullTree == null) {
			beautifullTree = new Tree();
		
		beautifullTree.isLeaf = false;
		beautifullTree.previousNodesAttributes = new HashMap<String,String>();
		beautifullTree.attrValue = "root";

		beautifullTree.innerBranches = new ArrayList<Tree>();
		
		for (String str : node.keySet()) {
			Tree innerNode = new Tree();
			innerNode.attrValue = str;
			double sum = 0.0;
			for (int l = 0; l < node.get(str).size() - 1; l++) {
				sum += node.get(str).get(l);

			}
			innerNode.totalLines = (int) sum;
			innerNode.shannonEntropy = node.get(str).get(node.get(str).size() - 1);
			if (innerNode.shannonEntropy == 0) {
				innerNode.isLeaf = true;
			} else
				innerNode.isLeaf = false;
			innerNode.attrCat = Categories.categories[index];
			
			// System.out.println(node.toString());
			if (innerNode.isLeaf == true) {
				for (int i = 0; i < Attributes.classes.size(); i++) {
					innerNode.leafValues.put(Attributes.classes.get(i), node.get(str).get(i));
				}
			}

			innerNode.previousNodesAttributes.put(innerNode.attrCat,innerNode.attrValue);
			beautifullTree.innerBranches.add(innerNode);

		}
		// beautifullTree.showAll();
		Iterator it = beautifullTree.innerBranches.iterator();
		while (it.hasNext()) {
			Tree aux=(Tree) it.next();
		
			if (aux.shannonEntropy == 0)
				;
			else
				calculateAndAddNodes(aux);
		}
		
		}else{
			nodeInitial.innerBranches=new ArrayList<Tree>();
			for (String str : node.keySet()) {
				Tree innerNode = new Tree();
				innerNode.attrValue = str;
				double sum = 0.0;
				for (int l = 0; l < node.get(str).size() - 1; l++) {
					sum += node.get(str).get(l);

				}
				innerNode.totalLines = (int) sum;
				innerNode.shannonEntropy = node.get(str).get(node.get(str).size() - 1);
				if (innerNode.shannonEntropy == 0) {
					innerNode.isLeaf = true;
				} else
					innerNode.isLeaf = false;
				innerNode.attrCat = Categories.categories[index];
				//beautifullTree.innerBranches.add(innerNode);
				// System.out.println(node.toString());
				if (innerNode.isLeaf == true) {
					for (int i = 0; i < Attributes.classes.size(); i++) {
						innerNode.leafValues.put(Attributes.classes.get(i), node.get(str).get(i));
					}
				}

				innerNode.previousNodesAttributes.put(innerNode.attrCat,innerNode.attrValue);
				nodeInitial.innerBranches.add(innerNode);
				
			}
			Iterator it = nodeInitial.innerBranches.iterator();
			while (it.hasNext()) {
				Tree aux=(Tree) it.next();
			
				if (aux.shannonEntropy == 0)
					;
				else
					calculateAndAddNodes(aux);
			}
		}

	}

	public void calculateAndAddNodes(Tree node) {
		String csvFile = "car.data";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] car = { "123", "234", "543" };

		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, ArrayList<Double>> addedMap = new HashMap<String, ArrayList<Double>>();
		int initialLinesNo;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			initialLinesNo = node.totalLines;
			Map<Integer, Map<String, ArrayList<Double>>> total = new HashMap<Integer, Map<String, ArrayList<Double>>>();
			int categoryNo = -1;
			for (int i = 0; i < Categories.categories.length; i++) {
				if (Categories.categories[i].equals(node.attrCat))
					categoryNo = i;
			}
			for (int i = 0; i < car.length - 1; i++) {

				br = new BufferedReader(new FileReader(csvFile));

				map = new HashMap<String, Integer>();
				addedMap = new HashMap<String, ArrayList<Double>>();
				while ((line = br.readLine()) != null) {

					// use comma as separator
					car = line.split(cvsSplitBy);
					boolean goodLine = true;

					Iterator it = node.previousNodesAttributes.keySet().iterator();

					while (it.hasNext()) {
						int gasit = 0;
						String aux=(String) it.next();
						for (int k = 0; k < car.length; k++) {
							if (node.previousNodesAttributes.get(aux).equals(car[k])&&k==(findIndexOfCat(aux)))
							{
								gasit = 1;
							}
						}
						if (gasit == 0)
							goodLine = false;

					}
					if (goodLine==true) {
					if (!addedMap.containsKey(car[i])) {
						ArrayList<Double> temp = new ArrayList<Double>();
						for (int k = 0; k < Attributes.classes.size(); k++) {
							temp.add(0.0);
						}

						addedMap.put(car[i], temp);
					}
					
						for (int k = 0; k < Attributes.classes.size(); k++) {
							if (car[car.length - 1].equals(Attributes.classes.get(k))) {
								double tempNr = (double) ((ArrayList) addedMap.get(car[i])).get(k);
								((ArrayList) addedMap.get(car[i])).remove(k);
								tempNr++;
								((ArrayList) addedMap.get(car[i])).add(k, tempNr);

							

							}
						}
					}
					// calculate the entropy
				}
				double result = 0.0;
				// System.out.println(addedMap.toString());
				Iterator it = addedMap.keySet().iterator();
				
				while (it.hasNext()) {
					String sequence = it.next().toString();
					result = 0.0;
					int totalSum = 0;
					for (int k = 0; k < Attributes.classes.size(); k++) {

						totalSum += (double) ((ArrayList) addedMap.get(sequence)).get(k);

					}
					Double frequency;
					for (int k = 0; k < Attributes.classes.size(); k++) {

						frequency = (double) ((ArrayList) addedMap.get(sequence)).get(k) / (double) totalSum;
						if (frequency != 0) 
							result -= frequency * (Math.log(frequency) / Math.log(2));

					}

					addedMap.get(sequence).add(Attributes.classes.size(), result);
					// System.out.println(result);
				}
				total.put(i, addedMap);

				br.close();

				br = null;

			}
			System.out.println(total.toString());

			// CALCULAREA GAIN-ului
			calculateMaxGain(node,total, initialLinesNo);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

	}
	
	public int findIndexOfCat(String cat){
		for(int i=0;i<Categories.categories.length;i++){
			if(Categories.categories[i].equals(cat)) return i;
		}
		return -1;
	}

}
