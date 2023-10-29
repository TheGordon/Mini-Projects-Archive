//SecurityManager essentially manages the "Lines' linkedlist into another linked list.
//It's functionality includes adding and removing attendees and lines while maintaining each line having the roughly the same amount of persons.

import java.util.Arrays;

class TakenSeatException extends Exception {
	public TakenSeatException(String msg) {
		super(msg);
	}
}

class AllLinesEmptyException extends Exception {
	public AllLinesEmptyException(String msg) {
		super(msg);
	}
}

class InvalidLineCountException extends Exception {
	public InvalidLineCountException(String msg) {
		super(msg);
	}
}

class LineDoesNotExistException extends Exception {
	public LineDoesNotExistException (String msg) {
		super(msg);
	}
}

class SingleLineRemovalException extends Exception {
	public SingleLineRemovalException (String msg) {
		super(msg);
	}
}

class SecurityCheck{
    //Variables
    private Line headLine;
    private Line tailLine;
    private Line cursorLine;
    private int lineCount;
    private int lineChanged;

    //Constructor
    public SecurityCheck(){
        headLine = new Line();
        tailLine = headLine;
        cursorLine = headLine;
        lineCount = 1;
    }

    public int getLineChanged(){return lineChanged;}
    public Line getHeadLine(){return headLine;}

    //Adds person to a line while maintaining line length to be balanced
    public void addPerson(String Name, int seatNumber) throws TakenSeatException{
        boolean insertion = false;
        int i = 1;

        for(cursorLine = headLine; cursorLine != null; cursorLine = cursorLine.getLineLink()){
            if(cursorLine.listSearch(seatNumber) || (cursorLine.getLineLink() != null && cursorLine.getLineLink().listSearch((seatNumber))))
                throw new TakenSeatException("Seat is already taken.");
        
            if((insertion == false) && (cursorLine.getLineLink() == null || cursorLine.getLength() < cursorLine.getLineLink().getLength())){
                cursorLine.addPerson(new Person(Name, seatNumber));
                insertion = true;
                lineChanged = i;
            }
            i++;
        }

    }

    //Removes a person with the lowest seat number among the the longest set of lines
    public Person removeNextAttendee() throws AllLinesEmptyException{
        if (headLine == null) throw new AllLinesEmptyException("There are no lines.");
        
        Line removedAtLine = headLine;
        Person removedPerson;
        int i = 1;

        //Goes through the linkedlist to find the longest line(s) then removes the lowest seat number
        for(cursorLine = headLine.getLineLink(); cursorLine != null; cursorLine = cursorLine.getLineLink()){
            if(removedAtLine.getLength() < cursorLine.getLength()){
                removedAtLine = cursorLine;
                lineChanged = i;
            }
            if((removedAtLine.getLength() != 0 || cursorLine.getLength() != 0)
            && removedAtLine.getLength() == cursorLine.getLength()
            && removedAtLine.getHeadPerson().getSeatNumber() > cursorLine.getHeadPerson().getSeatNumber()){
                removedAtLine = cursorLine;
                lineChanged = i;
            }
            i++;
        }

        if(removedAtLine == headLine && headLine.getLength() == 0) throw new AllLinesEmptyException("All lines are empty.");

        removedPerson = removedAtLine.getHeadPerson();
        removedAtLine.removeFrontPerson();

        return removedPerson;
    }

    //Adds x amount of new lines
    //The strategy here is to unload all the data onto a singular "dummy" storage variable,
    //add the new lines in, and then unload all the data from the storage variable back into the rest of the linkedlist
    public void addNewLines(int newLines) throws InvalidLineCountException {
        if (newLines < 0) throw new InvalidLineCountException("The number of new lines cannot be negative.");

        if(lineCount == 0){
            Line newLine = new Line(); 
            headLine = newLine;
            tailLine = newLine;
            cursorLine = newLine;
            for (int i = 0; i < newLines-1; i++) {
                newLine = new Line(); 
                tailLine.setLineLink(newLine);
                tailLine = newLine;
            }
            
        }
        else{
            Line storedLines  = new Line();
                try{
                    while(true) storedLines.addPerson(removeNextAttendee());

                } catch(AllLinesEmptyException e){
                    for (int i = 0; i < newLines; i++) {
                        Line newLine = new Line(); 
                        tailLine.setLineLink(newLine);
                        tailLine = newLine;
                    }

                    try{
                        Person movedPerson = storedLines.removeFrontPerson();
                        while(movedPerson != null){
                            addPerson(movedPerson.getName(),movedPerson.getSeatNumber());
                            movedPerson = storedLines.removeFrontPerson();   
                        }

                    } catch(TakenSeatException ex){
                        System.out.println("Something went wrong");
                    }

                }

        }

        lineCount += newLines;
    }

