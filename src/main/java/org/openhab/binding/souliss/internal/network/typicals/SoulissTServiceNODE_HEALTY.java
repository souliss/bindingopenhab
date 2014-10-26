package org.openhab.binding.souliss.internal.network.typicals;

import org.openhab.core.library.types.DecimalType;
import org.openhab.core.types.State;

public class SoulissTServiceNODE_HEALTY extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
//	private static final short SERVICE_SLOT=999;
	
	public SoulissTServiceNODE_HEALTY(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_TService_NODE_HEALTY);
		this.setNote(sOHType);
	}
	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(), (short)this.getState());
		if (sOHState == null) return DecimalType.valueOf(Float.toString(this.getState()));
		else return DecimalType.valueOf(sOHState);
	}
		
}
