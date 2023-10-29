//This is the user interface for this project. It implements all the functionalities from 'SecurityCheck' in an interactive interface

import java.util.Scanner;

public class SecurityManager {
    public static void main(String[] args) {
        SecurityCheck manager = new SecurityCheck();
        Scanner scanner = new Scanner(System.in);
        String option = "";

        System.out.println("\nStarting...");
        while (option != "Q") {
            System.out.println("\n");
            manager.lineCheck();
            System.out.println("\n");
            System.out.println("Menu:");
            System.out.println("\t(A) - Add Person");
            System.out.println("\t(N) - Next Person");
            System.out.println("\t(R) - Remove Lines");
            System.out.println("\t(L) - Add Lines");
            System.out.println("\t(P) - Print All Lines");
            System.out.println("\t(Q) - Quit");
            System.out.println("\t__________________________________________________________");
            System.out.print("Please select an option: ");
            option = scanner.nextLine().toUpperCase();
            try{
                switch (option) {
                    
                    case "A":
                        System.out.print("Please enter a name: ");
                        String name = scanner.nextLine();
                        System.out.print("Please enter a seat number: ");
                        int seatNumber = scanner.nextInt(); scanner.nextLine();
                        System.out.println("\nLoading...");
                        manager.addPerson(name, seatNumber);
                        
                        System.out.println(name + " successfully added to line " + manager.getLineChanged() + "!");
                        break;
                    case "N":
                        System.out.println("Loading...");
                        Person removed = manager.removeNextAttendee();
                        System.out.println(removed.getName() + " from seat " + removed.getSeatNumber() + " removed from line " + manager.getLineChanged() + "!");
                        break;

                    case "R":
                        System.out.print("Lines to remove: " );
                        String str = scanner.nextLine();
                        int[] lineList = {};
                        try{
                            String[] linesRemoved = str.split(",");
                            lineList = new int[linesRemoved.length];
                            for (int i = 0; i < linesRemoved.length; i++) lineList[i] = Integer.parseInt(linesRemoved[i]);
                        }catch(NumberFormatException e){
                            try{
                                String[] linesRemoved = str.split(", ");
                                lineList = new int[linesRemoved.length];
                                for (int i = 0; i < linesRemoved.length; i++) lineList[i] = Integer.parseInt(linesRemoved[i]);
                            }catch(NumberFormatException ex){
                                System.out.println("Improper formatting.");
                                break;
                            }
                        }
                        System.out.println("\nLoading..."); 
                        manager.removeLines(lineList);
                        
                        if(lineList.length == 1)
                            System.out.println("Line " + lineList[0] + " has been decommissioned!");
                        else{
                            System.out.print("Lines ");
                            int i = 0;
                            while (i < lineList.length-1){
                                System.out.print(lineList[i] + ", ");
                                i++;
                            }
                            System.out.println("and " + lineList[i] + " have been decommissioned!");
                        }
                        
                        break;

                    case "L":
                        System.out.print("How many more lines?: ");
                        int linesAdded = scanner.nextInt(); scanner.nextLine();
                        System.out.println("\nLoading...");
                        manager.addNewLines(linesAdded);
                        break;

                    case "P":
                        System.out.println("\nLoading...");
                        manager.printTable();
                        break;

                    case "Q":
                        System.out.println("Goodbye!");
                        System.out.println(option);
                        option = "Q";
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            catch(InvalidLineCountException e){System.out.println(e);}
            catch(TakenSeatException e){System.out.println(e);}
            catch(AllLinesEmptyException e){System.out.println(e);}
            catch(LineDoesNotExistException e){System.out.println("The input you entered is incorrect. Please try again!");}
            catch(SingleLineRemovalException e){System.out.println(e);}

        }
        
        scanner.close();
    }
}

