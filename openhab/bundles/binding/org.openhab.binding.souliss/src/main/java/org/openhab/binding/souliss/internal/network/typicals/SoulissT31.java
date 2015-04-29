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
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.State;

/**
 * Typical T31 Thermostat
 * 
 * @author Tonino Fazio
 * @since 1.7.0
 */
public class SoulissT31 extends SoulissGenericTypical {

	private String sItemNameCommandState;
	private String sItemTypeCommandState;
	
	private String sItemNameSetpointValue;
	private String sItemTypeSetpointValue;
	
	private String sItemNameMeasuredValue;
	private String sItemTypeMeasuredValue;
	
	private String sItemNameSetAsMeasured;
	private String sItemTypeSetAsMeasured;
	
	
	private String sItemTypeHeatingValue;	
	private String sItemNameHeatingValue;
	
	private String sItemTypeCoolingValue;	
	private String sItemNameCoolingValue;
	
	private String sItemTypeFan1Value;	
	private String sItemNameFan1Value;
	
	private String sItemTypeFan2Value;	
	private String sItemNameFan2Value;
	
	private String sItemTypeFan3Value;	
	private String sItemNameFan3Value;
	
	private String sItemTypeManualModeValue;	
	private String sItemNameManualModeValue;
	
	private String sItemTypeHeatingModeValue;	
	private String sItemNameHeatingModeValue;

