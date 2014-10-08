package org.openhab.binding.souliss.internal.network.typicals;

import java.util.ArrayList;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;

public class SoulissTServiceNODE_TIMESTAMP extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	//private static final short SERVICE_SLOT=998;
	private static String timestamp="";
	
	public SoulissTServiceNODE_TIMESTAMP(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeIPAddress(sSoulissNodeIPAddress);
		this.setSoulissNodeIPAddressOnLAN(sSoulissNodeIPAddressOnLAN);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_TService_NODE_TIMESTAMP);
		this.setNote(sOHType);
	}

	public String getTIMESTAMP() {
		return timestamp;
	}

	public void setTIMESTAMP(String TIMESTAMP) {
		timestamp = TIMESTAMP;
		setUpdatedTrue();
	}
		
		
}
