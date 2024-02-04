------------------ READ ME ---------------------
1. Projects scope is to perform WEB/API tests on such sites as
   1) epam.com
   2) https://demowebshop.tricentis.com
   3) https://petstore.swagger.io/v2/
2. For tests creation and running were used
   1)junit-jupiter
   2)selenium
   3)rest-assured
3. Classes that contain tests are located in  */test/java/Tasks/
4. Support classes that contain webpage elements locators for web tests located in */test/java/pages/
5. To switch between Firefox and Chrome browsers to run tests on, change variable called "browser" to "ch" for Chrome or "ff" for Firefox within application.properties file

![image](https://github.com/zagmax/combinedmodulesrepo/assets/45147763/411b5bbe-9969-4d5a-8991-d074c4589c25)

6. For tests that require downloading files, directory for those files to be downloaded to should be changed in application.properties file according to user preferences 

![image](https://github.com/zagmax/combinedmodulesrepo/assets/45147763/589572e6-c8ec-4dbb-a102-12e9641abe40)

7. Project logs are created with Log4j and saved within */target/logs/
8. Projects testing reporting is executed by maven surefire.
   1) Test reports file "surefire-report.html" located in */target/site/
   2) file should open in browser and have similar look to attached example  
    ![report](https://github.com/zagmax/combinedmodulesrepo/assets/45147763/87dff02d-8a79-4c7e-8ab0-d9f7e932f845)
9. Next commands could be used in terminal within IDE in which this project is opened
   1) mvn run - runs Maven build to go through all stages from clean start
   2) mvn test - to run tests
   3) mvn site - to create test report
   Or also maven plugin could be used instead to select and run commands via simple double clicking 
![image](https://github.com/zagmax/combinedmodulesrepo/assets/45147763/82d1273a-5f26-49cd-9216-9d237f7498df)
  
