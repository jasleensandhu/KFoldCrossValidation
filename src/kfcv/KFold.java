package kfcv;

//import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

public class KFold {
	String line = "";
	String ecoli = "C:\\UTA\\Fall2016\\Machine Learning\\Assn1\\ecoli.csv";
	String csvSplitBy = ",";
	static ArrayList<EcoliAttributes> attributeValueList = null;

	public void ReadDataset() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(ecoli));

			// fetching the column name
			line = br.readLine();

			// column names in array
			String[] ecoliColumnName = line.split(csvSplitBy);
			int size = ecoliColumnName.length;

			for (String colName : ecoliColumnName) {
				System.out.print(colName + " ");
			}
			System.out.print("\n");

			// Fetching attributes into ArrayList
			attributeValueList = new ArrayList<EcoliAttributes>();
			while ((line = br.readLine()) != null) {
				EcoliAttributes ea = new EcoliAttributes(size, ecoliColumnName);
				ea.parseString(line);
				attributeValueList.add(ea);
				System.out.println(ea);
				System.out.println("--------------------------");
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open csv file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			EcoliAttributes.printMinMax();
			System.out.println("Normalized Dataset");
			for (EcoliAttributes ea : attributeValueList) {
				ea.Normalize();
				System.out.println("***********************");
				System.out.println(ea);
			}
		}
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public void KFoldCrossValidation(int partitionSize) {

		ArrayList<EcoliAttributes> tempAttributeValueList = new ArrayList<EcoliAttributes>(attributeValueList);
		int rowPartition = (int) Math.ceil((attributeValueList.size() / partitionSize)) + 1;

		int randNum;

		EcoliAttributes[][] kFoldArray = new EcoliAttributes[partitionSize][];
		int count = 0;
		int i = 0;

		while (tempAttributeValueList.size() != 0) {
			if (tempAttributeValueList.size() > rowPartition)
				kFoldArray[i] = new EcoliAttributes[rowPartition];
			else
				kFoldArray[i] = new EcoliAttributes[tempAttributeValueList.size()];

			for (int j = 0; j < rowPartition && tempAttributeValueList.size() != 0; j++) {

				randNum = randInt(0, tempAttributeValueList.size() - 1);
				count++;
				System.out.println("Count = " + count + " Random = " + randNum);

				kFoldArray[i][j] = tempAttributeValueList.get(randNum);
				tempAttributeValueList.remove(randNum);

			}
			i++;
		}

		for (int x = 0; x < partitionSize; x++) {
			for (int j = 0; j < kFoldArray[x].length; j++) {
				try(FileWriter fw = new FileWriter("Partition_"+x+".txt", true);
					    BufferedWriter bw = new BufferedWriter(fw);
					    PrintWriter out = new PrintWriter(bw))
					{
					    out.println(kFoldArray[x][j].toString());
					    out.println("---------------------------------------------");
					}
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.print(kFoldArray[x][j] + "\n");
				System.out.println("------------------------");
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		KFold kFold = new KFold();
		kFold.ReadDataset();
		int k = 10;
		kFold.KFoldCrossValidation(k);

	}

}
