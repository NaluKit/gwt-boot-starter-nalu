package com.github.nalukit.gwtbootstarternalu.client;

import com.github.nalukit.gwtbootstarternalu.client.ui.Routes;
import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.client.application.annotation.Version;

@Application(startRoute = Routes.ROUTE_SET_UP,
             context = AppContext.class,
             history = false,
             illegalRouteTarget = Routes.ROUTE_SET_UP)
@Version("2.7.1.0007")
interface NaluBootStarterApplication
    extends IsApplication {

}