
public class Book {
    // Private instance variables to store book information.
    private String name;
    private String author;
    private String genre;
    private Condition bookCondition;
    private long ISBN;
    private long checkOutUserID;
    private int yearPublished;
    private Date checkOutDate;
    private Book nextBook;
    private boolean checkedOut;

    // Enum for book condition.
    
    public enum Condition {
        New, Good, Bad, Replace
    }
    //Default constructor for a book. Initializes values to default values.
    public Book() {
        this.name = "";
        this.author = "";
        this.genre = "";
        this.bookCondition = Condition.New; // Default condition
        this.ISBN = 0L;
        this.checkOutUserID = 0L;
        this.yearPublished = 0;
        this.checkOutDate = new Date();
        this.nextBook = null;
        this.checkedOut = false;
    }

    //Constructor with parameters to set book information.
    public Book(long ISBN, String name, String author, String genre, int yearPublished, Condition condition) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.bookCondition = condition;
        this.ISBN = ISBN;
        this.yearPublished = yearPublished;
    }

    
    // Getter and setter methods for all member variables.
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}
    public Condition getBookCondition() {return bookCondition;}
    public void setBookCondition(Condition bookCondition) {this.bookCondition = bookCondition;}
    public long getISBN() {return ISBN;}
    public void setISBN(long ISBN) {this.ISBN = ISBN;}
    public long getCheckOutUserID() {return checkOutUserID;}
    public void setCheckOutUserID(long checkOutUserID) {this.checkOutUserID = checkOutUserID;}
    public int getYearPublished() {return yearPublished;}
    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished;}
    public Date getCheckOutDate() {return checkOutDate;}
    public void setCheckOutDate(Date checkOutDate) {this.checkOutDate = checkOutDate;}
    public Book getNextBook() {return nextBook;}
    public void setNextBook(Book nextBook) {this.nextBook = nextBook;}
    public boolean isCheckedOut() {return checkedOut;}
    public void setCheckedOut(boolean checkedOut) {this.checkedOut = checkedOut;}

    public static void main(String[] args) {
        System.out.println(Condition.New + "");
    }
}
