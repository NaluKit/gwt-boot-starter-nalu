To run the project, use the following command:


Change directory to your generated project and issue the following commands:

<#if mavenSettings == "SINGLE_MAVEN_MODULE">
    * mvn gwt:codeserver
<#else>
* in one terminal window: mvn gwt:codeserver -pl *-client -am

* in another terminal window: mvn jetty:run -pl *-server -am -Denv=dev
</#if>


To start the applicaiton, call:

-> http://localhost:8080
