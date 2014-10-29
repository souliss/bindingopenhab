package org.openhab.binding.souliss.internal.network.typicals;


public class SoulissT53 extends SoulissT51 {
	
	public SoulissT53(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(sSoulissNodeIPAddress,sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T53_HumiditySensor);
	}
}
