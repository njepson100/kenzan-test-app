# kenzan-test-app
Fizz Buzz application for Kenzan

How to run

clone  https://github.com/njepson100/kenzan-test-app

In the project directory run mvn clean package
then run mvn exec:java
open your browser and go to http://localhost:8080/fizzbuzzapp/fizzbuzz/100
you can enter any number instead of 100, however numbers over 100000 will take some time
If you enter a value after the last slash which is not an integer, you'll get a message that you must enter an integer

Here are some design notes

For this project I chose to use the builder pattern for the NumericSortSpecClass.  This helps with any increasing complexity for the sorters, and allows the SortMediator class to not have much knowledge of how the object builds.  All it needs to know is what is required to build it.  Since sorting algorithms can get complex I thought this was handy

My solution also uses an Identity Map pattern when loading the sorters.  Since there could be any number of them and they are loaded via an xml config file, it makes sense to load them once since they are stateless and only handle processing based on the rules loaded from the config file.

This project is also run with a restful api since this is very lightweight, keeps the communication down to simple http calls.  

I do have concerns over sorts where the numeric range is over 100000.  The program slows down significantly, and I think some parallel processing would go a long way to fixing this.

Also there is probably a higher level sorter than the NumericRangeSorter so that the Map of sorters could probably comprise a large variety of sorters.

As I mentioned there are issues when the range of numbers to be sorted would be over 100000.  I would also add aspectj to the project so we can have an efficient way of measuring what is the most intensive areas of code.
