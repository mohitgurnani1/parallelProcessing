import com.sun.jmx.remote.internal.ArrayQueue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Main_ExecutorService {

   private static final boolean toggleIsParallel=true;
    private static final boolean toggleIsList=true;


    public static void main(String[] args) {
        Long start=System.currentTimeMillis();
        //System.out.println(Runtime.getRuntime().availableProcessors());
        if(toggleIsParallel) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
           // ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

            List<Future<Response>> resultList2 = new ArrayList<>();
            Queue<Future<Response>> resultList=new LinkedList<>();

            for (int i = 0; i < 100; i++) {
                PingPoller pingPoller = new PingPoller();
                pingPoller.setIpAddress("www.google.com",i);
                Future<Response> result = executor.submit(pingPoller);
                resultList.add(result);
                resultList2.add(result);
            }

            if(toggleIsList) {
                for (Future<Response> future : resultList2) {
                    try {
                        System.out.println("Future result is - " + future.get().pingResponse + " -index- " + future.get().index + "; And Task done is " + future.isDone());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                while (!resultList.isEmpty()) {
                    Future<Response> future = resultList.peek();
                    if (future.isDone()) {
                        try {
                            System.out.println("Future result is - " + future.get().pingResponse + " -index- " + future.get().index + "; And Task done is " + future.isDone());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        resultList.poll();
                    } else {
                        Future<Response> booleanFuture = resultList.poll();
                        resultList.offer(booleanFuture);
                    }
                }
            }
             //shut down the executor service now
            executor.shutdown();
        }
        else
        {

            String ipAddress="www.google.com";
            for (int i = 0; i < 100; i++) {
                Boolean pingResult = false;
                try
                {
                    InetAddress inet = InetAddress.getByName(ipAddress);
                    System.out.println("Sending Ping Request to " + ipAddress);
                    pingResult = inet.isReachable(5000); //Timeout = 5000 milli seconds
                    System.out.println("Future result is - " + " - " + pingResult + "; And Task is done ");

                }
                catch (UnknownHostException e)
                {
                    System.err.println("Host does not exists");
                }
                catch (IOException e)
                {
                    System.err.println("Error in reaching the Host");
                }
            }
        }


        Long finish=System.currentTimeMillis();
        System.out.println((finish-start));
    }

}
