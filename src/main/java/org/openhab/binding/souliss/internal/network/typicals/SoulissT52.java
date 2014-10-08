package org.openhab.binding.souliss.internal.network.typicals;

import java.awt.font.NumericShaper;
import java.net.DatagramSocket;
import java.util.ArrayList;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.State;
import org.openhab.core.types.Type;

public class SoulissT52 extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	public SoulissT52(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeIPAddress(sSoulissNodeIPAddress);
		this.setSoulissNodeIPAddressOnLAN(sSoulissNodeIPAddressOnLAN);
	//	this.setSoulissNodeVNetAddress(sSoulissNodeVNetAddress);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T52_TemperatureSensor);
		this.setNote(sOHType);
	}

	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),this.getState());
		if (sOHState == null) return DecimalType.valueOf(Float.toString(this.getState()));
		else return DecimalType.valueOf(sOHState);
	}

	/**
	 * @return the commandclose
	 */
//	public void CommandREADANALOG(DatagramSocket datagramSocket) {
//		SoulissCommGate.sendREADWITHSUBSCRIPTIONFrame(datagramSocket, this.getSoulissNodeIPAddress(),  this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot());
//	}


	
}
