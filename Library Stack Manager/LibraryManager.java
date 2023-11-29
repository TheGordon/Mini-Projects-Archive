//The user interface for managing the BookRepository and ReturnStack.

import java.util.Scanner;

public class LibraryManager {
    private static BookRepository bookRepository;
    private static ReturnStack returnStack;

    //Main method
    public static void main(String[] args) {
        bookRepository = new BookRepository();
        returnStack = new ReturnStack();

        Scanner scanner = new Scanner(System.in);
        String choice;
        String choice2;
    
        System.out.println("\nStarting...\n");
        do {
            displayMenu();
            System.out.print("Please select what to manage: ");
            choice = scanner.nextLine();

            System.out.print("Please select an option: ");
            try{
                switch (choice) {
                case "B": 
                    choice2 = scanner.nextLine();

                    switch(choice2){
                        case "C":
                            System.out.print("Please provide a library user ID: ");
                            long userID = scanner.nextLong();
                            scanner.nextLine();

                        
                            System.out.print("Please provide an ISBN number: ");
                            long ISBN = scanner.nextLong();
                            scanner.nextLine();
                            
                            System.out.print("Please provide the checkout date: ");
                            String date = scanner.nextLine();
                            String[] dates = date.split("/");
                            Date checkOuDate = new Date(Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), Integer.parseInt(dates[2]));
                            

                            System.out.print("Please provide a return date: ");
                            date = scanner.nextLine();
                            dates = date.split("/");
                            Date returnDate = new Date(Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), Integer.parseInt(dates[2]));

                            bookRepository.checkOutBook(ISBN, userID, checkOuDate, returnDate);
                            System.out.println("\nLoading...");

                            if (bookRepository.getBookShelf(ISBN) != null){
                                String name = bookRepository.getBookShelf(ISBN).getName();
                                System.out.println(name + " has been checked out by " + userID + " and must be returned on " + returnDate);
                            } else{
                              System.out.println("Book does not exist");  
                            }
                            break;

                        case "N":
                            System.out.print("Please provide an ISBN number: ");
                            ISBN = scanner.nextLong();
                            scanner.nextLine();

                            System.out.print("Please provide a name: ");
                            String name = scanner.nextLine();

                            System.out.print("Please provide an author: ");
                            String author = scanner.nextLine();
                            
                            System.out.print("Please provide a genre: ");
                            String genre = scanner.nextLine();

                            System.out.print("Please provide the publication year: ");
                            int year = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Please provide a condition: ");
                            String sCondition = scanner.nextLine();
                            Book.Condition condition = Book.Condition.valueOf(sCondition);

                            bookRepository.addBook(ISBN, name, author, genre, year, condition);

                            System.out.println("\nLoading...");
                            System.out.println(name + " has been successfully added to the book repository!");
                            break;

                        case "R":
                            System.out.print("Please provide an ISBN number: ");
                            ISBN = scanner.nextLong();
                            scanner.nextLine();

                            Book bookRemoved = bookRepository.getBookShelf(ISBN);
                            if(bookRemoved != null){
                                name =  bookRepository.getBookShelf(ISBN).getName();

                                System.out.println("calling");                              
                                bookRepository.removeBook(ISBN);

                                System.out.println("\nLoading...");
                                System.out.println(name + " has been successfully removed from the book repository!");
                            }
                            else throw new BookDoesNotExistException();

                            break;

                        case "P":
                            System.out.print("Please select a shelf: ");
                            int shelf = scanner.nextInt(); 
                            scanner.nextLine();

                            System.out.println("\nLoading...\n\n");
                            System.out.println(bookRepository.getShelf(shelf));
                            break;

                        case "S":
                            System.out.print("Please select a shelf: ");
                            shelf = scanner.nextInt(); 
                            scanner.nextLine();

                            System.out.print("Please select a sorting criteria: ");
                            String criteria = scanner.nextLine(); 

                            System.out.println("Loading...\n");
                            switch(criteria){
                                case "I":
                                    bookRepository.sortShelf(shelf, "ISBN");
                                    System.out.println("Shelf " + shelf + " has been sorted by " + "ISBN!");
                                    break;

                                case "N":
                                    bookRepository.sortShelf(shelf, "Name");
                                    System.out.println("Shelf " + shelf + " has been sorted by " + "name!");
                                    break;

                                case "A":
                                    bookRepository.sortShelf(shelf, "Author");
                                    System.out.println("Shelf " + shelf + " has been sorted by " + "author!");
                                    break;

                                case "G":
                                    bookRepository.sortShelf(shelf, "Genre");
                                    System.out.println("Shelf " + shelf + " has been sorted by " + "genre!");
                                    break;

                                case "Y":
                                    bookRepository.sortShelf(shelf, "Year");
                                    System.out.println("Shelf " + shelf + " has been sorted by " + "year!");
                                    break;

                                case "C":
                                    bookRepository.sortShelf(shelf, "Condition");
                                    System.out.println("Shelf " + shelf + " has been sorted by " + "condition!");
                                    break;

                                default:
                                    throw new InvalidSortCriteraException();
                            }
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    break;

                case "R":

                    choice2 = scanner.nextLine();

                    switch(choice2){
                        case "R":
                            System.out.print("Please provide the ISBN of the book you're returning: ");
                            long ISBN = scanner.nextLong(); 
                            scanner.nextLine();

                            System.out.print("Please your Library UserID: ");
                            long userID = scanner.nextLong();
                            scanner.nextLine();

                            System.out.print("Please your current date: ");
                            String date = scanner.nextLine();
                            String[] dates = date.split("/");
                            Date currentDate = new Date(Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), Integer.parseInt(dates[2]));
                            

                            boolean onTime = returnStack.pushLog(ISBN, userID, currentDate, bookRepository);
                            String name = bookRepository.getBookShelf(ISBN).getName();

                            System.out.println("\nLoading...\n");
                            if(onTime) System.out.println(name + " has been returned on time!");
                            else{
                                System.out.println(name + " has been returned LATE! Checking everything in...");
                                returnStack.emptyStack(bookRepository);
                            }
                            break;

                        case "L":
                            String peek = returnStack.peekLog(bookRepository);
                            System.out.println("\nLoading...\n");
                            if(!(peek.equals(""))) System.out.println(peek + " is the next book to be checked in.");
                            else System.out.println("There are no books in the return stack.");
                            break;

                        case "C":
                            peek = returnStack.peekLog(bookRepository);
                            System.out.println("\n\nLoading...\n");
                            bookRepository.checkInBook(returnStack.popLog().getISBN(), 0);
                            System.out.println(peek + " has been checked in!");
                            break;

                        case "P":
                            System.out.println("\nLoading...\n\n\n");
                            System.out.println(returnStack);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    break;

                case "Q":
                    System.out.println("Exiting Library Manager. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
        catch(InvalidUserIDException e){System.out.println("Invalid UserID.");}
        catch(InvalidISBNException e){ System.out.println("Invalid ISBN.");}
        catch(BookAlreadyExistsException e){System.out.println("Book Already Exists.");}
        catch(BookAlreadyCheckedOutException e){System.out.println("Book Already Checked Out.");}
        catch(BookDoesNotExistException e){System.out.println("Book Does Not Exist.");}
        catch(InvalidSortCriteraException e){System.out.println("Invalid Sort Critera.");}
        catch(InvalidReturnDateException e){System.out.println("Invalid Return Date.");}
        catch(BookNotCheckedOutException e){System.out.println("Book Not Checked Out.");}
        catch(BookCheckedOutBySomeoneElseException e){System.out.println("Book Checked Out By Someone Else.");}
        catch(EmptyStackException e){System.out.println("Empty Stack.");}

            

        } while (!choice.equals("Q"));
        scanner.close();
    }

    //A method to display the selection menu
    private static void displayMenu() {

        System.out.println("\nMenu:\n");
        System.out.println("\t(B) - Manage Book Repository");
        System.out.println("\t\t(C) - Checkout Book");
        System.out.println("\t\t(N) - Add New Book");
        System.out.println("\t\t(R) - Remove Book");
        System.out.println("\t\t(P) - Print Repository");
        System.out.println("\t\t(S) - Sort Shelf");
        System.out.println("\t\t\t(I) - ISBN Number");
        System.out.println("\t\t\t(N) - Name");
        System.out.println("\t\t\t(A) - Author");
        System.out.println("\t\t\t(G) - Genre");
        System.out.println("\t\t\t(Y) - Year");
        System.out.println("\t\t\t(C) - Condition");
        System.out.println("\t(R) - Manage Return Stack");
        System.out.println("\t\t(R) - Return Book");
        System.out.println("\t\t(L) - See Last Return");
        System.out.println("\t\t(C) - Check In Last Return");
        System.out.println("\t\t(P) - Print Return Stack");
        System.out.println("\t(Q) - Quit");
        System.out.println("\t_____________________________________________________________________________________________\n");
    }

}
