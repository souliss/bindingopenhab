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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;


/**
 * This class implements the base Souliss Typical
 * All other Typicals derive from this class
 * 
 * @author Antonino-Fazio
 */
public class SoulissGenericTypical {
	
	//*************************************
	//*************************************
	private int iSlot;
	private int iSoulissNodeID;
	private short sType;
	private float fState;
	private String sName;
	private boolean isUpdated=false;
	private String sNote;
	private static Logger LOGGER = LoggerFactory.getLogger(SoulissGenericTypical.class);

	/**
	 * @return the iSlot
	 */
	public int getSlot() {
		return iSlot;
	}
	/**
	 * @param iSlot the iSlot to set
	 */
	public void setSlot(int iSlot) {
		this.iSlot = iSlot;
	}
	
	/**
	 * @param SoulissNode the SoulissNodeID to get
	 */
	public int getSoulissNodeID() {
		return iSoulissNodeID;
	}
	
	/**
	 * @param SoulissNode the SoulissNodeID to set
	 */
	public void setSoulissNodeID(int setSoulissNodeID) {
		this.iSoulissNodeID = setSoulissNodeID;
	}
		
	/**
	 * @return the sType
	 */
	public short getType() {
		return sType;
	}
	/**
	 * @param soulissT11 the sType to set
	 */
	protected void setType(short soulissT11) {
		this.sType = soulissT11;
	}
	/**
	 * @return the iState
	 */
	public float getState() {
		return fState;
	}
	/**
	 * @param iState the iState to set
	 */
	public void setState(float iState) {
		LOGGER.debug("Update State. Name: "+ getName() +", Typ: " + "0x"+Integer.toHexString(getType()) + ", Node: "+ getSoulissNodeID() + ", Slot: " + getSlot() + ". New State: "+ iState);
		this.fState = iState;
		setUpdatedTrue();
	}
	
	/**
	 * @return the nodeName
	 */
	public String getName() {
		return sName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setName(String nodeName) {
		this.sName = nodeName;
	}
	/**
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}
	/**
	 */
	public void resetUpdate() {
		isUpdated=false;
	}
	public void setUpdatedTrue() {
		this.isUpdated = true;
	}
	
	public String getNote() {
		return sNote;
	}
	public void setNote(String sNote) {
		this.sNote = sNote;
	}
	
	/**
	 * Invio un comando in Multicast 
	 * @param datagramSocket
	 * @param command
	 */
	void CommandMulticast(DatagramSocket datagramSocket, short command){
		LOGGER.debug("Typ: " + getType() + ", Name: " + getName()  +" - CommandMulticast: " + command);
		SoulissCommGate.sendMULTICASTFORCEFrame(datagramSocket, SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, getType(), command );
	}
	
	/**
	 * Invia il comando DBStruct
	 * @param datagramSocket
	 */
	public void sendDBStructFrame(DatagramSocket datagramSocket){
		LOGGER.debug("Typ: " + getType() + ", Name: " + getName()  +" - sendDBStructFrame ");
		SoulissCommGate.sendDBStructFrame(datagramSocket, SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN);
	}
	
	/**
	 * Invia un ping
	 * @param datagramSocket
	 * @param putIn_1
	 * @param punIn_2
	 */
	public void ping(DatagramSocket datagramSocket, short putIn_1, short punIn_2){
		LOGGER.debug("Typ: " + getType() + ", Name: " + getName()  +" - ping");
		SoulissCommGate.sendPing(datagramSocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, putIn_1, punIn_2);
	}

	
	public org.openhab.core.types.State getOHState() {
		return null;
	}

	
}
