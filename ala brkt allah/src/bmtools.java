import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class bmtools {
//get normal index
	public static void  printNormalIndex(String tablename,String columnName) {
		HashMap<Object,ArrayList<Integer>> entryIndex;
		FileInputStream fileIn;
		
		ArrayList<Object> columnValuesList=bitmapIndex.getColumnDistinctOrdered(tablename, columnName);
		int indexpages=bitmapIndex.getValueBitmapAsList(columnValuesList.get(0), tablename, columnName).size();
		for(int i=0;i<indexpages;i++) {
			try {
				fileIn = new FileInputStream(constants.bmnormalpath+"\\"+tablename+"\\"+columnName+"\\"+ String.valueOf(i)
				+ ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				entryIndex =  (HashMap<Object, ArrayList<Integer>>) in.readObject();
				System.out.println("The "+columnName+" index from the "+i+" page is:" );
				System.out.println(Arrays.asList(entryIndex));
				in.close();
				fileIn.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
		
	}
	//this doesnt need explantion really
	public static HashMap<Object, ArrayList<Integer>> getnadaIndex(String tablename,String columnName) {
		HashMap<Object,ArrayList<Integer>> entryIndex = null;
		FileInputStream fileIn;
		
		ArrayList<Object> columnValuesList=bitmapIndex.getColumnDistinctOrdered(tablename, columnName);
		int indexpages=bitmapIndex.getValueBitmapAsList(columnValuesList.get(0), tablename, columnName).size();
		for(int i=0;i<indexpages;i++) {
			try {
				fileIn = new FileInputStream(constants.bmnormalpath+"\\"+tablename+"\\"+columnName+"\\"+ String.valueOf(i)
				+ ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				entryIndex =  (HashMap<Object, ArrayList<Integer>>) in.readObject();
				
				in.close();
				fileIn.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}return entryIndex;
		
	}


/*takes the table and column names and return the index as Hash<value,arrayofindex>
 * 
 * value is the value inside the column (for example name column would have values like hadi, lobna, shaimaa )
 * 
 * arrayOfIndex is the index corresponding to the value (for example, Hadi:[0,1,1,1,0,0])
 * 
 * PLEASEEE NOTEE THAT THIS METHOD RETURNS ALL INDEXES OF ALL PAGESSSSS ALLLL PAGESSSS IN A SINGLE ARRAY
 * 
 * */
	public static HashMap<Object,ArrayList<Integer>> getNormalIndex(String tablename,String columnName){
		HashMap<Object,ArrayList<Integer>> entryIndex;
		HashMap<Object,ArrayList<Integer>> finalIndex=new HashMap<>();
		FileInputStream fileIn;
		
		ArrayList<Object> columnValuesList=bitmapIndex.getColumnDistinctOrdered(tablename, tablename);
		int indexpages=bitmapIndex.getValueBitmapAsList(columnValuesList.get(0), tablename, columnName).size();
		for(int i=0;i<indexpages;i++) {
			try {
				fileIn = new FileInputStream(constants.bmnormalpath+"\\"+tablename+"\\"+columnName+"\\"+ String.valueOf(i)
				+ ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				entryIndex =  (HashMap<Object, ArrayList<Integer>>) in.readObject();
				 for (Entry<Object, ArrayList<Integer>> entry : entryIndex.entrySet()) {
				        ArrayList<Integer> bValues = entry.getValue();
				        Object bKey = entry.getKey();
				        ArrayList<Integer> cValues = finalIndex.get(bKey);
				        if (cValues == null) {
				        	finalIndex.put(bKey, new ArrayList<Integer>(bValues));
				        } else {
				            cValues.addAll(bValues);
				        }
				    }
				
				 
				
				in.close();
				fileIn.close();
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
		
		return finalIndex;
	}


//same as the method above, just adds a specific page number.

	public static HashMap<Object,ArrayList<Integer>> getNormalIndex(String tablename,String columnName,int page){
		HashMap<Object,ArrayList<Integer>> entryIndex;
		HashMap<Object,ArrayList<Integer>> finalIndex=new HashMap<>();
		FileInputStream fileIn;
		
		ArrayList<Object> columnValuesList=bitmapIndex.getColumnDistinctOrdered(tablename, tablename);
		int indexpages=bitmapIndex.getValueBitmapAsList(columnValuesList.get(0), tablename, columnName).size();
	
			try {
				fileIn = new FileInputStream(constants.bmnormalpath+"\\"+tablename+"\\"+columnName+"\\"+ String.valueOf(page)
				+ ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				entryIndex =  (HashMap<Object, ArrayList<Integer>>) in.readObject();
				 for (Entry<Object, ArrayList<Integer>> entry : entryIndex.entrySet()) {
				        ArrayList<Integer> bValues = entry.getValue();
				        Object bKey = entry.getKey();
				        ArrayList<Integer> cValues = finalIndex.get(bKey);
				        if (cValues == null) {
				        	finalIndex.put(bKey, new ArrayList<Integer>(bValues));
				        } else {
				            cValues.addAll(bValues);
				        }
				    }
				
				 
				
				in.close();
				fileIn.close();
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		
			System.out.println(Arrays.asList(finalIndex));
		return finalIndex;
	}

	
	//returns true if column is indexed
	public static boolean isColumnIndexed (String tablename,String columnName) {
		String path=constants.bmnormalpath+"\\"+tablename+"\\"+columnName+"\\";
		File dir = new File(path);
		if (dir.exists()) {
			
			return true;
		}
		else {
			
			return false;
		}
	}
	
	


}




