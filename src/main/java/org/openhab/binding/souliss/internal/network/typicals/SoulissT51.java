package org.openhab.binding.souliss.internal.network.typicals;

import org.openhab.core.library.types.DecimalType;
import org.openhab.core.types.State;

public class SoulissT51 extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	public SoulissT51(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
//		this.setSoulissNodeIPAddress(sSoulissNodeIPAddress);
//		this.setSoulissNodeIPAddressOnLAN(sSoulissNodeIPAddressOnLAN);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T53_HumiditySensor);
		this.setNote(sOHType);
		
	}
	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),this.getState());
		if (sOHState == null) return DecimalType.valueOf(Float.toString(this.getState()));
		else return DecimalType.valueOf(sOHState);
	}
}
