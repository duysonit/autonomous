# ai-autonomous

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href=“#”>
    <img src="https://user-images.githubusercontent.com/32425769/125204147-f8685380-e2a5-11eb-9c3d-ad1cd1022002.jpeg" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Setup-And-Execution-Guideline</h3>

  <p align="center">
    Kindly spend your time README to enjoy the project!
    <br />
    <a href="https://github.com/duysonit/autonomous/#readme"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://drive.google.com/drive/folders/1wv9tjGyu-4bZIRjQdpFdulfPMfccEEaB">View Demo</a>
    ·
    <a href="https://github.com/duysonit/autonomous/issues">Report Bug</a>
    ·
    <a href="https://github.com/duysonit/autonomous/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
	<li><a href="#project-structure">Project Structure</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Nowaday, we have many tools for the automation testing like Selenium, Katalon, Cypress,etc...In this project, I recommend we use the Selenium using Serenity BDD framework which is an open source library, based on the following reasons:
* Serenity BDD helps you write cleaner and more maintainable automated acceptance and regression tests faster. 
* Serenity also uses the test results to produce illustrated, narrative reports that document and describe what your application does and how it works. Serenity tells you not only what tests have been executed, but more importantly, what requirements have been tested.
* One key advantage of using Serenity BDD is that you do not have to invest time in building and maintaining your own automation framework.
* Serenity BDD provides strong support for different types of automated acceptance testing, including:
    * Rich built-in support for web testing with Selenium.
    * REST API testing with RestAssured.
    * Highly readable, maintainable and scalable automated testing with the Screenplay pattern.
* Serenity reports aim to be more than simple test reports - they are designed to provide living documentation of your product. The reports give an overview of the test results

![image](https://user-images.githubusercontent.com/32425769/125211303-9fadb080-e2cf-11eb-9b57-d377c2361ae7.png)


* But they also let you document your requirements hierarchy, and the state of the acceptance criteria associated with your requirements

![image](https://user-images.githubusercontent.com/32425769/125211319-c370f680-e2cf-11eb-8b9b-ebc461228721.png)



### Built With

These components run Serenity BDD Framework. They are all required.
* [Java SDK 8](https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html)
* [IntelliJ IDE](https://www.jetbrains.com/idea/)
* [Maven](https://www.jetbrains.com/help/idea/maven-support.html)
* [Serenity BDD Plugin](https://mvnrepository.com/artifact/net.serenity-bdd/serenity-junit/1.2.3-rc.9)


### Project Structure

We use Page Object Model (POM) for design pattern in this project. The advantage of the model is that it reduces code duplication and improves test maintenance.

![POM](https://user-images.githubusercontent.com/32425769/125211141-89532500-e2ce-11eb-9937-6ae38d238c7f.png)

Page Object Class

- The first page (class or class file) represents the web page we want to model or carry actions on.


Page Method Class

- Secondly, we need another page object (class file) to hold the methods to be used in interacting with objects on the web page we are modeling.


Page Test Class

- Thirdly, with Page object model page methods are held separate from test methods, so this class file will hold our test code. We run the JUnit test using the Serenity test runner.

- The @Steps annotation marks a Serenity step library.

- The @WithTag annotation : We use this annotation to execute all test with the tag in terminal using $ mvn clean verify -Dtags=“type={variable1}:name={variable2}”





<!-- GETTING STARTED -->
## Getting Started


### Prerequisites

* brew install
  ```sh
  brew install wget
  ```
* git install
  ```sh
  brew install git
  ```

### Installation

Clone the repo
   ```sh
   git clone https://github.com/duysonit/ai-autonomous.git
   ```



<!-- USAGE -->
### Usage

To run the project, you can either just run the `AmazonTesting` test runner class, or run `mvn verify` from the command line.

By default, the tests will run using Chrome. You can run them in Firefox by overriding the `driver` system property, e.g.
```json
$ cd dir
$ mvn clean verify -Dtags="ui:amazon"
```

The test results will be recorded in the `target/site/serenity` directory.




<!-- CONTACT -->
## Contact

Mr.Son Vu - duysonit@gmail.com

Project Link: [https://github.com/duysonit/autonomous](https://github.com/duysonit/autonomous)

