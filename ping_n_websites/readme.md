# Perform N tasks in parallel
There are some cases in which performing tasks in parallel can be useful. This option is especially attractive when:

 * the tasks are independent of each other, but do essentially the same thing
 * the tasks are limited by slow operations - typically, network access or disk access 

A good example of this comes from web site administration. Administrators need to monitor a set of N web sites, so they might send a 'ping' of some sort to each site, to see if a healthy response is returned in each case. Note that these pings fit the above criteria - each ping is independent of the others, and yet is essentially the same task. 

**Sample O/P**

> Parallel execution with instant reporting on completion
Result : true 51ms http://www.google.ca/
Result : true 51ms http://www.date4j.net
Result : true 51ms http://www.web4j.com
Result : true 51ms http://www.youtube.com/
Parallel execution with reporting on completion of all at the end
Result : true 0ms http://www.youtube.com/
Result : true 1ms http://www.google.ca/
Result : true 3ms http://www.date4j.net
Result : true 0ms http://www.web4j.com
serial or sequential report one by one
Result : true 0ms http://www.youtube.com/
Result : true 0ms http://www.google.ca/
Result : true 1ms http://www.date4j.net
Result : true 0ms http://www.web4j.com
Done.