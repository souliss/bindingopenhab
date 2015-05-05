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
	
	private String sItemTypeHeatingCoolingModeValue;	
	private String sItemNameHeatingCoolingModeValue;

	private String sItemTypeFanOffValue;	
	private String sItemNameFanOffValue;
	
	private String sItemTypeFanLowValue;	
	private String sItemNameFanLowValue;
	
	private String sItemTypeFanMedValue;	
	private String sItemNameFanMedValue;
	
	private String sItemTypeFanHighValue;	
	private String sItemNameFanHighValue;
	
	private String sItemTypeManualModeValue;	
	private String sItemNameManualModeValue;

	private short sRawCommandState;
	private Float TemperatureSetpointValue;
	private Float MeasuredValue;
	private boolean heatingValue;
	private boolean coolingValue;
	private boolean fanLowValue;
	private boolean fanMedValue;
	private boolean fanHighValue;
	private boolean autoModeValue;
	private boolean heatingCoolingModeValue;
	
	
	
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
//	DA INVIARE TUTTI COMANDI
//	
//	#define Souliss_T3n_InSetPoint			0x01
//	#define Souliss_T3n_OutSetPoint			0x02
//	#define Souliss_T3n_AsMeasured			0x03
//	#define Souliss_T3n_Cooling				0x04
//	#define Souliss_T3n_Heating				0x05
//	#define Souliss_T3n_FanOff				0x06
//	#define Souliss_T3n_FanLow				0x07
//	#define Souliss_T3n_FanMed				0x08
//	#define Souliss_T3n_FanHigh				0x09
//	#define Souliss_T3n_FanAuto				0x0A
//	#define Souliss_T3n_FanManual			0x0B
//	#define Souliss_T3n_SetTemp				0x0C
//	#define Souliss_T3n_ShutDown			0x0D
	

	public State getOHState_Heating(){
		return getOHState(getsItemNameHeatingValue(), getsItemTypeHeatingValue(),isHeatingValue());
	}
	
	public State getOHState_Cooling(){
		return getOHState(getsItemNameCoolingValue(), getsItemTypeCoolingValue(),isCoolingValue());
	}
	
	public State getOHState_HeatingCoolingMode() {
		return getOHState(getsItemNameHeatingCoolingModeValue(), getsItemTypeHeatingCoolingModeValue(), isHeatingModeValue());
	}

	public State getOHState_FanOff(){
		return getOHState(getsItemNameFanOffValue(), getsItemTypeFanOffValue(),isFan1Value());
	}
	
	public State getOHState_Fan1(){
		return getOHState(getsItemNameFanLowValue(), getsItemTypeFanLowValue(),isFan1Value());
	}
	
	public State getOHState_Fan2(){
		return getOHState(getsItemNameFanMedValue(), getsItemTypeFanMedValue(),isFan2Value());
	}
	
	public State getOHState_Fan3(){
		return getOHState(getsItemNameFanHighValue(), getsItemTypeFanHighValue(),isFan3Value());
	}

	public State getOHState_FanAutoMode(){
		return getOHState(getsItemNameFanAutoModeValue(), getsItemTypeFanAutoModeValue(),isFanAutoModeValue());
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
			
		//String sOHState = StateTraslator.statesSoulissToOH(sType + "_" + sItemName,this.getType(), shortState);
		String sOHState = StateTraslator.statesSoulissToOH(sType,this.getType(), shortState);
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

	public String getsItemNameFanOffValue() {
		return sItemNameFanOffValue;
	}

	public void setsItemNameFanOffValue(String sItemNameFanOffValue) {
		this.sItemNameFanOffValue = sItemNameFanOffValue;
	}
	
	public String getsItemTypeFanOffValue() {
		return sItemTypeFanOffValue;
	}

	public void setsItemTypeFanOffValue(String sItemTypeFanOffValue) {
		this.sItemTypeFanOffValue = sItemTypeFanOffValue;
	}
	
	public String getsItemNameFanLowValue() {
		return sItemNameFanLowValue;
	}

	public void setsItemNameFanLowValue(String sItemNameFan1Value) {
		this.sItemNameFanLowValue = sItemNameFan1Value;
	}

	public String getsItemTypeFanMedValue() {
		return sItemTypeFanMedValue;
	}

	public void setsItemTypeFanMedValue(String sItemTypeFan2Value) {
		this.sItemTypeFanMedValue = sItemTypeFan2Value;
	}

	public String getsItemNameFanMedValue() {
		return sItemNameFanMedValue;
	}

	public void setsItemNameFanMedValue(String sItemNameFan2Value) {
		this.sItemNameFanMedValue = sItemNameFan2Value;
	}

	public String getsItemTypeFanHighValue() {
		return sItemTypeFanHighValue;
	}

	public void setsItemTypeFanHighValue(String sItemTypeFan3Value) {
		this.sItemTypeFanHighValue = sItemTypeFan3Value;
	}

	public String getsItemNameFanHighValue() {
		return sItemNameFanHighValue;
	}

	public void setsItemNameFanHighValue(String sItemNameFanFanHighValue) {
		this.sItemNameFanHighValue = sItemNameFanFanHighValue;
	}

	public String getsItemTypeFanAutoModeValue() {
		return sItemTypeManualModeValue;
	}

	public void setsItemTypeAutoModeValue(String sItemTypeManualModeValue) {
		this.sItemTypeManualModeValue = sItemTypeManualModeValue;
	}

	public String getsItemNameFanAutoModeValue() {
		return sItemNameManualModeValue;
	}

	public void setsItemNameAutoModeValue(String sItemNameManualModeValue) {
		setsItemNameFanAutoModeValue(sItemNameManualModeValue);
	}

	public void setsItemNameFanAutoModeValue(String sItemNameManualModeValue) {
		this.sItemNameManualModeValue = sItemNameManualModeValue;
	}

	public void setsItemTypeHeatingCoolingModeValue(String sItemTypeHeatingCoolingModeValue) {
		this.sItemTypeHeatingCoolingModeValue = sItemTypeHeatingCoolingModeValue;
	}
	
	public void setsItemNameHeatingCoolingModeValue(String sItemNameHeatingCoolingModeValue) {
		this.sItemNameHeatingCoolingModeValue = sItemNameHeatingCoolingModeValue;
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

	public String getsItemNameHeatingCoolingModeValue() {
		return sItemNameHeatingCoolingModeValue;
	}

	public void setsItemNameHeatingCoolingValue(String sItemTypeHeatingCoolingModeValue) {
		this.sItemTypeHeatingCoolingModeValue = sItemTypeHeatingCoolingModeValue;
	}
	
	public String getsItemTypeHeatingCoolingModeValue() {
		return sItemTypeHeatingCoolingModeValue;
	}

	public String getsItemTypeFanLowValue() {
		return sItemTypeFanLowValue;
	}

	public void setsItemTypeFanLowValue(String sItemTypeFanLowValue) {
		this.sItemTypeFanLowValue = sItemTypeFanLowValue;
	}

	public boolean isHeatingValue() {
		return heatingValue;
	}

	public void setHeatingValue(boolean heatingValue) {
		this.heatingValue = heatingValue;
		setUpdatedTrue();
	}

	public boolean isCoolingValue() {
		return coolingValue;
	}

	public void setCoolingValue(boolean coolingValue) {
		this.coolingValue = coolingValue;
		setUpdatedTrue();
	}

	public boolean isFan1Value() {
		return fanLowValue;
	}

	public void setFanLowValue(boolean fan1Value) {
		this.fanLowValue = fan1Value;
		setUpdatedTrue();
	}
	
	public boolean isFan2Value() {
		return fanMedValue;
	}

	public void setFanMedValue(boolean fan2Value) {
		this.fanMedValue = fan2Value;
		setUpdatedTrue();
	}

	public boolean isFan3Value() {
		return fanHighValue;
	}

	public void setFanHighValue(boolean fan3Value) {
		this.fanHighValue = fan3Value;
		setUpdatedTrue();
	}

	public boolean isFanAutoModeValue() {
		return autoModeValue;
	}

	public void setFanAutoModeValue(boolean autoModeValue) {
		this.autoModeValue = autoModeValue;
		setUpdatedTrue();
	}

	public boolean isHeatingModeValue() {
		return heatingCoolingModeValue;
	}

	public void setHeatingCoolingModeValue(boolean heatingCoolingModeValue) {
		this.heatingCoolingModeValue = heatingCoolingModeValue;
		setUpdatedTrue();
	}

	@Override
	public State getOHState() {
		// TODO Auto-generated method stub
		return null;
	}
	
}