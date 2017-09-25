package com.enci.merchant;

import org.apache.log4j.Logger;
import org.junit.Test ;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.*;


public class testJDefault extends BaseTest {
    private final static Logger LOGGER = Logger.getLogger(testJDefault.class);

    @Test
    public void test1() {
        Integer[] b = {1, 2};

        List a = Arrays.asList(b);

        String c = "ccccca";

        System.out.println(TestKKt.getLastChar(c));

        System.out.println(a);

        System.out.println(TestKKt.joinToString(a));
    }

    @Test
    public void test2() {
        Person user = new Person("a", false);
        user.setMarry(true);
    }

    @Test
    public void test3() {
        List<Integer> a = new ArrayList<>();
        a.add(2);
        a.add(3);
        a.add(0, 1);
        Integer[] b = new Integer[a.size()];
        a.toArray(b);
        System.out.println(b[0]);
    }

    public static double max(double... a) {
        double max = Double.MIN_VALUE;
        for (Double d : a) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    @Test
    public void test4() {
        System.out.println(max(2.1, 3.1, 4.5, 3.3));
        System.out.println(max(new double[]{1.1, 2.3}));

    }

    @Test
    public void test5() {
        if (Arrays.asList(1, 2, 3).contains(3)) {
            System.out.println("ice 999");
        }
    }

    @Test
    public void test6() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Date a = new Date();
        String b = a.getClass().getName();
        Class<Date> c = (Class<Date>) Class.forName(b);
        Date d = c.newInstance();
        System.out.println(b);
        System.out.println(c == a.getClass());
        System.out.println(c == Date.class);
    }

    @Test
    public void test7() throws Exception {
        TestK.Employee e = new TestK.Employee("ly", 10);
        System.out.println(e.getName());
    }

    class TraceHandler implements InvocationHandler {
        private Object target;

        TraceHandler(Object target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.print(target);
            System.out.print("." + method.getName() + "(");
            if (args != null) {
                System.out.print(args[0]);
            }
            System.out.println(");");
            return method.invoke(target, args);
        }
    }

    InvocationHandler handler = (proxy, method, args) -> {
        System.out.print(222);
        System.out.print("." + method.getName() + "(");
        if (args != null) {
            System.out.print(args[0]);
        }
        System.out.println(");");
        return method.invoke(222, args);
    };

    @Test
    public void test9() {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = Proxy.newProxyInstance(
                    null, new Class[]{Comparable.class}, new TraceHandler(i + 1));
        }
        Integer key = new Random().nextInt(elements.length) + 1;
        int result = Arrays.binarySearch(elements, key);
        if (result >= 0) System.out.println(elements[result]);

    }

    @Test
    public void testBinary() {
        int[] ints = new int[1000];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i + 1;
        }
        System.out.println(ints[555]);
        Integer key = new Random().nextInt(ints.length) + 1;
        System.out.println(key);
        int result = Arrays.binarySearch(ints, key);
        LOGGER.info("日志阔以" + result);
    }

    class Pair<E> {
        public E first;
        public E second;

        public Pair(E first, E second) {
            this.first = first;
            this.second = second;
        }
    }

    public <T extends Comparable<T>> Pair<T> minmax(T... args) {
        return new Pair<>(Arrays.stream(args).max(Comparator.comparing(arg -> arg)).get()
                , Arrays.stream(args).min(Comparator.comparing(arg -> arg)).get());
    }

    @Test
    public void testT() {
        Pair a = minmax(2.1, 3.2, 3.4);
        System.out.println(a.first.toString() + a.second.toString());
    }

    @Test
    public void testT2() {
        Pair<String> a = new Pair<>("3", "3");
        Pair<Integer> b = new Pair<>(3, 3);
        if (a.getClass() == b.getClass()) {
            System.out.println("wtf");
        }
    }

    @SafeVarargs
    public static <T> void addAll(Collection<T> collection, T... ts) {
        for (T t : ts) {
            collection.add(t);
        }
        //        collection.addAll(Arrays.asList(ts));
    }

    @Test
    public void testVarargs() {
        Pair[] a = new Pair[1];
        List<Pair<String>> b = new ArrayList<>();
        //  Pair<String>[] d = new Pair<String>[1]; false
        Collection<Pair<String>> collection = new ArrayList<Pair<String>>() {
            private static final long serialVersionUID = 4030367313679797324L;

            {
                add(new Pair<>("1", "1"));
            }
        };
        addAll(collection, new Pair<>("1", "1"), new Pair<>("2", "2"));
        addAll(collection, a);
        addAll(collection, (Pair[]) b.toArray());
    }

    @Test
    public void testTClass() throws Exception {
        String a = "1";
    }

//    class Manager extends TestK.Employee {
//        private int sex;
//        public Manager(String name, double salary,int sex) {
//            super(name, salary);
//            this.sex = sex;
//        }
//    }


    @Test
    public void testWildCard() {
        Pair<TestK.Manager> a = new Pair<>(new TestK.Manager("1", 1.0, 1), new TestK.Manager("2", 2.0, 1));

        Pair<TestK.Employee> b = new Pair<>(new TestK.Employee("1", 1.0), new TestK.Employee("2", 2.0));

        Pair<? extends TestK.Employee> c = b;
//        c.first = b.second; false
        TestK.Employee e = c.first;
        Pair<? super TestK.Manager> d = a;
        d.first = a.second;
//      TestK.Manager f = d.first; false
    }

    @Test
    public void testSubList() {
        List a = Arrays.asList(1, 2, 3);
        List b = a.subList(0, 2);
        for (Object o : b) {
            System.out.println(o);
        }
    }

    @Test
    public void testBig() {
        BigDecimal bigDecimal = new BigDecimal(123123.115);
        BigDecimal bigDecimal2 = new BigDecimal(0);
        BigDecimal bigDecimal3 = new BigDecimal(0.1);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal2);
        System.out.println(bigDecimal3);
        System.out.println(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bigDecimal3.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testLambda() {
        Comparator<Integer> comparator = (o1, o2) -> o1.compareTo(o2);
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        Comparator<Integer> comparator2 = Comparator.naturalOrder();
    }

    @Test
    public void testEquals() {
        int a = 2, b = 1;
        if(a != b){
            System.out.println("666");

        }
    }

    //开始学习多线程咯- -
    @Test
    public void testMultithreaded1(){
        Thread t1 = new Thread(() -> {
            System.out.println("000");
        });
        Thread t2 = new Thread(() ->
                System.out.println("111")
        );
        Thread t3 = new Thread(() ->
                System.out.println("222")
        );
        t3.setPriority(6);
        t2.setPriority(4);
        t1.start();
        t1.interrupt();
        System.out.println(t1.isInterrupted());
        t2.start();
        t3.start();
    }


}

