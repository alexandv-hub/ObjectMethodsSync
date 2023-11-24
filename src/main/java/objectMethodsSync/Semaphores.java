package objectMethodsSync;

import java.util.concurrent.Semaphore;

public class Semaphores {

    public static void main(String[] args) {

        Foo2 fooObj = new Foo2();

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


class Foo2 {
    private final Semaphore secondSemaphore = new Semaphore(0);
    private final Semaphore thirdSemaphore = new Semaphore(0);

    public void first() {
        System.out.print("first");
        secondSemaphore.release();
    }

    public void second() {
        try {
            secondSemaphore.acquire();
            System.out.print("second");
            thirdSemaphore.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void third() {
        try {
            thirdSemaphore.acquire();
            System.out.print("third");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
