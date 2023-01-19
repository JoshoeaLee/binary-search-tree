package application;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;


/**
 * This class is one which represents the current tree.
 * It also contains comparators which will determine how its nodes are arranged
 * 
 * @author joshualee
 * @version 1.0
 * @since 1.0
 *
 */
public class MyTree {
	
	

	MyNode root;
	Comparator<Person> comparator;
	Comparator<Person> secondaryComparator;
	Comparator<Person> tertiaryComparator;
	ArrayList<MyNode> searchedNodes = new ArrayList<MyNode>();


	
	/**
	 * Constructor for the MyTree class.
	 * @param root Root node of the BST.
	 * @param comparator Comparator used for searching specific people
	 * @param secondaryComparator Comparator used for searching name lengths
	 * @param tertiaryComparator Comparator used for deleting and inserting people with IDs
	 */
	public MyTree(MyNode root, Comparator<Person> comparator, Comparator<Person> secondaryComparator, Comparator<Person> tertiaryComparator) {
		
		this.root = root;
		this.comparator = comparator;
		this.secondaryComparator = secondaryComparator;
		this.tertiaryComparator = tertiaryComparator;
	
		
	}
	
	
	/**
	 * Rotates right around the passed node.
	 * @param node
	 * @return
	 */
	public MyNode rotateR(MyNode node) {
		
		MyNode newRoot = node.getLeftNode();
	    MyNode leftOfOriginalRoot = newRoot.getRightNode();
	    
	    newRoot.setRightNode(node);
	    node.setLeftNode(leftOfOriginalRoot);
	    
	    this.setNodeDepths(newRoot);
	    this.setNodeHeights(this.getRoot(), 0);
	    
	    return newRoot;
		
	}
	
	/**
	 * Rotates left around the passed node.
	 * @param node
	 * @return
	 */
	public MyNode rotateL(MyNode node) {
		
		MyNode newRoot = node.getRightNode();
		MyNode rightOfOriginalNode = newRoot.getLeftNode();
		
		newRoot.setLeftNode(node);
		node.setRightNode(rightOfOriginalNode);
		
		this.setNodeDepths(newRoot);
	    this.setNodeHeights(this.getRoot(), 0);

		
		return newRoot;
		
	}
	
	/**
	 * Assigns the heights of the nodes in a tree/sub-tree
	 * @param node The root node of the tree/sub-tree
	 * @param height The height wanted for the tree/sub-tree
	 */
	public void setNodeHeights(MyNode node, int height) {
		
		if(node!=null) {
		node.setHeight(height);
		setNodeHeights(node.getLeftNode(), height+1);
		setNodeHeights(node.getRightNode(), height+1);
		
		}
	}
	
	
	
	
	/**
	 * Assigns the left and right depths of the nodes in a tree
	 * @param node  Pass through the top node of a tree
	 */
	public void setNodeDepths(MyNode node) {
		
		if(node!=null) {
			
			setNodeDepths(node.getLeftNode());
			node.setLeftDepth();
			setNodeDepths(node.getRightNode());
			node.setRightDepth();
			node.setBalance();
			
		}
		
		
	}
	
