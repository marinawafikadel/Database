import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class utils {
	// gets all pages names when u pass the table name
	public static ArrayList<String> getnameFROMAllTablesCSV(String name) {
		ArrayList<String> returnednames = new ArrayList<>();
		ArrayList<String> nameslist = new ArrayList<>();
		BufferedReader fileReader = null;
		String path = constants.path+"AllTables.csv";
		boolean flag = false;
		try {
			fileReader = new BufferedReader(new FileReader(path));
			String l = "";
			while ((l = fileReader.readLine()) != null) {
				nameslist.add(l);
			}

			for (int i = 0; i < nameslist.size(); i++) {
				String splitted[] = nameslist.get(i).split(",");
				if (splitted[0].equals(name)) {
					flag = true;
					returnednames.add(nameslist.get(i));
				}
			}

			if (!flag) {
				returnednames.add("no");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnednames;
	}

	// add the name of the new page created
	public static void addnameToAllTablesCSV(String name) {
		ArrayList<String> names = new ArrayList<>();
		String path = constants.path+"AllTables.csv";
		BufferedReader fileReader = null;
		FileWriter fileWriter = null;
		try {
			fileReader = new BufferedReader(new FileReader(path));
			String l = "";
			while ((l = fileReader.readLine()) != null) {
				names.add(l);
			}
			if (!names.contains(name)) {
				names.add(name);
			}
			fileWriter = new FileWriter(path);
			for (int i = 0; i < names.size(); i++) {
				fileWriter.append(names.get(i));
				fileWriter.append("\n");

			}
			fileWriter.flush();
			fileWriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void removenameToAllTablesCSV(String name) {
		String path = constants.path+"AllTables.csv";
		BufferedReader fileReader = null;
		FileWriter fileWriter = null;
		ArrayList<String> names = new ArrayList<>();
		try {
			fileReader = new BufferedReader(new FileReader(path));
			String l = "";
			while ((l = fileReader.readLine()) != null) {
				if (!l.equals(name)) {
					// System.out.println(l);
					names.add(l);
				}
			}

			fileWriter = new FileWriter(path);
			for (int i = 0; i < names.size(); i++) {
				fileWriter.append(names.get(i));
				fileWriter.append("\n");

			}
			fileWriter.flush();
			fileWriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// checks if TABLE name ha table ya nada msh Page is dublicate
	public static boolean isTableDublicate(String TableName) {
		ArrayList<String> names = getnameFROMAllTablesCSV(TableName);
		if (names.get(0).equals("no"))
			return false;

		else
			return true;

	}

	// creates file if not availble and does nothing if taht file is availble
	public static void createfile(String path) {
		BufferedReader fileReader = null;

		try {
			fileReader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(path);
				System.out.println(path + " created");
			}

			catch (Exception ee) {
				System.out.println("Error in CsvFileWriter !!!");
				ee.printStackTrace();
			} finally {

				try {
					fileWriter.flush();
					fileWriter.close();

				} catch (IOException eee) {
					System.out
							.println("Error while flushing/closing fileWriter !!!");
					eee.printStackTrace();
				}

			}
		}
	}

	// gets hashtable keys as string if that hash is <String,String>

	public static ArrayList<String> getHashKeysAsList(
			Hashtable<String, String> table) {
		ArrayList<String> keyList = new ArrayList<>();
		for (Map.Entry<String, String> entry : table.entrySet()) {
			String key = entry.getKey();
			keyList.add(key);
		}
		return keyList;
	}

	// gets hashtable keys as string if that hash is <String,Object>
	public static ArrayList<String> getHashKeysAsList2(
			Hashtable<String, Object> table) {
		ArrayList<String> keyList = new ArrayList<>();
		for (Map.Entry<String, Object> entry : table.entrySet()) {
			String key = entry.getKey();
			keyList.add(key);
		}
		return keyList;
	}

	// Checks if insterted data (htblColNameValue) has correct types in regards
	// to the table original columns (columns)
	public static boolean isInputTypeValid(
			Hashtable<String, Object> htblColNameValue,
			Hashtable<String, String> columns) {
		ArrayList<String> keys = getHashKeysAsList2(htblColNameValue);
		for (int i = 0; i < keys.size(); i++) {
			String inputType = htblColNameValue.get(keys.get(i)).getClass()
					.getName();

			String ColumnType = columns.get(keys.get(i));

			if (!inputType.equals(ColumnType) && inputType.equals("")) {
				
				System.out.println(Arrays.asList(inputType));
				System.out.println(Arrays.asList(ColumnType));
				
				return false;
			}
				
		}
		return true;
	}

	
	}
