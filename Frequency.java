import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Frequency  implements Comparator<Node> {
	private Map<Byte, Integer> freq = new HashMap<>();
	Map<Byte,String> code = new HashMap<>(); 
	PriorityQueue<Node> pque;
	String inputFile;
	String freqFile;
	Node ETree;
	String outputFile;
	String decodedFile;

	public Frequency(){
		
	}
	public Frequency(String InputName, String frequency , String OutputName,String decodedName) {
		this.inputFile = InputName;
		this.freqFile = frequency;
		this.outputFile = OutputName;
		this.decodedFile = decodedName;
	}
	
	public void makeTheFreqFile() {
		BinaryIn  input  = new BinaryIn(inputFile);
        BinaryOut output = new BinaryOut(freqFile);

        // read one 8-bit char at a time
//        System.out.println(input.isEmpty());
//        System.out.println(input.readInt());

        while (!input.isEmpty()) {
            byte b = input.readByte();
            if(this.freq.get(b) == null)this.freq.put(b, 1);
            else {
            	this.freq.put(b, this.freq.get(b) + 1);
            }
        }
        for(Map.Entry<Byte, Integer> pair : freq.entrySet()) {
//        	System.out.printf("%c : %d\n",pair.getKey(),pair.getValue());
        	output.write(pair.getKey());
        	output.write(pair.getValue());
        	

        }
        output.flush(); // Really Important

        
        
//        BinaryOut output1 = new BinaryOut("mine.txt");
//        for(int i = 0; i < 30; i++) {
//        	output1.write("n:"+ i +"\n");
//        }
//        output1.flush(); // Really Important
//
//        BinaryIn  input1  = new BinaryIn("mine.txt");
//        System.out.println(input1.exists());
//        System.out.println(input1.isEmpty());
//        int k = 1;
//        int[] num = new int[30];
//        while (!input1.isEmpty()) {
//        	char s = input1.readChar();
//        	System.out.print(s);
//        	if(s == ':') {
//        		while(true) {
//        			char g = input1.readChar();
//        			if(g == '\n')break;
//        			System.out.print(g);
//        		}
//        		System.out.println();
//        	}
//        }
        
	}
	public void makeFreqTable() {
		BinaryIn in = new BinaryIn(freqFile);
		
		freq.clear();
		
		while(!in.isEmpty()) {
			freq.put(in.readByte(), in.readInt());
		}
		for(Map.Entry<Byte, Integer> part: freq.entrySet()) {
			System.out.printf("%c : %d\n", part.getKey(), part.getValue());
		}
		
	}
	
	
	public void putTheMapInQue() {
		this.pque = new PriorityQueue<>(new Frequency());
		for(Map.Entry<Byte, Integer> pair : freq.entrySet()) {
			Node node = new Node(pair.getKey(),pair.getValue());
			pque.offer(node);
		}
	}
	
	public Node buildTheTree() {
		if(pque.size() < 2)return null;
		while(!pque.isEmpty()) {
			Node n1 = pque.poll();
			Node n2 = pque.poll();
			if(pque.isEmpty()) {
				pque.offer(new Node((byte)0,(n1.getFreq()+ n2.getFreq()),n2,n1));
				break;
			}
			pque.offer(new Node((byte)0,(n1.getFreq()+ n2.getFreq()),n2,n1));

		}
		return pque.poll();
	}
	
	public Map<Byte, Integer> getFreq() {
		return freq;
	}

	public void setFreq(Map<Byte, Integer> freq) {
		this.freq = freq;
	}
	
	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		if(o1.getFreq() > o2.getFreq()) return 1;
		if(o1.getFreq() < o2.getFreq()) return -1;
		return 0;
	}
	public void EncodingTheTree(Node root, String str) {
		if(root== null) return;
		EncodingTheTree(root.getLeft(),str+"0");
		code.put(root.getData(), str);
		EncodingTheTree(root.getRight(), str+"1");
	}
	public void makeEncodedFile() {
		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);
		
		while(!in.isEmpty()) {
			byte b = in.readByte();
			String str = code.get(b);
			int n = str.length();
			for(int i = 0; i < n; i++) {
				if(str.charAt(i) == '0')out.write(false);
				else out.write(true);
			}
		}
		out.flush();
		BinaryIn in2 = new BinaryIn(outputFile);
		while(!in2.isEmpty()) {
			if(in2.readBoolean())System.out.print('1');
			else System.out.print('0');
		}
		System.out.println();
		
		
	}
	public void inOrder(Node root) {
		if(root == null) return;
		inOrder(root.getLeft());
		System.out.println(root.getFreq());
		inOrder(root.getRight());
	}
	
	public void getTheFileBack() {
		BinaryIn in = new BinaryIn(outputFile);
		BinaryOut out = new BinaryOut(decodedFile);
		Node ptr = ETree;
		
		while(!in.isEmpty()) {
			while(ptr.getData() == 0) {
				if(in.readBoolean()) ptr = ptr.getRight();
				else ptr = ptr.getLeft();
			}
			out.write(ptr.getData());
			ptr = ETree;
		}
		out.flush();
		
//		inOrder(ETree);
		
		BinaryIn in2 = new BinaryIn(decodedFile);
		while(!in2.isEmpty()) {
			System.out.printf("%c",in2.readByte());
		}
		System.out.println();
		
		BinaryIn in3 = new BinaryIn(inputFile);
		while(!in3.isEmpty()) {
			System.out.printf("%c",in3.readByte());
		}
		
	}
	
}
