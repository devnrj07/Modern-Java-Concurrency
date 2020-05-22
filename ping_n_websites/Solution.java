import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/*this example actually has 3 different implementations:

    execute tasks in parallel, and report the result of each task as soon as it completes.
    execute tasks in parallel, but report the results of all tasks only at the end, after they have all completed.
    execute tasks serially, one after the other, and all within a single thread; report each task as it completes. 
*/

public class Solution {
    public static void main(String[] args) {

        Solution solution = new Solution();
        try{
            log("Parallel execution with instant reporting on completion");
            solution.pingAndReportEachInstantly();

            log("Parallel execution with reporting on completion of all at the end");
            solution.pingAndReportAlltogether();

            log("serial or sequential report one by one");
            solution.pingAndReportSequentially();
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
          }
          catch(ExecutionException ex){
            log("Problem executing worker: " + ex.getCause());
          }
          catch(MalformedURLException ex){
            log("Bad URL: " + ex.getCause());
          }
          log("Done.");
    }

    /** 
   Check N sites, in parallel, using up to 4 threads. 
   Report the result of each 'ping' as it comes in. 
   
  */
  
    void pingAndReportEachInstantly() throws InterruptedException, ExecutionException{
        int numOfThreads = URLs.size() > 5 ? 5: URLs.size();
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        CompletionService<PingResult> compService = new ExecutorCompletionService<>(executor);

        for(String url : URLs){
            compService.submit(new Task(url));
        }
        for(String url : URLs){
            Future<PingResult> future = compService.take();
            log(future.get());
        }
        executor.shutdown();

    }

    /** 
   Check N sites, in parallel, using up to 4 threads. 
   Report the results only when all have completed. 
  */ 
   void pingAndReportAlltogether() throws InterruptedException, ExecutionException{
       Collection<Callable<PingResult>> tasks = new ArrayList<>();
       for(String url : URLs){
           tasks.add(new Task(url));
       }
       
       int numThreads = URLs.size() > 5 ? 5 : URLs.size(); //max 4 threads
       ExecutorService executor = Executors.newFixedThreadPool(numThreads);
       List<Future<PingResult>> futures = executor.invokeAll(tasks);
       for(Future<PingResult> future : futures){
           PingResult pingResult = future.get();
           log(pingResult);
       }
      executor.shutdown(); 
   }




    /**
   Check N sites, but sequentially, not in parallel. 
   Does not use multiple threads at all. 
  */
  void pingAndReportSequentially() throws MalformedURLException {
    for(String url : URLs){
        Task task = new Task(url);
      PingResult pingResult = task.pingAndReportStatus(url);
      log(pingResult);
    }
  }


    private static final List<String> URLs = Arrays.asList("http://www.youtube.com/", "http://www.google.ca/","http://www.date4j.net", "http://www.web4j.com");

    private static void log(Object msg) {
        System.out.println(Objects.toString(msg));
    }

    private final class Task implements Callable<PingResult> {

        private final String url;

        Task(String url) {
            this.url = url;
        }

        @Override
        public PingResult call() throws Exception {
            return pingAndReportStatus(url);
        }

        private PingResult pingAndReportStatus(String url) throws MalformedURLException{
            PingResult pingResult = new PingResult();
            pingResult.URL = url;
            long start = System.currentTimeMillis();
            URL newUrl = new URL(url);
            try{    
                URLConnection connection = newUrl.openConnection();
                // connection successful hence ping passed
                //log("output "+connection.getInputStream().read());
                pingResult.SUCCESS = true;
                long end = System.currentTimeMillis();
                pingResult.TIMING = end - start;
            }catch(IOException  ex){
                //log or something
            }
        return pingResult;
        }
    }

    private static final class PingResult {
        String URL;
        Boolean SUCCESS;
        Long TIMING;

        @Override
        public String toString() {
            return "Result : " + SUCCESS + " " + TIMING + "ms" + " " + URL;
        }
    }
}