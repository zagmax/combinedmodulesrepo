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
5. To switch between Firefox and Chrome browsers to run tests on, change variable called "browserType" within test classes under Tasks package
6. Projects testing reporting is executed by maven surefire
    - To get test results in report format next steps should be done
       1) run maven build  
       2) after build is complete, open in any browser file "surefire-report.html"  located in */target/site/
       3) page should open in browser and have similar look to attached example  
          ![report](https://github.com/zagmax/combinedmodulesrepo/assets/45147763/87dff02d-8a79-4c7e-8ab0-d9f7e932f845)
