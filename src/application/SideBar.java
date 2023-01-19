package application;

import java.util.Comparator;

import comparators.AgeComparator;
import comparators.FirstLastNameDiffComparator;
import comparators.FirstNameComparator;
import comparators.LastNameComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class to make the SideBar UI. This class mainly exists to make the Main class more
 * easy to read.
 * @author joshualee
 * @version 1.0
 * @since 1.0
 *
 */
public class SideBar {
	
	Main main;
	
	/**
	 * Constructor for the SideBar class. Takes the main which the sidebar will go to.
	 * @param main The main class which has the root node to put this sidebar in.
	 */
	public SideBar(Main main) {
		
		this.main = main;
		
	}
	
	/**
	 * Returns the sidebar UI for the BST program
	 * @return A VBox which contains all the relevant buttons/textfields for this program.
	 */
    @SuppressWarnings("unchecked")
	public VBox getSideBar() {
    	
  ////////////BASIC PRINT BOX AND SLIDER SECTION//////////////////////////////////
		
		//This textField and button deals with selecting how many people will be loaded in.
    	
    	Label peopleNumLabel = new Label("Number of People");
    	Slider peopleNum = new Slider(0, 100, 20);
    	peopleNum.setMajorTickUnit(10);
    	peopleNum.setSnapToTicks(true);
    	peopleNum.setShowTickMarks(true);
    	peopleNum.setShowTickLabels(true);
    	
    	
    	peopleNum.setOnMouseReleased(event->{
    		
    		int loadNum = (int)peopleNum.getValue();
			FileLoader fileLoader = new FileLoader("names.txt", loadNum);

			fileLoader.writeFiles();

    	});
    	
    	
		Label printLabel = new Label("Basic Tree Print Functions");
    	
		
		ChoiceBox<String> printType = new ChoiceBox<String>();
        printType.getItems().addAll("Depth", "Breadth", "Pre-Order", "Post-Order");
		printType.setValue("Depth");
		
		
		//Choicebox for TreeType. Shows current TreeType
		
		ObservableList<Comparator<Person>> comparators = FXCollections.observableArrayList();
		comparators.addAll(new FirstNameComparator(), new LastNameComparator(), new AgeComparator(), new FirstLastNameDiffComparator());
		
		ChoiceBox<Comparator<Person>> treeType = new ChoiceBox<Comparator<Person>>(comparators);
		treeType.setValue(new FirstNameComparator());
		

	
		
		//Print Tree Button////
		
		Button print = new Button("Print");
		
		print.setOnAction(event ->{
			
			main.createTree( treeType.getValue(), printType.getValue());
			
		});
		
		
	
		VBox basicPrintBox = new VBox(10);
		basicPrintBox.getChildren().addAll(peopleNumLabel, peopleNum, printLabel, printType, treeType, print);
		basicPrintBox.setAlignment(Pos.CENTER);
		basicPrintBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

		
		
	////////SEARCH FUNCTION SECTION	///////////////////////////////////////////////
		
		
		Label searchLabel = new Label("Search Function");
		Label explanation = new Label("(Change Tree to search something else)");
		
		TextField searchField = new TextField();
		Button searchButton = new Button("Search by First Name" );



		//Button switches text depending on current tree
	        treeType.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) ->{
		
	        	searchButton.setText( "Search by " + arg2);
	        	}
		);
	       
	       
			
			//SEARCH FOR Button function//////
	     	searchButton.setOnAction(event ->{
	     	
	     	
	     	//IF SELECTED TREE DOESN'T MATCH WHAT USER IS SEARCHING FOR
	     	if(treeType.getValue().getClass()!=Main.currentTree.getComparator().getClass()) {
	     		
	     		Alert pleasePrint = new Alert(Alert.AlertType.INFORMATION);
	     		pleasePrint.setContentText("If you wish to search by " + treeType.getValue() + " then please print a " + treeType.getValue() + " tree first!" );
	     		pleasePrint.show();
	     		
	     	}
	     	else {
	     		
	     		
	     		
			String input = searchField.getText();
			Person searchP = new Person("","",0,0);

			if(treeType.getValue() instanceof FirstNameComparator) {
				
				 searchP = new Person(input, "", 0, 0);

			}
			
			else if(treeType.getValue() instanceof LastNameComparator) {
				
				 searchP = new Person("", input, 0, 0);

			}
			
			else if(treeType.getValue() instanceof AgeComparator) {
				try {
					int inputInt = Integer.parseInt(input);
					 searchP = new Person("", "", inputInt, 0);
				}catch(NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please enter an integer if you want to search an age");
					alert.show();
					
				}
			

			}

				
			Main.currentTree.searchTree(Main.currentTree.getRoot(), searchP);
			Main.listview.getItems().clear();

			for(MyNode node : Main.currentTree.searchedNodes) {
				Main.listview.getItems().add(node);
				}
				
			Main.currentTree.searchedNodes.clear();
	      }
		});
	        
	        
	        
	    
	        VBox searchBox = new VBox(10);
			searchBox.getChildren().addAll(searchLabel, explanation, searchField, searchButton);
			searchBox.setAlignment(Pos.CENTER);
			searchBox.setBackground(new Background(new BackgroundFill(Color.LEMONCHIFFON, CornerRadii.EMPTY, Insets.EMPTY)));
	        
	        
		
		
	
		
		
		//ADVANCED SEARCH FUNCTION BOX/////////////////////////
			
		Label advancedLabel = new Label("Advanced Search Functions");
		Label betweenLabel = new Label("Change tree to search for something else");
		
		TextField lowerLim = new TextField("At least... (Inclusive)");
		TextField higherLim = new TextField("At most... (Inclusive)");
		
		Button specificInts = new Button("Search by First Name Length");
		
		
		
		//Button text switches depending on current tree
        treeType.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) ->{
	
        	if(arg2 instanceof AgeComparator) {
            	specificInts.setText( "Search by " + arg2);

        	}
        	else {
            	specificInts.setText( "Search by " + arg2 + " Length");

        	}
        });
		
        //Search Between Two Values Function
        
		specificInts.setOnAction(event->{
			
			//IF SELECTED TREE DOESN'T MATCH WHAT USER IS SEARCHING FOR
	     	if(treeType.getValue().getClass()!=Main.currentTree.getComparator().getClass()) {
	     		
	     		Alert pleasePrint = new Alert(Alert.AlertType.INFORMATION);
	     		pleasePrint.setContentText("If you wish to search by " + treeType.getValue() + " then please print a " + treeType.getValue() + " tree first!" );
	     		pleasePrint.show();
	     		
	     	}
	     	else {
			
			Person lowerP = new Person("", "", 0, 0);
			Person higherP = new Person("", "", 0, 0);
			try {
			int lower = Integer.parseInt(lowerLim.getText());
			int higher = Integer.parseInt(higherLim.getText());
			
			
			StringBuilder lowerLimitBuilder = new StringBuilder();
			StringBuilder upperLimitBuilder = new StringBuilder();
			lowerLimitBuilder.setLength(lower);
			upperLimitBuilder.setLength(higher);
			
			
			
			if(treeType.getValue() instanceof FirstNameComparator) {
				
				lowerP = new Person(lowerLimitBuilder.toString(), "", 0, 0);
				higherP = new Person(upperLimitBuilder.toString(), "", 0, 0);



			}
			
			else if(treeType.getValue() instanceof LastNameComparator) {
				
				lowerP = new Person("", lowerLimitBuilder.toString(), 0, 0);
				higherP = new Person("", upperLimitBuilder.toString(), 0, 0);

			}
			
			else if(treeType.getValue() instanceof AgeComparator) {
				
				lowerP = new Person("", "", lower, 0);
				higherP = new Person("", "", higher, 0);
			

			}
			}catch(NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Please enter an integer.");
				alert.show();
				
			}
			
			Main.currentTree.searchTree(Main.currentTree.getRoot(), lowerP, higherP);
			Main.listview.getItems().clear();

			for(MyNode node : Main.currentTree.searchedNodes) {
				Main.listview.getItems().add(node);
			}
				
			Main.currentTree.searchedNodes.clear();
			
			
	     	}
		});
		
		
		VBox advancedBox = new VBox(10);
		advancedBox.getChildren().addAll(advancedLabel, betweenLabel, lowerLim, higherLim, specificInts);
		advancedBox.setAlignment(Pos.CENTER);
		advancedBox.setBackground(new Background(new BackgroundFill(Color.PAPAYAWHIP, CornerRadii.EMPTY, Insets.EMPTY)));
        
		

		
		/////REPLACING NODE FUNCTIONS BOX//////////////////
		
		Label replaceLabel = new Label("Change someone's name");  //listener
		Label differentNameLabel = new Label("Change tree to change the other name");//


	    TextField removeField = new TextField("Name to Change");
		TextField addField = new TextField("Change to");
	    Button renameButton = new Button("Rename First Name");
	    
	    
	  //Button switches depending on current tree
        treeType.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) ->{
	
            	renameButton.setText("Rename " + arg2);
        	
        });
	       
	       
	       
	       
	      //Rename Function
	       renameButton.setOnAction(event ->{
	    	   
	    	 //IF SELECTED TREE DOESN'T MATCH WHAT USER IS TRYING TO RENAME 
		     	if(treeType.getValue().getClass()!=Main.currentTree.getComparator().getClass()) {
		     		
		     		Alert pleasePrint = new Alert(Alert.AlertType.INFORMATION);
		     		pleasePrint.setContentText("If you wish to change someone's " + treeType.getValue() + " then please print a " + treeType.getValue() + " tree first!" );
		     		pleasePrint.show();
		     		
		     	}
	    	   

	    	   
				String oldName = removeField.getText();
				String newName = addField.getText();
				
				Person searchP = new Person("", "", 0, 0);
				
				
				//Making a person to search with 
				if(treeType.getValue() instanceof FirstNameComparator) {
					
					 searchP = new Person(oldName, "", 0, 0);

				}
				
				else if(treeType.getValue() instanceof LastNameComparator) {
					
					 searchP = new Person("", oldName, 0, 0);

				}
				
				//Find people with matching name. Add them to a list
				Main.currentTree.searchTree(Main.currentTree.getRoot(), searchP);
				
				//Nothing found
				if(Main.currentTree.searchedNodes.isEmpty()) {
					Main.listview.getItems().clear();
				       return;  
				}
				
				
				else { //If there's something there
				
				//Adding all nodes that match to an options list
				ListView<MyNode> options = new ListView<MyNode>();
				
				for(MyNode node : Main.currentTree.searchedNodes) {
					   options.getItems().add(node);
				}
				
				Main.currentTree.searchedNodes.clear();

				
				//Window opens showing the options of people they can rename	        
		        VBox selectionBox = new VBox();
		        selectionBox.getChildren().add(options);
		        
		        Stage stage = new Stage();
		        Scene scene = new Scene(selectionBox, 600, 600);
		        stage.setScene(scene);
		        stage.setTitle("Click on the Person you wish to rename");
		        stage.show();
				
				
		        //When the person you want to rename is double clicked. Delete them and then 
		        //Insert a fresh copy of them with their new name
				options.setOnMouseClicked(click->{

					
					if(click.getButton().equals(MouseButton.PRIMARY)) {
						
						MyNode toDelete = options.getSelectionModel().getSelectedItem();
						MyNode newDude = new MyNode(options.getSelectionModel().getSelectedItem().getValue());
				
						
						Main.currentTree.setRoot(Main.currentTree.deleteNode(Main.currentTree.getRoot(), toDelete));

						
						if(treeType.getValue() instanceof FirstNameComparator) {
							
							newDude.getValue().setFirstName(newName);
							
						}
						
						else if(treeType.getValue() instanceof LastNameComparator) {
							
							newDude.getValue().setLastName(newName);
							
						}
						
						
						stage.close();
						options.getItems().clear();
						Main.listview.getItems().clear();
						
						Main.currentTree.insertNode(Main.currentTree.getRoot(), newDude);
						Main.currentTree.printNodes(Main.currentTree.getRoot(), "");
						
					}			
				});
		
			   }	
				
			});
	       
	       
	   	VBox renameBox = new VBox(10);
		renameBox.getChildren().addAll(replaceLabel, differentNameLabel, removeField, addField, renameButton);
		renameBox.setAlignment(Pos.CENTER);
		renameBox.setBackground(new Background(new BackgroundFill(Color.MISTYROSE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		
		
		//Disable specific Boxes based on the type of tree selected
        treeType.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) ->{
        	      
        	 
        	        if(arg2 instanceof AgeComparator) {
        	        	
        	        	searchBox.setDisable(false);
        	        	advancedBox.setDisable(false);
        	        	renameBox.setDisable(true);
        	        	
        	        }
        	
        	        else if(arg2 instanceof FirstLastNameDiffComparator) {
        	        	
        	        	searchBox.setDisable(true);
        	        	advancedBox.setDisable(true);
        	        	renameBox.setDisable(true);
        	        	
        	        }
        	        
        	        else {
        	        	
        	        	searchBox.setDisable(false);
        	        	advancedBox.setDisable(false);
        	        	renameBox.setDisable(false);
        	        	
        	        }
        	
        	}
	);
		
		
		//NOTE TO MARKER//////////////////////////////////////////////
		Label note1 = new Label("To whom it may concern");
		Label note2 = new Label("Within the first 20 names, there are...");
		Label note3 = new Label("3 people with the last name Kenison");
		Label note4 = new Label("2 people with the first name Yun");
		
		VBox heyMarker = new VBox();
		heyMarker.getChildren().addAll(note1, note2, note3, note4);
		heyMarker.setAlignment(Pos.CENTER);
		heyMarker.setBackground(new Background(new BackgroundFill(Color.MINTCREAM, CornerRadii.EMPTY, Insets.EMPTY)));
		
		
		
		
		//Add all my boxes to the sidebar and return!
		VBox sideBar = new VBox(10);
		sideBar.getChildren().addAll(basicPrintBox, searchBox, advancedBox, renameBox, heyMarker);
		
		return sideBar;
		
		
	}
	
	

}
