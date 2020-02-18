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

    <parent>
        <groupId>${groupId}</groupId>
        <artifactId>${artifactId}</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>${artifactId}-client</artifactId>

    <packaging>gwt-app</packaging>

    <properties>
        <!-- nalu version -->
        <nalu.version>2.0.1</nalu.version>
        <#if widgetLibrary == "DOMINO_UI">
            <!-- DominoUI version -->
            <domino.version>1.0-SNAPSHOT</domino.version>
        <#elseif widgetLibrary == "ELEMENTO">
            <!-- Elemento version -->
            <elemento.version>0.9.6</elemento.version>
        <#elseif widgetLibrary == "GXT">
            <!-- GXT version -->
            <gxt.version>4.0.0</gxt.version>
        </#if>
        <#if serverImplementation == "SPRING_BOOT">
            <!-- Public Spring Boot directory -->
            <spring-boot.public.dir>${basedir}/../${artifactId}-server/target/classes/public/</spring-boot.public.dir>
        </#if>
    </properties>

    <dependencies>
        <dependency>
            <groupId>${projectGroupId}</groupId>
            <artifactId>${artifactId}-shared</artifactId>
            <version>${projectVersion}</version>
        </dependency>
        <dependency>
            <groupId>${projectGroupId}</groupId>
            <artifactId>${artifactId}-shared</artifactId>
            <version>${projectVersion}</version>
            <classifier>sources</classifier>
        </dependency>
        <dependency>
            <groupId>com.google.gwt</groupId>
            <artifactId>gwt-user</artifactId>
        </dependency>
        <dependency>
            <groupId>com.google.gwt</groupId>
            <artifactId>gwt-dev</artifactId>
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
        <plugins>
            <plugin>
                <groupId>net.ltgt.gwt.maven</groupId>
                <artifactId>gwt-maven-plugin</artifactId>
                <configuration>
                    <moduleName>${groupId}.${artifactIdLowerCase}.${artifactId}</moduleName>
                    <moduleShortName>${artifactId}</moduleShortName>
                    <#if serverImplementation == "SPRING_BOOT">
                        <launcherDir>${projectBuildDirectory}/gwt/launcherDir</launcherDir>
                        <!-- Spring Boot as default: put the compile result under public directory -->
                        <classpathScope>compile+runtime</classpathScope>
                        <launcherDir>${springBootPublicDir}</launcherDir>
                        <warDir>${springBootPublicDir}</warDir>
                    </#if>
                </configuration>
            </plugin>
        </plugins>
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