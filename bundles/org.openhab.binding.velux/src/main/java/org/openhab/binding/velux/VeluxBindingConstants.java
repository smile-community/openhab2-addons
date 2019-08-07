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
package org.openhab.binding.velux;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link VeluxBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * For an in-depth view of the available Item type with description of parameters, take a look onto
 * {@link VeluxItemType}.
 *
 * @author Guenther Schreiner - Initial contribution
 * @since 1.13.0
 */
@NonNullByDefault
public class VeluxBindingConstants {

    /** Basic binding identification */
    public static final String BINDING_ID = "velux";

    // Id of support bridge
    public static final String BRIDGE_TYPE = "klf200";
    public static final String THING_VELUX_SCENE = "scene";
    public static final String THING_VELUX_ACTUATOR = "actuator";

    // List of all Bridge Type UIDs
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, BRIDGE_TYPE);

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_VELUX_SCENE = new ThingTypeUID(BINDING_ID, THING_VELUX_SCENE);
    public static final ThingTypeUID THING_TYPE_VELUX_ACTUATOR = new ThingTypeUID(BINDING_ID, THING_VELUX_ACTUATOR);

    // Definitions of different set of Things
    public static final Set<ThingTypeUID> SUPPORTED_THINGS_BRIDGE = new HashSet<>(Arrays.asList(THING_TYPE_BRIDGE));
    public static final Set<ThingTypeUID> SUPPORTED_THINGS_ITEMS = new HashSet<>(
            Arrays.asList(THING_TYPE_VELUX_SCENE, THING_TYPE_VELUX_ACTUATOR));

    // List of all Channel ids

    // List of all bridge channel ids
    public static final String CHANNEL_BRIDGE_STATUS = "status";
    public static final String CHANNEL_BRIDGE_RELOAD = "reload";
    public static final String CHANNEL_BRIDGE_TIMESTAMP = "timestamp";
    public static final String CHANNEL_BRIDGE_DO_DETECTION = "doDetection";
    public static final String CHANNEL_BRIDGE_FIRMWARE = "firmware";
    public static final String CHANNEL_BRIDGE_IPADDRESS = "ipAddress";
    public static final String CHANNEL_BRIDGE_SUBNETMASK = "subnetMask";
    public static final String CHANNEL_BRIDGE_DEFAULTGW = "defaultGW";
    public static final String CHANNEL_BRIDGE_DHCP = "DHCP";
    public static final String CHANNEL_BRIDGE_WLANSSID = "WLANSSID";
    public static final String CHANNEL_BRIDGE_WLANPASSWORD = "WLANPassword";
    public static final String CHANNEL_BRIDGE_PRODUCTS = "products";
    public static final String CHANNEL_BRIDGE_SCENES = "scenes";
    public static final String CHANNEL_BRIDGE_CHECK = "check";
    public static final String CHANNEL_BRIDGE_SHUTTER = "shutter";

    // List of all scene channel ids
    public static final String CHANNEL_SCENE_ACTION = "action";
    public static final String CHANNEL_SCENE_SILENTMODE = "silentMode";

    // List of all actuator channel ids
    public static final String CHANNEL_ACTUATOR_SERIAL = "serial";

    /** Helper definitions */
    public static final String BINDING_KVPAIR_SEPARATOR = ";";
    public static final String BINDING_KV_SEPARATOR = "=";
    public static final String BINDING_VALUE_SEPARATOR = "#";
    public static final String BINDING_VALUES_SEPARATOR = ",";
    public static final String BINDING_ID_THING = "thing";
    public static final String BINDING_ID_CHANNEL = "channel";

}
