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

<!--
  When updating your version of GWT, you should also update this DTD reference,
  so that your app can take advantage of the latest GWT module capabilities.
-->
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.8.2//EN"
        "http://gwtproject.org/doctype/2.8.2/gwt-module.dtd">
<module rename-to="${artifactId}">
    <!-- Inherit the core Web Toolkit stuff.                        -->
    <inherits name='com.google.gwt.user.User'/>
    <#if widgetLibrary == "GWT">

        <!-- Inherit the default GWT style sheet.  You can change       -->
        <!-- the theme of your GWT application by uncommenting          -->
        <!-- any one of the following lines.                            -->
        <inherits name='com.google.gwt.user.theme.clean.Clean'/>
        <!-- <inherits historyName='com.google.gwt.user.theme.standard.Standard'/> -->
        <!-- <inherits historyName='com.google.gwt.user.theme.chrome.Chrome'/> -->
        <!-- <inherits historyName='com.google.gwt.user.theme.dark.Dark'/>     -->
    </#if>

    <!-- Other module inherits                                      -->
    <inherits name='com.github.nalukit.nalu.Nalu'/>
    <#if widgetLibrary == "DOMINO_UI">
        <inherits name='com.github.nalukit.nalu.plugin.elemental2.NaluPluginElemental2'/>
        <inherits name='org.dominokit.domino.ui.DominoUI'/>
    <#elseif widgetLibrary == "ELEMENTO">
        <inherits name='com.github.nalukit.nalu.plugin.elemental2.NaluPluginElemental2'/>
        <inherits name='org.jboss.gwt.elemento.Core'/>
    <#elseif widgetLibrary == "GWT">
        <inherits name='com.github.nalukit.nalu.plugin.gwt.NaluPluginGWT'/>
    <#elseif widgetLibrary == "GXT">
        <inherits name='com.github.nalukit.nalu.plugin.gwt.NaluPluginGWT'/>
        <inherits name='com.sencha.gxt.ui.GXT'/>
        <inherits name='com.sencha.gxt.theme.neptune.Theme'/>

        <!-- GXT Stylesheet -->
        <stylesheet src="reset.css"/>
    </#if>

    <!-- Specify the app entry point class.                         -->
    <entry-point class='${groupIdLowerCase}.${artifactIdLowerCase}.client.${artifactIdFirstUpperCase}'/>

    <!-- Specify the paths for translatable code                    -->
    <source path='client'/>
    <source path='shared'/>

    <#if widgetLibrary == "GXT">
        <!-- collapse properties for fewer permutations -->
        <collapse-property name="gxt.device"
                           values="phone, tablet"/>

        <collapse-property name="gxt.user.agent"
                           values="air, safari*, chrome*, gecko*, ie11"/>
        <collapse-property name="user.agent"
                           values="safari*, gecko*"/>

        <collapse-property name="gxt.user.agent"
                           values="ie8, ie9, ie10"/>
        <collapse-property name="user.agent"
                           values="ie*"/>
    </#if>
</module>