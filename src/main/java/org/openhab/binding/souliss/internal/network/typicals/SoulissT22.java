package org.openhab.binding.souliss.internal.network.typicals;

import java.util.ArrayList;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.UpDownType;
import org.openhab.core.types.State;

public class SoulissT22 extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	private static final short commandCLOSE=Constants.Souliss_T2n_CloseCmd;
	private static final short commandOPEN=Constants.Souliss_T2n_OpenCmd;
	//private static final short commandOPEN=Short.parseShort(StateTraslator.commandsOHtoSOULISS(Integer.parseInt(Integer.toHexString(Constants.Souliss_T22)), "UP").substring(2));
	private static final short commandSTOP=Constants.Souliss_T2n_StopCmd;
	

	public SoulissT22(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeIPAddress(sSoulissNodeIPAddress);
		this.setSoulissNodeIPAddressOnLAN(sSoulissNodeIPAddressOnLAN);
		//this.setSoulissNodeVNetAddress(sSoulissNodeVNetAddress);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T22);
		this.setNote(sOHType);
	}

	/**
	 * @return the commandclose
	 */
	public void CommandCLOSE() {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(), getSoulissNodeIPAddressOnLAN(),  this.getSoulissNodeID(), this.getSlot(), commandCLOSE );
	}

	/**
	 * @return the commandopen
	 */
	public void CommandOPEN() {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(), getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), commandOPEN );
	}

	/**
	 * @return the commandstop
	 */
	public void CommandSTOP() {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(), getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), commandSTOP );
	}
	
	public void CommandSEND(short command) {
		SoulissCommGate.sendFORCEFrame(this.getSoulissNodeIPAddress(), getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot(), command );
	}
	/**
	 * @return the CommandMulticastCLOSE
	 */
	public void CommandMulticastCLOSE() {
		this.CommandMulticast(commandCLOSE);
	}
	
	/**
	 * @return the CommandMulticastOPEN
	 */
	public void CommandMulticastOPEN() {
		this.CommandMulticast(commandOPEN);
	}
	
	/**
	 * @return the CommandMulticastSTOP
	 */
	public void CommandMulticastSTOP() {
		this.CommandMulticast(commandSTOP);
	}

	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),this.getState());
		return PercentType.valueOf(sOHState);
		

	}
}