    //Removes certain lines according to parameter input
    public void removeLines(int[] removedLines) throws LineDoesNotExistException, SingleLineRemovalException{

        //Checks for throw exceptions
        for (int lineIndex : removedLines) {
            if (lineIndex < 1 || lineIndex > lineCount)
                throw new LineDoesNotExistException("Line " + lineIndex + " does not exist.");
        }

        if (removedLines.length == 1 && lineCount == 1) {
            throw new SingleLineRemovalException("Cannot remove the only available line.");
        }
        

        //pointer variables, a wrapper class variable, and a storage variable
        Line ptr = headLine;
        Line prevPtr = null;
        Integer[] removedLinesWrap = Arrays.stream(removedLines).boxed().toArray(Integer[]::new);
        Line storedLines  = new Line();

        //Loops through the lines and checks if a line is the correct line to be removed
        //Stores all the removed line into a "dummy" storage variable
        //Uses pointer nodes to remove the variables while updating the tail and head
        //Unloads all the data from the storage variables back onto the updated linkedlist
        for(int i = 1; i <= lineCount; i++){
           if(Arrays.asList(removedLinesWrap).contains(i)){
               
                while(ptr.getLength() != 0) storedLines.addPerson(ptr.removeFrontPerson());

                if(ptr == headLine){
                    headLine = headLine.getLineLink();
                    prevPtr = ptr;
                }
                else{
                    if(i == lineCount) tailLine = prevPtr;
                    prevPtr.setLineLink(prevPtr.getLineLink().getLineLink());

                }
           } else prevPtr = ptr;

           ptr = ptr.getLineLink();
            
        }
        try{
            Person movedPerson = storedLines.removeFrontPerson();
            while(movedPerson != null){
                addPerson(movedPerson.getName(),movedPerson.getSeatNumber());
                movedPerson = storedLines.removeFrontPerson();  
            }

        } catch(TakenSeatException ex){
            System.out.println("Something went wrong");
        }


        lineCount -= removedLines.length; //updates line count
        
    }

    
    //Prints all information in the linked list in tabular format
    public void printTable(){

        System.out.println("|\tLine\t |     Name      |  Seat Number |");
        System.out.println("=================================================");

        int i = 1;
        for(Line ptr = headLine; ptr != null; ptr = ptr.getLineLink()){
            for(Person ptr2 = ptr.getHeadPerson(); ptr2 != null; ptr2 = ptr2.getNextPerson())
                System.out.println("|\t " + i + "\t |\t"
                                    + ptr2.getName() + "\t |\t "
                                    + ptr2.getSeatNumber() + "\t|");
            i++;
        }
    }

    //Prints how many people are in each line
    public void lineCheck(){
        
        int i = 1;
        for(cursorLine = headLine; cursorLine != null; cursorLine = cursorLine.getLineLink()){
            
            System.out.print("Line " + i + ": " + cursorLine.getLength());
            if(cursorLine.getLength() == 1) System.out.println(" Person Waiting");
            else System.out.println(" People Waiting");
            i++;
        }
    }
    
    //debugging
    public static void main (String [] args){
        SecurityCheck a = new SecurityCheck();
        System.out.println(a.headLine.getLength());
        try{
            
            a.addNewLines(3);
            a.addPerson("Yo1", 1);
            a.addPerson("Yo2", 2);
            a.addPerson("Yo3", 3);
            a.addPerson("Yo4", 4);
            a.addPerson("Yo5", 5);
            a.addPerson("Yo6", 6);
            a.addPerson("Yo7", 7);
            a.addPerson("Yo8", 8);
            a.addPerson("Yo9", 9);
            System.out.println(a.lineCount);
            System.out.println(a.headLine.getLength());
            for(Line ptr = a.headLine; ptr != null; ptr = ptr.getLineLink()) System.out.println(ptr.getTailPerson().getName() + " " + ptr.getLength());

            
            a.removeNextAttendee();
            a.removeNextAttendee();
            a.removeNextAttendee();

            System.out.println("________________");
            System.out.println(a.lineCount);
            for(Line ptr = a.headLine; ptr != null; ptr = ptr.getLineLink()) System.out.println(ptr.getTailPerson().getName() + " " + ptr.getLength());

            System.out.println("________________");
             a.addNewLines(3);
             System.out.println(a.lineCount);
            for(Line ptr = a.headLine; ptr != null; ptr = ptr.getLineLink()) System.out.println(ptr.getHeadPerson().getName() + " " + ptr.getLength());
            
            int[] removalIndex = {1,6};
            a.removeLines(removalIndex);
            System.out.println("________________");
            System.out.println(a.lineCount);
            for(Line ptr = a.headLine; ptr != null; ptr = ptr.getLineLink()) System.out.println(ptr.getHeadPerson().getName() + " " + ptr.getLength());
            
            
            int[] removalIndex2 = {1,2};
            a.removeLines(removalIndex2);
            System.out.println("________________");
            System.out.println(a.lineCount);
            for(Line ptr = a.headLine; ptr != null; ptr = ptr.getLineLink()) System.out.println(ptr.getHeadPerson().getName() + " " + ptr.getLength());

           // System.out.println(removalIndex.length);

            //a.removeNextAttendee();
        }
        catch(InvalidLineCountException e){System.out.println(e);}
        catch(TakenSeatException e){System.out.println(e);}
        catch(AllLinesEmptyException e){System.out.println(e);}
        catch(LineDoesNotExistException e){System.out.println(e);}
        catch(SingleLineRemovalException e){System.out.println(e);}
        //catch(AllLinesEmptyException e){}
        a.lineCheck();
        a.printTable();

    }


}
