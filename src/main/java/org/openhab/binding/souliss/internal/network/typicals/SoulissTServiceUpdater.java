package org.openhab.binding.souliss.internal.network.typicals;

public class SoulissTServiceUpdater {

	public static void updateHEALTY(SoulissTypicals soulissTypicalsRecipients, int idNodo, Short valueOf) {
		//CREAZIONE / AGGIORNAMENTO NODI FITTIZI
		SoulissTServiceNODE_HEALTY VirtualTypical = (SoulissTServiceNODE_HEALTY) soulissTypicalsRecipients.getTypicalFromAddress(SoulissNetworkParameter.IPAddress, idNodo, Constants.Souliss_TService_NODE_HEALTY_VIRTUAL_SLOT);
		if(VirtualTypical!=null) VirtualTypical.setState(valueOf);

	};

	public static void updateTIMESTAMP(	SoulissTypicals soulissTypicalsRecipients, int idNodo) {
		//CREAZIONE / AGGIORNAMENTO NODI FITTIZI
		SoulissTServiceNODE_TIMESTAMP VirtualTypical = (SoulissTServiceNODE_TIMESTAMP) soulissTypicalsRecipients.getTypicalFromAddress(SoulissNetworkParameter.IPAddress, idNodo, Constants.Souliss_TService_NODE_TIMESTAMP_VIRTUAL_SLOT);
		if(VirtualTypical!=null) VirtualTypical.setTIMESTAMP(String.valueOf(getTimestamp()));
	
}

	private static String getTimestamp() {
		java.util.Date date= new java.util.Date();
		 return date.toString();
	}
}


