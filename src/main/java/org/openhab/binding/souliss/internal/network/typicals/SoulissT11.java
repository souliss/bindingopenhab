package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;

public class SoulissT11 extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
//	private static final short commandON=Constants.Souliss_T1n_OnCmd;
//	private static final short commandOFF=Constants.Souliss_T1n_OffCmd;;
//	private static final short commandTOGGLE=Constants.Souliss_T1n_ToogleCmd;
	
	public SoulissT11(DatagramSocket _datagramsocket, String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T11);
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
