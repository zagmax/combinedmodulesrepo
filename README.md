# Description
This is a test project created as a final task for Test Automation course based on Java programming language using aquired knowledge during previous modules

# Projects scope 
Projects scope is creating framework with test cases and performing WEB and API tests on next sites: 
   1) https://epam.com
   2) https://demowebshop.tricentis.com
   3) https://petstore.swagger.io/v2/

# Test cases 
For tests creation and running were used
   1)junit-jupiter
   2)selenium
   3)rest-assured
   
# Project structure
## Test Classes 

Located in "*/test/java/Tasks/", among those are:
- TaskOneTests -> contains tests for epam.com site
- TaskTwoTests -> contains tests for demowebshop.com site
- TaskThreeTests -> contains API tests for petstore.swagger.io

## Support classes 

Located in "*/test/java/manager/" :
- PageFactoryManager -> responsible for creating instances of page objects
- PropertyManager -> handles file with properties, needed for tests to work and customize 

## Page classes 

Contain methods and locators, located in "*/test/java/pages/" :
- CorePage -> contains basic methods usable for any site
- DemoWebShopPage, DemoWebCheckout -> contains locators and methods for demo webshop site 
- EpamPages -> contains methods and locators for epam site

## Resources

Resources contains two files:
- application.properies -> file contains variables like links and settings for easier test case adaptation and increase code readability
- log4j2.xml -> file contains configuration for logging

# Usage
## Setup and configuration  
- For tests that require downloading a file, set a path where files would be saved to by changing "downloadPath" variable

## Test running
To run testing send next command within IDE terminal in which project is opened:
- mvn test => this command will run all tests on Chrome browser

## Parameters
- -Dtest=* => where "*" is the name of test class to include in testing (e.g. -Dtest=TaskOneTests)
- -Dbrowser=* => where "*" is the name of browser to run tests on. Currently supported browsers are Chrome(run by default if no parameter sent) and Firefox "-Dbrowser=firefox"

For more complex command structures please check out maven documentation:
https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html 

## Logging 
Project logs are created with Log4j and saved within "*/target/logs/"
Generated file appTest.log could be opened for investigation of any occured issues 

## Reporting
Projects testing reporting is executed by maven surefire. 
To generate report send in IDE terminal next command: 
- mvn site
After site is done report file "surefire-report.html" could be found in "*/target/site/" and for visualization could be opened in any browser 
( looks as follows )
![report](https://github.com/zagmax/combinedmodulesrepo/assets/45147763/87dff02d-8a79-4c7e-8ab0-d9f7e932f845)



   
