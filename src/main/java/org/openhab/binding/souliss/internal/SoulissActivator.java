/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal;

import java.io.File;
import java.util.logging.Logger;

import org.openhab.binding.souliss.internal.network.SoulissLogger;
import org.openhab.binding.souliss.internal.network.typicals.Constants;
import org.openhab.binding.souliss.internal.network.typicals.MonitorThread;
import org.openhab.binding.souliss.internal.network.typicals.SoulissNetworkParameter;
import org.openhab.binding.souliss.internal.network.typicals.SoulissTypicals;
import org.openhab.binding.souliss.internal.network.typicals.StateTraslator;
import org.openhab.binding.souliss.internal.network.udp.SendDispatcherThread;
import org.openhab.binding.souliss.internal.network.udp.UDPServerThread;
import org.openhab.core.binding.AbstractActiveBinding;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



/**
 * Extension of the default OSGi bundle activator
 * 
 * @author Thomas.Eichstaedt-Engelen
 * @since 0.8.0
 */
public final class SoulissActivator implements BundleActivator {

	final Logger LOGGER = Logger.getLogger(Constants.LOGNAME); 
	
	String sConfigurationFileName=Constants.ConfigurationFileName_typicals_value_bytes;
	
	
	String sConfigurationFileName_commands_OHtoSOULISS=Constants.ConfigurationFileName_commands_OHtoSOULISS;
	String sConfigurationFileName_states_SOULISStoOH=Constants.ConfigurationFileName_states_SOULISStoOH;
	String sConfigurationFileName_ItemsType_OHtoSOULISS=Constants.ConfigurationFileName_ItemsType_SOULISS;
	
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		LOGGER.info("souliss binding has been started.");
		
		SoulissLogger.setup();
	
		SoulissNetworkParameter.load(sConfigurationFileName);
		StateTraslator.loadCommands(sConfigurationFileName_commands_OHtoSOULISS);
		StateTraslator.loadStates(sConfigurationFileName_states_SOULISStoOH);
		StateTraslator.loadItemsType(sConfigurationFileName_ItemsType_OHtoSOULISS);
	}


	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		LOGGER.info("souliss binding has been stopped.");
	}
	
}
