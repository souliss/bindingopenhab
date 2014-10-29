package org.openhab.binding.souliss.internal.network.typicals;


public class SoulissT52 extends SoulissT51 {

	public SoulissT52(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(sSoulissNodeIPAddress,sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T52_TemperatureSensor);
	}
}
