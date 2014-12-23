/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.types.State;


/**
 * Typical T19
 * RGB LED Strip
 * 
 * @author Antonino-Fazio
 */
public class SoulissT19 extends SoulissGenericTypical {
	int stateLED;
	
	public int getStateLED() {
		return stateLED;
	}

	public void setStateLED(int stateLED) {
		this.stateLED = stateLED;
	}


	public SoulissT19(DatagramSocket _datagramsocket, String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T19);
		this.setNote(sOHType);
 	}
/**
 * Invia un comando 
 * @param command
 */
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, this.getSoulissNodeID(), this.getSlot(), command );	
	}
	/**
	 * Send Command with Dimmer Value
	 * @param command
	 */
	public void CommandSEND(short command, short LDimmer) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, this.getSoulissNodeID(), this.getSlot(), command, LDimmer);	
	}
	@Override
	/**
	 * Restituisce un tipo openhab che rappresenta lo stato del tipico
	 * @return org.openhab.core.types.State
	 */
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),(short) this.getState());
		if(sOHState!=null){
			return OnOffType.valueOf(sOHState);
		} else {
			return new PercentType(String.valueOf((this.getState()/250)*100));
		}
	}

}
