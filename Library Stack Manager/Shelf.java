//A Shelf class that is a linked list of Book nodes
//Has several funtionalities, such as adding a node, removing a node, and sorting the linked list based on a given criteria
class BookAlreadyExistsException extends Exception {}
class InvalidISBNException extends Exception {}
class BookDoesNotExistException extends Exception {}


public class Shelf {
    // Private instance variables to store shelf information.
    private Book headBook;
    private Book tailBook;
    private int length;
    private SortCriteria shelfSortCriteria;

    // Enum for sorting criteria.
    public enum SortCriteria {
        ISBN, Name, Author, Genre, Year, Condition
    }

    //Default constructor for a shelf. Initializes values to default values.
    public Shelf() {
        this.headBook = null;
        this.tailBook = null;
        this.length = 0; 
        this.shelfSortCriteria = SortCriteria.ISBN; // Default sorting criteria
    }

    //Adds a book to the shelf while maintaining the shelf's current sorting criteria.
    public void addBook(Book addedBook) throws BookAlreadyExistsException {
        for(Book ptr = headBook; ptr != null; ptr = ptr.getNextBook()){
            if (ptr.getISBN() == addedBook.getISBN()) throw new BookAlreadyExistsException();
        }

        // Add the book while maintaining sorting criteria.
        length++;
        if (headBook == null) {
            headBook = addedBook;
            tailBook = headBook;
        } else {
            
            addedBook.setNextBook(headBook);
            headBook = addedBook;
            sort(shelfSortCriteria);
            
        }

    }

    // Removes the book with the specified ISBN from the shelf.
    public void removeBook(String removedISBN) throws InvalidISBNException, BookDoesNotExistException {
        if(removedISBN.length() > 13) throw new InvalidISBNException(); 
        if(length == 0) throw new BookDoesNotExistException();

        long lISBN = Long.parseLong(removedISBN);
        Book ptr = headBook;

        if(headBook.getISBN() == lISBN){
            headBook = headBook.getNextBook();
            length--;
            if(headBook == null) tailBook = null;
            return;
        }
        while(ptr != tailBook && !(ptr.getNextBook().getISBN() == lISBN))
            ptr = ptr.getNextBook();
        
        if(ptr == tailBook) throw new BookDoesNotExistException();

        ptr.setNextBook(ptr.getNextBook().getNextBook());
        length--;
        if(ptr.getNextBook() == null) tailBook = ptr;
    }

    //Swaps two nodes that are next to eachother
    private Boolean swap(Book ptr, Book prevPtr, Book prevPtr2){
        prevPtr.setNextBook(ptr.getNextBook());
        ptr.setNextBook(prevPtr);

        if(tailBook == ptr) tailBook = prevPtr;
        if(headBook == prevPtr) headBook = ptr;
        else prevPtr2.setNextBook(ptr);
        return true;
    }

    //Uses bubble sort on the books in the shelf given some sorting criteria in ascending order.
    public void sort(SortCriteria sortCriteria) {
        this.shelfSortCriteria = sortCriteria;

        if (headBook == null) return;

        for(int i = 0; i < length - 1; i++){
            Book ptr = headBook.getNextBook(), prevPtr = headBook, prevPtr2 = null;

            for(int j = 0; j < length - i - 1; j++){
                Boolean swapped = false;
                switch(sortCriteria){
                    case ISBN: 
                        if(ptr.getISBN() < prevPtr.getISBN()) swapped = swap(ptr, prevPtr, prevPtr2);
                        break;
                    case Name:
                        if(ptr.getName().compareTo(prevPtr.getName()) < 0) swapped = swap(ptr, prevPtr, prevPtr2);
                        break;
                    case Author:
                        if(ptr.getAuthor().compareTo(prevPtr.getAuthor()) < 0) swapped = swap(ptr, prevPtr, prevPtr2);
                        break;
                    case Genre:
                        if(ptr.getGenre().compareTo(prevPtr.getGenre()) < 0) swapped = swap(ptr, prevPtr, prevPtr2);
                        break;
                    case Year:
                        if(ptr.getYearPublished() < prevPtr.getYearPublished()) swapped = swap(ptr, prevPtr, prevPtr2);
                        break;
                    case Condition:
                        if(ptr.getBookCondition().ordinal() < prevPtr.getBookCondition().ordinal()) swapped = swap(ptr, prevPtr, prevPtr2);
                        break;
                }
                if(swapped){
                    prevPtr = ptr;
                    ptr = ptr.getNextBook();
                }
                prevPtr2 = prevPtr;
                prevPtr = ptr;
                ptr = ptr.getNextBook();
            }
        }
    }

