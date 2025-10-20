package com.api.automation.utils;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {

    private static Logger logger = Logger.getLogger(DataGenerator.class);
    private static Random random = new Random();

    public static String generateEmail() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            email.append(chars.charAt(random.nextInt(chars.length())));
        }
        String result = email.append("@test.com").toString();
        logger.debug("Generated email: " + result);
        return result;
    }

    public static String generatePhoneNumber() {
        String result = String.format("+1%d%d%d%d%d%d%d%d%d%d",
                random.nextInt(10), random.nextInt(10), random.nextInt(10),
                random.nextInt(10), random.nextInt(10), random.nextInt(10),
                random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10));
        logger.debug("Generated phone: " + result);
        return result;
    }

    public static String generateName() {
        String[] firstNames = {"John", "Jane", "Mike", "Sarah", "Tom", "Emma"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Davis", "Wilson", "Moore"};
        String result = firstNames[random.nextInt(firstNames.length)] + " " +
                lastNames[random.nextInt(lastNames.length)];
        logger.debug("Generated name: " + result);
        return result;
    }

    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        logger.debug("Generated random string");
        return result.toString();
    }

    public static int generateRandomNumber(int min, int max) {
        int result = random.nextInt((max - min) + 1) + min;
        logger.debug("Generated random number: " + result);
        return result;
    }

    public static String generateTimestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String result = LocalDateTime.now().format(dtf);
        logger.debug("Generated timestamp: " + result);
        return result;
    }

    public static String generateUUID() {
        String result = UUID.randomUUID().toString();
        logger.debug("Generated UUID: " + result);
        return result;
    }

    public static String generateOrderId() {
        String result = "ORD-" + System.currentTimeMillis();
        logger.debug("Generated order ID: " + result);
        return result;
    }

    public static String generateCustomerId() {
        String result = "CUST-" + generateRandomString(6);
        logger.debug("Generated customer ID: " + result);
        return result;
    }
}
