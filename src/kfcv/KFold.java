package kfcv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;



public class KFold {
	String line = "";
	String ecoli = "C:\\UTA\\Fall2016\\Machine Learning\\Assn1\\ecoli.csv";
	String csvSplitBy = ",";
	public void ReadDataset(){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(ecoli));
			while ((line = br.readLine()) != null) {
				String[] ecoliColumn = line.split(csvSplitBy);
				System.out.println(ecoliColumn[0] +" "+ ecoliColumn[1]+" "+ecoliColumn[2]+" "+ecoliColumn[3]+" "+ecoliColumn[4]+" "+ecoliColumn[5]+" "+ecoliColumn[6]+" "+ecoliColumn[7]);
//				ArrayList<EcoliAttributes> ecoliList = new ArrayList<EcoliAttributes>();
//				EcoliAttributes ecoliAttr = new EcoliAttributes();
//				String[] ecoliColumn = line.split(",");
//				ecoliAttr.mcg = 5;
//				ecoliList.add(ecoliAttr);
//				//ecoliList.put(ecoliColumn[EcoliAttributes.ECOLI_MCG_INDEX].replace("'", ""));
			}
			br.close();
		}
		 catch(FileNotFoundException ex){
			 System.out.println("Unable to open csv file" );		
		 }	
		 catch (IOException e) {		
			 e.printStackTrace();
		 }
	}

	public static void main(String[] args){
		KFold k = new KFold();
		k.ReadDataset();
	}
		
}

