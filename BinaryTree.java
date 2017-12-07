
public class BinaryTree {
	private Node root = null;
	
	
	public BinaryTree(){
		root = new Node((byte) 0,0,null,null);
	}
	
	public BinaryTree(byte data, int freq) {
		root = new Node(data,freq,null,null);
	}
	
	public BinaryTree(byte data, int freq, Node left, Node right) {
		root = new Node(data,freq,left,right);
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
}
