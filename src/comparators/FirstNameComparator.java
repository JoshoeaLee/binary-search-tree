package comparators;

import java.util.Comparator;

import application.Person;

public class FirstNameComparator implements Comparator<Person>{

	@Override
	public int compare(Person o1, Person o2) {
		
		
			return ( o1.getFirstName().compareToIgnoreCase(o2.getFirstName()));
		
	}
	
	@Override
	public String toString() {
		return "First Name";
	}
	


}
