public class Person {
    private int age;
    private double height;
    private double weight;
    private String name;
    private String gender;

    public Person(){

    }
    public Person(String name, String gender, int age, double height, double weight){
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.name = name;
		this.gender = gender;
    }
	 //return the age
	public int getAge() {
		return age;
	}
	//Sets the age
	public void setAge(int age) {
		this.age = age;
	}
	//return the height
	public double getHeight() {
		return height;
	}
	//Sets the height
	public void setHeight(double height) {
		this.height = height;
	}
	//return the weight
	public double getWeight() {
		return weight;
	}
	//Sets weight to new value
	public void setWeight(double weight) {
		this.weight = weight;
	}

	//return the name
	public String getName() {
		return name;
	}
    //Sets name to new value
	public void setName(String name) {
		this.name = name;
	}
	//return the gender
	public String getGender() {
		return gender;
	}
	//Sets gender to new value
	public void setGender(String gender) {
		this.gender = gender;
	}

	//Prints the data in tabular format
    public String toString() {
		int feet = (int)(height/12);
		int inch = (int)(height%12);
		String ftin = feet + " feet " + inch + " inches ";
		String lbs = (int)weight + "";
		if (inch>=10) ftin = feet + " feet " + inch + " inches";
		if (weight<100) lbs = " " + (int)weight;

		return ("     "+name+"\t|\t"+age+" \t|\t"+gender+" \t| "+ftin+" |  "+lbs + " pounds");
	}

	//debugging
    public static void main(String[] args) {
            Person a = new Person("Jake", "M", 20, 74, 300);
            System.out.println(a);			

	}
}
