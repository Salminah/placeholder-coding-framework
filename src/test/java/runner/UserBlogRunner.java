package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features"}
        ,glue = {"stepDefinition"}
        ,plugin = {"pretty", "html:results/html/cucumber.html"}
        ,tags = "@User_Blog_comments"
        ,monochrome = false)

public class UserBlogRunner {
}