    //Searches through the shelf and returns the book
    public Book getBook(long ISBN){

        for(Book ptr = headBook; ptr != null; ptr = ptr.getNextBook()){
            if(ptr.getISBN() == ISBN) return ptr;
        }
        return null;
    }

    //Converts the shelf's data to a tabular string form.
    @Override
    public String toString() {

        switch(shelfSortCriteria){
            case ISBN:
                System.out.print("| \tISBN\t |");
                break;
            case Name:
                System.out.print("| \tName\t |");
                break;
            case Author:
                System.out.print("| \tAuthor\t |");
                break;
            case Genre:
                System.out.print("| \tGenre\t |");
                break;
            case Year:
                System.out.print("| \tYear\t |");
                break;
            case Condition:
                System.out.print("| \tCondition\t |");
                break;
        }

        System.out.println(" \tChecked Out\t | \tCheck Out Date\t |    Checkout UserID\t|\t Due Date");
        System.out.println(" =====================================================================================================================");
        
        for(Book ptr = headBook; ptr != null; ptr = ptr.getNextBook()){

            switch(shelfSortCriteria){
                case ISBN:
                    System.out.print("| ");
                    System.out.printf("%0 14d", ptr.getISBN());
                    System.out.print(" |\t");
                    break;
                case Name:
                    System.out.print("|\t" + ptr.getName() + "\t|\t");
                    break;
                case Author:
                    System.out.print("|\t" + ptr.getAuthor() + "\t |\t");
                    break;
                case Genre:
                    System.out.print("|\t" + ptr.getGenre() + "    |\t");
                    break;
                case Year:
                    System.out.print("|\t" + ptr.getYearPublished() + "\t |\t");
                    break;
                case Condition:
                    System.out.print("|     " + "\t"+ ptr.getBookCondition() + "\t         |\t");
                    break;
        }
            if(ptr.isCheckedOut()) System.out.print("      Y          |\t");
            else System.out.print("      N          |\t");

            if(ptr.getCheckOutDate() == null) System.out.print("    N/A   \t |\t");
            else System.out.print(ptr.getCheckOutDate() + "\t|\t   ");
            
            if(ptr.getCheckOutUserID() == -1) System.out.print("    N/A   \t|\t");
            else System.out.print("    " + ptr.getCheckOutUserID() + "   \t|\t");

            if(ptr.getDueDate() == null) System.out.println("    N/A");
            else System.out.println(ptr.getDueDate());
        
        }
        return "";
    }

    //Debugging
    public static void main(String[] args) {
        Shelf a = new Shelf();

        try{
            a.addBook(new Book(1, "1","author1","test", 1, Book.Condition.Bad));
            a.addBook(new Book(4, "4","author1","test", 4, Book.Condition.Bad));
            a.addBook(new Book(3, "3","author1","test", 3, Book.Condition.Bad));
            System.out.println(a);
            System.out.println("length: " + a.length);

            a.removeBook("1");
            System.out.println("length: " + a.length);

            System.out.println(a);
            System.out.println("length: " + a.length);

            System.out.println("remove all");
            a.removeBook("4");
            a.removeBook("3");
            System.out.println("length: " + a.length);

            a.sort(SortCriteria.Author);
            a.addBook(new Book(1, "x1G","xau1","xtest", 1, Book.Condition.Good));
            a.addBook(new Book(6, "g6B","gau1","gtest", 6, Book.Condition.Bad));
            a.addBook(new Book(8, "c8G","cau1","ctest", 8, Book.Condition.Good));
            a.addBook(new Book(5, "a5B","aau1","atest", 5, Book.Condition.Bad));
            a.addBook(new Book(9, "b9R","bau1","btest", 9, Book.Condition.Replace));
            a.addBook(new Book(2, "z2N","zau1","ztest", 2, Book.Condition.New));
            
            System.out.println(a);
            System.out.println("length: " + a.length);
            
            System.out.println("Now sorting");
            a.sort(SortCriteria.ISBN);
        
            System.out.println("length: " + a.length);
            System.out.println(a);

            Book test = a.getBook(9);
            test.setAuthor("testing"); 
            System.out.println(a);

            a.sort(SortCriteria.Year);
            System.out.println(a);

            a.sort(SortCriteria.Name);
            System.out.println(a);
            

            a.removeBook("1");
            a.sort(SortCriteria.ISBN);

           System.out.println(a);


        }catch(BookAlreadyExistsException e){
            System.out.println("e1");

        }catch(InvalidISBNException e){
            System.out.println("e2");

        }catch(BookDoesNotExistException e){
            System.out.println("e3");

        }
    }

}
