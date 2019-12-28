<!doctype html>

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

<!-- The DOCTYPE declaration above will set the     -->
<!-- browser's rendering engine into                -->
<!-- "Standards Mode". Replacing this declaration   -->
<!-- with a "Quirks Mode" doctype is not supported. -->

<html>
    <head>
        <meta http-equiv="content-type"
              content="text/html; charset=UTF-8">

        <!-- Any title is fine (please update)               -->
        <title>Nalu Boot Starter Project ==> ElHossProject</title>

        <#if widgetLibrary == "DOMINO_UI">
            <!-- Consider inlining CSS to reduce the number of requested files -->
            <link type="text/css"
                  rel="stylesheet"
                  href="${artifactId}/css/domino-ui.css">
            <link type="text/css"
                  rel="stylesheet"
                  href="${artifactId}/css/themes/all-themes.css">

            <!-- This script is for IE11-support only. -->
            <script type="text/javascript"
                    language="javascript"
                    src="${artifactId}/polyfill/polyfill-ie11.js"></script>
        <#else>
            <!-- Consider inlining CSS to reduce the number of requested files -->
        <link type="text/css"
              rel="stylesheet"
              href="${artifactId}.css">
        </#if>

        <!-- This script loads your compiled module.   -->
        <!-- If you add any GWT meta tags, they must   -->
        <!-- be added before this line.                -->
        <script type="text/javascript"
                language="javascript"
                src="${artifactId}/${artifactId}.nocache.js"></script>
    </head>

    <!-- The body can have arbitrary html, or      -->
    <!-- you can leave the body empty if you want  -->
    <!-- to create a completely dynamic UI.        -->
    <body>
        <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
        <noscript>
            Your web browser must have JavaScript enabled
            in order for this application to display correctly.
        </noscript>
    </body>
</html>
