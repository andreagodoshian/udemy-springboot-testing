package org.example;

import lombok.Getter;

import java.util.*;
import java.util.stream.*;

@Getter
public class DemoUtils {

    private String academy = "Yaroslavl State Technical University";
    private String academyDuplicate = academy;
    private String [] firstThreeLetters = {"A", "B", "C"};
    private List<String> academyAsList = Stream.of(academy.split(" "))
            .collect(Collectors.toList());

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public Object checkNull(Object obj) {
        if (obj != null) return obj;
        else return null;
    }

    public Boolean isGreater(int n1, int n2) {
        return n1 > n2;
    }

    public String throwExceptionMethod(int a) throws Exception {
        if (a < 0) throw new Exception("Can't be less than zero!");
        return "Value is greater than (or equal to) zero... nice :)";
    }

    public void checkTimeout() throws InterruptedException {
        System.out.println("Andria needs a god-damn nap... goodnight");
        Thread.sleep(2000);
        System.out.println("Back to work (Andria deserves better)");
    }
}