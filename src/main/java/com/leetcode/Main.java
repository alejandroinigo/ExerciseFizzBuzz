package com.leetcode;

import java.util.function.IntConsumer;

public class Main {

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);

        new Thread(() -> {
            threadPrintFizz(fizzBuzz);
        }).start();
        new Thread(() -> {
            threadPrintBuzz(fizzBuzz);
        }).start();
        new Thread(() -> {
            threadPrintFizzBuzz(fizzBuzz);
        }).start();
        new Thread(() -> {
            threadPrintNumber(fizzBuzz);
        }).start();
    }

    private static void threadPrintFizz(FizzBuzz fizzBuzz) {
        Runnable printFizz = () -> System.out.println("fizz");
        try {
            fizzBuzz.fizz(printFizz);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            fizzBuzz.stop();
            System.out.println("Thread threadPrintFizz was interrupted, Failed to complete operation");
        }
    }

    private static void threadPrintBuzz(FizzBuzz fizzBuzz) {
        Runnable printBuzz = () -> System.out.println("buzz");
        try {
            fizzBuzz.buzz(printBuzz);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            fizzBuzz.stop();
            System.out.println("Thread threadPrintBuzz was interrupted, Failed to complete operation");
        }
    }

    private static void threadPrintFizzBuzz(FizzBuzz fizzBuzz) {
        Runnable printFizzBuzz = () -> System.out.println("fizzbuzz");
        try {
            fizzBuzz.fizzbuzz(printFizzBuzz);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            fizzBuzz.stop();
            System.out.println("Thread threadPrintFizzBuzz was interrupted, Failed to complete operation");
        }
    }

    private static void threadPrintNumber(FizzBuzz fizzBuzz) {
        IntConsumer printNumber = x -> System.out.println(x);
        try {
            fizzBuzz.number(printNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            fizzBuzz.stop();
            System.out.println("Thread threadPrintNumber was interrupted, Failed to complete operation");
        }
    }
}
