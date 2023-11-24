package objectMethodsSync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {

    public static void main(String[] args) {

        Foo fooObj = new Foo();

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


class Foo {
    private final Lock lock = new ReentrantLock();
    private final Condition methodFirstDone = lock.newCondition();
    private final Condition methodSecondDone = lock.newCondition();
    private boolean isMethodFirstDone = false;
    private boolean isMethodSecondDone = false;

    public void first() {
        lock.lock();
        try {
            System.out.print("first");
            isMethodFirstDone = true;
            methodFirstDone.signal();
        } finally {
            lock.unlock();
        }
    }

    public void second() {
        lock.lock();
        try {
            while (!isMethodFirstDone) {
                methodFirstDone.await();
            }
            System.out.print("second");
            isMethodSecondDone = true;
            methodSecondDone.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }  finally {
            lock.unlock();
        }
    }

    public void third() {
        lock.lock();
        try {
            while (!isMethodSecondDone) {
                methodSecondDone.await();
            }
            System.out.print("third");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
