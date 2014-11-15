package org.openhab.binding.souliss.internal.network.typicals;

import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;

public class SoulissT1A extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
    
	//bit del tipico 1A che rappresenta lo stato
	int iBit;
	
	public int getBit() {
		return iBit;
	}

	public SoulissT1A(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType, int iBit) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T1A);
		this.setNote(sOHType);
		
	}
		
	@Override
	public State getOHState() {
		String sOHState=StateTraslator.statesSoulissToOH(this.getNote(), this.getType(),(short) this.getState(iBit));
		if(sOHState!=null){
		if (this.getNote().equals("ContactItem"))
			return OpenClosedType.valueOf(sOHState);
		else return OnOffType.valueOf(sOHState);
		}
		return null;
	}

	public byte getRawState() {
		return (byte) this.getState();
	}

	public short getState(int i) {
		int[] bit = { 0, 1, 2, 4, 8, 16, 32, 64, 128 };
		
			if ((((byte) this.getState()) & bit[i]) == 0) {
				return Constants.Souliss_T1n_OffCoil;
			} else {
				return Constants.Souliss_T1n_OnCoil;
			}
	}

}
