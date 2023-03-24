APPLICATION TITLE: Initech Desktop Scheduler
PURPOSE: To provide a desktop scheduler for the Initech Corporation.


AUTHOR: Brendan Brown
CONTACT INFORMATION: bbrend7@wgu.edu
APPLICATION VERSION: 1.0
RELEASE DATE: 03/24/2023


IDE VERSION: IntelliJ IDEA Community Edition 2021.1.3
JDK VERSION: 17.0.1
JavaFX VERSION: 17.0.1

DIRECTIONS TO RUN:
    1. Unzip package and open in IntelliJ IDEA.
    2. Add new configuration.
       - Select Main class: Main
       - Select VM options: --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml,javafx.graphics
    3. Add mysql-connector-java-8.0.25.jar to project.
       - Select File > Project Structure > Libraries > Add > mysql-connector-java-8.0.25.jar
    4. Run application.

ADDITIONAL REPORT: My additional report lists all countries that have at least one customer in the database. It also
                   lists the number of customers in each country.

LAMBDA EXPRESSIONS:

    1. Lambda expression used to MainFormController.java line 394 to iterate through appointments list and make a new
    list of types.

    2. Lambda expression used to MainFormController.java line 453 to iterate through customers list and make a new
    list of countries from all customers.

MySQL CONNECTOR DRIVER VERSION: 8.0.32