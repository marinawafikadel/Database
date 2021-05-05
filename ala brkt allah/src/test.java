import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class test {

	public static void main(String[] args) {

		//DBApp db = new DBApp();
		//db.init();
		String strTableName = "Student";
		/*Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.double");
		try {
			db.createTable( strTableName, "id", htblColNameType );
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*Hashtable htblColNameValue = new Hashtable( );
		htblColNameValue.put("id", new Integer( 6 ));
		htblColNameValue.put("name", new String("nancy" ) );
		htblColNameValue.put("gpa", new Double( 0.7 ) );
		try {
			db.insertIntoTable( strTableName , htblColNameValue );
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//db.createBitmapIndex( strTableName, "gpa" ); 
		HashMap<Object,ArrayList<Integer>>a=bmtools.getnadaIndex(strTableName, "gpa");
		ArrayList<ArrayList<Integer>> x= selectbitmapUtils.valueindexes( a,">",0.1);
		System.out.println(x.size());
		for(int i =0;i<x.size();i++){
			for(int j=0; j<x.get(i).size();j++){
				System.out.print(x.get(i).get(j));
			}
			System.out.println("");
		}
    	
//		ArrayList<String> list=selectbitmapUtils.getHashKeysAsList3(a);
//		selectbitmapUtils.printarrays(list);
		//
	//	bmtools.printNormalIndex(strTableName, "gpa");
		//selectbitmapUtils.printarrays(a);
		
	


	}

}
