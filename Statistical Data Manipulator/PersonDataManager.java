//This file
/* This file implements all the tools to manipulate the files.
*The tools include:
*   - Reading a file
*   - Retrieving data
*   - Adding data
*   - Removing data
*   - Printing the data in tabular format
*   - Saving the modified data into a new local file
*/
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

class PersonAlreadyExistsException extends Exception {
	public PersonAlreadyExistsException(String msg) {
		super(msg);
	}
}
class PersonDoesNotExistsException extends Exception {
	public PersonDoesNotExistsException(String msg) {
		super(msg);
	}
}

public class PersonDataManager{
    private static Person[] people;

    public static void buildFromFile(String location) throws IllegalArgumentException {

        try {
            //Used Scanner object to conut how many lines are in the file
            Scanner scan= new Scanner(new File(location));
            int rowCount = 0;
            while (scan.hasNextLine()) {
                rowCount++;
                scan.nextLine();
            }
            rowCount--; //Gets rid of the header
            people = new Person[rowCount];
            scan.close();
            
            //Uses scanner to throw each line into an array, cleans that array, tests the array if it has any wacky symbols,
            //Then loads that array into the a person object, and that object into the Person's array
            rowCount = 0;
            scan = new Scanner(new File(location));
            scan.nextLine();

            while (scan.hasNextLine()) {
                String row = scan.nextLine();
                String[] arr = row.split(",");
                arr[1] = arr[1].replace("\"", "").replace(" ", "");
                String arrTest1 = arr[0] + arr[1];
                String arrTest2 = arr[2] + arr[3] + arr[4];
                //scan.close();
                for (char c: arrTest1.toCharArray()){
                    if(Character.isDigit(c)){
                        scan.close();   
                        throw new IllegalArgumentException("The CSV file has invalid characters.");
                    }
                    
                }
                for (char c: arrTest2.toCharArray()){
                    if(!Character.isDigit(c)){
                        scan.close();   
                        throw new IllegalArgumentException("The CSV file has invalid characters");
                    }
                }
                
                people[rowCount] = new Person(arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]), Double.parseDouble(arr[4]));
                rowCount++;
            }
            System.out.println("Person data loaded successfully!");
            scan.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }
 
	//Adds a person to the bag array
    public void addPerson (Person newPerson) throws PersonAlreadyExistsException {
        //Checks if there's any open space in the array
        //If there is no open space, it doubles the size of the array   
        Boolean isFull = true;
        for(Person i: people) if(i == null) isFull = false;

        if(isFull){
            Person[] newPeople = new Person[people.length*2];
            System.arraycopy(people, 0, newPeople, 0, people.length);
            people = newPeople;
        }

        //Gets the top most part of the array that isn't null
        int top = 0;
        for(Person i: people) if(i != null) top++;

        //Sorts through the array, checks if the new person already exists, then inserts the new person in alphabetic order
        for (int i = 0; i < top; i++){
            if(people[i].toString().equals(newPerson.toString())) throw new PersonAlreadyExistsException("Person already exists!"); 

            if(people[i].getName().compareTo(newPerson.getName()) >= 0){
                for(int j=top-1; j>=i; j--) people[j+1] = people[j];
                people[i] = newPerson;   
                break;
            }else if(people[top-1].getName().compareTo(newPerson.getName()) < 0) people[top] = newPerson;           
        }
    }
    public void getPerson (String name) throws PersonDoesNotExistsException{
        Boolean elementExists = false;

       //Identifies the last element that isn't null
       int top = 0;
       for(Person i: people) if(i != null) top++;

       //Sorts to the element and prints it     
       for (int i = 0; i < top; i++){
           if(people[i].getName().equals(name)){
                int feet = (int)(people[i].getHeight()/12);
                int inch = (int)(people[i].getHeight()%12);
                String ftin = feet + " feet and " + inch + " inches tall ";
                String lbs = (int)people[i].getWeight() + "";
                String gender;
                if (people[i].getGender().equals("M")) gender = "male";
                else if(people[i].getGender().equals("F")) gender = "female";
                else gender = "";
                

               System.out.println(people[i].getName() + " is a " + people[i].getAge() + " year old " + gender + " who is " + ftin + "and weighs " + lbs + " pounds.");
               elementExists = true;
           } 
       } 
       if(!elementExists) throw new PersonDoesNotExistsException("This Person doesn't exist");
    }
	//Removes a person from the array
    public void removePerson (String name) throws PersonDoesNotExistsException{
        Boolean elementExists = false;
        
        //Identifies the last element that isn't null
        int top = 0;
        for(Person i: people) if(i != null) top++;

        //Sorts to the element and removes it        
         for (int i = 0; i < top-1; i++){            
            if(people[i].getName().equals(name)){
                for(int j=i; j<top-1; j++) people[j] = people[j+1];
                top--;
                elementExists=true;
            }
            
        }
        if(people[top-1].getName().equals(name)){
            people[top-1] = null;
            elementExists = true;
        }
        if(!elementExists) throw new PersonDoesNotExistsException("This Person doesn't exist");
    }
    //Prints the whole data table
    public void printTable(){
        //identifies the last element that isn't null
        int top = 0;
        for(Person i: people) if(i != null) top++;
        //Prints the whole shabang
        System.out.println("     Name\t| \tage    \t|     Gender    |     Height       |\tWeight");
        System.out.println("==================================================================================");
        for (int i = 0; i < top; i++){
            System.out.println(people[i]);
        }
    }
    //Saves it to local file
    public void saveToFile(String name){
        try{
        FileWriter writer=new FileWriter(new File(name));
                writer.write("Name"+","+"Sex"+","+"Age"+","+"Height (in)"+","+"Weight (lbs)");
                writer.write("\n");
                for(Person i: people) {
                    if(i!=null) {
                    writer.write(i.getName()+","+i.getGender()+","+i.getAge()+","+i.getHeight()+","+i.getWeight());
                    writer.write("\n");
                    }
                }
                writer.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
//Debugging
    public static void main(String[] args) {
        buildFromFile("biostats.csv");            
        Person newPerson = new Person("Alexa", "M", 41, 74, 170);
        PersonDataManager test = new PersonDataManager();
        
        test.printTable();

        try{
            test.addPerson(newPerson);
            System.out.println("GetPerson ''bert''");
            test.getPerson("Bert");
            test.removePerson("Ruth");
        }catch(PersonAlreadyExistsException e){
            System.out.println(e);
        
        }catch(PersonDoesNotExistsException e){
            System.out.println(e);
        }
        System.out.println(Arrays.toString(people));
        
        //test.saveToFile();


        //System.out.println("Table");
         //test.printTable();
    
	}
}

