
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Vector;

public class bitmapIndex {

	private static final int bitmapM=5;
	private static final String bmpath=constants.path+"\\bitmaps";
	
	
	
	public static ArrayList<Object> getColumnDistinctOrdered(String tablename,String columnname){
		ArrayList<Object> columnvalues=new ArrayList<>();
		ArrayList<Object> finalList=new ArrayList<>();
		ArrayList<String> pagenames=utils.getnameFROMAllTablesCSV(tablename);
		for(int i=0;i<pagenames.size();i++) {
			Page p=new Page(pagenames.get(i));
			p.GetSerializedPaged();
			Vector<Hashtable<String, Object>> pageTuples=p.getTuples();
			for(int j=0;j<pageTuples.size();j++) {
				columnvalues.add(pageTuples.get(j).get(columnname));
			}
			
		}
		
		ArrayList<Object> distinctValues=getListDistinctValues(columnvalues);
		
		// string 
		if(distinctValues.get(0) instanceof String) {
			finalList=sort(distinctValues,1);
		}
		//double
		if(distinctValues.get(0) instanceof Double) {
			finalList=sort(distinctValues,3);
		}
		//int
		if(distinctValues.get(0) instanceof Integer) {
			finalList=sort(distinctValues,2);
		}
		
		
	return finalList;
	}
	
	public static ArrayList<Object> getListDistinctValues(ArrayList<Object> list){
		ArrayList<Object> distinct=new ArrayList<>();
		for (int i=0;i<list.size();i++) {
			if(!distinct.contains(list.get(i))) {
				distinct.add(list.get(i));
			}
		}
		return distinct;
	}
	
	
	public static ArrayList<Object> sort(ArrayList<Object> list,int parse){
		ArrayList<Object> objlist=new ArrayList<>();
		if(parse==1) {
			ArrayList<String> strings = new ArrayList<>(list.size());
			for (Object object : list) {
			    strings.add(Objects.toString(object, null));
			}
			Collections.sort(strings);
			for (Object sobj : strings) {
			    objlist.add(sobj);
			}
		}
		if(parse==3) {
			ArrayList<Double> doubles = new ArrayList<>(list.size());
			for (Object object : list) {
				doubles.add((Double) object);
			}
			
			Collections.sort(doubles);
			for (Object dobj : doubles) {
			    objlist.add(dobj);
			}
		} 
		if(parse==2) {
			ArrayList<Integer> integers = new ArrayList<>(list.size());
			for (Object object : list) {
				integers.add((Integer)object);
			}
			Collections.sort(integers);
			for (Object iobj : integers) {
			    objlist.add(iobj);
			}
			
		}
		return objlist;
	}

	public static void setValuesBitmap(ArrayList<Object> valueslist,String tablename,String columnname){
		int pagesRequired=getValueBitmapAsList(valueslist.get(0), tablename, columnname).size();
		
		for (int i=0;i<pagesRequired;i++) {
			LinkedHashMap<Object,ArrayList<Integer>> entryhash=new LinkedHashMap<>();
			for (Object object : valueslist) {
				ArrayList<ArrayList<Integer>> bitmapaslist=getValueBitmapAsList(object, tablename, columnname);
				ArrayList<Integer> entrybm=bitmapaslist.get(i);
				entryhash.put(object, entrybm);
				}
			BMbuilder.createNormalBmSerialized(tablename,columnname,entryhash,i);
			BMbuilder.saveColumnBMencoded(tablename, columnname, entryhash, i);
			
		}
		
		
		
	}
	
	public static ArrayList<ArrayList<Integer>> getValueBitmapAsList(Object value,String tablename,String columnname){
		ArrayList<Object> columnvalues=new ArrayList<>();
		
		ArrayList<String> pagenames=utils.getnameFROMAllTablesCSV(tablename);
		
		for(int i=0;i<pagenames.size();i++) {
			Page p=new Page(pagenames.get(i));
			p.GetSerializedPaged();
			Vector<Hashtable<String, Object>> pageTuples=p.getTuples();
			for(int j=0;j<pageTuples.size();j++) {
				columnvalues.add(pageTuples.get(j).get(columnname));
			}
			
		}
		
		ArrayList<ArrayList<Integer>> bitmapIndexlist=new ArrayList<>();
		int stoppindIndex=columnvalues.size();
		int lastColumnValuesPos=0;
		
		
		while(stoppindIndex>0) {
			if (stoppindIndex>=bitmapM) {
				stoppindIndex=stoppindIndex-bitmapM;
				ArrayList<Integer> sublist=new ArrayList<>();
				for(int j=0;j<bitmapM;j++) {
					
					
					if(columnvalues.get(lastColumnValuesPos).equals(value)) {
						sublist.add(1);
					}
					else {
						sublist.add(0);
					}
					lastColumnValuesPos++;
					
				}
				
				bitmapIndexlist.add(sublist);
				
				
			}
			else {
				
				
				ArrayList<Integer> sublist=new ArrayList<>();
				for(int i=0;i<stoppindIndex;i++) {
					if(columnvalues.get(lastColumnValuesPos).equals(value)) {
						sublist.add(1);
					}
					else {
						sublist.add(0);
					}
					stoppindIndex--;
					lastColumnValuesPos++;
				}
				bitmapIndexlist.add(sublist);
				
				
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		

		return bitmapIndexlist;
	}
	
	
}
