package org.openhab.binding.souliss.internal.network.typicals;

import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;

public class SoulissT13 extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	
	public SoulissT13(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
//		this.setSoulissNodeIPAddress(sSoulissNodeIPAddress);
//		this.setSoulissNodeIPAddressOnLAN(sSoulissNodeIPAddressOnLAN);
	//	this.setSoulissNodeVNetAddress(sSoulissNodeVNetAddress);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T13);
		this.setNote(sOHType);
	}
		
	
	//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),this.getState());
		if(sOHState!=null){
		if (this.getNote().equals("ContactItem"))
			return OpenClosedType.valueOf(sOHState);
		else return OnOffType.valueOf(sOHState);
		}
		return null;
	}

}
