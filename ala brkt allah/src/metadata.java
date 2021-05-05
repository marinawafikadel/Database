import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class metadata {
	static String path = constants.path+"metadata.csv";

	// gets the meta data lines
	public static ArrayList<String> getMetaData() {
		ArrayList<String> metalist = new ArrayList<>();
		BufferedReader fileReader = null;
		FileWriter fileWriter = null;

		try {
			String l = "";
			fileReader = new BufferedReader(new FileReader(path));
			while ((l = fileReader.readLine()) != null) {
				metalist.add(l);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return metalist;

	}

	// adds to meta data
	public static void addMetadata(String strTableName,
			String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) {
		ArrayList<String> metalist = getMetaData();
		ArrayList<String> keyList = utils.getHashKeysAsList(htblColNameType);

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(path);
			for (int i = 0; i < metalist.size(); i++) {
				fileWriter.append(metalist.get(i));
				fileWriter.append("\n");
			}

			for (int i = 0; i < htblColNameType.size(); i++) {
				fileWriter.append(strTableName);
				fileWriter.append(",");

				fileWriter.append(keyList.get(i));
				fileWriter.append(",");
				fileWriter.append(htblColNameType.get(keyList.get(i)));
				fileWriter.append(",");
				if (keyList.get(i).equals(strClusteringKeyColumn))
					fileWriter.append("True");

				else
					fileWriter.append("False");
				fileWriter.append(",");
				fileWriter.append("True");
				fileWriter.append("\n");

			}
			System.out.println("CSV file was created successfully !!!");
		}

		catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	public static String getMetaData2(String tablename){
		ArrayList<String> metalist2=new ArrayList<>();
		BufferedReader fileReader = null;
		  FileWriter fileWriter = null;
		 String column="";
		 String[]array;
			try {
				String l="";
				fileReader = new BufferedReader(new FileReader(path));
				while((l=fileReader.readLine())!=null){
					metalist2.add(l);
					array=l.split(",");
					if(array[0].equals(tablename))
						{
						if(array[3].equals("True")){
						
							column=array[1];
							break;
						}}
				
					}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return column;
	}
	
	public static String[] getMetaData3(String tablename){
		ArrayList<String> metalist2=new ArrayList<>();
		BufferedReader fileReader = null;
		  FileWriter fileWriter = null;
		 String column="";
		 String[]array1 =new String[2];
		 String[]array;
			try {
				String l="";
				fileReader = new BufferedReader(new FileReader(path));
				while((l=fileReader.readLine())!=null){
					metalist2.add(l);
					array=l.split(",");
					if(array[0].equals(tablename))
						{
						if(array[3].equals("True")){
						
							column=array[1];
							array1[0]=column;
							array1[1]=array[2];
							break;
						}}
				
					}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			return array1;
	}
	
///////get all table names /////
	public static ArrayList<String> getalltablenames (){
		ArrayList<String> metalist2=new ArrayList<>();
		ArrayList<String> tables=new ArrayList<>();
		BufferedReader fileReader = null;
		  FileWriter fileWriter = null;
		 String column="";
		 String[]array;
			try {
				String l="";
				fileReader = new BufferedReader(new FileReader(path));
				while((l=fileReader.readLine())!=null){
					metalist2.add(l);
					array=l.split(",");
					
						tables.add(array[0]);
						
				
					}
			
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tables;
	}
	
	

	

}
