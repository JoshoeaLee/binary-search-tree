package comparators;

import java.util.Comparator;

import application.Person;

public class LastNameIDComparator implements Comparator<Person>{

	@Override
	public int compare(Person o1, Person o2) {
		
		
		if(o1.getLastName().compareToIgnoreCase(o2.getLastName())==0) {
			return o1.getID() - o2.getID();
		}
		
			return ( o1.getLastName().compareToIgnoreCase(o2.getLastName()));
		
	}
	
}
