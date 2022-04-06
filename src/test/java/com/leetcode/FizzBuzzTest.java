package com.leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FizzBuzzTest {
    private static final int iterations = 15;
    private FizzBuzz fizzBuzz;
    private CountDownLatch latch;
    private ExecutorService service;

    @BeforeEach
    void beforeEach() {
        fizzBuzz = new FizzBuzz(iterations);
        latch = new CountDownLatch(2);
        service = Executors.newFixedThreadPool(2);
    }
    
    @Test
    void fizz() throws InterruptedException {
        service.submit(() -> {
            try {
                Runnable printFizz = () -> System.out.println("fizz");
                fizzBuzz.fizz(printFizz);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fizzBuzz.stop();
            }
            // signal task completed
            latch.countDown();
        });

        service.submit(() -> {
            while (fizzBuzz.getValue() <= iterations) {
                if (!(fizzBuzz.getValue() % 3 == 0 && fizzBuzz.getValue() % 5 != 0)) {
                    fizzBuzz.increment();
                }
            }
            latch.countDown();
        });

        boolean receivedSignal = latch.await(5, TimeUnit.SECONDS);
        assertEquals(true, receivedSignal);
    }

    @Test
    void buzz() throws InterruptedException {
        service.submit(() -> {
            try {
                Runnable printFizz = () -> System.out.println("buzz");
                fizzBuzz.buzz(printFizz);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fizzBuzz.stop();
            }
            latch.countDown();
        });

        service.submit(() -> {
            while (fizzBuzz.getValue() <= iterations) {
                if (!(fizzBuzz.getValue() % 3 != 0 && fizzBuzz.getValue() % 5 == 0)) {
                    fizzBuzz.increment();
                }
            }
            latch.countDown();
        });

        boolean receivedSignal = latch.await(5, TimeUnit.SECONDS);
        assertEquals(true, receivedSignal);
    }

    @Test
    void fizzbuzz() throws InterruptedException {
        service.submit(() -> {
            try {
                Runnable printFizz = () -> System.out.println("fizzBuzz");
                fizzBuzz.fizzbuzz(printFizz);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fizzBuzz.stop();
            }
            latch.countDown();
        });

        service.submit(() -> {
            while (fizzBuzz.getValue() <= iterations) {
                if (!(fizzBuzz.getValue() % 3 == 0 && fizzBuzz.getValue() % 5 == 0)) {
                    fizzBuzz.increment();
                }
            }
            latch.countDown();
        });

        boolean receivedSignal = latch.await(5, TimeUnit.SECONDS);
        assertEquals(true, receivedSignal);
    }

    @Test
    void number() throws InterruptedException {
        service.submit(() -> {
            try {
                IntConsumer printNumber = x -> System.out.println(x);
                fizzBuzz.number(printNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fizzBuzz.stop();
            }
            latch.countDown();
        });

        service.submit(() -> {
            while (fizzBuzz.getValue() <= iterations) {
                if (!(fizzBuzz.getValue() % 3 != 0 && fizzBuzz.getValue() % 5 != 0)) {
                    fizzBuzz.increment();
                }
            }
            latch.countDown();
        });

        boolean receivedSignal = latch.await(5, TimeUnit.SECONDS);
        assertEquals(true, receivedSignal);
    }

    @Test
    void stop() throws InterruptedException {
        service.submit(() -> {
            try {
                IntConsumer printNumber = x -> System.out.println(x);
                fizzBuzz.number(printNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fizzBuzz.stop();
            }
            latch.countDown();
        });

        service.submit(() -> {
            fizzBuzz.stop();
            latch.countDown();
        });

        boolean receivedSignal = latch.await(5, TimeUnit.SECONDS);
        assertEquals(true, receivedSignal);
    }

    @Test
    void increment() throws InterruptedException {
        final int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 1; i < numberOfThreads; i++) {
            service.submit(() -> {
                fizzBuzz.increment();
                latch.countDown();
            });
        }
        latch.await(5, TimeUnit.SECONDS);;
        assertEquals(numberOfThreads, fizzBuzz.getValue());
    }
}