package fetch_list_of_printers;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;

public class Solution{
    public static void main(String[] args) {
    log("Launching application.");
    PrinterListDAO.init(); //launches worker thread
    
    //construct the user interface, other start tasks, etc...
    
    //use the list of printers later in processing
    List<PrintService> printers = new PrinterListDAO().getPrinters();
    log("Seeing this many printers:" + printers.size());
  }
  
  private static void log(String msg){
    System.out.println(msg);
  }
    }



final class PrinterListDAO{
    /** Used to communicate between threads. */
  static private CountDownLatch latch;
  
  static private PrinterListWorker worker;
  
  private static void log(String msg){
    System.out.println(msg);
  }

  /** This must be called early upon startup. */
  static void init(){
    latch = new CountDownLatch(1);
    worker = new PrinterListWorker(latch);
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(worker);
    executor.shutdown(); //reclaims resources
  }

  /** Return the list of printers that can print PDFs (double-sided, portrait).*/
  List<PrintService> getPrinters(){
    try {
      //block until the worker has set the latch to 0:
      latch.await();
    }
    catch (InterruptedException ex){
      log(ex.toString());
      Thread.currentThread().interrupt();
    }
    return worker.getPrinterList();
  }
}



    /** Look up the list of printers. */
final class PrinterListWorker implements Runnable {
    
    /** Used to communicate between threads. */
  private CountDownLatch latch;

  private List<PrintService> printServices;
  
  private static void log(String msg){
    System.out.println(msg);
  }
  
  PrinterListWorker(CountDownLatch latch){
        this.latch = latch;
    }

    @Override public void run() {
        log("Worker thread started...");
        long start = System.nanoTime();
    
        //double-sided, portrait, for PDF files.
        PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
        attrs.add(Sides.DUPLEX);
        attrs.add(OrientationRequested.PORTRAIT);
        //this can take several seconds in some environments:
        printServices = Arrays.asList(
          PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PDF, attrs)
        );
        
        long end = System.nanoTime();
        log("Finished fetching list of printers. Nanos: " + (end-start));
        log("Num printers found:" + printServices.size());
        latch.countDown();
      }
    
      /** Return an unmodifiable list of printers. */
      List<PrintService> getPrinterList(){
        return Collections.unmodifiableList(printServices);
      }  
}

