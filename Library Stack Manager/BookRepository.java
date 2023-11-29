class InvalidUserIDException extends Exception {}
class BookAlreadyCheckedOutException extends Exception {}
class InvalidSortCriteraException extends Exception{}

public class BookRepository {
    // Private instance variable to store shelves.
    private Shelf[] shelves;

    //Public constructor for the BookRepository.
    public BookRepository() {
        shelves = new Shelf[10];
        for (int i = 0; i < 10; i++) shelves[i] = new Shelf();
    }

    //Checks in a book with the specified ISBN from the BookRepository.
    public void checkInBook(long checkedInISBN, long checkInUserID) {

            Book checkedBook = getBookShelf(checkedInISBN);
            if(checkedBook != null){
                checkedBook.setCheckedOut(false);
                checkedBook.setCheckOutDate(null);
                checkedBook.setDueDate(null);
            } 
    }

    //Checks out a book with the specified ISBN from the BookRepository.
    public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date checkOutDate, Date dueDate) throws InvalidISBNException, InvalidUserIDException, BookAlreadyCheckedOutException {
        if(checkedOutISBN/10000000000000L > 1) throw new InvalidISBNException();
        if(checkOutUserID/10000000000L > 1) throw new InvalidUserIDException();

        Book checkedBook = getBookShelf(checkedOutISBN);

        if(checkedBook != null){
            if(checkedBook.isCheckedOut() == true) throw new BookAlreadyCheckedOutException();
            checkedBook.setCheckedOut(true);
            checkedBook.setCheckOutDate(checkOutDate);
            checkedBook.setCheckOutUserID(checkOutUserID);
            checkedBook.setDueDate(dueDate);
        }         
    }

    //Adds a book to the BookRepository on the correct shelf.
    //If ISBN is less than 12, then it means the real ISBN has 0's in front so it adds it to the 0 shelf
    public void addBook(long addISBN, String addName, String addAuthor, String addGenre, int addYear, Book.Condition addCondition) throws InvalidISBNException, BookAlreadyExistsException {
        String sISBN = "" + addISBN;
        int intISBN = Integer.parseInt(sISBN.substring(0,1));

        if(sISBN.length() > 13) throw new InvalidISBNException();

        Book newBook = new Book(addISBN, addName, addAuthor, addGenre, addYear, addCondition);
        try{
            if (sISBN.length() < 13) shelves[0].addBook(newBook);
            else shelves[intISBN].addBook(newBook);

        }catch(BookAlreadyExistsException e){
            throw e;
        }
    }

    // Removes a book on the correct Shelf
    public void removeBook(long removeISBN) throws InvalidISBNException {

        String sISBN = "" + removeISBN;
        int intISBN = Integer.parseInt(sISBN.substring(0,1));
        try{
            if(sISBN.length() < 13) shelves[0].removeBook(sISBN);
            else shelves[intISBN].removeBook(sISBN);

        }
        catch(InvalidISBNException e){throw e;}
        catch(BookDoesNotExistException e2){System.out.println("Book Does Not Exist.");}
    }

    //Sorts Shelf based on a category
    public void sortShelf(int shelfInd, String sortCriteria) throws InvalidSortCriteraException{
        
        Boolean exists = false;
        for (Shelf.SortCriteria criteria : Shelf.SortCriteria.values()) {
            if ("Condition".equals(criteria + "")) exists = true;
        }
        if(!exists) throw new InvalidSortCriteraException();

        shelves[shelfInd].sort(Shelf.SortCriteria.valueOf(sortCriteria));
        
    }

    //Getter methods
    public Shelf getShelf(int i) {
        return shelves[i];
    }

    public Shelf getShelf(long ISBN) {
        for(int i = 0; i < 10; i++){
            if (shelves[i].getBook(ISBN) != null) return shelves[i];
        }
        return null;
    }
    
    public Book getBookShelf(long ISBN){
        for (Shelf shelf : shelves) {
            if(shelf.getBook(ISBN) != null) return shelf.getBook(ISBN);
        
        }
        return null;
    }

    //Debugging
    public static void main(String[] args) {
        BookRepository a = new BookRepository();

        try{
            a.checkInBook(1,1);
            a.checkOutBook(1,1, new Date(), new Date());
            long ISBN = 123431231232L;
            a.addBook(ISBN, "name1", "author1", "genre1", 2000, Book.Condition.Bad);
            System.out.println("added Book\n" + a.getShelf(ISBN));
            a.checkOutBook(ISBN,1, new Date(), new Date());
            System.out.println("Checked out book\n" + a.getShelf(ISBN));
            a.checkInBook(ISBN,0);
            System.out.println("Checked in book\n" + a.getShelf(ISBN));
            a.sortShelf(0, "Condition");
            System.out.println("Sorted Shelf\n" + a.getShelf(ISBN));
            a.sortShelf(0, "ISBN");
            System.out.println("Sorted Shelf\n" + a.getShelf(0));


            a.removeBook(ISBN);
            System.out.println("Removed Book\n" + a.getShelf(0));

        }
        catch(InvalidUserIDException e){System.out.println("e1");}
        catch(InvalidISBNException e){System.out.println("e2");}
        catch(BookAlreadyExistsException e){System.out.println("e3");}
        catch(BookAlreadyCheckedOutException e){System.out.println("e4");}
        catch(InvalidSortCriteraException e){System.out.println("e5");}

    }

}
