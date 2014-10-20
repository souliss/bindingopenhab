package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;

public class SoulissT12 extends SoulissT11 {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
//	private static final short commandON=Constants.Souliss_T1n_OnCmd;
//	private static final short commandOFF=Constants.Souliss_T1n_OffCmd;;
//	private static final short commandTOGGLE=Constants.Souliss_T1n_ToogleCmd;
	
	public SoulissT12(DatagramSocket _datagramsocket, String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(_datagramsocket, sSoulissNodeIPAddress, sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T12);
	}
}
