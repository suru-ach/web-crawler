package core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

class Adder extends Thread {
    int n;
    int sum;
    Adder(int num) {
        sum = n = num;
    }
    public void run() {
        for(int i=0;i<n;i++) {
            sum += i;
        }
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sum is " + sum);
    }
}

class Summer implements Callable<String> {

    @Override
    public String call() {
        return "Hello from " + Thread.currentThread().getName();
    }
}

public class ThreadPoolBlockingQueue {
    // Executes the test
    final private int max_threads = 5;
    final private int max_tasks = 10;

    ThreadPoolBlockingQueue() {
        // generate threads
        Adder[] threads = new Adder[max_tasks];

        for(int i=0;i<max_tasks;i++)
            threads[i] = new Adder(5);

        ExecutorService executorService = Executors.newFixedThreadPool(max_threads);

        for(Adder thread: threads) executorService.submit(thread);

        executorService.shutdown();

        ExecutorService executorService1 = Executors.newFixedThreadPool(max_threads);

        List<Callable<String>> summers = new ArrayList<>(max_tasks);
        for(int i=0;i<max_tasks;i++) {
            summers.add(new Summer());
        }

        try {
            List<Future<String>> futures = executorService1.invokeAll(summers);
            for(Future<String> future: futures) {
                System.out.println(future.get());
            }
        } catch(InterruptedException exc) {
            System.out.println(exc);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService1.shutdown();

    }
}
