package com.github.nalukit.gwtbootstarternalu.client;

import com.github.nalukit.gwtbootstarternalu.client.ui.Routes;
import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;

@Application(startRoute = Routes.ROUTE_SET_UP, context = AppContext.class, history = false)
interface NaluBootStarterApplication
    extends IsApplication {

}