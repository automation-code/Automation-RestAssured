package com.api.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/complex_payloads.feature",
        glue = "com.api.automation.stepDefs",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/index.html",
                "json:target/cucumber-reports/report.json",
                "junit:target/cucumber-reports/report.xml"
        },
        monochrome = true,
        publish = true,
        tags = "@delete"
)

public class APITestRunner {
}
