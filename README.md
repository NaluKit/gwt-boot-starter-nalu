<a title="Gitter" href="https://gitter.im/Nalukit42/Lobby"><img src="https://badges.gitter.im/Join%20Chat.svg"></a>

# Nalu Project Generator

Nalu is a routing based application framework for GWT 2, GWT 3 & J2CL. More informations about Nalu can be found [here](https://github.com/nalukit/nalu).

## Motivation

Setting up a Nalu project can be difficult, especially if you are not familiar with Nalu. There are a lot of things
to do before it is possible to run the project and check whether things are working or not.

To improve the process of setting up a project using Nalu, the
[Nalu project generator](http://www.mvp4g.org/boot-starter-nalu/BootStarterNalu.html) was created. Once you have configured and generated the project, you will get a zip file containing the - ready to import - Nalu project.

Keep in mind, Nalu helps you to structure your application. Navigation, dependencies and confirmation will be generated and
work. It is not a widget library. So, the components are nearly empty. It is up to you to implement the components.

**Important note:** Nalu requires Java 8!

**Note:**

The implementation of the generator uses Nalu, [Domino-UI](https://github.com/DominoKit/domino-ui) and is based on GWT 2.8.2! The generated project requires GWT 2.8.2 and Java 8. The generated Nalu source code will work with J2CL / GWT 3 once all dependencies to GWT
2.8.2 are replaced. You have to choose a widget set, that will work with J2CL / GWT 3 to be compatible with J2CL / GWT 3!

**The generator nor Nalu will make any existing source code compatible with J2CL / GWT 3. There is no magic to
transform GWT 2.x code to work with GWT 3!**

## Configuration

### Project Meta Data

In this area the Group Id, the Artifact Id and GWT version of the project is defined.

![Project Meta Data](https://github.com/nalukit/gwt-boot-starter-nalu/blob/master/etc/images/ProjectMetaData.png?raw=true)

#### Group Id

The group id is used as the group id inside the POM. Also it will be used as the name of the package. The group id can
not be empty. You can enter letters [a - z], numbers [0 - 9] and dots [.].

#### Artifact Id

The artifact id is used as the artifact id inside the POM. It is also used as the name of the GWT module. The artifact id
can not be empty. You can enter letters [a - z, A - Z] and numbers [0 - 9].

#### GWT Version

The GWT version of the generated project. (This version of the generator supports only GWT 2.8.2.)

#### Widget Set

The widget library that will be used for the code generation.
At the moment the Nalu Example generator supports:

- [Domino-UI](https://github.com/DominoKit/domino-ui)

- [Elemento](https://github.com/hal/elemento)

- [native GWT widgets](http://www.gwtproject.org/)

- [GXT GPL 4.0.0](https://www.sencha.com/products/gxt/#overview)

**Note:** The native GWT and the GXT GPL 4.0.0 widget sets will not work with J2CL / GWT 3!

#### Maven

The project generator supports a single Maven project and a multi module Maven project (based on Thomas Broyer's artifact).

### Application Meta Data

In this area the features of Nalu for generated the project are defined.

![Project Meta Data](https://github.com/nalukit/gwt-boot-starter-nalu/blob/master/etc/images/ApplicationMetaData.png?raw=true)

#### Application Loader
If checked, a application loader will be added to the project source code.
More information can be found [here](https://github.com/NaluKit/nalu/wiki/05.-Application-Loader).

#### Debug Support
If checked, the [Debug](https://github.com/NaluKit/nalu/wiki/04.-Application#debug-annotation) annotation will be
added to the application interface.

#### Generate Login Screen
Generates a login screen and a filter, that checks, if the user is logged in. In case of reloading, the project will always route to the login screen.

#### Generate Error Screen
Generates a error screen. Entering a illegal route in the URL will make the error screen visible.

#### Use hash in URL

If checked, the code needed to tell Nalu to use a hash inside the URL is generated. In case unchecked, a redirect filter is generated and the flag to tell Nalu to work with hash less URL is set.

### Screen Meta Data

In this area the screens are defined. A screen in Nalu is a combination of a controller and a component. There are five
predefined screens called Screen01, Screen02, Screen03, Screen04 and Screen05. The generator needs at least one screen to generate a fully working project.

![Project Meta Data](https://github.com/nalukit/gwt-boot-starter-nalu/blob/master/etc/images/ScreenMetaData.png?raw=true)

* To add a new screen, press the ADD button on the top right side of the Screen Meta Data Card.

* To delete a screen, press the trash can icon on the right side of the row.

* To change a component name, click in the component name cell and edit the value

* To change the route, click in the route cell of the row and edit the value

* To change the start screen, press 'Yes' in the start screen cell of the row, that should be the start screen

* To generate confirmation code, click 'yes' inside the confirmation row.

## Development

### Build application
`mvn package`

### Run application
`mvn gwt:codeserver` (for devmode)

`mvn spring-boot:run`

Use this URL to run the application: `http://localhost:8080/GwtBootStarterNalu.html`
