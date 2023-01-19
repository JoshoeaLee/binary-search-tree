package comparators;

import java.util.Comparator;

import application.Person;

public class FirstNameLengthComparator implements Comparator<Person> {

	@Override
	public int compare(Person o1, Person o2) {
		return o1.getFirstName().length() - o2.getFirstName().length();
	}

}
