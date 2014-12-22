/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal.network.typicals;

import java.awt.Color;
import java.net.DatagramSocket;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.HSBType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.types.State;

public class SoulissT16 extends SoulissGenericTypical {

	int stateRED;
	int stateGREEN;
	int stateBLU;
	
	public int getStateRED() {
		return stateRED;
	}

	public void setStateRED(int stateRED) {
		this.stateRED = stateRED;
	}

	public int getStateGREEN() {
		return stateGREEN;
	}

	public void setStateGREEN(int stateGREEN) {
		this.stateGREEN = stateGREEN;
	}

	public int getStateBLU() {
		return stateBLU;
	}

	public void setStateBLU(int stateBLU) {
		this.stateBLU = stateBLU;
	}
	
	public SoulissT16(DatagramSocket _datagramsocket, String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T16);
		this.setNote(sOHType);
 	}
/**
 * Invia un comando senza valori RGB
 * @param command
 */
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, this.getSoulissNodeID(), this.getSlot(), command );	
	}
	
	/**
	 * Invia un comando includendo i valori RGB
	 * @param command
	 */	
	public void CommandSEND(short command, short R, short G, short B) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, this.getSoulissNodeID(), this.getSlot(), command, R, G, B );	
	}
	
	@Override
	/**
	 * Restituisce lo stato del tipico, in termini di percentuale
	 */
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),(short)this.getState());
		if (sOHState!=null)
			return PercentType.valueOf(sOHState);
		else 
			return null;
			
	}

	/**
	 * Restituisce il colore nel formato HSB accetto da OT
	 * @return org.openhab.core.types.State
	 */
	public org.openhab.core.types.State getOHStateRGB() {
		Color colr = new Color(this.stateRED, this.stateGREEN, this.stateBLU);
		HSBType hsb =new HSBType(colr);
		return hsb;
	}

}
