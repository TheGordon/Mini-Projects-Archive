import java.util.InputMismatchException;

import java.util.Scanner;

public class PersonManager {
	private static PersonDataManager personDataManager;

     //Implements interactive user interface that can import the file, add and remove data, retrive info, and save to file.
    public static void main(String[] args) {
        personDataManager = new PersonDataManager();
        System.out.println("Greetings user! :D");
        System.out.println("Starting...");
		Scanner scan = new Scanner(System.in);
        char choice = '0';
        String location = "";
        String name = "";

        while(choice!='Q') {      
            System.out.println("Menu:");
            System.out.println("\t(I) - Import from file");
			System.out.println("\t(A) - Add person");
			System.out.println("\t(R) - Remove person");
			System.out.println("\t(G) - Get Info on person");
			System.out.println("\t(P) - Print table");
			System.out.println("\t(S) - Save to file");
			System.out.println("\t(Q) - Quit");
            System.out.print("Please select an option: ");
            choice = scan.next().charAt(0);
            scan.nextLine();
            
            switch(choice)
			{
                case 'I':
                    System.out.print("Please enter a location: ");
                    location = scan.nextLine();
                    System.out.println("Loading...");
                    try{
                        PersonDataManager.buildFromFile(location);
                    
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'A':
                    try {
                        System.out.print("Please enter the name of the person: ");
                        name=scan.nextLine();
                        System.out.print("Please enter the age: ");
                        int age=scan.nextInt();
                        scan.nextLine();
                        System.out.print("Please enter the gender (M or F): ");
                        String gender=scan.nextLine();
                        System.out.print("Please enter the height (in inches): ");
                        double height=scan.nextDouble();
                        scan.nextLine();
                        System.out.print("Please enter the weight (in lbs): ");
                        double weight=scan.nextDouble();
                        scan.nextLine();
                        Person person = new Person(name, gender, age, height, weight);

                        personDataManager.addPerson(person);
                        System.out.println(person.getName() + " has been added to the list!");
                    } catch (PersonAlreadyExistsException e) {  
                        System.out.println(e.getMessage());
                    } catch(NullPointerException e){
                        System.out.println("No file has been imported yet! :(");
                    }catch(InputMismatchException e){
                        System.out.println("The input you entered is incorrect. Please try again!");
                        scan.nextLine();
                    }
                    break;
                
                case 'R':
                    System.out.print("Please enter the name of the person: ");
                    name = scan.nextLine();
                    try {
                        personDataManager.removePerson(name);
                        System.out.println(name + " has been removed!");
                    } catch (PersonDoesNotExistsException e) {
                        System.out.println(e.getMessage());
                    }catch(NullPointerException e){
                        System.out.println("No file has been imported yet! :(");
                    }
                    break;
                
                case 'G':
                    System.out.print("Please enter the name of the person: ");
                    name =scan.nextLine();
                    try {
                        personDataManager.getPerson(name);
                    } catch (PersonDoesNotExistsException e) {
                        System.out.println(e.getMessage());
                    }catch(NullPointerException e){
                        System.out.println("No file has been imported yet! :(");
                    }
                    break;

                case 'P':
                try{
                    personDataManager.printTable();
                    break;
                }catch(NullPointerException e){
                    System.out.println("No file has been imported yet! :(");
                    }
                case 'S':
                    try{
                        System.out.print("Please select a name for the file: ");
                        name = scan.nextLine();
                        personDataManager.saveToFile(name);
                        System.out.println("A file named " + name + " has been created!");
                        break;
                    }catch(NullPointerException e){
                        System.out.println("No file has been imported yet! :(");
                    }

                case 'Q':
                    System.out.println("Sorry to see you go!");
                    break;
            }

        
        }
        
        scan.close();

    }
}
