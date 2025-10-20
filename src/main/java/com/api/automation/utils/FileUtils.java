package com.api.automation.utils;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    private static Logger logger = Logger.getLogger(FileUtils.class);
    private static final String TEST_DATA_PATH = "src/test/resources/testingData/";

    public static String readJsonFile(String fileName) {
        try {
            String filePath = TEST_DATA_PATH + fileName;
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            logger.info("JSON file read: " + fileName);
            return content;
        } catch (Exception e) {
            logger.error("Error reading JSON file: " + fileName, e);
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public static String readXmlFile(String fileName) {
        try {
            String filePath = TEST_DATA_PATH + fileName;
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            logger.info("XML file read: " + fileName);
            return content;
        } catch (Exception e) {
            logger.error("Error reading XML file: " + fileName, e);
            throw new RuntimeException("Failed to read XML file", e);
        }
    }

    public static String readTextFile(String fileName) {
        try {
            String filePath = TEST_DATA_PATH + fileName;
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            logger.info("Text file read: " + fileName);
            return content;
        } catch (Exception e) {
            logger.error("Error reading text file: " + fileName, e);
            throw new RuntimeException("Failed to read text file", e);
        }
    }

    public static String readJsonFileWithReplacements(String fileName, String... replacements) {
        try {
            String content = readJsonFile(fileName);
            for (int i = 0; i < replacements.length; i += 2) {
                if (i + 1 < replacements.length) {
                    String key = "{{" + replacements[i] + "}}";
                    String value = replacements[i + 1];
                    content = content.replace(key, value);
                    logger.debug("Replaced: " + key);
                }
            }
            logger.info("File processed with replacements");
            return content;
        } catch (Exception e) {
            logger.error("Error processing file", e);
            throw new RuntimeException("Failed to process file", e);
        }
    }

    public static boolean fileExists(String fileName) {
        String filePath = TEST_DATA_PATH + fileName;
        return new java.io.File(filePath).exists();
    }

    public static void createTestFile(String fileName, String content) {
        try {
            String filePath = TEST_DATA_PATH + fileName;
            Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8));
            logger.info("Test file created: " + fileName);
        } catch (Exception e) {
            logger.error("Error creating file", e);
        }
    }
}
