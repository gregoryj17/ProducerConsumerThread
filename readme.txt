This program takes in a length of time, a number of producer threads, and a number of consumer threads as command line arguments to perform a producer/consumer thread simulation.
The first argument should be the length of time to run (in seconds).
The second argument should be the number of producer threads to use.
The third argument should be the number of consumer threads to use.
So, calling this program from the command line looks like this: java ProducerConsumer <length> <producers> <consumers>
If the command line arguments are not given properly, the program will default to 20 seconds with 5 producers and 1 consumer.

The program will output to stdout the given settings and then every number added to or removed from the buffer.
The program will run until every producer and consumer thread has performed its allotted action 100 times each, or until the time limit is reached, whichever comes first.
Each thread will only produce or consume 100 numbers, as specified in the assignment.
All numbers will be from 0 to MAX_INTEGER, inclusive.