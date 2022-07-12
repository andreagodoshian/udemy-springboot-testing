package org.example;

public class App {
    public static void main( String[] args ) {

        for (int i=1; i<=100; i++) {
            System.out.println(i + ": " + FizzBuzz.compute(i));
        }

    }
}
