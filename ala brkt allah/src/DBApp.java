import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

public class DBApp {
	////main method //
	public static void main (String[]args){
		
	}
	//select with index
	/*public Vector<Hashtable<String, Object>> selectFromTable(SQLTerm[] arrSQLTerms,
			 String[] strarrOperators) {
		
		
	}*/

	public void init() {
		// creates file if not availble and does nothing if that file is
		// availble
		utils.createfile(constants.path+"AllTables.csv");
		utils.createfile(constants.path+"metadata.csv");
	}

	public  void createTable(String strTableName,
			String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) throws DBAppException 
			
	{
		
		htblColNameType.put("touchdate","java.util.Date");
		
		
		if (utils.isTableDublicate(strTableName)) {
			//System.out.print("DUPLICATE! Choose another table name");
             throw new DBAppException("DUPLICATE! Choose another table name");
		} else {
			metadata.addMetadata(strTableName, strClusteringKeyColumn,
					htblColNameType);
			Page p = new Page(strTableName);
			p.CreateSerializedPaged();
			utils.addnameToAllTablesCSV(strTableName);

		}

	}

	
	
	

	public  void insertIntoTable(String strTableName,Hashtable<String, Object> htblColNameValue) throws DBAppException{
		htblColNameValue.put("touchdate", Calendar.getInstance().getTime());
		Vector<Hashtable<String, Object>> tuples2 = new Vector<Hashtable<String, Object>>();
		Vector<Hashtable<String, Object>> tuples3 = new Vector<Hashtable<String, Object>>();
		String primarykey = metadata.getMetaData2(strTableName);
		System.out.print(primarykey);
		ArrayList<String> pagesnames = utils
				.getnameFROMAllTablesCSV(strTableName);
		ArrayList<Object> allprimarykeys = new ArrayList<>();
		ArrayList<String>s =metadata.getalltablenames();
		if (!s.contains(strTableName))
			//System.out.println("no table with this name");
			throw new DBAppException("no table with this name");
		else {
			if (!htblColNameValue.containsKey(primarykey)){
				//System.out.println("please insert primary key");
				throw new DBAppException("please insert primary key");
			}
			else{
				if(!pagesnames.contains(strTableName)){
				Page p = new Page(strTableName);
				p.CreateSerializedPaged();
				utils.addnameToAllTablesCSV(strTableName);
					
				}
				else {
			for(int i=0 ; i<pagesnames.size() ; i++){
				Page page = new Page(pagesnames.get(i));
				page.GetSerializedPaged();
				tuples3=page.getTuples();
				for (int j = 0 ; j<tuples3.size() ; j++){
					allprimarykeys.add(tuples3.get(j).get(primarykey));
				}
				
			}
			if(allprimarykeys.contains(htblColNameValue.get(primarykey)))
				//System.out.print("choose another primary key");
				throw new DBAppException("choose another primary key");
			else {
			System.out.println(pagesnames.get(pagesnames.size() - 1));

			Page p = new Page(pagesnames.get(pagesnames.size() - 1));
			htblColNameValue.put("touchdate", Calendar.getInstance().getTime());
			p.insert(htblColNameValue);
			
		}
	}
	}
	}
	}


	/* elcode start here */

	public void deleteFromTable(String strTableName,
			Hashtable<String, Object> htblColNameValue)throws DBAppException {
		ArrayList<String> pagesnames = utils
				.getnameFROMAllTablesCSV(strTableName);
		ArrayList<String> x = utils.getHashKeysAsList2(htblColNameValue);
		String name;
		Vector<Hashtable<String, Object>> tuples2 = new Vector<Hashtable<String, Object>>();

		if (pagesnames.get(0).equals("no"))
			//System.out.println("no table with this name");
			throw new DBAppException("no table with this name");
		else {

			for (int i = 0; i < pagesnames.size(); i++) {
				Page p = new Page(pagesnames.get(i));
				p.GetSerializedPaged();
				tuples2 = p.getTuples();

				for (int j = tuples2.size() - 1; j >= 0; j--) {
					boolean flag = true;
					for (int z = 0; z < x.size(); z++) {
						if (!htblColNameValue.get(x.get(z)).equals(
								tuples2.get(j).get(x.get(z)))) {

							flag = false;
						}

					}
					if (flag == true) {
						tuples2.remove(j);
						// System.out.println("11");
						p.CreateSerializedPaged(tuples2);
						//p.getTuples().size();
						
				
						

					}

				}

			}
			
		
		}
		Page.shiftpage(pagesnames);
		Page.delete_empty(pagesnames);
	}

