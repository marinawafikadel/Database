import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BMbuilder {
	
	
	
	//creates the serialized as HashMap<Object,ArrayList<Integer>>
	public static void createNormalBmSerialized(String tablename,String columnname,HashMap<Object,ArrayList<Integer>> entryhash,int page) {
	//creates the required folders if not avaible
		createfolders(constants.bmnormalpath,tablename,columnname);
		try {
		FileOutputStream fileOut = new FileOutputStream(constants.bmnormalpath+"\\"+tablename+"\\"+columnname+"\\"+ String.valueOf(page)
				+ ".ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(entryhash);
		out.close();
		fileOut.close();
		System.out.println("Normal BM Index Of "+columnname+" "+ page+" is saved in Path" + "\\"+tablename+"\\"+columnname);
	} catch (IOException i) {
		i.printStackTrace();
	}
		
		
	}
	
	//creates the serialized as HashMap<Object,String>
public static void saveColumnBMencoded(String tablename,String columnname,HashMap<Object,ArrayList<Integer>> entryhash,int page) {
	
	
	//creates the required folders if not avaible
			createfolders(constants.bmcodedpath,tablename,columnname);
			try {
			FileOutputStream fileOut = new FileOutputStream(constants.bmcodedpath+"\\"+tablename+"\\"+columnname+"\\"+ String.valueOf(page)
					+ ".ser");
			HashMap<Object,String> encodeData= encodeIndex(entryhash);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(encodeData);
			out.close();
			fileOut.close();
			System.out.println("Encoded BM Index Of "+columnname+" "+ page+" is saved in Path" + "\\"+tablename+"\\"+columnname);
		} catch (IOException i) {
			i.printStackTrace();
		}
	
	
		
	}

private static void createfolders(String basePath,String tablename,String columnName) {
	String path=basePath+"\\"+tablename+"\\"+columnName+"\\";
	File dir = new File(path);
	if (dir.exists()) {
		
		return;
	}
	else {
		
		dir.mkdirs();
	}
	
}




private static HashMap<Object,String> encodeIndex(HashMap<Object,ArrayList<Integer>> entryhash){
	HashMap<Object,String> encodedHash=new HashMap<>();
	for ( Entry<Object, ArrayList<Integer>> entry : entryhash.entrySet()) {
	    Object key =  entry.getKey();
	    ArrayList<Integer> list = entry.getValue();
	  
	    String encodedlistString=getListEncoded(list);
	    encodedHash.put(key, encodedlistString);
	    
	}
	return encodedHash;
}


private static String getListEncoded(ArrayList<Integer> list) {
	
	int zerocounter=0;
	int onecounter=0;
	String all="";
	for ( int entry : list) {
		if (entry==0) {
			if(onecounter!=0) {
			all=all+String.valueOf(onecounter)+String.valueOf(1);
				onecounter=0;
			}
			
			zerocounter++;
			}
		if (entry==1) {
			if(zerocounter!=0) {
				all=all+String.valueOf(zerocounter)+String.valueOf(0);
			zerocounter=0;
			}
			
			onecounter++;
			}
	}
	if(onecounter!=0) {
		all=all+String.valueOf(onecounter)+String.valueOf(1);
			onecounter=0;
		}
	if(zerocounter!=0) {
		all=all+String.valueOf(zerocounter)+String.valueOf(0);
	zerocounter=0;
	}
	
	
	
	return all;
	
}

}
