import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;


public class selectbitmapUtils implements Comparable {
	//operators on array list
	public static ArrayList<Integer> logicalOperators(ArrayList <Integer>a , ArrayList<Integer> b ,String operator){
		if(operator.equals("and")){
			for(int i =0 ; i<a.size();i++){
				
				if(a.get(i)==1&&b.get(i)==1){
					a.set(i, 1);
				}
				else{
					a.set(i,0);
				}
			}
		}
		if(operator.equals("or")){
			for(int i =0 ; i<a.size();i++){
				if(a.get(i)==0&&b.get(i)==0){
					a.set(i, 0);
				}
				else{
					a.set(i, 1);
				}
			}
		}
		if(operator.equals("xor")){
			for(int i =0 ; i<a.size();i++){
				if(a.get(i)!=b.get(i)){
					a.set(i, 1);
				}
				else{
					a.set(i, 0);
				}
			}
		}
		return a;
	}
	
		//print array list
	public static void printarray (ArrayList<Integer>a){
		for(int i =0 ; i<a.size();i++){
			System.out.print(a.get(i));
		}
	}
	//print array list string
public static void printarrays (ArrayList<String>a){
	for(int i =0 ; i<a.size();i++){
		System.out.print(a.get(i));
	}
}

   //or an arraylist
public static  ArrayList<Integer> Orallthearray (ArrayList<ArrayList<Integer>> a){
	ArrayList<Integer> finalarray = a.get(0);
   for(int i=0;i<a.size();i++){
		finalarray=logicalOperators(finalarray , a.get(i) ,"or");
	}
	return finalarray;
	
	
}

	//get all array lists of a value
	public static ArrayList<ArrayList<Integer>>  valueindexes(HashMap<Object,ArrayList<Integer>> h,String operator ,Object value){
		ArrayList<ArrayList<Integer>> a = new ArrayList<>();
		
			if(operator.equals("=")){
				for (Map.Entry<Object, ArrayList<Integer>> entry : h.entrySet()) {
					Object key = entry.getKey();
					if(newcompareTo(key,value)==0){
						a.add(h.get(key));
					}
				}
			}
		if(operator.equals(">")){
			for (Map.Entry<Object, ArrayList<Integer>> entry : h.entrySet()) {
				Object key = entry.getKey();
				if(newcompareTo(key,value)>0){
					a.add(h.get(key));
				}
			}
		}
		if(operator.equals("<")){
			for (Map.Entry<Object, ArrayList<Integer>> entry : h.entrySet()) {
				Object key = entry.getKey();
				if(newcompareTo(key,value)<0){
					a.add(h.get(key));
				}
			}
		}
		
	    
		return a  ;
		}

	
	
	//get hashkeys as list for bitmap
	public static ArrayList<String> getHashKeysAsList3(
			HashMap<Object,ArrayList<Integer>> h) {
		ArrayList<String> keyList = new ArrayList<>();
		for (Entry<Object, ArrayList<Integer>> entry : h.entrySet()) {
			String key =  entry.getKey().toString();
			keyList.add(key);
		}
		return keyList;
	}
	public static int newcompareTo(Object a , Object b){
		int x =0;
		if(a instanceof Integer){
			Integer a2 = (Integer)a;
			Integer b2 = (Integer)b;
			if(a2.compareTo(b2)>0){
	      		x=1;
		
	       }
	      	else if(a2.compareTo(b2)<0){
	      		x=-1;
	      	}
	      	else{
	      		x=0;
				
			}
		}
		else if(a instanceof String){
			String a2 = (String)a;
			String b2 = (String)b;
			if(a2.compareTo(b2)>0){
	      		x=1;
		
	       }
	      	else if(a2.compareTo(b2)<0){
	      		x=-1;
	      	}
	      	else{
	      		x=0;
				
			}
		}
		else if(a instanceof Double){
			Double a2 = (Double)a;
			Double b2 = (Double)b;
			if(a2.compareTo(b2)>0){
	      		x=1;
		
	       }
	      	else if(a2.compareTo(b2)<0){
	      		x=-1;
	      	}
	      	else{
	      		x=0;
				
			}
		}
			
      	
		return x;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
