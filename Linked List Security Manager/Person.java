public class Person {
    //Variables
    private int seatNumber;
    private String name;
    private Person nextPerson;

    //Constructors
    public Person(){
        name = "";
        seatNumber = 0;
        nextPerson = null;
    }

    public Person(String name, int seatNumber) {
        this.name = name;
        this.seatNumber = seatNumber;
        nextPerson = null;
    }
    //getter for name
    public String getName() { 
        return name;
    }

    //setter for name
    public void setName(String name) {    
        this.name = name;
     }
    
     //getter for seatNumber
    public int getSeatNumber() { 
        return seatNumber;
    }

    //setter for seatNumber
    public void setSeatNumber(int seatNumber) { 
        this.seatNumber = seatNumber;
    }

    // getter for nextPerson
    public Person getNextPerson() { 
        return nextPerson;
    }

    // setter for nextPerson
    public void setNextPerson(Person nextPerson) { 
        this.nextPerson = nextPerson;
    }

    public static void main(String[] args) {
        System.out.println("a");
    }
}
