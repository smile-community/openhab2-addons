/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.velux.discovery;

import static org.openhab.binding.velux.VeluxBindingConstants.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.velux.VeluxBindingProperties;
import org.openhab.binding.velux.handler.VeluxBridgeHandlerOH2;
import org.openhab.binding.velux.things.VeluxProduct;
import org.openhab.binding.velux.things.VeluxScene;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link VeluxDiscoveryService} is responsible for discovering scenes on
 * the current Velux Bridge.
 *
 * @author Guenther Schreiner - Initial contribution.
 */
@NonNullByDefault
@Component(service = DiscoveryService.class, immediate = true, configurationPid = "discovery.velux")
public class VeluxDiscoveryService extends AbstractDiscoveryService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(VeluxDiscoveryService.class);

    /** Set of things provided by {@link VeluxDiscoveryService}. */
    public static final @Nullable Set<@NonNull ThingTypeUID> SUPPORTED_THING_TYPES = new HashSet<>(
            Arrays.asList(THING_TYPE_VELUX_SCENE, THING_TYPE_VELUX_SCENE));

    private static final int DISCOVER_TIMEOUT_SECONDS = 300;

    private static VeluxBridgeHandlerOH2 bridgeHandler = new VeluxBridgeHandlerOH2();

    public VeluxDiscoveryService() {
        super(SUPPORTED_THING_TYPES, DISCOVER_TIMEOUT_SECONDS);
        logger.trace("VeluxDiscoveryService() just initialized.");
    }

    /**
     * Initializes the {@link VeluxDiscoveryService} with a reference to the well-prepared environment with a
     * {@link VeluxBridgeHandler}.
     *
     * @param bridge Initialized Velux bridge handler.
     */
    public VeluxDiscoveryService(VeluxBridgeHandlerOH2 bridge) {
        super(SUPPORTED_THING_TYPES, DISCOVER_TIMEOUT_SECONDS);
        logger.trace("VeluxDiscoveryService({}) just initialized.", bridge);
        bridgeHandler = bridge;
    }

    /**
     * Called on component activation.
     */
    public void activate() {
        logger.trace("activate() called.");
        super.activate(null);
    }

    @Override
    public void deactivate() {
        logger.trace("deactivate() called.");
        super.deactivate();
    }

    @Override
    protected void startScan() {
        logger.trace("startScan() called.");
        logger.debug("Starting Velux discovery scan");

        // if (!bridgeHandler.isInitialized()) {
        // logger.error("VeluxDiscoveryService.startScan() cannot proceed as bridgeHandler is not yet initialized.");
        // return;
        // }

        ThingUID bridgeUID = bridgeHandler.getThing().getUID();

        logger.trace("startScan() discovering all scenes");
        for (VeluxScene scene : bridgeHandler.existingScenes().values()) {
            String sceneName = scene.getName().toString();
            logger.trace("startScan() found scene {}.", sceneName);

            String label = sceneName.replaceAll("\\P{Alnum}", "_");
            logger.trace("startScan() using name {}.", label);

            ThingUID thingUID = new ThingUID(THING_TYPE_VELUX_SCENE, bridgeUID, label);

            // @formatter:off
            DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID)
                    .withProperty(VeluxBindingProperties.PROPERTY_SCENE_NAME, sceneName)
                    .withBridge(bridgeUID)
                    .withLabel(label)
                    .build();
            // @formatter:on
            logger.trace("startScan() registering new thing {}.", discoveryResult);
            thingDiscovered(discoveryResult);
        }

        logger.trace("startScan() discovering all actuators");
        for (VeluxProduct product : bridgeHandler.existingProducts().values()) {
            String actuatorName = product.getProductUniqueIndex().toString();
            logger.trace("startScan() found actuator {}.", actuatorName);

            String label = actuatorName.replaceAll("\\P{Alnum}", "_");
            logger.trace("startScan() using name {}.", label);

            ThingUID thingUID = new ThingUID(THING_TYPE_VELUX_ACTUATOR, bridgeUID, label);

            // @formatter:off
            DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID)
                    .withProperty(VeluxBindingProperties.PROPERTY_ACTUATOR_NAME, actuatorName)
                    .withBridge(bridgeUID)
                    .withLabel(label)
                    .build();
            // @formatter:on
            logger.trace("startScan() registering new thing {}.", discoveryResult);
            thingDiscovered(discoveryResult);
        }

        logger.trace("startScan() finished.");
        stopScan();
    }

    @Override
    public synchronized void stopScan() {
        logger.debug("Stopping Velux discovery scan");
        super.stopScan();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
/**
 * end-of-VeluxDiscoveryService.java
 */
