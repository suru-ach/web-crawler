package core;
// Similar to Producer Consumer problem

// Shared resource

class PrintModule {
    private int n;
    private boolean validFlag = false;

    synchronized void set(int n) {
        while(validFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        validFlag = true;
        System.out.println("Set : "+this.n);
        this.n = n;
        notify();
    }

    synchronized int get() {
        while(!validFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Get : "+this.n);
        validFlag = false;
        notify();
        return this.n;
    }
}

class Producer extends Thread {
    PrintModule pt;

    Producer(PrintModule pt) {
        this.pt = pt;
        new Thread(this).start();
    }

    public void run() {
        int cnt = 1;
        while(true) {
            pt.set(cnt++);
        }
    }

}

class Consumer extends Thread {
    PrintModule pt;

    Consumer(PrintModule pt) {
        this.pt = pt;
        new Thread(this).start();
    }

    public void run() {
        while(true) {
            pt.get();
        }
    }

}

public class BoundedBuffer {
    BoundedBuffer() {
        PrintModule pt = new PrintModule();
        Producer producer = new Producer(pt);
        Consumer consumer = new Consumer(pt);
    }
}