	public  void updateTable(String strTableName,String strKey,Hashtable<String,Object> htblColNameValue)throws DBAppException{
		String[]array1=null;
		//int Key=Integer.parseInt(strKey);
		int key =0;
		boolean flag=true;
		ArrayList<String> x = utils.getHashKeysAsList2(htblColNameValue);
		ArrayList<String> pagesnames = utils
				.getnameFROMAllTablesCSV(strTableName);
		Vector<Hashtable<String, Object>> tuples2 = new Vector<Hashtable<String, Object>>();
		
		
		if (pagesnames.get(0).equals("no")){
			//System.out.println("no table with this name");
			throw new DBAppException("no table with this name");
			}
		else {
			
	       array1=metadata.getMetaData3(strTableName);
	       System.out.println(array1[1]);
	       if(array1[1].equals("java.lang.Integer")||array1[1].equals("java.lang.Double")){
	    	   key=Integer.parseInt(strKey);
	    	  
	    	   }
	       
			for (int i = 0; i < pagesnames.size(); i++) {
				Page p = new Page(pagesnames.get(i));
				p.GetSerializedPaged();
				tuples2 = p.getTuples();
				
				
				if ( tuples2.size()>0){
					ArrayList<String> tablecolumns = utils.getHashKeysAsList2(tuples2.get(0));
					if (!(tablecolumns.containsAll(x))){
						//System.out.print("the table doesn't contain those columns");
						throw new DBAppException("the table doesn't contain those columns");
					}
				
				else{
					
				for (int j = 0; j <tuples2.size(); j++) {
					if(array1[1].equals("java.lang.Integer")||array1[1].equals("java.lang.Double")){
				    	  
					if((Integer)tuples2.get(j).get(array1[0])==key){
						flag=false;
						for (int z = 0 ; z<x.size() ; z++){
							
							tuples2.get(j).put(x.get(z), htblColNameValue.get(x.get(z)));
							tuples2.get(j).put("touchdate", Calendar.getInstance().getTime());
						
						}	
						
					}

				}
					else{
						
						if(tuples2.get(j).get(array1[0]).equals(new String(strKey))){	
							flag=false;
							for (int z = 0 ; z<x.size() ; z++){
								
								tuples2.get(j).put(x.get(z), htblColNameValue.get(x.get(z)));
								tuples2.get(j).put("touchdate", Calendar.getInstance().getTime());
							
							}	
					}
				}}
				if(flag==true){
					//System.out.print("there is no id with this value");
					throw new DBAppException("there is no id with this value");
				}
				
				
			}
				p.CreateSerializedPaged(tuples2);
		}
			}}
	}

	
	
	public void createBitmapIndex(String strTableName,
			String strColName) {
		
		//gets the values of a specific column ordered and distinct 
		ArrayList<Object> columnValuesList=bitmapIndex.getColumnDistinctOrdered(strTableName, strColName);
		//sets the bitmap index of both normal and encoded
		bitmapIndex.setValuesBitmap(columnValuesList,strTableName,strColName);
		
		
	}
	
	
	
	
	
	
	/* ends here */
	public  void printPage(String name) {
		Page p = new Page(name);
		p.SetColumns();
		p.GetSerializedPaged();
		Vector<Hashtable<String, Object>> tuples = p.getTuples();
		Hashtable<String, String> columns = p.getColumns();

		ArrayList<String> columnskeys = utils.getHashKeysAsList(columns);
		for (int i = 0; i < columnskeys.size(); i++) {
			System.out.print(columnskeys.get(i) + "   |   ");
		}

		for (int i = 0; i < tuples.size(); i++) {
			Hashtable<String, Object> table = tuples.get(i);
			System.out.print("\n");
			for (int j = 0; j < columnskeys.size(); j++) {
				System.out.print(table.get(columnskeys.get(j)) + " | ");
			}
		}
	}

}
