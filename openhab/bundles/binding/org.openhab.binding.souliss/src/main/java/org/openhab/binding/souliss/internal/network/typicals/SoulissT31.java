/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;

/**
 * Typical T31 Thermostat
 * 
 * @author Tonino Fazio
 * @since 1.7.0
 */
public class SoulissT31 extends SoulissGenericTypical {
	
	private String	UseOfSlot;
	
	private short sRawCommandState;
	private	Float TemperatureSetpointValue;
	private Float TemperatureMeasuredValue;
	
	private String sItemNameTemperatureMeasuredValue;
	private String sItemNameCommandState;
	private String sItemNameTemperatureSetpointValue;
	
	public String getUseOfSlot() {
		return UseOfSlot;
	}

	public void setUseOfSlot(String useOfSlot) {
		UseOfSlot = useOfSlot;
	}

	/**
	 * Typical T31
	 * 
	 * @param _datagramsocket
	 * @param sSoulissNodeIPAddress
	 * @param sSoulissNodeIPAddressOnLAN
	 * @param iIDNodo
	 * @param iSlot
	 * @param sOHType
	 */

	// Parameters sSoulissNode, iSlot, Type and State are stored in the class
	public SoulissT31(DatagramSocket _datagramsocket,
			String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot,
			String sOHType, String UseOfSlot) {
		super();
		
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T11);
		this.setNote(sOHType);
		this.setUseOfSlot(UseOfSlot);
	}

	/**
	 * Send a command as hexadecimal, e.g.: 
	 * Souliss_T3n_InSetPoint 0x01
	 * Souliss_T3n_OutSetPoint 0x02
	 * Souliss_T3n_AsMeasured 0x03
	 * Souliss_T3n_Cooling ----- 0x04
	 * Souliss_T3n_Heating ----- 0x05
	 * Souliss_T3n_FanOff ----- 0x06
	 * Souliss_T3n_FanLow ----- 0x07
	 * Souliss_T3n_FanMed ----- 0x08
	 * Souliss_T3n_FanHigh ----- 0x09
	 * Souliss_T3n_FanAuto ----- 0x0A
	 * Souliss_T3n_FanManual 0x0B
	 * Souliss_T3n_SetTemp ----- 0x0C
	 * Souliss_T3n_ShutDown 0x0D
	 *  
	 * @param command
	 */
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,
				SoulissNetworkParameter.IPAddressOnLAN,
				this.getSoulissNodeID(), this.getSlot(), command);
	}

	@Override
	/**
	 * Returns a type used by openHAB to show the actual state of the souliss' typical
	 * @return org.openhab.core.types.State
	 */
	public State getOHState() {
		String sOHState = StateTraslator.statesSoulissToOH(this.getNote(),
				this.getType(), (short) this.getState());
		if (sOHState != null) {
			if(getUseOfSlot().equals(Constants.Souliss_T31_Use_Of_Slot_SETPOINT)){
				//return a number
				return return_a_DecimalType(sOHState);
			} else if(getUseOfSlot().equals(Constants.Souliss_T31_Use_Of_Slot_SWITCH)){
				//return a switch
				return OnOffType.valueOf(sOHState);
			} else if(getUseOfSlot().equals(Constants.Souliss_T31_Use_Of_Slot_VALUE)){
				//return a number
				return return_a_DecimalType(sOHState);
			}
		}
		return null;
	}

	
	private DecimalType return_a_DecimalType(String sOHState ){
		if (sOHState == null) {
			if (!Float.isNaN(this.getState())) {
				return DecimalType.valueOf(Float.toString(this.getState()));
			} else
				return null;
		} else
			return DecimalType.valueOf(sOHState);
	}

	
	public State getOHStateTemperatureMeasuredValue() {
		String sOHState = StateTraslator.statesSoulissToOH(this.getNote(),
				this.getType(), (short) this.getState());
		if (sOHState == null) {
			if (!Float.isNaN(this.getState())) {
				return DecimalType.valueOf(Float.toString(this.getTemperatureMeasuredValue()));
			} else
				return null;
		} else
			return DecimalType.valueOf(sOHState);
	}

	public State getOHStateTemperatureSetpointValue() {
		String sOHState = StateTraslator.statesSoulissToOH(this.getNote(),
				this.getType(), (short) this.getState());
		if (sOHState == null) {
			if (!Float.isNaN(this.getState())) {
				return DecimalType.valueOf(Float.toString(this.getTemperatureMeasuredValue()));
			} else
				return null;
		} else
			return DecimalType.valueOf(sOHState);
	}

	public State getOHCommandState() {
		String sOHState = StateTraslator.statesSoulissToOH(this.getNote(), this.getType(), (short) sRawCommandState);
		if (sOHState != null) {
			if (this.getNote().equals("ContactItem"))
				return OpenClosedType.valueOf(sOHState);
			else
				return OnOffType.valueOf(sOHState);
		}
		return null;
	}

	public void setRawCommandState(short sRawCommandState) {
		this.sRawCommandState = sRawCommandState;
	}
	public String getsItemNameCommandState() {
		return sItemNameCommandState;
	}

	public void setsItemNameCommandState(String sItemNameCommandState) {
		this.sItemNameCommandState = sItemNameCommandState;
	}

	public String getsItemNameTemperatureMeasuredValue() {
		return sItemNameTemperatureMeasuredValue;
	}

	public void setsItemNameTemperatureMeasuredValue(
			String sItemNameTemperatureMeasuredValue) {
		this.sItemNameTemperatureMeasuredValue = sItemNameTemperatureMeasuredValue;
	}

	public String getsItemNameTemperatureSetpointValue() {
		return sItemNameTemperatureSetpointValue;
	}

	public void setsItemNameTemperatureSetpointValue(
			String sItemNameTemperatureSetpointValue) {
		this.sItemNameTemperatureSetpointValue = sItemNameTemperatureSetpointValue;
	}

	public Float getTemperatureMeasuredValue() {
		return TemperatureMeasuredValue;
	}

	public void setTemperatureMeasuredValue(Float temperatureMeasuredValue) {
		TemperatureMeasuredValue = temperatureMeasuredValue;
	}
	public short getsRawCommandState() {
		return sRawCommandState;
	}

	public void setsRawCommandState(short sRawCommandState) {
		this.sRawCommandState = sRawCommandState;
	}
	
	public Float getTemperatureSetpointValue() {
		return TemperatureSetpointValue;
	}

	public void setTemperatureSetpointValue(Float temperatureSetpointValue) {
		TemperatureSetpointValue = temperatureSetpointValue;
	}
}
