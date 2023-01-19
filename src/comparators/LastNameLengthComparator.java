package comparators;

import java.util.Comparator;

import application.Person;

public class LastNameLengthComparator implements Comparator<Person> {

	@Override
	public int compare(Person o1, Person o2) {
		return o1.getLastName().length() - o2.getLastName().length();
	}

}
