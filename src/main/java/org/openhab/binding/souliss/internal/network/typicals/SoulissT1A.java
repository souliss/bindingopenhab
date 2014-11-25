package org.openhab.binding.souliss.internal.network.typicals;

import org.openhab.binding.souliss.internal.network.udp.UDPSoulissDecoder;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SoulissT1A extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	private static Logger LOGGER = LoggerFactory.getLogger(SoulissT1A.class);
	
	//bit del tipico 1A che rappresenta lo stato
	int iBit;
	short sRawState;
	
	public int getBit() {
		return iBit;
	}
	
	private void setBit(int _iBit) {
		iBit=_iBit;	
	}

	
	public SoulissT1A(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType, byte iBit) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_T1A);
		this.setNote(sOHType);
		this.setBit(iBit);
		
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

	public short getRawState() {
		return sRawState;
	}

	public short getState(int i) {
		if(getBitState()){
			return Constants.Souliss_T1n_OnCoil;
		}else {
			return Constants.Souliss_T1n_OffCoil;			
		}

	}
	
		
public boolean getBitState() {
	//int[] bit = { 0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096,8192, 16384};
	final int MASK_BIT_1 = 0x1;
	
	
	if (((getRawState() >>> getBit()) & MASK_BIT_1)==0) {
		return false;
	} else {
		return true;
	}
	}

//	
	/**
	 * @param iState the iState to set
	 */
	public void setState(short iState) {
		sRawState= iState;
		LOGGER.debug("Update State. Name: "+ getName() +", Typ: " + "0x"+Integer.toHexString(getType()) + ", Node: "+ getSoulissNodeID() + ", Slot: " + getSlot() + ", Bit: "+ getBit() + ", RawBin: "+Integer.toBinaryString(getRawState()) +". New Bit State: "+ getBitState());
		setUpdatedTrue();
	}

}
