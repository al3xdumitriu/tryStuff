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

	Map<String, HashMap<String, ArrayList<Double>>> memoredAttr = new HashMap<String, HashMap<String, ArrayList<Double>>>();
	String[] bestAtributesNames;

	public static void main(String[] args) {

		Attributes.setAttr();
		System.out.println(Attributes.attributes.toString());
		System.out.println(Attributes.classes.toString());
		App obj = new App();
		//obj.run();

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
			Map<Integer,Map<String, ArrayList<Double>>> total=new HashMap<Integer,Map<String, ArrayList<Double>>>();
			for (int i = 0; i < car.length-1; i++) {

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
						temp.add(0.0);
						temp.add(0.0);
						addedMap.put(car[i], temp);
					}
					((ArrayList) addedMap.get(car[i])).set(0, (double) (((ArrayList) addedMap.get(car[i])).get(0)) + 1);

					if (car[car.length - 1].equals("yes")) {
						double tempNr = (double) ((ArrayList) addedMap.get(car[i])).get(1);
						((ArrayList) addedMap.get(car[i])).remove(1);
						tempNr++;
						((ArrayList) addedMap.get(car[i])).add(1, tempNr);

						// ((ArrayList)addedMap.get(car[i])).set(0,(int)(((ArrayList)addedMap.get(car[i])).get(1))+1);
						//

					}
					// }

					// calculate the entropy
				}
				result = 0.0;
				//System.out.println(addedMap.toString());
				Iterator it = addedMap.keySet().iterator();
				while (it.hasNext()) {
					String sequence = it.next().toString();
					result = 0.0;
					Double frequency = (double) ((ArrayList) addedMap.get(sequence)).get(1)
							/ (double) ((ArrayList) addedMap.get(sequence)).get(0);
					if (frequency != 0)
						result -= frequency * (Math.log(frequency) / Math.log(2));
					frequency = ((double) ((ArrayList) addedMap.get(sequence)).get(0)
							- (double) ((ArrayList) addedMap.get(sequence)).get(1))
							/ (double) ((ArrayList) addedMap.get(sequence)).get(0);
					if (frequency != 0)
						result -= frequency * (Math.log(frequency) / Math.log(2));
					
					addedMap.get(sequence).add(2, result);
					//System.out.println(result);
				}
				total.put(i, addedMap);
				
				br.close();

				br = null;

			}
			System.out.println(total.toString());
			
			//CALCULAREA GAIN-ului
			calculateMaxGain(total, initialLinesNo);

			
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

	
	
	public void calculateMaxGain(Map<Integer,Map<String, ArrayList<Double>>> total,int initialLinesNo){
		double minSumEntropy=-1;
		Iterator it=total.keySet().iterator();
		while(it.hasNext()){
			int it2=(int) it.next();
			Iterator innerMap =total.get(it2).keySet().iterator();
			double localSumEntropy=0.0;
			while(innerMap.hasNext()){
				String sequence = innerMap.next().toString();
				if((total.get(it2).get(sequence).get(total.get(it2).get(sequence).size()-1))!=0.0)
				localSumEntropy -= (total.get(it2).get(sequence).get(0)/initialLinesNo)*(total.get(it2).get(sequence).get(total.get(it2).get(sequence).size()-1));
				
				
			}
			System.out.println("localSum:"+localSumEntropy);
			if (minSumEntropy<localSumEntropy) {
				minSumEntropy=localSumEntropy;
			}
			
		}

		System.out.println("final"+minSumEntropy);
	}
	
	
//	public static Double calculateShannonEntropy(List<String> values) {
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		// count the occurrences of each value
//		for (String sequence : values) {
//			if (!map.containsKey(sequence)) {
//				map.put(sequence, 0);
//			}
//			map.put(sequence, map.get(sequence) + 1);
//		}
//
//		// calculate the entropy
//		Double result = 0.0;
//		for (String sequence : map.keySet()) {
//			Double frequency = (double) map.get(sequence) / values.size();
//			result -= frequency * (Math.log(frequency) / Math.log(2));
//		}
//		return result;
//	}

}
