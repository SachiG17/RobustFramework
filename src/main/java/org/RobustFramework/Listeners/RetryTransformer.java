package org.RobustFramework.Listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Method;

public class RetryTransformer implements IAnnotationTransformer {
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Method testMethod) {

        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}