	/**
	 * Deletes a node from a given tree/sub-tree
	 * @param node Root of the tree/sub-tree from which you to delete your node.
	 * @param toDelete Node to delete
	 * 	Credit to this website for clarifying concept -> https://www.digitalocean.com/community/tutorials/binary-search-tree-bst-search-insert-remove
	 */
	public MyNode deleteNode(MyNode node, MyNode toDelete) {
		
			
		if(node==null) {
			return null;
		}
			
		//As an ID inclusive comparator is used to insert nodes as well, I can traverse the tree and judge whether people with the same name
		//are < or > each other and not have to worry about == cases
		     if(this.tertiaryComparator.compare(toDelete.getValue(), node.getValue())<0) {
		        node.setLeftNode(deleteNode(node.getLeftNode(), toDelete)); 
		      }
		     
		     else if(this.tertiaryComparator.compare(toDelete.getValue(), node.getValue())>0) {
		    	 node.setRightNode(deleteNode(node.getRightNode(), toDelete));
		     }

		     
		     else {

		    	 //Here is where the node to delete is equivalent to the node you're on (sane ID as well)
		    	 
		    	 
		    	 //0 children
		    	
		    	 if(node.getLeftNode()==null && node.getRightNode() ==null) {
		    		 
		    		 return null;
		    	 }
		    	 
		    	 //if there is no node on the left, then force node you're on rn to be the right one(the only child)
		    	 //or null if there are no children
		    	 else if(node.getLeftNode() == null) {
		    		 return node.getRightNode();
		    	 }
		    	 else if(node.getRightNode()==null) {
		    		 return node.getLeftNode();
		    	 }
		    	 

		    	 else {
		    	 
		    	 
		    	 //2 children. 
		         //Find the next biggest node
		    	 MyNode temp = this.leftMost(node.getRightNode());
		    		 
		    	 //Replace current node with next biggest node
		    	 node.setValue(temp.getValue());
		    		 
		    	 //Delete the original copy down the tree (Go one to the right and then find the original within that subtree)
		    	 node.setRightNode(deleteNode(node.getRightNode(), temp));
		    	 }
		    	 
		     }
		     
		     
		     //AVL STUFF
		     //Reset depth & height values
		     this.setNodeDepths(node);
		     this.setNodeHeights(this.getRoot(), 0);
		     
		     //This only needs to be done when the node is unbalanced
		     if(node.unbalanced) {
		     
		    	 //4 Different situations requiring 4 different rotations
		    	 //Whenever anything is skewed one direction, rotate it the opposite direction to fix it
		    	 
		    	 //Skewed to left and left child is either balanced or also to a degree skewed left (Rotate right on current node)
		    	 if(node.getBalanceNum() > 1 
		    			 && node.getLeftNode().getBalanceNum()>=0) {
		    		 return rotateR(node);
		    	 }
		    	 
		    	 //Skewed to left and left child is to a degree skewed right (rotate left child to left and then rotate current node to right)
		    	 else if(node.getBalanceNum() >1 
		    			 && node.getLeftNode().getBalanceNum() < 0) {
		    		 node.setLeftNode(rotateL(node.getLeftNode()));
		    		 return rotateR(node);
		    	 }
		    	 
		    	 //Skewed to right and right child is either balanced or to a degree skewed left (Rotate left on current node)
		    	 else if(node.getBalanceNum() < -1
		    			 && node.getRightNode().getBalanceNum() <=0) {
		    		 return rotateL(node);
		    	 }
		    	 
		    	 //Skewed to right and right child is also to a degree skewed right (Rotate left on right child and then rotate left on current node)
		    	 else {
		    		 node.setRightNode(rotateR(node.getRightNode()));
		    		 return rotateL(node);
		    	 }
		    	 
		     }
		     
		     
		     return node;
		
	}
	
	
	/**
	 * Utility for the delete function.
	 * Finds the smallest node coming from the given node.
	 * @param node Root of tree/sub-tree from which you want to find the smallest value of
	 */
	public MyNode leftMost(MyNode node) {
		
		MyNode leftMost = node;
		
		while(node.getLeftNode()!=null) {
			leftMost = node.getLeftNode();
			node = node.getLeftNode();
		}
		
		
		return leftMost;
	}
	
	
	
	
		
