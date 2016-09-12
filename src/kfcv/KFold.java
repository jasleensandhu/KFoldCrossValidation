package kfcv;

//import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class KFold {
	String line = "";
	String ecoli = "C:\\UTA\\Fall2016\\Machine Learning\\Assn1\\ecoli.csv";
	String csvSplitBy = ",";
	static ArrayList<EcoliAttributes> attributeValueList = null;
	
	public void ReadDataset(){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(ecoli));			
			
			//ArrayList<String> locSite = new ArrayList<String>();
			
			//fetching the column name
			line=br.readLine();
			
			//column names in array
			String[] ecoliColumnName = line.split(csvSplitBy);
			int size = ecoliColumnName.length;
			
			
			for (String colName : ecoliColumnName) {
				System.out.print(colName + " ");
			}
			System.out.print("\n");
			
			//Fetching attributes into ArrayList
			attributeValueList = new ArrayList<EcoliAttributes>();
			while ((line = br.readLine()) != null) {
				//String[] ecoliColumnValue = line.split(csvSplitBy);
				EcoliAttributes ea = new EcoliAttributes(size,ecoliColumnName);				
				ea.parseString(line);				
				attributeValueList.add(ea);
				System.out.println(ea);
				System.out.println("--------------------------");
			}			
			br.close();
		}
		 catch(FileNotFoundException ex){
			 System.out.println("Unable to open csv file" );		
		 }	
		 catch (IOException e) {		
			 e.printStackTrace();
		 }
		finally {
		
			EcoliAttributes.printMinMax();
			//System.out.println(max_mcg+" "+min_msg);
			for(EcoliAttributes ea:attributeValueList){
				ea.Normalize();
				System.out.println("*********************");
				System.out.println(ea);
			}
		}
	}
	
	public static void main(String[] args){
		KFold k = new KFold();
		k.ReadDataset();
		//k.ExtractAttribute(attributeValueList);
	}
		
}
