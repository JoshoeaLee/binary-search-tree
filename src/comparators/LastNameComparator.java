package comparators;

import java.util.Comparator;

import application.Person;

public class LastNameComparator implements Comparator<Person>{

	@Override
	public int compare(Person o1, Person o2) {
		
		return  o1.getLastName().compareToIgnoreCase( o2.getLastName());

	}

	
	@Override
	public String toString() {
		return "Last Name";
	}
	
}
