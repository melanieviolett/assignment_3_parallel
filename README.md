## Assignment 3 parallel processing

## To compile and run my solution to problem 2, run the following commands in the terminal:

### javac Problem2.java

### java Problem2.java

##### Correctness
The program is correct in that it has synchronization of shared data structures, namely the list of temperature readings. Each temperature sensor, represented by a separate thread, independently generates temperature readings and stores them in the shared list. I use synchronized blocks to prevent data races and make sure that multiple threads can safely access and edit the shared list without any conflicts. The algorithm for compiling hourly reports processes the temperature readings accurately. It divides the list of readings into hourly segments, and the program simulates 3 hours worth of reports.

##### Efficiency
To speak on the efficiency of my solution, it uses fixed thread pool, and as a result maximizes CPU utilization while minimizing the overhead associated with creating threads. The algorithm for making reports operates with a time complexity that is linear with respect to the number of temperature readings.

##### Experimental evaluation and progress guarantee
Through experimentation, the program reliably generates hourly temperature reports within the specified time frame. The use of a random seed based on system time makes sure there are diverse sets of temperature readings in each execution. The lack of blocking operations while the report is generated guarantees steady progress.
