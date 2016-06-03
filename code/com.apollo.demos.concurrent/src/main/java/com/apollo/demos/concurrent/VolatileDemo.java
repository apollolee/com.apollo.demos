/*
 * 此代码创建于 2013-2-17 下午03:10:08。
 */
package com.apollo.demos.concurrent;

public class VolatileDemo {

    public static void main(String[] args) {
        Visibility.test();
        //Synchronized.test();
        //Reordering.test();
    }

}

/**
 * 可见性，说明volatile起作用的场景。
 */
@SuppressWarnings("all")
class Visibility {

    /**
     * 成员变量m_booleanValue使用volatile和不使用volatile会有明显区别的，本程序需要多试几次，就能知道两者之间的区别的。
     */
    static void test() {
        final Visibility visibility = new Visibility();

        new Thread(new Runnable() { //创建并启动一个无限交换value真假值的线程。

            @Override
            public void run() {
                System.out.println("交换线程启动。");

                while (true) {
                    visibility.swap();
                    Thread.yield(); //这一句防止程序死循环太占CPU。
                }
            }

        }).start();

        int count = 0; //计数器。
        while (true) { //无限创建验证线程，如果需要的话。
            final int current = count++;

            Thread test = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("验证线程启动。第" + current + "次");

                    while (visibility.isEqual()) { //当value自己和自己相等时退出，结束线程。
                        Thread.yield(); //这一句防止程序死循环太占CPU。
                    }
                }

            });

            test.start(); //启动验证线程。

            try {
                test.join(); //等待验证线程结束。

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            Thread.yield(); //这一句防止程序死循环太占CPU。
        }
    }

    /**
     * 不加volatile修饰时，打印会最终停在某次的验证线程处，也就是验证线程必有一次会一直出现m_value == m_value，尽管此时value值在频繁的交换真假值。
     * 加volatile修饰时则不会，验证线程会一直循环打印下去。
     */
    volatile boolean m_value;

    /**
     * 判断value是否与自己相等，看上去有些奇怪。
     */
    boolean isEqual() {
        /*
         * 这条语句中存在两个对value的use操作（use操作是JMM为工作内存和主内存间同步定义的8个操作之一）。
         * 如果value是非volatile时，JVM不保证每个use操作前都会存在load操作。即不保证每个use动作能使用最新的主内存中的value。
         * 如果value是volatile时，JVM会保证每个use操作前都会存在load操作。即保证每个use动作能使用最新的主内存中的value。
         * 结论：
         *     1.value是非volatile：当线程A中value频繁变化时，线程B中的m_value == m_value可能一直返回true，因为有可能value一直没有从主内存中更新。
         *     2.value是volatile：当线程A中value频繁变化时，线程B中的m_value == m_value不可能一直返回true，因为这两个value都会如实反馈前后2个时刻主内存中value的值。
         */
        return m_value == m_value;
    }

    /**
     * 交换value的真假值。
     */
    void swap() {
        m_value = !m_value; //这里同样存在isEqual()中的情况，只不过把读操作换成写操作，原理类似。
    }

}

/**
 * 同步，说明volatile不起作用的场景，无法替代同步机制。
 */
class Synchronized {

    /**
     * 反复验证ab互换场景是否存在。
     */
    static void test() {
        while (true) { //无限验证ab互换的测试。
            final Synchronized sync = new Synchronized();

            final Thread ab = new Thread(new Runnable() { //创建a=b线程。

                @Override
                public void run() {
                    sync.ab();
                }

            });

            final Thread ba = new Thread(new Runnable() { //创建b=a线程。

                @Override
                public void run() {
                    sync.ba();
                }

            });

            ab.start();
            ba.start();

            new Thread(new Runnable() { //创建检查ab互换的线程。

                @Override
                public void run() {
                    try {
                        ab.join();
                        ba.join();

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (sync.m_a != sync.m_b) { //因为前面的join，这里不会有关于a或b的数据竞争，尽管是在多个线程中访问。
                        //例如：Thread-52829: a=2 b=1
                        System.out.println(Thread.currentThread().getName() + ": a=" + sync.m_a + " b=" + sync.m_b);
                        System.exit(0);
                    }
                }

            }).start();

            Thread.yield();
        }
    }

    /**
     * 这里a,b无论是否使用volatile修饰，都会出现 a、b值交换。因为这里的竞态条件涉及a和b两个变量，volatile在这里无法保证原子性。
     * 这里只有使用同步才能避免出现ab互换的情况。比如对ab()和ba()同时添加synchronized修饰。
     */
    int m_a = 1, m_b = 2;

    /**
     * a=b。
     */
    void ab() {
        m_a = m_b;
    }

    /**
     * b=a。
     */
    void ba() {
        m_b = m_a;
    }

}

/**
 * 重排序，一个关于重排序的错误验证，反复启动多次才能出现。
 */
class Reordering {

    /**
     * 反复验证x == 0, y == 0是否存在。
     */
    static void test() {
        while (true) { //无限验证x == 0, y == 0的测试。
            final Reordering reordering = new Reordering();

            final Thread a1xb = new Thread(new Runnable() { //创建a=1;x=b线程。

                @Override
                public void run() {
                    reordering.a1xb();
                }

            });

            final Thread b1ya = new Thread(new Runnable() { //创建b=1;y=a线程。

                @Override
                public void run() {
                    reordering.b1ya();
                }

            });

            a1xb.start();
            b1ya.start();

            new Thread(new Runnable() { //创建检查x == 0, y == 0的线程。

                @Override
                public void run() {
                    try {
                        a1xb.join();
                        b1ya.join();

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (reordering.m_x == 0 && reordering.m_y == 0) { //因为前面的join，这里不会有关于x或y的数据竞争，尽管是在多个线程中访问。
                        //例如：Thread-70898: x=0 y=0
                        System.out.println(Thread.currentThread().getName() + ": x=" + reordering.m_x + " y=" + reordering.m_y);
                        System.exit(0);
                    }
                }

            }).start();

            Thread.yield();
        }
    }

    /**
     * 这个例子是《Java Concurrency in Practice》P279的例子，是为了证明重排序的存在，当出现x=0 y=0的结果时，即可证明。
     * 我认为这个例子还是存在问题的，因为从JMM的描述来看，x=0 y=0的结果理解为“工作内存同主内存的同步延迟”更为合理。
     */
    int m_a = 0, m_b = 0;

    int m_x = 0, m_y = 0;

    /**
     * a=1;x=b。
     */
    void a1xb() {
        m_a = 1;
        m_x = m_b;
    }

    /**
     * b=1;y=a。
     */
    void b1ya() {
        m_b = 1;
        m_y = m_a;
    }

}
