/*
 * Name: Youssef Hussein
 * NetId: yhussein
 * 
 * "All the work in this file has been my own"
 * 
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

// Import any package as required


public class HuffmanSubmit implements Huffman,Comparator<Node>{
	

	
	// Feel free to add more methods and variables as required.
	private Map<Byte, Integer> freq = new HashMap<>();
	private Map<Byte,String> code = new HashMap<>(); 
	private PriorityQueue<Node> pque;
	private String inputFile;
	private String freqFile;
	private Node ETree;
	private String encodedFile;
	private String decodedFile;
	
 
	public void encode(String inputFile, String outputFile, String freqFile){
		// TODO: Your code here
		this.inputFile = inputFile;
		this.encodedFile = outputFile;
		this.freqFile = freqFile;
		//make the frequency table out of the file and map
		this.makeTheFreqFile();
		//make the map out of the frequency file I created
		this.makeFreqTable();
		//put the map in a priority queue of nodes
		this.putTheMapInQue();
		//use the queue to build a tree
		this.ETree = this.buildTheTree();
		//put code in the tree and make a code map
		this.EncodingTheTree(this.ETree, "");
		//make the encoded file
		this.makeEncodedFile();
		
		
   }


   public void decode(String inputFile, String outputFile, String freqFile){
		// TODO: Your code here
	   this.encodedFile = inputFile;
	   this.decodedFile = outputFile;
	   this.freqFile = freqFile;
	   //make the map out of the frequency file I created
	   this.makeFreqTable();
	   this.makeFreqTable();
		//put the map in a priority queue of nodes
		this.putTheMapInQue();
		//use the queue to build a tree
		this.ETree = this.buildTheTree();
		//decode the file
		this.getTheFileBack();
	   
	   
	   
	   
   }
   
   
   public void makeTheFreqFile() {
		BinaryIn  input  = new BinaryIn(inputFile);
       BinaryOut output = new BinaryOut(freqFile);

       while (!input.isEmpty()) {
           byte b = input.readByte();
           if(freq.get(b) == null)freq.put(b, 1);
           else {
           	freq.put(b, freq.get(b) + 1);
           }
       }
       for(Map.Entry<Byte, Integer> pair : freq.entrySet()) {
//       	System.out.printf("%c : %d\n",pair.getKey(),pair.getValue());
       	output.write(pair.getKey());
       	output.write(pair.getValue());
       	

       }
       output.flush(); // Really Important
       
	}
	public void makeFreqTable() {
		BinaryIn in = new BinaryIn(freqFile);
		
		freq.clear();
		
		while(!in.isEmpty()) {
			freq.put(in.readByte(), in.readInt());
		}
//		for(Map.Entry<Byte, Integer> part: freq.entrySet()) {
//			System.out.printf("%c : %d\n", part.getKey(), part.getValue());
//		}
		
	}
	
	
	public void putTheMapInQue() {
		pque = new PriorityQueue<>(new Frequency());
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
	
	
	
	
	public void EncodingTheTree(Node root, String str) {
		if(root== null) return;
		EncodingTheTree(root.getLeft(),str+"0");
		code.put(root.getData(), str);
		EncodingTheTree(root.getRight(), str+"1");
	}
	public void makeEncodedFile() {
		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(encodedFile);
		
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
//		BinaryIn in2 = new BinaryIn(encodedFile);
//		while(!in2.isEmpty()) {
//			if(in2.readBoolean())System.out.print('1');
//			else System.out.print('0');
//		}
//		System.out.println();
		
		
	}
	public void inOrder(Node root) {
		if(root == null) return;
		inOrder(root.getLeft());
		System.out.println(root.getFreq());
		inOrder(root.getRight());
	}
	
	public void getTheFileBack() {
		BinaryIn in = new BinaryIn(encodedFile);
		BinaryOut out = new BinaryOut(decodedFile);
		Node ptr = ETree;
		
		while(!in.isEmpty()) {
			while(ptr.getData() == 0) {
				if(in.readBoolean()) ptr = ptr.getRight();
				else ptr = ptr.getLeft();
			}
			out.write(ptr.getData());
			ptr = ETree;
//			if(in.readBoolean()) {
//				if(ptr.getData() != 0) {
//					out.write(ptr.getData());
//					ptr = ETree;
//				}
//				else ptr = ptr.getRight();
//			}
//			else {
//				if(ptr.getData() != 0) {
//					out.write(ptr.getData());
//					ptr = ETree;
//				}
//				else ptr = ptr.getLeft();
//			}
		}
		out.flush();
		
//		inOrder(ETree);
		
//		BinaryIn in2 = new BinaryIn(decodedFile);
//		while(!in2.isEmpty()) {
//			System.out.printf("%c",in2.readByte());
//		}
//		System.out.println();
		
		
		
	}
   
//   //create the encoding manual
//   public void createTheTree(String freqFile) {
//	   BinaryIn  in  = new BinaryIn(freqFile);
//       BinaryOut out = new BinaryOut("freqManual.txt");
//       int i = 0;
//       while (!in.isEmpty()) {
//    	   String s = in.readString();	
//    	   i++;
//       }
//     //encoding tree
//       BinaryTree[] tree = new BinaryTree[i];
//       
//       
//       
//       // read one 8-bit char at a time
//       while (!in.isEmpty()) {
//           boolean b = in.readBoolean();
//           out.write(b);
//       }
//       
//       out.flush(); // Really Important
//   }
//   
//
	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getFreq() > o2.getFreq()) return 1;
		if(o1.getFreq() < o2.getFreq()) return -1;
		return 0;
	}


   public static void main(String[] args) {
      Huffman  huffman = new HuffmanSubmit();
      Scanner scan = new Scanner(System.in);
      String  input = "",encoded = "",freq = "",decoded = "";
      int i;
      do {
          System.out.print("What would you like to do:\n1. Encode\n2. Decode\n3. Quit\n");
    	  i = scan.nextInt();
    	  if(i == 3)break;
	      switch(i) {
	      case 2:
	    	  //ask for a decoded file
	    	  switch(args.length) {
	    	  case 0://decoded file
	    		  System.out.print("please name a file to get the output file from the encoded: ");
	    		  decoded = scan.next();
	    	  case 1://frequency file
	    		  System.out.print("please name or give a frequency file: ");
	    		  freq = scan.next();
	    	  case 2://encoded file
	    		  System.out.print("please name or give a file for encoding in: ");
	    		  encoded = scan.next();
	    		  break;
	    	  case 3:
	    		  encoded = args[0];
	    		  decoded = args[1];
	    		  freq = args[2];
	    	  }
	          huffman.decode(encoded, decoded, freq);
	    	  break;
	      case 1:
	    	  switch(args.length) {
	    	  case 0://input file
	    		  System.out.print("please give me an input file: ");
	    		  input = scan.next();
	    	  case 1://frequency file
	    		  System.out.print("please name or give a frequency file: ");
	    		  freq = scan.next();
	    	  case 2://encoded file
	    		  System.out.print("please name or give a file for encoding in: ");
	    		  encoded = scan.next();
	    		  break;
	    	  case 3:
	    		  input = args[0];
	    		  encoded = args[1];
	    		  freq = args[2];
	    	  }
	    	  huffman.encode(input, encoded, freq);
	    	  huffman = new HuffmanSubmit();
	    	  break;
	      default:
			  System.out.print("please enter \'1\' or \'2\' or enter \'3\' to quit : ");
			  continue;	  
	      }
	      
      } while(i != 1 && i != 2);
      System.out.println("Thanks for using my Program");
      
//		huffman.encode("ur.jpg", "ur.enc", "freq.txt");
//		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same. 
   scan.close();
   }
   



}
class Node{
	private byte data;
	private int freq;
	private Node right;
	private Node left;
	
	public Node(byte data, int freq) {
		this.data = data;
		this.freq = freq;
		this.right = null;
		this.left = null;
		
	}
	public Node(byte data, int freq, Node right, Node left) {
		this.data = data;
		this.freq = freq;
		this.right = right;
		this.left = left;
		
	}
	public byte getData() {
		return data;
	}
	public void setData(byte data) {
		this.data = data;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	
}
