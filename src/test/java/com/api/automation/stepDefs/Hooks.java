package com.api.automation.stepDefs;

import com.api.automation.config.ConfigManager;
import com.api.automation.pojo.APIClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.log4j.Logger;

public class Hooks {

    private static Logger logger = Logger.getLogger(Hooks.class);
    private SharedDriver sharedDriver;

    public Hooks(SharedDriver sharedDriver) {
        this.sharedDriver = sharedDriver;
    }

    @Before
    public void setUp() {
        logger.info("========== TEST SCENARIO STARTED ==========");
        String baseURI = ConfigManager.getBaseURI();
        APIClient apiClient = new APIClient(baseURI);
        sharedDriver.setApiClient(apiClient);
        sharedDriver.getTestContext().clearContext();
    }

    @After
    public void tearDown() {
        logger.info("========== TEST SCENARIO COMPLETED ==========\n");
        sharedDriver.getTestContext().clearContext();
    }
}
