To run the project, use the following command:


Change directory to your generated project and issue the following commands:


* run in one terminal window:

- mvn clean install

<#if serverImplementation == "GWT_MAVEN_PLUGIN">
    - mvn gwt:codeserver -pl *-client -am
<#else>
    * run in client module either:

    - mvn gwt:codeserver
    or
    mvn gwt:devmode
</#if>

* in another terminal window:

<#if serverImplementation == "GWT_MAVEN_PLUGIN">
    - mvn jetty:run -pl *-server -am -Denv=dev
<#else>
    * run in server module:

    - mvn spring-boot:run -P env-dev
</#if>

To start the application, call:

-> http://localhost:8080/${artefactId}.html


<#if serverImplementation == "SPRING_BOOT">
    Running

    - mvn clean install

    on the parent module will create a executable war file in the "${artefactId}-server" module, which can be run using

    java -jar ${artefactId}-server\target\${artefactId}-server-1.0.0.war
</#if>

