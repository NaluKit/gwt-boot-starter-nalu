/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.bootstarternalu.server.generator;

public class GeneratorConstants {
  
  public static final String APPLICAITON    = "Application";
  public static final String CONTEXT        = "Context";
  public static final String LOADER         = "Loader";
  public final static String COPYRIGHT_HTML = String.format("<!--\n" +
                                                            "  ~ Copyright (C) 2018 - 2019 Frank Hossfeld <frank.hossfeld@googlemail.com>\n" +
                                                            "  ~\n" +
                                                            "  ~ Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                                            "  ~  you may not use this file except in compliance with the License.\n" +
                                                            "  ~ You may obtain a copy of the License at\n" +
                                                            "  ~\n" +
                                                            "  ~ http://www.apache.org/licenses/LICENSE-2.0\n" +
                                                            "  ~\n" +
                                                            "  ~ Unless required by applicable law or agreed to in writing, software\n" +
                                                            "  ~ distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                                            "  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                                            "  ~ See the License for the specific language governing permissions and\n" +
                                                            "  ~ limitations under the License.\n" +
                                                            "  ~\n" +
                                                            "  -->");
  public final static String COPYRIGHT_JAVA = String.format("Copyright (C) 2018 - 2019 Frank Hossfeld <frank.hossfeld@googlemail.com>%n" +
                                                            "%n" +
                                                            "Licensed under the Apache License, Version 2.0 (the \"License\");%n" +
                                                            "you may not use this file except in compliance with the License.%n" +
                                                            "You may obtain a copy of the License at%n" +
                                                            "%n" +
                                                            "http://www.apache.org/licenses/LICENSE-2.0%n" +
                                                            "%n" +
                                                            "Unless required by applicable law or agreed to in writing, software%n" +
                                                            "distributed under the License is distributed on an \"AS IS\" BASIS,%n" +
                                                            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.%n" +
                                                            "See the License for the specific language governing permissions and%n" +
                                                            "limitations under the License.%n");
  public static final String LINE_BREAK     = "\n";
  
  //  public static final String AS_WIDGET_TEXT = "Nalu does not know Widget-, Element- or any other GWT specific class. So, the\n" +
  //                                              "presenter have to manage the widget by themselves. The method will\n" +
  //                                              "enable the presenter to get the view. (In our case it is a\n" +
  //                                              "GWT widget)\n" +
  //                                              "\n" +
  //                                              "@return The shell widget\n";
}
