package ru.marina.tshop.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
@Component
public class UniqueSeq {

    private final AtomicLong sequence = new AtomicLong(System.currentTimeMillis() / 1000);

    public String getNext() {
        return String.valueOf(sequence.incrementAndGet());
    }
}