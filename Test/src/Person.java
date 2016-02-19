
public class Person {
	String name;
	private int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int newAge) {
		age = newAge;
		System.out.println("age has been changed");
	}
	
	public String canDrink() {
		if(age >= 21) {
			return "Yes";
		} else {
			return "no";
		}
	}
}
