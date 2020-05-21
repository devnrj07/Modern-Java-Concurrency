# Perform N tasks in parallel
There are some cases in which performing tasks in parallel can be useful. This option is especially attractive when:

 * the tasks are independent of each other, but do essentially the same thing
 * the tasks are limited by slow operations - typically, network access or disk access 

A good example of this comes from web site administration. Administrators need to monitor a set of N web sites, so they might send a 'ping' of some sort to each site, to see if a healthy response is returned in each case. Note that these pings fit the above criteria - each ping is independent of the others, and yet is essentially the same task. 