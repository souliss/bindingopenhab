/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal.network.typicals;

import java.util.Date;

import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.State;

public class SoulissTServiceNODE_TIMESTAMP extends SoulissGenericTypical {
//i parametri sSoulissNode, iSlot, Type, State vengono memorizzati nell'istanza della classe che estendo
	
	//private static final short SERVICE_SLOT=998;
	private static String timestamp;
	
	public SoulissTServiceNODE_TIMESTAMP(String sSoulissNodeIPAddress, String sSoulissNodeIPAddressOnLAN, int iIDNodo, int iSlot, String sOHType) {
		super();
		this.setSlot(iSlot);
		this.setSoulissNodeID(iIDNodo);
		this.setType(Constants.Souliss_TService_NODE_TIMESTAMP);
		this.setNote(sOHType);
	}

	/**
	 * Metodo usato solo per loggare il valore timestamp
	 * @return String timestamp
	 */
	public String getTIMESTAMP() {
		return timestamp;
	}

	/**
	 * Imposta il valore del timestamp
	 * @param string
	 */
	public void setTIMESTAMP(String string) {
		timestamp = string;
		setUpdatedTrue();
	}
		
	@Override
	/**
	 * REstituisce il valore del tipico nel formato di OH DateTimeType 
	 */
	public State getOHState() {
			//return	StringType.valueOf(timestamp);
			return	DateTimeType.valueOf(timestamp);

	}
		
		
}
