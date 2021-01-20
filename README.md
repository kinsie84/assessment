# Cars Application
Spring boot application with a console interface as defined by the ConsoleInterface class.
A unique list of CarResults is retrieved via the data source, then the following operations are performed:
* The objects in the list are converted to segmented versions of themselves with additional fields added:
    * Corporate Flag -> AVIS, BUDGET, ENTERPRISE, FIREFLY, HEARTZ, SIXT, THRIFTY
    * Category -> MINI, ECONOMY, COMPACT, OTHER
* The list is sorted with all corporate suppliers appearing before any non-corporate suppliers
* The list is then sorted by category within each group following the above order of categories
* The list is then sorted within those subgroups based on rental cost, low to high. 
* The median rental cost of both corporate and non-corporate rentals is established
* Cars within each group above the median of the group are removed from the final list if the Fuel Policy of the vehicle
 is FULLFULL.
* The final list is then displayed to the User 

## Prerequisites

* JRE 1.11.X
* Maven 3.6.x

## Spring Boot Run
Navigate to the root directory and run using the following command:

* mvn spring-boot:run
 
## Executable Jar
An executable jar file is available in the "executable" directory.

To run this jar file, navigate to the directory and run the following command:

* java -jar assessment-0.0.1-SNAPSHOT.jar
