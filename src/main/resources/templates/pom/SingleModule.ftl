<?xml version="1.0" encoding="UTF-8"?>

<!--
  ~ Copyright (C) 2018 - 2019 Frank Hossfeld <frank.hossfeld@googlemail.com>
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~  you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  -->

<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>1.2.0</version>
    <packaging>gwt-app</packaging>

    <name>${artifactId}  - Nalu Boot Starter Project</name>
    <description>Nalu Boot Starter Project</description>

    <properties>
        <!-- gwt version -->
        <gwt.version>2.8.2</gwt.version>
        <!-- nalu version -->
        <nalu.version>1.2.0</nalu.version>
<#if widgetLibrary == "DOMINO_UI">
        <!-- DominoUI version -->
        <domino.version>1.0-SNAPSHOT</domino.version>
<#elseif widgetLibrary == "ELEMENTO">
        <!-- Elemento version -->
        <elemento.version>0.8.7</elemento.version>
<#elseif widgetLibrary == "GXT">
        <!-- GXT version -->
        <gxt.version>4.0.0</gxt.version>
</#if>
        <!-- plugin versions -->
        <plugin.version.eclipse.lifecyle>1.0.0</plugin.version.eclipse.lifecyle>
        <plugin.version.maven.compiler>3.6.1</plugin.version.maven.compiler>
        <plugin.version.maven.gwt>1.0-rc-10</plugin.version.maven.gwt>
        <plugin.version.maven.war>3.0.0</plugin.version.maven.war>

        <!-- GWT needs at least java 1.6 -->
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <generated.source.directory>${projectBuildDirectory}/generated-sources/annotations</generated.source.directory>

        <webappDirectory>${projectBuildDirectory}/${projectBuildFinalName}</webappDirectory>
    </properties>

    <!-- GWT BOM -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.google.gwt</groupId>
                <artifactId>gwt</artifactId>
                <version>${gwtVersion}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>com.google.gwt</groupId>
            <artifactId>gwt-user</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.google.gwt</groupId>
            <artifactId>gwt-dev</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu</artifactId>
            <version>${naluVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-processor</artifactId>
            <version>${naluVersion}</version>
        </dependency>
<#if widgetLibrary == "DOMINO_UI">
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-plugin-elemental2</artifactId>
            <version>${naluVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.dominokit</groupId>
            <artifactId>domino-ui</artifactId>
            <version>${dominoVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.dominokit</groupId>
            <artifactId>domino-ui</artifactId>
            <version>${dominoVersion}</version>
            <classifier>sources</classifier>
        </dependency>
<#elseif widgetLibrary == "ELEMENTO">
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-plugin-elemental2</artifactId>
            <version>${naluVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.gwt.elemento</groupId>
            <artifactId>elemento-core</artifactId>
            <version>${elementoVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.gwt.elemento</groupId>
            <artifactId>elemento-core</artifactId>
            <version>${elementoVersion}</version>
            <classifier>sources</classifier>
        </dependency>
<#elseif widgetLibrary == "GWT">
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-plugin-gwt</artifactId>
            <version>${naluVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-plugin-gwt-processor</artifactId>
            <version>${naluVersion}</version>
        </dependency>
<#elseif widgetLibrary == "GXT">
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-plugin-gwt</artifactId>
            <version>${naluVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.nalukit</groupId>
            <artifactId>nalu-plugin-gwt-processor</artifactId>
            <version>${naluVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.sencha.gxt</groupId>
            <artifactId>gxt</artifactId>
            <version>${gxtVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.sencha.gxt</groupId>
            <artifactId>gxt-theme-neptune</artifactId>
            <version>${gxtVersion}</version>
        </dependency>
</#if>
    </dependencies>

    <build>
        <!-- Compiled Classes -->
        <outputDirectory>${webappDirectory}/WEB-INF/classes</outputDirectory>

        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**</include>
                </includes>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${pluginVersionMavenCompiler}</version>
                <configuration>
                    <source>${mavenCompilerSource}</source>
                    <target>${mavenCompilerTarget}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>3.0.0</version>
                <executions>
                    <execution>
                        <id>add-source</id>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>add-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>${generatedSourceDirectory}</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>net.ltgt.gwt.maven</groupId>
                <artifactId>gwt-maven-plugin</artifactId>
                <version>1.0-rc-10</version>
                <extensions>true</extensions>
                <configuration>
                    <classpathScope>compile</classpathScope>
                    <moduleName>${groupId}.${artifactIdLowerCase}.${artifactId}</moduleName>
                    <moduleShortName>${artifactId}</moduleShortName>
                    <failOnError>true</failOnError>
                    <sourceLevel>${mavenCompilerSource}</sourceLevel>
                    <logLevel>TRACE</logLevel>
                    <startupUrls>
                        <startupUrl>${artifactId}.html</startupUrl>
                    </startupUrls>
                    <devmodeWorkDir>${projectBuildDirectory}/devModeWorkDir</devmodeWorkDir>
                    <launcherDir>${projectBuildDirectory}/${projectBuildFinalName}</launcherDir>
                    <warDir>${projectBuildDirectory}/${projectBuildFinalName}</warDir>
                    <jvmArgs>
                        <arg>-Xms1G</arg>
                        <arg>-Xmx2G</arg>
                    </jvmArgs>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>${pluginVersionMavenWar}</version>
                <configuration>
                    <webResources>
                        <resource>
                            <directory>${projectBuildDirectory}/${projectBuildFinalName}</directory>
                        </resource>
                    </webResources>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
                <executions>
                    <execution>
                        <id>copy-war-contents-to-exploded-dir</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>exploded</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.eclipse.m2e</groupId>
                    <artifactId>lifecycle-mapping</artifactId>
                    <version>${pluginVersionEclipseLifecyle}</version>
                    <configuration>
                        <lifecycleMappingMetadata>
                            <pluginExecutions>
                                <pluginExecution>
                                    <pluginExecutionFilter>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-war-plugin</artifactId>
                                        <versionRange>[3.0.0,)</versionRange>
                                        <goals>
                                            <goal>exploded</goal>
                                        </goals>
                                    </pluginExecutionFilter>
                                    <action>
                                        <execute>
                                            <runOnConfiguration>true</runOnConfiguration>
                                            <runOnIncremental>true</runOnIncremental>
                                        </execute>
                                    </action>
                                </pluginExecution>
                            </pluginExecutions>
                        </lifecycleMappingMetadata>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

<#if widgetLibrary == "DOMINO_UI">
   <repositories>
       <repository>
           <id>sonatype-snapshots-repo</id>
           <url>https://oss.sonatype.org/content/repositories/snapshots</url>
           <snapshots>
               <enabled>true</enabled>
               <updatePolicy>always</updatePolicy>
               <checksumPolicy>fail</checksumPolicy>
           </snapshots>
       </repository>
   </repositories>
<#elseif widgetLibrary == "GXT">
   <repositories>
       <repository>
           <id>sencha-gxt-repository</id>
           <name>sencha-gxt-repository</name>
           <!-- GPL -->
           <url>https://maven.sencha.com/repo/gxt-gpl-release</url>
       </repository>
   </repositories>
</#if>
</project>
