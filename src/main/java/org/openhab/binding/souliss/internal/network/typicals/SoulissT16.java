package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;

import org.openhab.binding.souliss.internal.network.udp.ConstantsUDP;
import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;

public class SoulissT16 extends SoulissGenericTypical {

	int stateRED;
	int stateGREEN;
	int stateBLU;
	boolean stateONOFF;
	
	public boolean isStateONOFF() {
		return stateONOFF;
	}

	public void setStateONOFF(boolean stateONOFF) {
		this.stateONOFF = stateONOFF;
	}

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
		
//		/**
//	 * @return the CommandON
//	 */
//	public void CommandON() {
//		SoulissCommGate.sendFORCEFrame(this.getDatagramsocket(), this.getSoulissNodeIPAddress(),this.getSoulissNodeIPAddressOnLAN() ,this.getSoulissNodeID(), this.getSlot(), commandON );
//	}
//
//	/**
//	 * @return the CommandOFF
//	 */
//	public void CommandOFF() {
//		SoulissCommGate.sendFORCEFrame(this.getDatagramsocket(), this.getSoulissNodeIPAddress(),this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), commandOFF );	
//	}
//	
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, this.getSoulissNodeID(), this.getSlot(), command );	
	}
	
	public void CommandSEND(short command, short R, short G, short B) {
		SoulissCommGate.sendFORCEFrame(SoulissNetworkParameter.datagramsocket,SoulissNetworkParameter.IPAddress,  SoulissNetworkParameter.IPAddressOnLAN, this.getSoulissNodeID(), this.getSlot(), command, R, G, B );	
	}
	

//	/**
//	 * @return the CommandTOGGLE
//	 */
//	public void CommandTOGGLE() {
//		SoulissCommGate.sendFORCEFrame(this.getDatagramsocket(), this.getSoulissNodeIPAddress(), this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), commandTOGGLE );
//	}
//
//	/**
//	 * @return the CommandMulticastON
//	 */
//	public void CommandMulticastON() {
//		this.CommandMulticast(this.getDatagramsocket(), commandON);
//	}
//	
//	/**
//	 * @return the CommandMulticastOFF
//	 */
//	public void CommandMulticastOFF() {
//		this.CommandMulticast(this.getDatagramsocket(), commandOFF);
//	}
//	
//	/**
//	 * @return the CommandMulticastTOGGLE
//	 */
//	public void CommandMulticastTOGGLE() {
//		this.CommandMulticast(this.getDatagramsocket(), commandTOGGLE);
//	}

	//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),this.getState());
		if(sOHState!=null){
		if (this.getNote().equals("ContactItem"))
			return OpenClosedType.valueOf(sOHState);
		else return OnOffType.valueOf(sOHState);
		}
		return null;
	}

}
