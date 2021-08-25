To run the project, use the following command:

Change directory to your generated project and issue the following commands:

* run in one terminal window:

    - mvn clean package

    - mvn gwt:codeserver -pl *-client -am

* in another terminal window:

    - mvn spring-boot:run -P env-dev

To start the application, call:

-> http://localhost:8080/index.html