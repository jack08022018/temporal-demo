package com.demo.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainAdapterImpl implements MainAdapter {

    @Override
    public void deduct() {
        System.out.println("deduct!");
    }

    @Override
    public void refund() {
        System.out.println("refund!");
    }

    @Override
    public String getInfo() {
        return "Greeting!";
    }

}
