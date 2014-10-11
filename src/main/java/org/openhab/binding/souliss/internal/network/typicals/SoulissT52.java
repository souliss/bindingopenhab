package org.openhab.binding.souliss.internal.network.typicals;

import java.awt.font.NumericShaper;
import java.net.DatagramSocket;
import java.util.ArrayList;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.State;
import org.openhab.core.types.Type;

public class SoulissT52 extends SoulissT51 {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	public SoulissT52(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(sSoulissNodeIPAddress,sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T52_TemperatureSensor);
	}

	/**
	 * @return the commandclose
	 */
//	public void CommandREADANALOG(DatagramSocket datagramSocket) {
//		SoulissCommGate.sendREADWITHSUBSCRIPTIONFrame(datagramSocket, this.getSoulissNodeIPAddress(),  this.getSoulissNodeIPAddressOnLAN(), this.getSoulissNodeID(), this.getSlot());
//	}


	
}
