public class ReturnLog {
    // Private instance variables to store return log information.
    private long ISBN;
    private long userID;
    private Date returnDate;
    private ReturnLog nextLog;

    //Default constructor for ReturnLog.
    public ReturnLog() {
        this.ISBN = 0;
        this.userID = 0;
        this.returnDate = null;
        this.nextLog = null;
    }

    //Parameterized constructor for ReturnLog.
    public ReturnLog(long ISBN, long userID, Date returnDate) {
        this.ISBN = ISBN;
        this.userID = userID;
        this.returnDate = returnDate;
        this.nextLog = null;
    }

    // Getters and setters for all members.
    public long getISBN() {return ISBN;}
    public void setISBN(long ISBN) {this.ISBN = ISBN;}
    public long getUserID() {return userID;}
    public void setUserID(long userID) {this.userID = userID;}
    public Date getReturnDate() {return returnDate;}
    public void setReturnDate(Date returnDate) {this.returnDate = returnDate;}
    public ReturnLog getNextLog() {return nextLog;}
    public void setNextLog(ReturnLog nextLog) {this.nextLog = nextLog;}
}
