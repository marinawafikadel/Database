import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public class Page implements Serializable {

	private Vector<Hashtable<String, Object>> tuples;
	private Hashtable<String, String> columns;

	private final int size = 5;
	private final String path = constants.path;
	private String Name;

	public Page(String TableName) {
		super();
		tuples = new Vector<>();
		columns = new Hashtable<>();
		Name = TableName;

	}

	public void CreateSerializedPaged() {
		try {
			FileOutputStream fileOut = new FileOutputStream(path + Name
					+ ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tuples);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in" + path);
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public void GetSerializedPaged() {
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(path + Name + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			tuples = (Vector<Hashtable<String, Object>>) in.readObject();
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

	/* new get and create ser */

	public void CreateSerializedPaged(Vector<Hashtable<String, Object>> tuples2) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path + Name
					+ ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tuples2);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in" + path);
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	// //////////////////////////
	public Vector<Hashtable<String, Object>> getTuples() {
		return tuples;
	}

	public Hashtable<String, String> getColumns() {
		return columns;
	}

	public void setTuples(Vector<Hashtable<String, Object>> tuples) {
		this.tuples = tuples;
	}

	public void SetColumns() {

		ArrayList<String> metaData = metadata.getMetaData();
		String[] name = Name.split(",");

		boolean flag = false;

		for (int i = 0; i < metaData.size(); i++) {

			// Get all tokens available in line
			String[] tokens = metaData.get(i).split(",");

			if (tokens.length > 0) {
				if (tokens[0].equals(name[0])) {

					columns.put(tokens[1], tokens[2]);
				}

			}
		}

	}

	public void insert(Hashtable<String, Object> htblColNameValue) {
		GetSerializedPaged();
		SetColumns();
		if (!utils.isInputTypeValid(htblColNameValue, columns)) {
			System.out.println("Invalid type for inputs");
		}
		if (tuples.size() == this.size) {
			String newpageName = "";
			String[] splittedname = this.Name.split(",");
			if (splittedname.length > 1) {
				int numofpage = Integer.parseInt(splittedname[1]);
				numofpage++;
				newpageName = splittedname[0] + "," + String.valueOf(numofpage);
			} else {
				newpageName = splittedname[0] + ",1";
			}
			Page extrapage = new Page(newpageName);
			extrapage.CreateSerializedPaged();
			extrapage.insert(htblColNameValue);
			utils.addnameToAllTablesCSV(newpageName);
		} else {
			ArrayList<String> insertedColumns = utils
					.getHashKeysAsList2(htblColNameValue);
			ArrayList<String> tableCoulmns = utils.getHashKeysAsList(columns);

			for (int i = 0; i < insertedColumns.size(); i++) {
				String column = insertedColumns.get(i);
				for (int j = 0; j < tableCoulmns.size(); j++) {
					if (tableCoulmns.get(j).equals(column)) {
						tableCoulmns.remove(j);
					}

				}
			}

			for (int w = 0; w < tableCoulmns.size(); w++) {
				htblColNameValue.put(tableCoulmns.get(w), "null");
			}

			tuples.addElement(htblColNameValue);

			CreateSerializedPaged();

		}

	}

	public static void shiftpage(ArrayList<String> pagesnames) {
		Vector<Hashtable<String, Object>> tuples1 = new Vector<Hashtable<String, Object>>();
		Vector<Hashtable<String, Object>> tuples2 = new Vector<Hashtable<String, Object>>();
		for (int i = 0; i < pagesnames.size() - 1; i++) {
			Page p = new Page(pagesnames.get(i));
			p.GetSerializedPaged();
			tuples1 = p.getTuples();
			Page p2 = new Page(pagesnames.get(i + 1));
			p2.GetSerializedPaged();
			tuples2 = p2.getTuples();

			while (tuples1.size() < 5 && !tuples2.isEmpty()) {
				tuples1.add(tuples2.get(0));
				tuples2.remove(0);

			}
			p.CreateSerializedPaged(tuples1);
			p2.CreateSerializedPaged(tuples2);

		}
	}

	public static void delete_empty(ArrayList<String> pagesnames) {
		Vector<Hashtable<String, Object>> tuples = new Vector<Hashtable<String, Object>>();
		String path = "C:\\Users\\Asiel\\Desktop\\dbAllTables.csv";
		BufferedReader fileReader = null;
		FileWriter fileWriter = null;

		for (int i = pagesnames.size()-1; i >=0; i--) {
			Page p = new Page(pagesnames.get(i));
			p.GetSerializedPaged();
			tuples = p.getTuples();
			//System.out.println(tuples.size());
			if (tuples.isEmpty()) {
				//System.out.println("yes");
				File fileOut = new File("C:\\Users\\Asiel\\Desktop\\db"
						+ pagesnames.get(i) + ".ser");
				try {
					fileOut.createNewFile();
					//System.out.println(fileOut.delete());
					if (fileOut.delete()) {
						System.out.println("file is deleted");
						utils.removenameToAllTablesCSV(pagesnames.get(i));
						pagesnames.remove(i);
					}
					//utils.removenameToAllTablesCSV(pagesnames.get(i));
					//pagesnames.remove(i);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

}
