package application;



/**
 * This class makes up the objects(nodes) which make up a given BST. 
 * 
 * 
 * @author joshualee
 * @version 1.0
 * @since 1.0
 * */
public class MyNode {
	
	
	private Person value;  //I originally had MyNode as a generic which took K/V. 
	                       //using the word value for Person kind of just stuck.
	private MyNode leftNode;
	private MyNode rightNode;
	private MyNode parentNode;
	private int height;  //Root has height 0. It's children has height 1 who have child 2 etc..
	private int leftDepth; //How many descendants a node has on its left
	private int rightDepth; //How many descendants a node has on its right
	boolean unbalanced; //True if a node's descendants are skewed one way or another.

	
	
	/**
	 * Constructor for the MyNode class. Takes a person for which it will represent.
	 * @param value The Person object which this node will represent.
	 */
	public MyNode(Person value) {
		this.value = value;
		this.unbalanced = false;
		
	}
	
	
	public String toString() {
		if(!this.unbalanced) {
		return (this.getValue().getFirstName() + " " + this.getValue().getLastName() + ", Age: " 
				+ this.getValue().getAge() + ", ID:" + this.getValue().getID()) + ", LND: " + this.getLeftDepth() + ", RND: " + this.getRightDepth() + " balanced "; //process node
		}
		else{
			return (this.getValue().getFirstName() + " " + this.getValue().getLastName() + ", Age: " 
					+ this.getValue().getAge() + ", ID:" + this.getValue().getID()) + ", LND: " + this.getLeftDepth() + ", RND: " + this.getRightDepth() + " unbalanced"; //process node

		}
	}
	
	/**
	 * Indicates whether a node is skewed two or more nodes in one direction or not.
	 */
	public void setBalance() {
		
		if(Math.abs(this.leftDepth - this.rightDepth)>=2) {
			this.unbalanced = true;
		}
		else {
			this.unbalanced = false;
		}
		
	}
	
		
	
	/**
	 * Indicates how many children a node has on its left side
	 */
	public void setLeftDepth() {
		if(this.getLeftNode()==null) {
			this.leftDepth = 0;
		}
		else if(this.getLeftNode().getLeftDepth()>=this.getLeftNode().getRightDepth()) {
			this.leftDepth = this.getLeftNode().getLeftDepth() + 1;
		}
		else {
			this.leftDepth = this.getLeftNode().getRightDepth() + 1;
		}
		
	}
	
	/**
	 * 	 * Indicates how many children a node has on its right side
	 */
	public void setRightDepth() {
		if(this.getRightNode()==null) {
			this.rightDepth = 0;
		}
		else if(this.getRightNode().getLeftDepth()>=this.getRightNode().getRightDepth()) {
			this.rightDepth = this.getRightNode().getLeftDepth() + 1;
		}
		else {
			this.rightDepth = this.getRightNode().getRightDepth() + 1;
		}
		
	}
	
	/**
	 * @return The skew to the left in int form.
	 */
	public int getBalanceNum() {
		
		return this.getLeftDepth() - this.getRightDepth();
	}
	
	
	//Getters and Setters Follow

	public int getRightDepth() {
		return rightDepth;
	}
	
	public int getLeftDepth() {
		return leftDepth;
	}

	public Person getValue() {
		return value;
	}
	public void setValue(Person value) {
		this.value = value;
	}
	public MyNode getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(MyNode leftNode) {
		this.leftNode = leftNode;
	}
	public MyNode getRightNode() {
		return rightNode;
	}
	public void setRightNode(MyNode newNode) {
		this.rightNode = newNode;
	}
	public MyNode getParentNode() {
		return parentNode;
	}
	public void setParentNode(MyNode parentNode) {
		this.parentNode = parentNode;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	

}
