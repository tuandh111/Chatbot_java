package com.tuandhpc05076.demo;

import java.util.Arrays;
import java.util.List;

public class demoStream {
    private String name;
    private int age;

    public demoStream(String name, int age) {
        this.name = name;
        this.age = age;
    }



    public static void main(String[] args) {
        List<demoStream> students = Arrays.asList(
                new demoStream("Alice", 20),
                new demoStream("Bob", 21),
                new demoStream("Charlie", 19)
        );
        students.stream().filter(student -> student.age > 20).filter(student -> student.name.equalsIgnoreCase("Bob")).forEach(student-> {System.out.println(student.name);

        });
    }
}



