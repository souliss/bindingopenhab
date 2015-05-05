/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal.network.typicals;

import java.util.Iterator;
import java.util.Map.Entry;
import org.openhab.core.events.EventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provide to read typical state and put it on Openhab bus events
 * 
 * @author Tonino Fazio
 * @since 1.7.0
 */
public class Monitor {
	private SoulissTypicals soulissTypicalsRecipients;
	private static Logger logger = LoggerFactory.getLogger(Monitor.class);
	private EventPublisher eventPublisher;

	/**
	 * Constructor
	 * 
	 * @author Tonino Fazio
	 * @since 1.7.0
	 */
	public Monitor(SoulissTypicals typicals, int iRefreshTime,
			EventPublisher _eventPublisher) {
		soulissTypicalsRecipients = typicals;
		logger.info("Start MonitorThread");
		eventPublisher = _eventPublisher;
	}

	/**
	 * Check and sleep for REFRESH_TIME mills
	 * 
	 * @author Tonino Fazio
	 * @since 1.7.0
	 */
	public void tick() {
		// Check all souliss'typicals (items) and get only the ones that
			// has been updated
			check(soulissTypicalsRecipients);
	}

	/**
	 * Goes though the hashtable and send on the openHAB bus only the souliss'
	 * typicals that has been updated
	 * 
	 * @author Tonino Fazio
	 * @since 1.7.0
	 * @param SoulissTypicals
	 *            typicals
	 */
	private void check(SoulissTypicals typicals) {
		Iterator<Entry<String, SoulissGenericTypical>> iteratorTypicals = soulissTypicalsRecipients
				.getIterator();
		synchronized (iteratorTypicals) {
			while (iteratorTypicals.hasNext()) {
				SoulissGenericTypical typ = iteratorTypicals.next().getValue();
				if (typ.isUpdated()) {
					// Here we start the openHAB's methods used to update the
					// item values
					if (typ.getType() == Constants.Souliss_TService_NODE_TIMESTAMP) {
						// All values are float out of TIMESTAMP that is a
						// string
						logger.debug("Put on Bus Events - {} = {}", typ.getName(), ((SoulissTServiceNODE_TIMESTAMP) typ).getTIMESTAMP());
						
					} else if (typ.getType() == Constants.Souliss_T16) {
						// RGB Only
						logger.debug("Put on Bus Events - {} = {}, R={}, G={}, B={}", typ.getName(),((SoulissT16) typ).getState(), ((SoulissT16) typ).stateRED,((SoulissT16) typ).stateGREEN,((SoulissT16) typ).stateBLU);
						
					} else if (typ.getType() == Constants.Souliss_T1A) {
						logger.debug("Put on Bus Events - {} - Bit {} - RawState: {} - Bit State: {}",typ.getName(),((SoulissT1A) typ).getBit(),Integer.toBinaryString(((SoulissT1A) typ).getRawState()),((SoulissT1A) typ).getBitState()); 
					} else if (typ.getType() == Constants.Souliss_T31) {
						//T31
						logger.debug("Put on Bus Events - Command State: {} - Temperature Measured Value {} - Set Point {}",((SoulissT31) typ).getRawCommandState(), ((SoulissT31) typ).getTemperatureMeasuredValue(), ((SoulissT31) typ).getSetpointValue());
					}
					else {
						logger.debug("Put on Bus Events - {} = {}", typ.getName(), Float.toString(typ.getState()));
					}

					if (typ.getType() == Constants.Souliss_T16) {
						eventPublisher.postUpdate(typ.getName(),
								((SoulissT16) typ).getOHStateRGB());
					} else {
						if (typ.getType() == Constants.Souliss_T31){
							//qui inserimento dati per tipico 31
							SoulissT31 typ31 = (SoulissT31) typ;
							if(typ31.getsItemNameCommandState()!=null) eventPublisher.postUpdate(typ31.getsItemNameCommandState(),typ31.getOHCommandState());
							if(typ31.getsItemNameMeasuredValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameMeasuredValue(),typ31.getOHStateMeasuredValue());
							if(typ31.getsItemNameSetpointValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameSetpointValue(),typ31.getOHStateSetpointValue());
							if(typ31.getsItemNameHeatingValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameHeatingValue(),typ31.getOHState_Heating());
							if(typ31.getsItemNameCoolingValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameCoolingValue(),typ31.getOHState_Cooling());
							if(typ31.getsItemNameHeatingCoolingModeValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameHeatingCoolingModeValue(),typ31.getOHState_HeatingCoolingMode());
							if(typ31.getsItemNameFanLowValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameFanLowValue(),typ31.getOHState_Fan1());
							if(typ31.getsItemNameFanMedValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameFanMedValue(),typ31.getOHState_Fan2());
							if(typ31.getsItemNameFanHighValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameFanHighValue(),typ31.getOHState_Fan3());
							if(typ31.getsItemNameFanAutoModeValue()!=null) eventPublisher.postUpdate(typ31.getsItemNameFanAutoModeValue(),typ31.getOHState_FanAutoMode());							
						}
						
						else {
						eventPublisher.postUpdate(typ.getName(),
								typ.getOHState());
					}
					typ.resetUpdate();
				}
			}
		}
	}
}
}
