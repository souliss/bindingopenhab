package org.openhab.binding.souliss.internal.network.typicals;

public class SoulissT57 extends SoulissT51 {
	
	public SoulissT57(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(sSoulissNodeIPAddress,sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T57_PowerSensor);
	}
}
