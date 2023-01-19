package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class loads in the names.txt file and then allows the user to select how many
 * Person objects they want to create and add to the people ArrayList which will be 
 * used for the diagram
 * @author joshualee
 * @version 1.0
 * @since 1.0
 *
 */
public class FileLoader {
	
	String file;
	int fileNum;
	public static ArrayList<Person> allPeople = new ArrayList<Person>();

	
	/**
	 * Constructor for Fileloader Class. 
	 * @param file The name of the original file (in the BinarySearchTree folder) which
	 * holds the full list of people.
	 * 
	 * @param fileNum The number of people to be written into the new file
	 */
	public FileLoader(String file, int fileNum) {
		
		this.file = file;
		this.fileNum = fileNum;
		
	}
	
	
	/*
	 * Load in all names, give ages and put in an ArrayList. 
	 */
	public void writeFiles() {
		allPeople.clear();
		Main.people.clear();
		
		try {
			Scanner sc = new Scanner(new File(file));
			
			while(sc.hasNext()) {
				
				String firstName = sc.next();
				String lastName = sc.next();
				int age = (int) (Math.random()*100);
				Main.idCounter++;
				int ID = Main.idCounter;
				
				allPeople.add(new Person(firstName, lastName, age, ID));
			
			}
			
			sc.close();
			this.createFiles();
			
		}catch (IOException e) {
			System.out.println("File error with the names.txt file");
			}			
		
	}
	
	/*
	 * Creating files to use for the program to load in
	 */
	public void createFiles() {
			
			File fileToUse = new File("ListToUse.txt");
			try {
				
			PrintStream ps = new PrintStream(fileToUse);
			for(int i = 0; i<fileNum; i++) {
				
				ps.println(allPeople.get(i).getFirstName() + " " + allPeople.get(i).getLastName() 
				+ " " + allPeople.get(i).getAge() + " " + allPeople.get(i).getID());
				
		        }
			    ps.close();
			    this.loadFile();
			    		
	   }catch(IOException e) {System.out.println("Sorry, your file could not be saved.");}
	
	}
	
	/*
	 * Loading in the file to be used
	 */
	public void loadFile() {
		
		
		try {
			
			Scanner sc = new Scanner(new File("ListToUse.txt"));
			
			while(sc.hasNext()) {
				
				String firstName = sc.next();
				String lastName = sc.next();
				int age = sc.nextInt();
				int ID = sc.nextInt();
				sc.nextLine();
				
				Main.people.add(new Person(firstName, lastName, age, ID));
				
				
			}
			
			sc.close();
			
		}catch (IOException e) {
			System.out.println("File error");
			}	
		
		
	}

}
