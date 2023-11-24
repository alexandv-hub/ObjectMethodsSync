package objectMethodsSync;

public class WaitAndNotify {

    public static void main(String[] args) {

        Foo5 fooObj = new Foo5();

        Thread a = new Thread("A") {
            @Override
            public void run() {
                fooObj.first();
            }
        };

        Thread b = new Thread("B") {
            @Override
            public void run() {
                fooObj.second();
            }
        };

        Thread c = new Thread("C") {
            @Override
            public void run() {
                fooObj.third();
            }
        };

        c.start();
        a.start();
        b.start();
    }
}


class Foo5 {
    private boolean isFirstCompleted = false;
    private boolean isSecondCompleted = false;

    public synchronized void first() {
        System.out.print("first");
        isFirstCompleted = true;
        notifyAll();
    }

    public synchronized void second() {
        try {
            while(!isFirstCompleted) {
                wait();
            }
            System.out.print("second");
            isSecondCompleted = true;
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void third() {
        try {
            while(!isSecondCompleted) {
                wait();
            }
            System.out.print("third");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
