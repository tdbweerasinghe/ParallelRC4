package Step1;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
public class TestParallelExecution {
    private static long endTime1;
    private static long startTime1;
           
    public static void main(String[] args) throws IOException {
        System.out.println("Multithreading started...");

        ExecutorService executor = Executors.newCachedThreadPool();
        
        startTime1 = System.nanoTime();                
        for (int i= 0; i<=85192045  ; i=i+10000000)
        {            
            executor.execute(new FileChunkAndEncrypt(i));
        }
        endTime1 = System.nanoTime();
        
        System.out.println("Starting shutdown...");
        executor.shutdown();
                
        //Calculate the elapsed time in milliseconds
        long timeElapsed1 = CalculateTimeDifference.GetTimeElapsed(startTime1, endTime1);
 
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted...");
        }
 
        System.out.println("All executed!");
        System.out.println("Time Elapsed: " + timeElapsed1 + "ns");
 
    }
}
