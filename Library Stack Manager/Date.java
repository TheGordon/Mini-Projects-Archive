//Simple Date Class that keeps track of a date
public class Date {
    // Private instance variables to store day, month, and year.
    private int day;
    private int month;
    private int year;

    //Default constructor that initializes the Date to January 1, 2000.
     
    public Date() {
        this.day = 1;
        this.month = 1;
        this.year = 1;
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

    @Override
    public String toString() {
        if(month < 10 && day < 10) return "0" + month + "/" + "0" + day + "/" + year;
        if(month < 10) return "0" + month + "/" + day + "/" + year;
        if(day < 10) return month + "/" + "0" + day + "/" + year; 
        return month + "/" + day + "/" + year;

    }

    // Getter and setter methods for day, month, and year.
    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;}
    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    public static void main(String[] args) {
        System.out.println(compare(new Date(03,1,2000), new Date()));
        System.out.println(new Date(03,41,2000));
    }

}
