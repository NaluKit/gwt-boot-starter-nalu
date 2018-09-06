<a title="Gitter" href="https://gitter.im/mvp4g/mvp4g"><img src="https://badges.gitter.im/Join%20Chat.svg"></a>

# Nalu Project Generator

## Motivation

Setting up a Nalu project can be difficult, especially if you are not familiar with Nalu. There are a lot of things
to do before it is possible to run the project and check wheather things are working or not.

To improve the process of setting up a GWT project using Nalu, we created a
[Nalu project generator](http://www.mvp4g.org/gwt-boot-starter-nalu/GwtBootStarterNalu.html). Once you have configurated and generated the project, you will get a zip file containing the - ready to import - Nalu project.

Keep in mind, Nalu helps you to structure your application. Navigation, dependencies and confirmation will be generated and
work. It is not a widget library. So, the views are nearly empty. It is up to you to implement the views.

**Important note:** Nalu requires Java 8!

**Note:**

The implementation of the generator uses Nalu, GXT and is based on GWT 2.8.2! The generated project requires in this
version GWT 2.8.2 and Java 8. The generated Nalu source code will work with J2CL / GWT 3 once all dependies to GWT
2.8.2 are replaced. You have to choose a widget set, that will work with J2CL / GWT 3 to be compatible with J2CL / GWT 3!

**The generator nor Nalu will make any existing source code compatible with J2CL / GWT 3. There is no magic to
transfom GWT 2.x code to work with GWT 3!**

## Configuration

### Project Meta Data

In this area the Group Id, the Artifact Id and GWT version of the project is defined.

![Project Meta Data](https://github.com/mvp4g/gwt-boot-starter-nalu/blob/master/etc/images/ProjectMetaData.png?raw=true)

#### Group Id

The group id is used as the group id inside the POM. Also it will be used as the name of the package. The group id can
not be empty. You can enter letters [a - z], numbers [0 - 9] and dots [.].

#### Artifact Id

The artifact id is used as the artifact id inside the POM. It is also used as the name of the GWT module. The group id
can not be empty. You can enter letters [a - z, A - Z] and numbers [0 - 9].

#### GWT Version

The GWT version of the generated project. (This voersion of the generator supports only GWT 2.8.2.)

#### Widget Set

The widget library that will be used for the code generation.
At the moment the Nalu Example generator supports:

- Domino-UI

- Elemento

- native GWT widgets

- GXT GPL 4.0.0

**Note:** The native GWT and the GXT GPL 4.0.0 widget sets will not work with J2CL / GWT 3!

### Application Meta Data

In this area the features of Nalu for generated the project are defined.

![Project Meta Data](https://github.com/mvp4g/gwt-boot-starter-nalu/blob/master/etc/images/ApplicationMetaData.png?raw=true)

#### Application Loader
If checked, a [application loader](https://github.com/mvp4g/nalu/wiki/02.-Application-Loader) will be added to the project source code.
More information can be found [here](https://github.com/mvp4g/nalu/wiki/03.-Application-Loader).

#### Debug Support
**TODO: Link**
If checked, the [Debug](https://github.com/mvp4g/nalu/wiki/02.-Application#debug-annotation) annotation will be
aded to the applicaiton interface.

### Screen Meta Data

In this area the screens are defined. A screen in Nalu is a combination of a controller and a component. There are five
predefined screens called Screen01, Screen02, Screen03, Screen04 and screen05. The generator needs at least one screen to generate a fully working project.

![Project Meta Data](https://github.com/mvp4g/gwt-boot-starter-nalu/blob/master/etc/images/ScreenMetaData.png?raw=true)The function of the tool bar buttons:

- the first buttons adds another screen

- the second button edits the selected screen

- the third button removes the selected screen.

In case you add or edit a screen, a window will open:

![Add or edit screen data](https://github.com/mvp4g/gwt-boot-starter-nalu/blob/master/etc/images/ScreenWindowMetaData.png?raw=true)

Using this window, you can:

* name of the component: this name will be used as package name, the controller- and component-name, etc
* route: this route is the url token binded to a controller
* use this screen as start screen of the application (if there is no history token, this will be the screen, that will
  be shown at start of the application)
* enable confirmation in case the controller will be stopped
