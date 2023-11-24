package objectMethodsSync;

import java.util.concurrent.Phaser;

public class PhaserUse {

    public static void main(String[] args) {
        Foo6 fooObj = new Foo6();

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

class Foo6 {
    private final Phaser phaserFirstToSecond = new Phaser(1);
    private final Phaser phaserSecondToThird = new Phaser(1);

    public void first() {
        System.out.print("first");
        phaserFirstToSecond.arrive();
    }

    public void second() {
        phaserFirstToSecond.awaitAdvance(0);
        System.out.print("second");
        phaserSecondToThird.arrive();

    }

    public void third() {
        phaserSecondToThird.awaitAdvance(0);
        System.out.print("third");
    }
}
