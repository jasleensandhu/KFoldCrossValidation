package kfcv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EcoliAttributes{
	static ArrayList<Float> min_attribute = null;
	static ArrayList<Float> max_attribute = null;
	static String[] attributeNames;
	HashMap<String, Float> attributeValue = null;
	public String className;

	public EcoliAttributes(int size, String[] attributeNames) {

		EcoliAttributes.attributeNames = attributeNames;
		
		if (min_attribute == null && max_attribute == null) {
			min_attribute = new ArrayList<Float>(size-1);
			max_attribute = new ArrayList<Float>(size-1);

			for (int i = 0; i < size - 1; i++) {
				min_attribute.add(Float.MAX_VALUE);
				max_attribute.add(Float.MIN_VALUE);
			}
		}
		attributeValue = new HashMap<>(size);
	}

	public void parseString(String line) {
		String[] tokens = line.split(",");
		int i = 0;
		for (i = 0; i < tokens.length - 1; i++) {
			float val = Float.parseFloat(tokens[i]);
			if (min_attribute.get(i) > val) {
				min_attribute.set(i, val);
			}
			if (max_attribute.get(i) < val) {
				max_attribute.set(i, val);
			}
			attributeValue.put(attributeNames[i], val);
		}
		className = tokens[tokens.length - 1];
	}

	public void Normalize(){
		float newValue;
		for(int i= 0; i<attributeValue.size();i++){
			newValue = (attributeValue.get(attributeNames[i])-min_attribute.get(i))/(max_attribute.get(i)-min_attribute.get(i));
			attributeValue.replace(attributeNames[i],newValue);
		}				
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Iterator it = attributeValue.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append(pair.getKey() + " = " + pair.getValue() + "\n");
		}
		sb.append("Class = " + className);
		return sb.toString();
	}

	public static void printMinMax() {
		for (int i = 0; i < min_attribute.size(); i++) {
			System.out.println(attributeNames[i] + " -> Min=" + min_attribute.get(i) + ", Max->" + max_attribute.get(i));
		}
	}

}
