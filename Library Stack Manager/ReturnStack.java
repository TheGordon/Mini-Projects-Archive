//A stack that implements a stack using linkedlists of ReturnLog.

class InvalidReturnDateException extends Exception {}
class BookNotCheckedOutException extends Exception {}
class BookCheckedOutBySomeoneElseException extends Exception{}
class EmptyStackException extends Exception{}

public class ReturnStack {
    // Private instance variable to store the top return log.
    private ReturnLog topLog;

    // Default constructor for ReturnStack.
    public ReturnStack() {
        this.topLog = null;
    }

    //Pushes a returned book's ReturnLog into the stack and returns true or false if the return date is past due date   
    public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, InvalidReturnDateException, BookNotCheckedOutException, BookCheckedOutBySomeoneElseException, InvalidUserIDException {
        if(returnISBN/10000000000000L > 1) throw new InvalidISBNException();
        if(returnUserID/10000000000L > 1) throw new InvalidUserIDException();
        
        Book retrieveBook = bookRepoRef.getBookShelf(returnISBN);

        if(retrieveBook != null){
            if (retrieveBook.isCheckedOut() == false) throw new BookNotCheckedOutException();
            if(Date.compare(retrieveBook.getCheckOutDate(), returnDate) == 1) throw new InvalidReturnDateException();
            if(retrieveBook.getCheckOutUserID() != returnUserID) throw new BookCheckedOutBySomeoneElseException();
            ReturnLog newLog = new ReturnLog(returnISBN, returnUserID, returnDate);
            newLog.setNextLog(topLog);
            topLog = newLog;

            if (Date.compare(retrieveBook.getDueDate(), returnDate) >= 0) return true;
            return false;
        }
        
        throw new BookNotCheckedOutException();
        
    }

    //Pops the last returned book's ReturnLog from the top of ReturnStack.
    public ReturnLog popLog() throws EmptyStackException {
        if(topLog == null) throw new EmptyStackException();

        ReturnLog poppedLog = topLog;
        topLog = topLog.getNextLog();
        return poppedLog;
    }

    //Peeps the top log
    public String peekLog(BookRepository bookRepoRef){
        if(topLog != null){
            return bookRepoRef.getBookShelf(topLog.getISBN()).getName();
        }
        return "";
    }

    public void emptyStack(BookRepository bookRepoRef){

        try{
            while (true) bookRepoRef.checkInBook(popLog().getISBN(), 0);
        }
        catch(EmptyStackException e){
            
        }
    }

    //Converts the ReturnStack's data to a tabular string form.
    @Override
    public String toString() {
        // Implementation to convert the ReturnStack's data to a tabular string form.
        System.out.println("|\t      ISBN\t     |\t         UserID\t        | \tReturn Date");
        System.out.println(" =================================================================================");

        ReturnLog ptr = topLog;

        while(ptr != null){
            System.out.print("|\t ");
            System.out.printf("%0 14d", ptr.getISBN());
            System.out.print("\t     |\t");
            System.out.print("         " + ptr.getUserID() + "   \t|\t   ");
            System.out.println(ptr.getReturnDate());
            ptr = ptr.getNextLog();
        }
        return "";
    }

    //Debugging
    public static void main(String[] args) {
        BookRepository a = new BookRepository();
        ReturnStack b = new ReturnStack();
        
        try{
            long ISBN = 234312312323L;
            a.addBook(ISBN, "name1", "author1", "genre1", 2000, Book.Condition.Bad);
            a.addBook(ISBN+1, "name1", "author1", "genre1", 2000, Book.Condition.Bad);
            a.addBook(ISBN+3, "name1", "author1", "genre1", 2000, Book.Condition.Bad);
            a.addBook(ISBN+2, "name1", "author1", "genre1", 2000, Book.Condition.Bad);
            System.out.println("added Book" + a.getShelf(ISBN));


             
            a.checkOutBook(ISBN, 6942011111L, new Date(1,1,1), new Date(1,1,3));
            a.checkOutBook(ISBN+1, 69420, new Date(1,1,1), new Date(1,1,3));
            a.checkOutBook(ISBN+2, 69420, new Date(1,1,1), new Date(1,1,3));
            a.checkOutBook(ISBN+3, 69420, new Date(1,1,1), new Date(1,1,3));


            b.pushLog(ISBN, 6942011111L, new Date(5,12,2), a);
            b.pushLog(ISBN+1, 69420, new Date(5,12,2), a);
            b.pushLog(ISBN+2, 69420, new Date(5,12,2), a);


            System.out.println(b);
            b.popLog();
            System.out.println(b);
            b.pushLog(ISBN+2, 69420, new Date(5,12,2), a);
            b.pushLog(ISBN+3, 69420, new Date(5,12,2), a);
            System.out.println(b);
            System.out.println(a.getShelf(0));
            b.emptyStack(a);

            System.out.println(a.getShelf(0));

        }
        catch(InvalidUserIDException e){System.out.println("e1");}
        catch(InvalidISBNException e){System.out.println("e2");}
        catch(BookAlreadyExistsException e){System.out.println("e3");}
        catch(BookAlreadyCheckedOutException e){System.out.println("e4");}
        //catch(InvalidSortCriteraException e){System.out.println("e5");}
        catch(InvalidReturnDateException e){System.out.println("e5");}
        catch(BookNotCheckedOutException e){System.out.println("e6");}
        catch(BookCheckedOutBySomeoneElseException e){System.out.println("e7");}
        catch(EmptyStackException e){System.out.println("e5");}

    }
}
