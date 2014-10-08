package org.openhab.binding.souliss.internal.network.typicals;

import java.util.ArrayList;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;

public class SoulissT11 extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	private static final short commandON=Constants.Souliss_T1n_OnCmd;
	private static final short commandOFF=Constants.Souliss_T1n_OffCmd;;
	private static final short commandTOGGLE=Constants.Souliss_T1n_ToogleCmd;
	
	public SoulissT11(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeIPAddress(sSoulissNodeIPAddress);
		this.setSoulissNodeIPAddressOnLAN(sSoulissNodeIPAddressOnLAN);
	//	this.setSoulissNodeVNetAddress(sSoulissNodeVNetAddress);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T11);
		this.setNote(sOHType);
	}
		
		/**
	 * @return the CommandON
	 */
	public void CommandON() {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(),this.getSoulissNodeIPAddressOnLAN() ,this.getSoulissNodeID(), this.getSlot(), commandON );
	}

	/**
	 * @return the CommandOFF
	 */
	public void CommandOFF() {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(),this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), commandOFF );	
	}
	
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(),this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), command );	
	}

	/**
	 * @return the CommandTOGGLE
	 */
	public void CommandTOGGLE() {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(), this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), commandTOGGLE );
	}

	/**
	 * @return the CommandMulticastON
	 */
	public void CommandMulticastON() {
		this.CommandMulticast(commandON);
	}
	
	/**
	 * @return the CommandMulticastOFF
	 */
	public void CommandMulticastOFF() {
		this.CommandMulticast(commandOFF);
	}
	
	/**
	 * @return the CommandMulticastTOGGLE
	 */
	public void CommandMulticastTOGGLE() {
		this.CommandMulticast(commandTOGGLE);
	}

	//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),this.getState());
		if (this.getNote().equals("ContactItem"))
			return OpenClosedType.valueOf(sOHState);
		else return OnOffType.valueOf(sOHState);
	}

}
