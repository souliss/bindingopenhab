package org.openhab.binding.souliss.internal.network.typicals;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SoulissTypicals {

//	private Hashtable<String, SoulissGenericTypical> hashTableAddressToTypicals_notSync= new Hashtable<String, SoulissGenericTypical>();
//	private Hashtable<String, String> hashTableItemToAddress_notSync= new Hashtable<String, String>();
private Map<String, SoulissGenericTypical> hashTableAddressToTypicals = Collections.synchronizedMap(new Hashtable<String, SoulissGenericTypical>());
private Map<String, String> hashTableItemToAddress =Collections.synchronizedMap(new Hashtable<String, String>());
private static Logger LOGGER = LoggerFactory.getLogger(SoulissTypicals.class);

	public void addTypical(String sItem, SoulissGenericTypical typical){
		synchronized (typical) {
			LOGGER.info("Add Item: " +  sItem + " - Typ: " + Integer.toHexString(typical.getType()) + ", Node: "+ typical.getSoulissNodeID() + ", Slot: " + typical.getSlot());
			typical.setName(sItem);
			//la chiave della hasttable è:  IP Address + VNET Address + slot
			hashTableItemToAddress.put(sItem, SoulissNetworkParameter.IPAddressOnLAN + typical.getSoulissNodeID() + typical.getSlot());
			//la chiave della hasttable è:  item
			hashTableAddressToTypicals.put(SoulissNetworkParameter.IPAddressOnLAN+ typical.getSoulissNodeID() + typical.getSlot(), typical);	
		}
		
	}
	
	public void clear(){
		LOGGER.debug("Clear hashtable");
		hashTableAddressToTypicals.clear();
		hashTableItemToAddress.clear();
	}
	
	public SoulissGenericTypical getTypicalFromAddress(String sSoulissNodeIPAddress, int getSoulissNodeID, int iSlot){
		return hashTableAddressToTypicals.get(sSoulissNodeIPAddress + getSoulissNodeID + iSlot);
	}
	
	public SoulissGenericTypical getTypicalFromItem(String sItem){
		String sKey=hashTableItemToAddress.get(sItem);
		if(sKey==null) return null;
		return hashTableAddressToTypicals.get(sKey);
	}

	public Iterator<Entry<String, SoulissGenericTypical>> getIterator(){
		//CopyOnWriteArraySet<SoulissGenericTypical> cw = new CopyOnWriteArraySet<SoulissGenericTypical>();
			
	//	Iterator<Entry<String, SoulissGenericTypical>> it= hashTableAddressToTypicals.entrySet().iterator();
		return hashTableAddressToTypicals.entrySet().iterator();	
	}


	public int getNodeNumbers() {
		//restituisce il numero massimo di IDNodo
		SoulissGenericTypical typ;
		int iTmp=0;
		Iterator<Entry<String, SoulissGenericTypical>> iteratorTypicals=getIterator();
		synchronized (iteratorTypicals) {
			while (iteratorTypicals.hasNext()){
				typ = iteratorTypicals.next().getValue();
			if(typ.getSoulissNodeID()>iTmp) iTmp=typ.getSoulissNodeID();
			}	
		}
		
		return iTmp+1;
	}

}
