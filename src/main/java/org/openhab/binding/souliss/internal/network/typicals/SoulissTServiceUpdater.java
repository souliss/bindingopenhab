package org.openhab.binding.souliss.internal.network.typicals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SoulissTServiceUpdater {

	public static void updateHEALTY(SoulissTypicals soulissTypicalsRecipients, int idNodo, Short valueOf) {
		//CREAZIONE / AGGIORNAMENTO NODI FITTIZI
		SoulissTServiceNODE_HEALTY VirtualTypical = (SoulissTServiceNODE_HEALTY) soulissTypicalsRecipients.getTypicalFromAddress(SoulissNetworkParameter.IPAddress, idNodo, Constants.Souliss_TService_NODE_HEALTY_VIRTUAL_SLOT);
		if(VirtualTypical!=null) VirtualTypical.setState(valueOf);

	};

	public static void updateTIMESTAMP(	SoulissTypicals soulissTypicalsRecipients, int idNodo) {
		//CREAZIONE / AGGIORNAMENTO NODI FITTIZI
		SoulissTServiceNODE_TIMESTAMP VirtualTypical = (SoulissTServiceNODE_TIMESTAMP) soulissTypicalsRecipients.getTypicalFromAddress(SoulissNetworkParameter.IPAddress, idNodo, Constants.Souliss_TService_NODE_TIMESTAMP_VIRTUAL_SLOT);
		if(VirtualTypical!=null) VirtualTypical.setTIMESTAMP(getTimestamp());
	
}

	private static String getTimestamp() {
		//pattern da ottenere: yyyy-MM-dd'T'HH:mm:ssz
		SimpleDateFormat sdf =	new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
		Date n=new Date();
		return sdf.format( n.getTime() ) ;
	}
}


