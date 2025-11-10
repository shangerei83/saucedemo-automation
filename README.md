# SauceDemo Automation Testing Project

## Task Description

This project implements automated testing for the SauceDemo application (https://www.saucedemo.com/) using Selenium WebDriver with Java and Maven.

### Test Cases

#### UC-1 Test Login form with empty credentials
- **Steps:**
  1. Type any credentials into "Username" and "Password" fields
  2. Clear the inputs
  3. Hit the "Login" button
- **Expected Result:** Error message "Username is required" should be displayed

#### UC-2 Test Login form with credentials by passing Username
- **Steps:**
  1. Type any credentials in username
  2. Enter password
  3. Clear the "Password" input
  4. Hit the "Login" button
- **Expected Result:** Error message "Password is required" should be displayed

#### UC-3 Test Login form with credentials by passing Username & Password
- **Steps:**
  1. Type credentials in username from "Accepted username" section
  2. Enter password as "secret sauce"
  3. Click on Login
- **Expected Result:** Title "Swag Labs" should be displayed in the dashboard

### Technical Requirements

- **Test Automation Tool:** Selenium WebDriver 
- **Project Builder:** Maven 
- **Browsers:** Firefox, Edge 
- **Locators:** CSS selectors 
- **Test Runner:** JUnit 
- **Assertions:** Hamcrest 
- **Logging:** Log4j 

### Additional Requirements
- **Parallel execution support** 
- **Data Provider for test parametrization** 
- **Comprehensive logging for all tests** 

### Optional Features
- **Patterns:** Singleton, Factory method 
- **Test automation approach:** BDD 
- **Patterns:** Abstract Factory 


## Setup & Execution

### Prerequisites
- Java 17
- Maven 3.6+
- Firefox and Edge browsers

### Running Tests
```bash
# Clone the repository
git clone [your-repository-url]

# Navigate to project directory
cd saucedemo-automation

# Run tests with parallel execution
mvn clean test
