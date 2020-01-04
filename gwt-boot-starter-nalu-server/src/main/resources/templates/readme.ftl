To run the project, use the following command:


Change directory to your generated project and issue the following commands:


* run in one terminal window:

- mvn clean install

<#if widgetLibrary == "GWT_MAVEN_PLUGIN">
    - mvn gwt:codeserver -pl *-client -am
<#else>
    - mvn gwt:codeserver -pl *-client -am
</#if>

* in another terminal window:

<#if widgetLibrary == "GWT_MAVEN_PLUGIN">
    - mvn jetty:run -pl *-server -am -Denv=dev
<#else>
    - mvn spring-boot:run -P env-dev
</#if>

To start the application, call:

-> http://localhost:8080


<#if widgetLibrary == "SPRING_BOOT">
    Running

    - mvn clean install

    on the parent module will create a executable war file in the "MyTestProject-server" module, which can be run usin

    java -jar MyTestProject-server\target\MyTestProject-server-1.0.0.war
</#if>

