# Objects communicating across threads

The idea is to pre-fectch stuff using worker thread and only display results when asked for.
The example has the following context: in client side programming, it's common to show the user a list of their available printers. However, the JDK's tool for fetching this information can have poor performance - sometimes resulting in delays of several seconds or more. So, one option is to fetch the list of available printers early upon startup of the application, in a separate worker thread. Later, when the user wishes to print something, the list of available printers will already be pre-fetched.

**Sample O/P**


> Launching application.
> Worker thread started...
> Finished fetching list of printers. Nanos: 926796146
> Num printers found:0
> Seeing this many printers:0 