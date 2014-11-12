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

/**
 * Aggiunge un tipo SoulissGenericTypical in due HashTable, una indicizzata con il nome ITEM e l'altra con IP+NodeID+slot 
 * @param sItem
 * @param typical
 */
	public void addTypical(String sItem, SoulissGenericTypical typical){
		synchronized (typical) {
			LOGGER.info("Add Item: " +  sItem + " - Typ: " + Integer.toHexString(typical.getType()) + ", Node: "+ typical.getSoulissNodeID() + ", Slot: " + typical.getSlot());
			typical.setName(sItem);
			//la chiave della hasttable è:  IP Address + VNET Address + slot
			LOGGER.info("hashTableItemToAddress <-- [key: " + sItem + " - value: " + String.valueOf(typical.getSoulissNodeID()) + String.valueOf(typical.getSlot()) + "]");
			hashTableItemToAddress.put(sItem, String.valueOf(typical.getSoulissNodeID()) + String.valueOf(typical.getSlot()));
			//la chiave della hasttable è:  item
			LOGGER.info("hashTableAddressToTypicals <-- [key: " + typical.getSoulissNodeID() + String.valueOf(typical.getSlot()) + " - value: " + typical + "]");
			hashTableAddressToTypicals.put(String.valueOf(typical.getSoulissNodeID()) + String.valueOf(typical.getSlot()), typical);	
		}
		
	}
	/**
	 * Cancella entrambe le Hashtable 
	 */
	public void clear(){
		LOGGER.debug("Clear hashtable");
		hashTableAddressToTypicals.clear();
		hashTableItemToAddress.clear();
	}
	
	/**
	 * Cerca un tipico nella Hashtable in base all'indirizzo IP+Nodo+Slot
	 * @param sSoulissNodeIPAddress
	 * @param getSoulissNodeID
	 * @param iSlot
	 * @return
	 */
	public SoulissGenericTypical getTypicalFromAddress(int getSoulissNodeID, int iSlot){
		return hashTableAddressToTypicals.get(String.valueOf(getSoulissNodeID) + String.valueOf(iSlot));
	}
	
	
	/**
	 * Cerca un tipico nella Hashtable in base al nome ITEM
	 * @param sItem
	 * @return
	 */
	public SoulissGenericTypical getTypicalFromItem(String sItem){
		String sKey=hashTableItemToAddress.get(sItem);
		if(sKey==null) return null;
		return hashTableAddressToTypicals.get(sKey);
	}
/**
 * REstituisce un iteratore 
 */
	public Iterator<Entry<String, SoulissGenericTypical>> getIterator(){
			return hashTableAddressToTypicals.entrySet().iterator();	
	}

/**
 * Esamina la Hashtable e restituisce il numero di nodi presenti in rete Souliss
 * @return intero
 */
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
