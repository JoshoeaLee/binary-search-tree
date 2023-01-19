package comparators;

import java.util.Comparator;

import application.Person;

public class FirstLastNameDiffComparator implements Comparator<Person>{

	@Override
	public int compare(Person o1, Person o2) {
		
		//If first name is significantly longer than last name it will appear on the left. 
		return (o1.getLastName().length() - o1.getFirstName().length()) - (o2.getLastName().length() - o2.getFirstName().length());
		
			}

	@Override
	public String toString() {
		return "Firstname - Surname Length";
	}
}