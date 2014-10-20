package org.openhab.binding.souliss.internal.network.typicals;


public class SoulissT53 extends SoulissT51 {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	public SoulissT53(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super(sSoulissNodeIPAddress,sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot, sOHType);
		this.setType(Constants.Souliss_T53_HumiditySensor);
	}
}
