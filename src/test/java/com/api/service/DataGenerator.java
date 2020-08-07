package com.api.service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class DataGenerator {
    public static void main(String[] args) {
        for(int i = 0; i < 999999; i++){
            String prefix = "Test001";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            LocalDateTime now = LocalDateTime.now();
            System.out.println( prefix + dtf.format(now) + ",");
        }
    }
}
