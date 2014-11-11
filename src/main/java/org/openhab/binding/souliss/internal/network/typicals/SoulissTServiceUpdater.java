package org.openhab.binding.souliss.internal.network.typicals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openhab.binding.souliss.internal.network.udp.UDPSoulissDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulissTServiceUpdater {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UDPSoulissDecoder.class);
	
	/**
	 * Cerca nella hashtable il tipico virtuale rappresentato da  idNodo e Souliss_TService_NODE_TIMESTAMP_VIRTUAL_SLOT e ne aggiorna il valore
	 * @param soulissTypicalsRecipients
	 * @param idNodo
	 * @param valueOf
	 */
	public static void updateHEALTY(SoulissTypicals soulissTypicalsRecipients, int idNodo, Short valueOf) {
		//CREAZIONE / AGGIORNAMENTO NODI FITTIZI
		LOGGER.debug("request to updateHEALTY. Node:  " + idNodo + "; Value: " + valueOf);
		SoulissTServiceNODE_HEALTY VirtualTypical = (SoulissTServiceNODE_HEALTY) soulissTypicalsRecipients.getTypicalFromAddress(SoulissNetworkParameter.IPAddress, idNodo, Constants.Souliss_TService_NODE_HEALTY_VIRTUAL_SLOT);
		if(VirtualTypical!=null){
			VirtualTypical.setState(valueOf);
			LOGGER.debug("updateHEALTY:  " + VirtualTypical.getName() + " ( " + Short.valueOf(VirtualTypical.getType()) + ") = " + valueOf);
		} else {
			LOGGER.debug("Error for retrieving VirtualTypical from HashTable: "+ SoulissNetworkParameter.IPAddress + ", "+ idNodo+", "+  Constants.Souliss_TService_NODE_HEALTY_VIRTUAL_SLOT);
		}
		
		
	};
	/**
	 * Cerca nella hashtable il tipico virtuale rappresentato da  idNodo e Souliss_TService_NODE_TIMESTAMP_VIRTUAL_SLOT e ne aggiorna il valore 
	 * @param soulissTypicalsRecipients
	 * @param idNodo
	 */
	public static void updateTIMESTAMP(	SoulissTypicals soulissTypicalsRecipients, int idNodo) {
		//CREAZIONE / AGGIORNAMENTO NODI FITTIZI
		LOGGER.debug("request to updateTIMESTAMP. Node:  " + idNodo);
		SoulissTServiceNODE_TIMESTAMP VirtualTypical = (SoulissTServiceNODE_TIMESTAMP) soulissTypicalsRecipients.getTypicalFromAddress(SoulissNetworkParameter.IPAddress, idNodo, Constants.Souliss_TService_NODE_TIMESTAMP_VIRTUAL_SLOT);
		
		if(VirtualTypical!=null){ 
			String tstamp = getTimestamp();
			VirtualTypical.setTIMESTAMP(tstamp);
			LOGGER.debug("updateTIMESTAMP:  " + VirtualTypical.getName() + " ( " + Short.valueOf(VirtualTypical.getType()) + ") = " + tstamp);
		} else {
			LOGGER.debug("Error for retrieving VirtualTypical from HashTable: "+ SoulissNetworkParameter.IPAddress + ", "+ idNodo+", "+  Constants.Souliss_TService_NODE_TIMESTAMP_VIRTUAL_SLOT);
		}
	
}
/**
 * Crea il timestamp con il pattern "yyyy-MM-dd'T'HH:mm:ssz"
 * @return String timestamp
 */
	private static String getTimestamp() {
		//pattern da ottenere: yyyy-MM-dd'T'HH:mm:ssz
		SimpleDateFormat sdf =	new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
		Date n=new Date();
		return sdf.format( n.getTime() ) ;
	}
}


