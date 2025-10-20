package com.api.automation.validation;

import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class PayloadAssertions {

    private static Logger logger = Logger.getLogger(PayloadAssertions.class);

    public static void assertResponseContains(Response response, String expectedValue) {
        Assert.assertThat(response.body().asString(), containsString(expectedValue));
        logger.info("Response contains: " + expectedValue);
    }

    public static void assertResponseNotContains(Response response, String unexpectedValue) {
        Assert.assertThat(response.body().asString(), not(containsString(unexpectedValue)));
        logger.info("Response does not contain: " + unexpectedValue);
    }

    public static void assertJsonArrayNotEmpty(Response response, String jsonPath) {
        int size = response.jsonPath().getList(jsonPath).size();
        Assert.assertTrue("Array " + jsonPath + " is empty", size > 0);
        logger.info("Array not empty: " + jsonPath);
    }

    public static void assertAllArrayElementsHaveProperty(Response response, String arrayPath, String property) {
        List<Map<String, Object>> list = response.jsonPath().getList(arrayPath);
        for (Map<String, Object> item : list) {
            Assert.assertTrue("Property " + property + " not found", item.containsKey(property));
        }
        logger.info("All array elements have property: " + property);
    }

    public static void assertArrayContainsValue(Response response, String arrayPath, Object value) {
        List<Object> list = response.jsonPath().getList(arrayPath);
        Assert.assertTrue("Array does not contain: " + value, list.contains(value));
        logger.info("Array contains value: " + value);
    }
}
