package org.openhab.binding.souliss.internal.network.typicals;

public class SoulissT57 extends SoulissT51 {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	public SoulissT57(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(sSoulissNodeIPAddress,sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T57_PowerSensor);
	}
}
