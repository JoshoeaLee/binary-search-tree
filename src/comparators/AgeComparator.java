package comparators;

import java.util.Comparator;

import application.Person;

public class AgeComparator implements Comparator<Person>{

	@Override
	public int compare(Person o1, Person o2) {
		return  o1.getAge() -  o2.getAge();
	}
	
	@Override
	public String toString() {
		return "Age";
	}

}
