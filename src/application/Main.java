package application;
	

import java.util.ArrayList;
import java.util.Comparator;

import comparators.AgeComparator;
import comparators.FirstNameComparator;
import comparators.FirstNameIDComparator;
import comparators.FirstNameLengthComparator;
import comparators.LastNameComparator;
import comparators.LastNameIDComparator;
import comparators.LastNameLengthComparator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * 
 * This is the Main Class which contains the Main Method
 * 
 * @author joshualee
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {
	
	public static int idCounter = 0;
	public static ArrayList<Person> people = new ArrayList<Person>();
	public static MyTree currentTree;
	public static ListView<Object> listview = new ListView<Object>();

	
	

	@Override
	public void start(Stage primaryStage) {
		try {			
			
			FileLoader fileLoader = new FileLoader("names.txt", 20);

			fileLoader.writeFiles();
			
			BorderPane root = new BorderPane();
			
			//Add sideBar
			SideBar sideBar = new SideBar(this);
			root.setLeft(sideBar.getSideBar());
			
			//Adding main Pane (ListView)
			root.setCenter(listview);
			
			
			Scene scene = new Scene(root,1000,1000);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Josh's BST/AVL Tree Program");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates a new MyTree object which is sorted by specific values depending on
	 * what comparator you pass through. Depending on the string passed through
	 * the tree will be printed in a specific way.
	 * 
	 * @param comparator Comparator used to construct the tree
	 * @param string Specifies how the nodes of the tree will get printed
	 */
	public void createTree(Comparator<Person> comparator, String string) {
		
		listview.setPlaceholder(new Label("Your search led to no results :("));
		
		
		//Setting secondary Comparator for Length based operations
		//Setting tertiary Comparator as one which also takes ID
		Comparator<Person> secondaryComp = null;
		Comparator<Person> tertiaryComp = null;
		
		if(comparator instanceof FirstNameComparator) {
			secondaryComp = new FirstNameLengthComparator();
			tertiaryComp = new FirstNameIDComparator();
		}
		if(comparator instanceof LastNameComparator) {
			secondaryComp = new LastNameLengthComparator();
			tertiaryComp = new LastNameIDComparator();
		}
		
		if(comparator instanceof AgeComparator) {
			secondaryComp = comparator;
			tertiaryComp = comparator;
		}
		
		
		MyTree newTree = new MyTree(null, comparator, secondaryComp, tertiaryComp);

		for(Person p : people) {
			
			MyNode newNode = new MyNode(p);
			newTree.setRoot(newTree.insertNode(newTree.getRoot(), newNode));

		}
		
		
		
		newTree.setNodeHeights(newTree.getRoot(), 0);
		currentTree = newTree;
		
		Main.listview.getItems().clear();
		
		if(string.equals("Depth")) {
		    newTree.printNodes(newTree.getRoot(), "");		
		}
		else if(string.equals("Breadth")) {
			newTree.printNodesBreadth(newTree.getRoot());
		}
		else if(string.equals("Pre-Order")) {
			newTree.printNodesPreOrder(newTree.getRoot(), "");
		}
		else if(string.equals("Post-Order")) {
			newTree.printNodesPostOrder(newTree.getRoot(), "");
		}

	}
	
	

	
	
	
	

	
	public static void main(String[] args) {
		launch(args);
	}
}
