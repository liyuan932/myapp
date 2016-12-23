package com.mycompany.myapp.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author wb-liyuan.j
 * @date 2016-12-09
 */
public class Test {
    interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    /**
     * 接口的默认方法
     */
    public static void test1() {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };
        formula.calculate(100);     // 100.0
        formula.sqrt(16);           // 4.0
    }

    /**
     * Lambda 表达式
     */
    public static void test2(){
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        Collections.sort(names, (a, b) -> b.compareTo(a));
    }

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    public static void test3(){
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123
    }


    public static void test4(){
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        String something = "hello Java";
        Converter<String, Boolean> converter2 = something::startsWith;
        Boolean converted2 = converter2.convert("Java");
        System.out.println(converted2);    // "J"
    }


    class Person {
        String firstName;
        String lastName;
        Person() {}
        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }

    public  void test5(){
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();

        List<Order> orderList1 = new ArrayList<Order>();
        for (int i = 0; i < 6; i++){
            Order temp = new Order(String.valueOf(i), "man", 98 + i);
            orderList1.add(temp);
        }
        Predicate<Order> filter1 = Predicate.isEqual(orderList1.get(2));
        Predicate<Order> filter2 = order -> order.getAmount() > 99;
        Predicate<Order> filter3 = o -> o.getCreatedBy().equals("man");
        orderList1.removeIf(filter1.and(filter2).and(filter3));
        System.out.println(orderList1.size());   //结果为5

    }

    static class Order{
        private String index;
       private int amount;
      private String createdBy;

        public Order(String index, String createdBy, int amount) {
            this.index = index;
            this.amount = amount;
            this.createdBy = createdBy;
        }

        public String getIndex() {
            return index;
        }

        public int getAmount() {
            return amount;
        }

        public String getCreatedBy() {
            return createdBy;
        }
    }

    static class Lambda4 {
        static int outerStaticNum;
        int outerNum;
        void testScopes() {
            Converter<Integer, String> stringConverter1 = (from) -> {
                outerNum = 23;
                return String.valueOf(from);
            };
            Converter<Integer, String> stringConverter2 = (from) -> {
                outerStaticNum = 72;
                return String.valueOf(from);
            };
        }
    }
}

