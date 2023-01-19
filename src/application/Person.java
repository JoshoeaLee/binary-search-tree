package application;


/**
 * The person class represents a single person who has a first name, last name, age and unique ID.
 * @author joshualee
 * 
 * @version 1.0
 * @since 1.0
 *
 */
public class Person{
	


	String firstName = "Generic";
	String lastName = "Generic";
	int age = 0;
	int ID = 0;
	
	
	/**
	 * Constructor for the Person object. None of these parameters may be null.
	 * @param firstName Person's first name.
	 * @param lastName Person's surname.
	 * @param age Person's age.
	 * @param ID An automatically assigned unique ID. (ID assigned based on order written 
	 * into file).
	 */
	public Person(String firstName, String lastName, int age, int ID) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.ID = ID;
		
	}
	
	
	
	//Getters and Setters Follow
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	

}
