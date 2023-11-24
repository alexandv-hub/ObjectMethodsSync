package objectMethodsSync;

public class JoinsOnThreads {

    public static void main(String[] args) {

        Foo4 fooObj = new Foo4();

        Thread a = new Thread("A") {
            @Override
            public void run() {
                fooObj.first();
            }
        };

        Thread b = new Thread("B") {
            @Override
            public void run() {
                try {
                    a.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                fooObj.second();
            }
        };

        Thread c = new Thread("C") {
            @Override
            public void run() {
                try {
                    a.join();
                    b.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                fooObj.third();
            }
        };

        c.start();
        a.start();
        b.start();
    }
}


class Foo4 {

    public void first() {
        System.out.print("first");
    }

    public void second() {
        System.out.print("second");
    }

    public void third() {
        System.out.print("third");
    }
}
