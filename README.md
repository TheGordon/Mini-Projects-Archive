# Mini Projects Archive

A collection of various side projects to practice several data structures
***
**Statistical Data Manipulator:** A simple tool that manipulates a biostatistical data file with multiple functionalities.

**Linked List Security Lines Manager**: A system that simulates a queue for multiple lines.  
The system itself is a linked list made of lines that are also linked lists made of the "Person" class. This system provides an interactive UI that allows the user to add 'Persons' to the queue, retrieve 'Persons' from the queue, add more lines to the queue, and remove lines from the queue.  
Each Person has a seat number and the system itself manages each line to be in ascending order of each Person's seat number. It also manages itself to have approximately equal line sizes for all lines at all times.

**Library Stack Manager**: A system that manages a book repository which is 10 book shelves that each stores books depending on the first digit of their ISBN value. Books can be added, removed, sorted, checked in, and checked out. It also manages a return stack, which is a linked list stack that keeps track of return logs. Each time a book is returned, it's pushed to the return stack. If a book is returned late/past its due date, then all entries from the return stack gets popped out and the books get checked back in.
