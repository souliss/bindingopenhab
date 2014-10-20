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
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.openhab.binding.souliss.internal.network.typicals.Constants;
import org.openhab.binding.souliss.internal.network.typicals.SoulissNetworkParameter;
import org.openhab.binding.souliss.internal.network.typicals.StateTraslator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.CompoundEnumeration;



/**
 * Extension of the default OSGi bundle activator
 * 
 * @author Thomas.Eichstaedt-Engelen
 * @since 0.8.0
 */
public final class SoulissActivator implements BundleActivator {

	private static Logger LOGGER = LoggerFactory.getLogger(SoulissActivator.class); 
	
	String sConfigurationFileName=Constants.ConfigurationFileName_typicals_value_bytes;
	
	
	String sConfigurationFileName_commands_OHtoSOULISS=Constants.ConfigurationFileName_commands_OHtoSOULISS;
	String sConfigurationFileName_states_SOULISStoOH=Constants.ConfigurationFileName_states_SOULISStoOH;
	String sConfigurationFileName_ItemsType_OHtoSOULISS=Constants.ConfigurationFileName_ItemsType_SOULISS;
	
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		LOGGER.info("souliss binding has been started.");
		
        InputStream is = getClass().getResourceAsStream("/"+sConfigurationFileName);
		LOGGER.info("Load parameter from file: " + sConfigurationFileName);  
		SoulissNetworkParameter.load(is);
		
		LOGGER.info("Load parameter from file: " + sConfigurationFileName_commands_OHtoSOULISS);  
		is = getClass().getResourceAsStream("/"+sConfigurationFileName_commands_OHtoSOULISS);
		StateTraslator.loadCommands(is);
		
		LOGGER.info("Load parameter from file: " + sConfigurationFileName_states_SOULISStoOH);  
		is = getClass().getResourceAsStream("/"+sConfigurationFileName_states_SOULISStoOH);
		StateTraslator.loadStates(is);
		
		LOGGER.info("Load parameter from file: " + sConfigurationFileName_ItemsType_OHtoSOULISS);  
		is = getClass().getResourceAsStream("/"+sConfigurationFileName_ItemsType_OHtoSOULISS);
		StateTraslator.loadItemsType(is);
	}


	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		LOGGER.info("souliss binding has been stopped.");
	}
	
}
