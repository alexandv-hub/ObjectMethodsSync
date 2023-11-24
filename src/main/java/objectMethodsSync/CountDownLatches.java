package objectMethodsSync;

import java.util.concurrent.CountDownLatch;

public class CountDownLatches {

    public static void main(String[] args) {

        Foo3 fooObj = new Foo3();

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


class Foo3 {
    private final CountDownLatch latchFirst = new CountDownLatch(1);
    private final CountDownLatch latchSecond = new CountDownLatch(1);

    public void first() {
        System.out.print("first");
        latchFirst.countDown();
    }

    public void second() {
        try {
            latchFirst.await();
            System.out.print("second");
            latchSecond.countDown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void third() {
        try {
            latchSecond.await();
            System.out.print("third");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
