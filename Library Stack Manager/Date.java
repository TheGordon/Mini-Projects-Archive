public class Date {
    // Private instance variables to store day, month, and year.
    private int day;
    private int month;
    private int year;

    //Default constructor that initializes the Date to January 1, 2000.
     
    public Date() {
        this.day = 1;
        this.month = 1;
        this.year = 2000;
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //Compares two Date objects chronologically.
     
    public static int compare(Date x, Date y) {
        if (x.year < y.year) return -1;
        else if (x.year > y.year) return 1;
        else {
            if (x.month < y.month) return -1;
            else if (x.month > y.month) return 1;
            else {
                if (x.day < y.day) return -1;
                else if (x.day > y.day) return 1;
                else return 0; // Dates are equal.
            }
        }
    }

    // Getter and setter methods for day, month, and year.
    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;}
    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    public static void main(String[] args) {
        System.out.println(compare(new Date(0,1,2000), new Date()));
    }

}