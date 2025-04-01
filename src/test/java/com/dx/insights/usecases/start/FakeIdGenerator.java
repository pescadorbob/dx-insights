package com.dx.insights.usecases.start;

import java.util.ArrayDeque;
import java.util.Deque;

public class FakeIdGenerator {
    private final Deque<String> queue;

    public FakeIdGenerator() {
        this.queue = new ArrayDeque<>();
    }

    public String next(){
        return queue.pop();
    }
    public void setupNext(String value){
        queue.push(value);
    }
}
