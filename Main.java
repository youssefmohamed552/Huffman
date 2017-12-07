
public class Main {
	public static void main(String args[]) {
		
		Frequency table = new Frequency(args[0], args[1], args[2],args[3]);
		table.makeTheFreqFile();
		table.makeFreqTable();
		table.putTheMapInQue();
		table.ETree = table.buildTheTree();
		table.EncodingTheTree(table.ETree,"");
		table.makeEncodedFile();
		table.getTheFileBack();
		
	}
}
