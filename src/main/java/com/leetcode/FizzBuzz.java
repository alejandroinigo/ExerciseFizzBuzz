package com.leetcode;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

public class FizzBuzz {
    private int n;
    private final AtomicInteger counter = new AtomicInteger(1);
    private final AtomicBoolean running = new AtomicBoolean(true);

    public FizzBuzz(int n) {
        if (n < 1 || n > 50) {
            System.out.println("Invalid input parameters. Expecting an int n where 1 <= n <= 50.");
            this.n = 0;
        } else {
            this.n = n;
        }
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (running.get() && getValue() <= this.n) {
            final int currentValue = getValue();
            if (isFizz(currentValue)) {
                printFizz.run();
                increment();
            }
        }
        System.out.println("Thread fizz end");
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (running.get() && getValue() <= this.n) {
            final int currentValue = getValue();
            if (isBuzz(currentValue)) {
                printBuzz.run();
                increment();
            }
        }
        System.out.println("Thread buzz end");
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (running.get() && getValue() <= this.n) {
            final int currentValue = getValue();
            if (isFizzBuzz(currentValue)) {
                printFizzBuzz.run();
                increment();
            }
        }
        System.out.println("Thread fizzbuzz end");
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (running.get() && getValue() <= this.n) {
            final int currentValue = getValue();
            if (!isFizz(currentValue) && !isBuzz(currentValue) && !isFizzBuzz(currentValue)) {
                printNumber.accept(getValue());
                increment();
            }
        }
        System.out.println("Thread number end");
    }

    public void stop() {
        running.set(false);
    }

    public int getValue() {
        return counter.get();
    }

    public void increment() {
        while(true) {
            int existingValue = getValue();
            int newValue = existingValue + 1;
            if(counter.compareAndSet(existingValue, newValue)) {
                return;
            }
        }
    }

    private boolean isBuzz(int currentValue) {
        return currentValue%3 != 0 && currentValue%5 == 0;
    }

    private boolean isFizz(int currentValue) {
        return currentValue%3 == 0 && currentValue%5 != 0;
    }

    private boolean isFizzBuzz(int currentValue) {
        return currentValue%3 == 0 && currentValue%5 == 0;
    }
}
