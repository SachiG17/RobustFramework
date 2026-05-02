# 🤖 Robust Automation Framework

> A production-grade, thread-safe Selenium + TestNG automation framework with Page Object Model, Data-Driven Testing, and rich Extent Reports.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Features](#features)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Reports](#test-reports)
- [Data-Driven Testing](#data-driven-testing)
- [Retry Mechanism](#retry-mechanism)
- [Logging](#logging)
- [Jenkins CI Integration](#jenkins-ci-integration)

---

## Overview

**RobustAutomationFramework** is a scalable, maintainable, and CI/CD-ready Selenium automation framework built in Java. It is designed around industry best practices — clean separation of concerns with Page Object Model, thread-safe parallel execution, automatic retries, screenshot capture on failure, and beautiful HTML test reports.

The framework is demonstrated against a live e-commerce application covering login, registration, product, and cart flows.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 25 | Core language |
| Selenium WebDriver | 4.39.0 | Browser automation |
| TestNG | 7.11.0 | Test orchestration & assertions |
| WebDriverManager | 6.3.3 | Automatic driver binary management |
| ExtentReports | 5.1.2 | HTML test reporting |
| Apache POI | 5.5.1 | Excel data-driven testing |
| Log4j2 | 2.22.1 | Structured logging |
| Maven | — | Build & dependency management |

---

## Project Structure

```
RobustAutomationFramework/
├── src/
│   ├── main/
│   │   ├── java/org/RobustFramework/
│   │   │   ├── BaseTest/
│   │   │   │   └── BaseTest.java          # @BeforeMethod/@AfterMethod lifecycle
│   │   │   ├── DriverFactory/
│   │   │   │   ├── DriverFactory.java     # Thread-safe WebDriver factory
│   │   │   │   └── BrowserType.java       # Enum: CHROME | EDGE | FIREFOX
│   │   │   ├── Listeners/
│   │   │   │   ├── Listeners.java         # TestNG listener: reports + screenshots
│   │   │   │   ├── RetryAnalyzer.java     # Auto-retry on flaky failures
│   │   │   │   └── RetryTransformer.java  # Applies RetryAnalyzer globally
│   │   │   ├── PageObjects/
│   │   │   │   ├── BasePage.java          # Common page utilities
│   │   │   │   ├── HomePage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── RegistrationPage.java
│   │   │   │   ├── ProductPage.java
│   │   │   │   ├── CartPage.java
│   │   │   │   ├── SignUpLoginPage.java
│   │   │   │   └── ContactUsPage.java
│   │   │   └── Utilities/
│   │   │       ├── ConfigReader.java      # config.properties loader
│   │   │       ├── WaitUtils.java         # Smart explicit waits
│   │   │       ├── ScreenshotUtils.java   # Failure screenshot capture
│   │   │       ├── ExtentReporterNG.java  # Report initializer
│   │   │       ├── ExcelUtils.java        # Apache POI Excel reader
│   │   │       └── JsonUtils.java         # JSON test data parser
│   │   └── resources/
│   │       ├── config.properties          # Browser & environment config
│   │       └── log4j2.xml                 # Logging configuration
│   └── test/
│       ├── java/org/RobustFramework/Tests/
│       │   ├── LoginTest.java             # Single-user login test
│       │   ├── LoginTestDDT.java          # Data-driven login test (JSON)
│       │   ├── IncorrectLoginTest.java    # Negative login scenario
│       │   ├── UserRegistrationTest.java  # Registration flow
│       │   └── ProductTest.java           # Product & cart flow
│       └── resources/Data/
│           └── MultipleLoginDetails.json  # JSON test data
├── reports/
│   └── index.html                         # Extent HTML report (auto-generated)
├── logs/
│   └── automation.log                     # Log4j2 output
└── pom.xml
```

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Test Classes                            │
│         LoginTest │ LoginTestDDT │ ProductTest │ ...            │
│              @Test methods — define WHAT to test                │
└──────────────────────────────┬──────────────────────────────────┘
                               │ use
┌──────────────────────────────▼──────────────────────────────────┐
│                        Page Objects                             │
│    HomePage │ LoginPage │ ProductPage │ CartPage │ ...          │
│         Encapsulate locators & page interactions                │
└──────────────────────────────┬──────────────────────────────────┘
                               │ extends
┌──────────────────────────────▼──────────────────────────────────┐
│                          BaseTest                               │
│   @BeforeMethod → read config → init driver → open browser      │
│   @AfterMethod  → quit driver                                   │
│   DriverFactory (ThreadLocal) │ WaitUtils                       │
└──────────────────────────────┬──────────────────────────────────┘
                               │ uses
┌──────────────────────────────▼──────────────────────────────────┐
│                         Utilities                               │
│  ConfigReader │ WaitUtils │ ScreenshotUtils │ ExtentReporterNG  │
│               ExcelUtils │ JsonUtils │ Log4j2                   │
└──────────────────────────────┬──────────────────────────────────┘
                               │ orchestrated by
┌──────────────────────────────▼──────────────────────────────────┐
│                        testng.xml                               │
│     Defines suites, test groups, listeners, parallel mode       │
│     RetryTransformer │ Listeners (Extent + Screenshots)         │
└─────────────────────────────────────────────────────────────────┘
```

---

## Features

### ✅ Thread-Safe Parallel Execution
`DriverFactory` uses `ThreadLocal<WebDriver>` to ensure each test thread gets its own isolated browser instance — no shared state, no race conditions.

```java
private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
```

### ✅ Smart Wait Utilities
`WaitUtils` wraps `WebDriverWait` with explicit conditions. The `smartClick()` method retries up to 3 times on `StaleElementReferenceException` and `ElementNotInteractableException`, then falls back to a JavaScript click for modal-heavy pages.

```java
waitUtils.waitForElementToBeVisible(By.id("username"));
waitUtils.waitForElementToBeClickable(By.cssSelector(".submit-btn"));
waitUtils.smartClick(By.id("modal-confirm-btn"));
```

### ✅ Page Object Model
All locators and page interactions are encapsulated in dedicated page classes — zero XPath duplication across tests.

### ✅ Data-Driven Testing (JSON & Excel)
Tests can be parameterized using JSON files via `JsonUtils` or Excel sheets via `ExcelUtils` + Apache POI. The `@DataProvider` feeds the test method with multiple data sets.

```java
@DataProvider
public Object[][] getData() throws IOException {
    List<HashMap<String, String>> data = getJsonData("...MultipleLoginDetails.json");
    return new Object[][] { {data.get(0)}, {data.get(1)} };
}
```

### ✅ Auto-Retry on Flaky Tests
`RetryAnalyzer` automatically re-runs a failing test up to **2 times** before marking it as failed. Applied globally via `RetryTransformer` — no per-test annotation needed.

### ✅ Screenshot on Failure
The `Listeners` class captures a screenshot on every test failure and attaches it directly into the Extent HTML report for immediate visual debugging.

### ✅ Extent HTML Reports
Rich, interactive HTML reports generated automatically at `reports/index.html` after every run. Includes pass/fail/skip status, logs, exception stack traces, and failure screenshots.

### ✅ Structured Logging
Log4j2 is configured via `log4j2.xml`. Logs are written to both the console and `logs/automation.log` for audit trails.

### ✅ Headless Mode Support
Supports Chrome, Edge, and Firefox in headless mode — ready for CI/CD pipelines (GitHub Actions, Jenkins, etc.).

---

## Getting Started

### Prerequisites
- Java 17+ (compiled with source 25)
- Maven 3.8+
- Google Chrome / Firefox / Microsoft Edge installed

### Clone & Build

```bash
git clone https://github.com/your-username/RobustAutomationFramework.git
cd RobustAutomationFramework
mvn clean install -DskipTests
```

---

## Configuration

Edit `src/main/resources/config.properties`:

```properties
# Browser: CHROME | FIREFOX | EDGE
browser = Chrome

# Run headless (true/false) — set true for CI/CD
headless = false
```

`ConfigReader` loads this file at class-load time using the ClassLoader, making it safe for Maven builds and JAR execution.

---

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run a Specific Test Class
```bash
mvn test -Dtest=LoginTest
```

### Run a Test Group
```bash
mvn test -Dgroups=LogInMatrix
```

### Run in Headless Mode (CI)
```bash
# Edit config.properties: headless = true
mvn test
```

---

## Test Reports

After the test run, open the generated Extent report:

```
reports/index.html
```

The report includes:
- Overall pass/fail/skip summary with pie chart
- Individual test timelines and durations
- Step-by-step log messages per test
- Failure stack traces
- Embedded failure screenshots

---

## Data-Driven Testing

### JSON (LoginTestDDT)
Test data lives in `src/test/resources/Data/MultipleLoginDetails.json`:

```json
[
  { "email": "user1@example.com", "password": "pass1" },
  { "email": "user2@example.com", "password": "pass2" }
]
```

### Excel (ExcelUtils)
Use `ExcelUtils` with Apache POI to read `.xlsx` sheets and feed data to `@DataProvider`.

---

## Retry Mechanism

`RetryAnalyzer` retries failing tests up to **2 additional attempts**:

```java
private static final int MAX_RETRY_COUNT = 2;
```

`RetryTransformer` applies `RetryAnalyzer` globally to every `@Test` method — no need to annotate each test individually.

---

## Logging

Log4j2 is configured in `src/main/resources/log4j2.xml`. All `BaseTest` subclasses inherit the `Logger`:

```java
protected static final Logger log = LogManager.getLogger(BaseTest.class);
log.info("Opening URL: " + url);
log.error("Browser is missing in config file");
```

Logs are output to:
- **Console** — for local development
- **`logs/automation.log`** — for CI audit trail

---

## Jenkins CI Integration

This framework is integrated with **Jenkins** for continuous test execution on every code push.

### Jenkins Pipeline Setup

The pipeline is configured as a **Freestyle Project** or **Pipeline Job** with the following build steps:

**1. Source Code Management**
- Connect Jenkins to your GitHub repo via Git SCM
- Branch: `main`

**2. Build Step — Maven**
```bash
clean test -Dheadless=true
```

**3. Post-Build Actions**
- **Publish HTML Reports** → point to `reports/index.html` (Extent Report)
- **Archive Artifacts** → `logs/automation.log`, `reports/**`

### Jenkinsfile (Pipeline as Code)

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk   'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/your-username/RobustAutomationFramework.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test -Dheadless=true'
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : 'reports',
                reportFiles          : 'index.html',
                reportName           : 'Extent Test Report'
            ])
            archiveArtifacts artifacts: 'logs/automation.log', fingerprint: true
        }
        failure {
            echo 'Tests failed — check the Extent Report for screenshots.'
        }
        success {
            echo 'All tests passed!'
        }
    }
}
```

### How it works in Jenkins

| Stage | What happens |
|---|---|
| Checkout | Jenkins pulls latest code from GitHub |
| Build | `mvn clean install` compiles and resolves dependencies |
| Run Tests | `mvn test` executes all TestNG tests in headless mode |
| Extent Report | Published as an HTML report directly in the Jenkins build page |
| Logs | `automation.log` archived as a build artifact |
| Failure | Screenshots captured by `Listeners.java` are embedded in the Extent Report |

> **Tip:** Set `headless = true` in `config.properties` (or pass `-Dheadless=true` as a Maven arg) so browsers run without a display on the Jenkins server.

---

## Author
Sachin Ghanteppagol
