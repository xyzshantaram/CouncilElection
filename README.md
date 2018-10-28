# CouncilElection

This is the election suite that I created for my school's student council election.

FileIO.java is a common class file across all the programs.
Since *I* wrote it, I highly do not recommend you use it in your own programs. [IT DOES, HOWEVER, WORK.]

The principle on which FileIO.java operates is to load a file into an ArrayList, then perform all operations on this ArrayList, then dump the file's contents back into the file.

DBCreator is a program to create a database in a stripped down, XML-like language. It has a simple structure, tho it does get hard to read, and the structure is explained in a comment at the starting of the supplied test file.

candidateData is a data storage class for storing candidate data. It maintains a record of candidate name, the number of votes, the position of that candidate, and the serial number of the candidate for a specific position.

electionPrg is the main election program. It reads from the file, conducts the election by dynamically creating a ton of candidateData objects and a form to accept votes, then dumps everything back into the file.

Results Display is a very simple program to parse the election file, intended for use after the election, and display the votes from each machine. Before you ask why a networked approach / external libraries were not used, it was simply a result of the school's limited computer infrastructure.