	/**
	 * Inserts a node into a given tree/sub-tree
	 * @param node Root of tree/sub-tree which you wish to insert a node into
	 * @param toInsert Node to insert
	 */
	public MyNode insertNode(MyNode node, MyNode toInsert) {
		
		
		if(node==null) {
			return toInsert;
		}
		
		
	//Tertiary Comparator here includes ID for the first name/ last name stats and doesn't for the age stat
		 if(this.tertiaryComparator.compare(toInsert.getValue(), node.getValue())<=0) {
			   node.setLeftNode(insertNode(node.getLeftNode(), toInsert));
			}
			
		else if(this.tertiaryComparator.compare(toInsert.getValue(), node.getValue())>0) {
			   node.setRightNode(insertNode(node.getRightNode(), toInsert));
			}
		 
		
		
		//AVL follows
		this.setNodeDepths(node);
		this.setNodeHeights(this.getRoot(), 0);
		
		
		
			if(node.unbalanced) {
			//Skewed to left and newest node on the left -> Rotate right
			if((node.getBalanceNum() > 1) && 
					this.tertiaryComparator.compare(toInsert.getValue(), node.getLeftNode().getValue())<0) {
				return rotateR(node);
			}
			
			//Skewed to right and newest node on the right
			else if((node.getBalanceNum()< -1) &&
					this.tertiaryComparator.compare(toInsert.getValue(), node.getRightNode().getValue())>0) {
				return rotateL(node);
			}
			
			//Skewed to left and newest node on the right
			else if((node.getBalanceNum() > 1) && 
					this.tertiaryComparator.compare(toInsert.getValue(), node.getLeftNode().getValue())>0){
				node.setLeftNode(rotateL(node.getLeftNode()));
				return rotateR(node);
			}
			
			//Skewed to the right and newest node on the left
			else {
				node.setRightNode(rotateR(node.getRightNode()));
				return rotateL(node);
			}
			
			
		}
		
		return node;
		
	}
	
	
	/**
	 * Prints out the nodes of a tree Using a depth first method. 
	 * @param node Root of tree/sub-tree to print from
	 * @param space Used to indent nodes
	 */
	public void printNodes(MyNode node, String space) {   //Depth-first
				
		if (node!=null){
			printNodes(node.getLeftNode(), "                    " + space); //process first subtree
			
			Main.listview.getItems().addAll(space + node);
				
			printNodes(node.getRightNode(), "                    " + space); //procees second subtree
			}
		

	}
	
	
	/**
	 * Prints out the nodes of a tree using a breadth first method.
	 * @param node Root of tree/sub-tree to print from
	 */
	public void printNodesBreadth(MyNode node) {
		
		Queue<MyNode> toPrint = new ArrayDeque<MyNode>();
		toPrint.offer(node); //root
		
		while(!toPrint.isEmpty()) {
			MyNode printNext = toPrint.poll();
			
			StringBuilder space = new StringBuilder();
			
			for(int i = 0; i<printNext.getHeight(); i++) {
				
				space.append("                    ");
				
			}
			
			
			
			Main.listview.getItems().addAll(space.toString() + printNext);
			
			if(printNext.getLeftNode()!=null) {
				toPrint.offer(printNext.getLeftNode());
			}
			
			if(printNext.getRightNode()!=null) {
				toPrint.offer(printNext.getRightNode());
			}
			
		}
		
	}
	
	
	/**
	 * Prints out the nodes of a tree using a pre-order depth first method.
	 * @param node Root of tree/sub-tree to print from
	 * @param space Used to indent nodes
	 */
	public void printNodesPreOrder(MyNode node, String space) {
		
		if(node!=null) {
			Main.listview.getItems().addAll(space +  node);
			printNodesPreOrder(node.getLeftNode(), "                    " + space );
			printNodesPreOrder(node.getRightNode(), "                    " + space );
			
		}
		
	}
	
	
	/**
	 * Prints out the nodes of a tree using a pre-order depth first method.
	 * @param node Root of tree/sub-tree to print from
	 * @param space Used to indent nodes
	 */
	public void printNodesPostOrder(MyNode node, String space) {
		
		if(node!=null) {
			printNodesPreOrder(node.getLeftNode(), "                    " +space);
			printNodesPreOrder(node.getRightNode(), "                    " +space);
			Main.listview.getItems().addAll( space + node);
			
		}
		
	}
	
	/**
	 * Finding a person in a given tree
	 * @param node Root of tree/sub-tree you want to search from
	 * @param searchP Contains the value you want to search for
	 */
	public void searchTree(MyNode node, Person searchP) {
		
		if(node!=null) {	
	
		
			searchTree(node.getLeftNode(), searchP);
			
		if(this.comparator.compare(searchP, node.getValue())==0) { 

			searchedNodes.add(node);
			searchTree(node.getLeftNode(), searchP);

		}
				
			searchTree(node.getRightNode(), searchP);
				
		}
		
		
	}
	
	
	   /**
	    * Finding people with stats between two given values
	    * @param node Root of tree/sub-tree you want to search from
	    * @param searchLowerP Contains the lower value (inclusive) you want to search for
	    * @param searchHigherP Contains the higher value (inclusive) you want to search for

	    */
       public void searchTree(MyNode node, Person searchLowerP, Person searchHigherP ) {
    	   
    	   
    	   
    		   if (node!=null){
    			searchTree(node.getLeftNode(), searchLowerP, searchHigherP); //process first subtree
    			
       		   if(this.secondaryComparator.compare(searchLowerP, node.getValue())<=0 && 
       				   this.secondaryComparator.compare(searchHigherP, node.getValue())>=0) { 
       			   
    			searchedNodes.add(node); //process node
    			
       		    }
    			
    			searchTree(node.getRightNode(), searchLowerP, searchHigherP); //procees second subtree
    			
    			}
    		   
       }

	
	
	
	//Getters and Setters Follow
	public MyNode getRoot() {
		return root;
	}

	/**
	 * Use this on a tree/sub-tree's root to use the recursive insertNode/deleteNode methods!!
	 * @param root
	 */
	public void setRoot(MyNode root) {
		this.root = root;
	}
	
	public Comparator<Person> getComparator(){
		return comparator;
	}
	

	
	
	
	
	

}
