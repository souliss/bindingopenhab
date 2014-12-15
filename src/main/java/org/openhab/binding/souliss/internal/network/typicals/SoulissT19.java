package org.openhab.binding.souliss.internal.network.typicals;

import java.awt.Color;
import java.net.DatagramSocket;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.types.HSBType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.types.State;

public class SoulissT19 extends SoulissGenericTypical {
//TIPICO CLONATO DAL T16 DA ADATTARE

//POI VEDERE ANCHE SEND E DECODER
//AMBIENTE OK
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
			return new PercentType(String.valueOf((this.getState()/255)*100));
		}
	}

}
