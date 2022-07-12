package org.example;

/**
 * Two methods, to test refactoring
 * (example: will both methods pass??)
 * (example: did we break anything??)
 */

public class FizzBuzz {

    public static String compute(int x) {
        StringBuilder result = new StringBuilder();

        if (x % 3 == 0) result.append("fizz");
        if (x % 5 == 0) result.append("buzz");
        if (result.isEmpty()) result.append(x);

        return result.toString();
    }

//    public static String compute(int i) {
//        if ((i % 3 == 0) && (i % 5 == 0)) return "fizzbuzz";
//        else if (i % 3 == 0) return "fizz";
//        else if (i % 5 == 0) return "buzz";
//
//        else return String.valueOf(i);
//    }

}