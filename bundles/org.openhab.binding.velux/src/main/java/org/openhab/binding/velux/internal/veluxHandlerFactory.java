/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.velux.internal;

import java.util.Hashtable;

import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.velux.discovery.VeluxDiscoveryService;
import org.openhab.binding.velux.handler.VeluxBridgeHandlerOH2;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link VeluxHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Guenther Schreiner - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, immediate = true, name = "binding.velux")
public class VeluxHandlerFactory extends BaseThingHandlerFactory {

    private final Logger logger = LoggerFactory.getLogger(VeluxHandlerFactory.class);
    private ServiceRegistration<?> discoveryServiceReg;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        boolean result = org.openhab.binding.velux.VeluxBindingConstants.SUPPORTED_THINGS_BRIDGE.contains(thingTypeUID)
                || org.openhab.binding.velux.VeluxBindingConstants.SUPPORTED_THINGS_ITEMS.contains(thingTypeUID);
        logger.trace("supportsThingType({}) called and returns {}.", thingTypeUID, result);
        return result;
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        logger.trace("createHandler({}) called.", thing.getLabel());

        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        // Handle Bridge creation as 1st choice
        if (org.openhab.binding.velux.VeluxBindingConstants.SUPPORTED_THINGS_BRIDGE.contains(thingTypeUID)) {
            logger.trace("Creating a VeluxBridgeHandler for thing '{}'.", thing.getUID());
            VeluxBridgeHandlerOH2 handler = new VeluxBridgeHandlerOH2((Bridge) thing);
            registerDeviceDiscoveryService(handler);
            return handler;
        }

        else if (org.openhab.binding.velux.VeluxBindingConstants.SUPPORTED_THINGS_ITEMS.contains(thingTypeUID)) {
            logger.trace("Creating a VeluxHandler for thing '{}'.", thing.getUID());
            // ToDo
            // return new VeluxHandler(thing);
            return null;

        } else {
            logger.warn("ThingHandler not found for {}.", thingTypeUID);
            return null;
        }
    }

    private void registerDeviceDiscoveryService(VeluxBridgeHandlerOH2 bridgeHandler) {
        VeluxDiscoveryService discoveryService = new VeluxDiscoveryService(bridgeHandler);
        discoveryServiceReg = bundleContext.registerService(DiscoveryService.class.getName(), discoveryService,
                new Hashtable<String, Object>());
    }

    @Override
    protected void removeHandler(ThingHandler thingHandler) {
        logger.trace("removeHandler({}) called.", thingHandler.toString());

        if (discoveryServiceReg != null && thingHandler.getThing().getThingTypeUID()
                .equals(org.openhab.binding.velux.VeluxBindingConstants.THING_TYPE_BRIDGE)) {
            discoveryServiceReg.unregister();
            discoveryServiceReg = null;
        }
        super.removeHandler(thingHandler);
    }

}
