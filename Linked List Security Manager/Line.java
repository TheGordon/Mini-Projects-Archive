
class Line {
    //Variables
    private Person headPerson;
    private Person tailPerson;
    private int length;
    private Line lineLink;

    //Constructor
    public Line(){
        headPerson = null;
        tailPerson = null;
        length=0;
        lineLink=null;
    }

    //Getter and Setter methods
    public Line getLineLink() {
        return lineLink;
    }    

    public void setLineLink(Line lineLink) {
        this.lineLink = lineLink;
    }

    public Person getTailPerson(){
        return tailPerson;
    }
    public Person getHeadPerson(){
        return headPerson;
    }
    
    public int getLength(){
        return length;
    }
    
    //Adds person to a line
    public void addPerson(Person attendee) {
        //NodePtr and PrevPtr
        Person ptr = headPerson;
        Person prevPtr = null;

        //First checks if there's no attendee
        if (length == 0){
            headPerson = attendee;
            tailPerson = attendee;
        }
        else{
            //Sorts through the linked list to find where to insert the attendee in proper ascending order
            while (ptr!= null){
                if (ptr.getSeatNumber()>attendee.getSeatNumber()) break;
                prevPtr = ptr;
                ptr = ptr.getNextPerson();
            }

            //Inserts attendee in proper place while updating headPerson and tailPerson
            if(prevPtr == null){
                attendee.setNextPerson(headPerson);
                headPerson = attendee;
            }
            else{
                if (ptr == null) tailPerson = attendee;
                prevPtr.setNextPerson(attendee);
                attendee.setNextPerson(ptr);
            }
        }
        length++;
    }

    //Removes the headPerson if they exist
    public Person removeFrontPerson(){
        if (length == 0) return null;

        Person removedPerson = headPerson;
        headPerson = headPerson.getNextPerson();
        length--;
        if (length == 0) tailPerson = null;

        return removedPerson;
    }

    //Searches if there a seatNumber exists in a line
    public boolean listSearch(int target){
        Person ptr = headPerson;
        while(ptr != null){
            if(target == ptr.getSeatNumber()){
                return true;
            }
            ptr = ptr.getNextPerson();
        }
        return false;

    }

    //Debugging
    public static void main (String [] args){
        Line a = new Line();
        a.addPerson(new Person("Yo",1));
        a.addPerson(new Person("Yo3",3));
        a.addPerson(new Person("Yo10",10));
        a.addPerson(new Person("Yo7",7));
        a.addPerson(new Person("Yo5",5)); 

        System.out.println(a.listSearch(9));
        
        System.out.println(a.headPerson.getSeatNumber());

        //Person x = a.headPerson;
        
        
  

    }

}