	private short sRawCommandState;
	private Float TemperatureSetpointValue;
	private Float MeasuredValue;
	private boolean HeatingValue;
	private boolean CoolingValue;
	private boolean Fan1Value;
	private boolean Fan2Value;
	private boolean Fan3Value;
	private boolean ManualModeValue;
	private boolean HeatingModeValue;
	
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
			String sOHType) {
		super();

		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T31);
		//this.setNote(sOHType);  //eliminato, perchè il tipo di item viene impostato in fase di inserimento dei dati nel tipico, dentro SoulissGenericBindingProvider, metodo processBindingConfiguration
	}

	/**
	 * Send a command as hexadecimal, e.g.: Souliss_T3n_InSetPoint 0x01
	 * Souliss_T3n_OutSetPoint 0x02 Souliss_T3n_AsMeasured 0x03
	 * Souliss_T3n_Cooling ----- 0x04 Souliss_T3n_Heating ----- 0x05
	 * Souliss_T3n_FanOff ----- 0x06 Souliss_T3n_FanLow ----- 0x07
	 * Souliss_T3n_FanMed ----- 0x08 Souliss_T3n_FanHigh ----- 0x09
	 * Souliss_T3n_FanAuto ----- 0x0A Souliss_T3n_FanManual 0x0B
	 * Souliss_T3n_SetTemp ----- 0x0C Souliss_T3n_ShutDown 0x0D
	 * 
	 * @param command
	 */
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,
				SoulissNetworkParameter.IPAddressOnLAN,
				this.getSoulissNodeID(), this.getSlot(), command);
	}

	/**
	 * Send a command with parameter 
	 * 
	 * @param command
	 */
	public void CommandSEND(short command, short B1, short B2) {
		SoulissCommGate.sendFORCEFrameT31SetPoint(SoulissNetworkParameter.datagramsocket,
				SoulissNetworkParameter.IPAddressOnLAN,
				this.getSoulissNodeID(), this.getSlot(), command, B1, B2);
}
	DA INVIARE TUTTI COMANDI
	
	#define Souliss_T3n_InSetPoint			0x01
	#define Souliss_T3n_OutSetPoint			0x02
	#define Souliss_T3n_AsMeasured			0x03
	#define Souliss_T3n_Cooling				0x04
	#define Souliss_T3n_Heating				0x05
	#define Souliss_T3n_FanOff				0x06
	#define Souliss_T3n_FanLow				0x07
	#define Souliss_T3n_FanMed				0x08
	#define Souliss_T3n_FanHigh				0x09
	#define Souliss_T3n_FanAuto				0x0A
	#define Souliss_T3n_FanManual			0x0B
	#define Souliss_T3n_SetTemp				0x0C
	#define Souliss_T3n_ShutDown			0x0D
	

	public State getOHState_Heating(){
		return getOHState(getsItemNameHeatingValue(), getsItemTypeHeatingValue(),isHeatingValue());
	}
	
	public State getOHState_Cooling(){
		return getOHState(getsItemNameCoolingValue(), getsItemTypeCoolingValue(),isCoolingValue());
	}
	
	public State getOHState_Fan1(){
		return getOHState(getsItemNameFan1Value(), getsItemTypeFan1Value(),isFan1Value());
	}
	
	public State getOHState_Fan2(){
		return getOHState(getsItemNameFan2Value(), getsItemTypeFan2Value(),isFan2Value());
	}
	
	public State getOHState_Fan3(){
		return getOHState(getsItemNameFan3Value(), getsItemTypeFan3Value(),isFan3Value());
	}

	public State getOHState_ManualMode(){
		return getOHState(getsItemNameManualModeValue(), getsItemTypeManualModeValue(),isManualModeValue());
	}

	public State getOHState_HeatingMode(){
		return getOHState(getsItemNameHeatingModeValue(), getsItemTypeHeatingModeValue(),isHeatingModeValue());
	}
	
	/**
	 * Returns a type used by openHAB to show the actual state of the souliss' typical
	 * @return org.openhab.core.types.State
	 */
	public State getOHState(String sItemName, String sType, boolean bState) {
		short shortState;
		if (bState)
			shortState=Constants.Souliss_T1n_OnCoil;
		else
			shortState=Constants.Souliss_T1n_OffCoil;
			
		String sOHState = StateTraslator.statesSoulissToOH(sType + "_" + sItemName,this.getType(), shortState);
		if (sOHState != null) {
			if (sType.equals("ContactItem"))
				return OpenClosedType.valueOf(sOHState);
			else
				return OnOffType.valueOf(sOHState);
		}
		return null;
	}

	private DecimalType return_a_DecimalType(String sOHState) {
		if (sOHState == null) {
			if (!Float.isNaN(this.getState())) {
				return DecimalType.valueOf(Float.toString(this.getState()));
			} else
				return null;
		} else
			return DecimalType.valueOf(sOHState);
	}

	public State getOHStateMeasuredValue() {
		String sOHState = StateTraslator.statesSoulissToOH(this.getsItemTypeMeasuredValue(),
				this.getType(), (short) this.getState());
		if (sOHState == null) {
			if (!Float.isNaN(this.getState())) {
				if (this.getTemperatureMeasuredValue() != null)
					return DecimalType.valueOf(Float.toString(this
							.getTemperatureMeasuredValue()));
				else
					return null;
			} else
				return null;
		} else
			return DecimalType.valueOf(sOHState);
	}

	public State getOHStateSetpointValue() {
		String sOHState = StateTraslator.statesSoulissToOH(this.getsItemTypeSetpointValue(),
				this.getType(), (short) this.getState());
		if (sOHState == null) {
			if (!Float.isNaN(this.getState())) {
				if (this.getSetpointValue() != null)
					return DecimalType.valueOf(Float.toString(this
							.getSetpointValue()));
				else
					return null;
			} else
				return null;
		} else
			return DecimalType.valueOf(sOHState);
	}

	public State getOHCommandState() {
		return StringType.valueOf(String.valueOf(sRawCommandState));
	}

	
	
	
	public String getsItemNameCommandState() {
		return sItemNameCommandState;
	}

	public void setsItemNameStateControlValue(String sItemNameCommandState) {
		this.sItemNameCommandState = sItemNameCommandState;
	}

	public String getsItemNameMeasuredValue() {
		return sItemNameMeasuredValue;
	}

	public void setsItemNameMeasuredValue(
			String sItemNameMeasuredValue) {
		this.sItemNameMeasuredValue = sItemNameMeasuredValue;
	}

	public String getsItemNameSetpointValue() {
		return sItemNameSetpointValue;
	}

	public void setsItemNameSetpointValue(
			String sItemNameSetpointValue) {
		this.sItemNameSetpointValue = sItemNameSetpointValue;
	}

	public Float getTemperatureMeasuredValue() {
		return MeasuredValue;
	}

	public Float getSetpointValue() {
		return TemperatureSetpointValue;
	}

	public short getRawCommandState() {
		return sRawCommandState;
	}

	public void setRawCommandState(short sRawCommandState) {
		this.sRawCommandState = sRawCommandState;
		setUpdatedTrue();
	}

	public void setMeasuredValue(Float MeasuredValue) {
		this.MeasuredValue = MeasuredValue;
		setUpdatedTrue();
	}

	public void setSetpointValue(Float temperatureSetpointValue) {
		TemperatureSetpointValue = temperatureSetpointValue;
		setUpdatedTrue();
	}

	public void setsItemTypeSetpointValue(String sNote) {
		this.sItemTypeSetpointValue=sNote;
	}

	public void setsItemTypeStateControlValue(String sNote) {
		this.sItemTypeCommandState=sNote;
	}

	public void setsItemTypeMeasuredValue(String sNote) {
		this.sItemTypeMeasuredValue=sNote;
	}
	public String getsItemTypeMeasuredValue() {
		return sItemTypeMeasuredValue;
	}

	public String getsItemTypeSetpointValue() {
		return sItemTypeSetpointValue;
	}

	public String getsItemTypeStateControlValue() {
	     return sItemTypeCommandState;
	}

	public Object getsItemNameSetAsMeasured() {
		return sItemNameSetAsMeasured;
	}

	public void setsItemNameSetAsMeasured(String sItemNameSetAsMeasured) {
		this.sItemNameSetAsMeasured = sItemNameSetAsMeasured;
	}

	public Object getsItemTypeSetAsMeasured() {
		return sItemTypeSetAsMeasured;
	}

	public void setsItemTypeSetAsMeasured(String sItemNameSetAsMeasured) {
		this.sItemTypeSetAsMeasured = sItemNameSetAsMeasured;
	}

	public String getsItemNameFan1Value() {
		return sItemNameFan1Value;
	}

	public void setsItemNameFan1Value(String sItemNameFan1Value) {
		this.sItemNameFan1Value = sItemNameFan1Value;
	}

	public String getsItemTypeFan2Value() {
		return sItemTypeFan2Value;
	}

	public void setsItemTypeFan2Value(String sItemTypeFan2Value) {
		this.sItemTypeFan2Value = sItemTypeFan2Value;
	}

	public String getsItemNameFan2Value() {
		return sItemNameFan2Value;
	}

	public void setsItemNameFan2Value(String sItemNameFan2Value) {
		this.sItemNameFan2Value = sItemNameFan2Value;
	}

	public String getsItemTypeFan3Value() {
		return sItemTypeFan3Value;
	}

	public void setsItemTypeFan3Value(String sItemTypeFan3Value) {
		this.sItemTypeFan3Value = sItemTypeFan3Value;
	}

	public String getsItemNameFan3Value() {
		return sItemNameFan3Value;
	}

	public void setsItemNameFan3Value(String sItemNameFan3Value) {
		this.sItemNameFan3Value = sItemNameFan3Value;
	}

	public String getsItemTypeManualModeValue() {
		return sItemTypeManualModeValue;
	}

	public void setsItemTypeManualModeValue(String sItemTypeManualModeValue) {
		this.sItemTypeManualModeValue = sItemTypeManualModeValue;
	}

	public String getsItemNameManualModeValue() {
		return sItemNameManualModeValue;
	}

	public void setsItemNameManualModeValue(String sItemNameManualModeValue) {
		this.sItemNameManualModeValue = sItemNameManualModeValue;
	}

	public String getsItemTypeHeatingModeValue() {
		return sItemTypeHeatingModeValue;
	}

	public void setsItemTypeHeatingModeValue(String sItemTypeHeatingModeValue) {
		this.sItemTypeHeatingModeValue = sItemTypeHeatingModeValue;
	}

	public String getsItemNameHeatingModeValue() {
		return sItemNameHeatingModeValue;
	}

	public void setsItemNameHeatingModeValue(String sItemNameHeatingModeValue) {
		this.sItemNameHeatingModeValue = sItemNameHeatingModeValue;
	}

	public String getsItemTypeHeatingValue() {
		return sItemTypeHeatingValue;
	}

	public void setsItemTypeHeatingValue(String sItemTypeHeatingValue) {
		this.sItemTypeHeatingValue = sItemTypeHeatingValue;
	}

	public String getsItemNameHeatingValue() {
		return sItemNameHeatingValue;
	}

	public void setsItemNameHeatingValue(String sItemNameHeatingValue) {
		this.sItemNameHeatingValue = sItemNameHeatingValue;
	}

	public String getsItemTypeCoolingValue() {
		return sItemTypeCoolingValue;
	}

	public void setsItemTypeCoolingValue(String sItemTypeCoolingValue) {
		this.sItemTypeCoolingValue = sItemTypeCoolingValue;
	}

	public String getsItemNameCoolingValue() {
		return sItemNameCoolingValue;
	}

	public void setsItemNameCoolingValue(String sItemNameCoolingValue) {
		this.sItemNameCoolingValue = sItemNameCoolingValue;
	}

	public String getsItemTypeFan1Value() {
		return sItemTypeFan1Value;
	}

	public void setsItemTypeFan1Value(String sItemTypeFan1Value) {
		this.sItemTypeFan1Value = sItemTypeFan1Value;
	}

	public boolean isHeatingValue() {
		return HeatingValue;
	}

	public void setHeatingValue(boolean heatingValue) {
		HeatingValue = heatingValue;
		setUpdatedTrue();
	}

	public boolean isCoolingValue() {
		return CoolingValue;
	}

	public void setCoolingValue(boolean coolingValue) {
		CoolingValue = coolingValue;
		setUpdatedTrue();
	}

	public boolean isFan1Value() {
		return Fan1Value;
	}

	public void setFan1Value(boolean fan1Value) {
		Fan1Value = fan1Value;
		setUpdatedTrue();
	}
	
	public boolean isFan2Value() {
		return Fan2Value;
	}

	public void setFan2Value(boolean fan2Value) {
		Fan2Value = fan2Value;
		setUpdatedTrue();
	}

	public boolean isFan3Value() {
		return Fan3Value;
	}

	public void setFan3Value(boolean fan3Value) {
		Fan3Value = fan3Value;
		setUpdatedTrue();
	}

	public boolean isManualModeValue() {
		return ManualModeValue;
	}

	public void setManualModeValue(boolean manualModeValue) {
		ManualModeValue = manualModeValue;
		setUpdatedTrue();
	}

	public boolean isHeatingModeValue() {
		return HeatingModeValue;
	}

	public void setHeatingModeValue(boolean heatingModeValue) {
		HeatingModeValue = heatingModeValue;
		setUpdatedTrue();
	}
	
}
