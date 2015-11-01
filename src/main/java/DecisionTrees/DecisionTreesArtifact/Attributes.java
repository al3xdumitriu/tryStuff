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

public class Attributes {

	static public Map<String, ArrayList<String>> attributes;
	static public List<String> classes;

	static public void setAttr() {
		attributes = new HashMap<String, ArrayList<String>>();
		classes=new ArrayList<String>();
		String csvFile = "names.data";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ":";
		String cvsSplitBy2 = ",";
		String name;
		String[] aux;
		String[] values;

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
				
				if(initialLinesNo==5) {
					values=line.split(cvsSplitBy2);
					for(String seq:values){
						classes.add(seq.trim());
					}
				}

				if (initialLinesNo > 8) {
					aux = line.split(cvsSplitBy);
					name = aux[0];
					values = aux[1].split(cvsSplitBy2);
					ArrayList aux2=new ArrayList<String>();
					for(String seq:values){
						aux2.add(seq.trim());
					}
					attributes.put(name, aux2);
				}
				// use comma as separator

				
			}
			br.close();
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

		System.out.println("Done loading attributes");

	}
}
