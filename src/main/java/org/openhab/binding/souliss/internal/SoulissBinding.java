/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.souliss.internal;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;

import org.openhab.binding.souliss.SoulissBindingProvider;
import org.openhab.binding.souliss.internal.network.typicals.Constants;
import org.openhab.binding.souliss.internal.network.typicals.MonitorThread;
import org.openhab.binding.souliss.internal.network.typicals.RefreshHEALTYThread;
import org.openhab.binding.souliss.internal.network.typicals.RefreshSUBSCRIPTIONThread;
import org.openhab.binding.souliss.internal.network.typicals.SoulissGenericTypical;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT11;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT22;
import org.openhab.binding.souliss.internal.network.typicals.StateTraslator;

import org.openhab.binding.souliss.internal.network.typicals.SoulissNetworkParameter;
import org.openhab.binding.souliss.internal.network.udp.SendDispatcherThread;
import org.openhab.binding.souliss.internal.network.udp.UDPServerThread;

import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.binding.BindingProvider;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The souliss Refresh Service polls the configured timeserver with a configurable 
 * interval and posts a new event of type ({@link DateTimeType} to the event bus.
 * The interval is 15 minutes by default and can be changed via openhab.cfg. 
 * 
 * @author Thomas.Eichstaedt-Engelen
 * @param <E>
 * @since 0.8.0
 */
public class SoulissBinding<E> extends AbstractActiveBinding<SoulissBindingProvider> implements ManagedService {

	private static Logger LOGGER = LoggerFactory.getLogger(SoulissBinding.class);
	
	
	   /** to keep track of all binding providers */
   
    protected EventPublisher eventPublisher = null;
   
   
    public void setEventPublisher(EventPublisher eventPublisher) {
            this.eventPublisher = eventPublisher;
    }

    public void unsetEventPublisher(EventPublisher eventPublisher) {
            this.eventPublisher = null;
    }


	@Override
	public void bindingChanged(BindingProvider provider, String itemName) {
		// TODO Auto-generated method stub
		super.bindingChanged(provider, itemName);
	}


	@Override
	public void activate() {
		// TODO Auto-generated method stub
		super.activate();
	}



	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		super.deactivate();
	}

	public void updated(Dictionary<String, ?> config)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		if (config != null) {
			Enumeration<String> enumConfig= config.keys();
						
			while (enumConfig.hasMoreElements()){
				String sName=enumConfig.nextElement();
//				String sPluginName="";
//				if (sName.equals("service.pid")) {
//					sPluginName=(String) config.get(sName);
//				}
				
				LOGGER.info("PARAMETER: " + sName + " = " + (String) config.get(sName));
				switch (sName) {
				case "IP_WAN":
					SoulissNetworkParameter.IPAddress=(String) config.get(sName);
					break;
				case "IP_LAN":
					SoulissNetworkParameter.IPAddressOnLAN=(String) config.get(sName);
					break;
				case "REFRESH_DBSTRUCT_TIME":
					SoulissNetworkParameter.REFRESH_DBSTRUCT_TIME=Integer.parseInt((String) config.get(sName));
					break;
				case "REFRESH_SUBSCRIPTION_TIME":
					SoulissNetworkParameter.REFRESH_SUBSCRIPTION_TIME=Integer.parseInt((String) config.get(sName));
					break;
				case "REFRESH_HEALTY_TIME":
					SoulissNetworkParameter.REFRESH_HEALTY_TIME=Integer.parseInt((String) config.get(sName));
					break;
				case "REFRESH_MONITOR_TIME":
					SoulissNetworkParameter.REFRESH_MONITOR_TIME=Integer.parseInt((String) config.get(sName));
					break;
				case "SEND_DELAY":
					SoulissNetworkParameter.SEND_DELAY=Integer.parseInt((String) config.get(sName));
					break;
				case "SEND_MIN_DELAY":
					SoulissNetworkParameter.SEND_MIN_DELAY=Integer.parseInt((String) config.get(sName));
					break;
				case "USER_INDEX":
					SoulissNetworkParameter.UserIndex=Integer.parseInt((String) config.get(sName));
					break;
				case "NODE_INDEX":
					SoulissNetworkParameter.NodeIndex=Integer.parseInt((String) config.get(sName));
					break;
				case "NODE_NUMBERS":
					SoulissNetworkParameter.nodes=Integer.parseInt((String) config.get(sName));
					break;
				case "SERVERPORT":
					if (config.get(sName).equals(""))
						SoulissNetworkParameter.serverPort=null;
					else
					SoulissNetworkParameter.serverPort=Integer.parseInt((String) config.get(sName));
					break;
				default:
					break;
				}
				
				
			
	}
		SoulissNetworkParameter.setConfigured(true);
		setProperlyConfigured(true);
		
	}
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		super.handleEvent(event);
		
	}



	@Override
	public void receiveCommand(String itemName, Command command) {
		// TODO Auto-generated method stub
		super.receiveCommand(itemName, command);
		
		//comando ricevuto 12:10:12.150 INFO  runtime.busevents[:22] - Shutter_GF_Toilet received command DOWN
		//qui bisogna cercare nella hastable ed inviare i comandi
		SoulissGenericTypical T =SoulissGenericBindingProvider.SoulissTypicalsRecipients.getTypicalFromItem(itemName);
		LOGGER.info("receiveCommand - " + itemName + " = " + command );
		
		switch (T.getType()){
		case Constants.Souliss_T11: 
			SoulissT11 T11 =  (SoulissT11) T;
			T11.CommandSEND(StateTraslator.commandsOHtoSOULISS(T.getType(),command.toString()));
			
//			if(command.toString().equals("ON")){
//				T11.CommandON();
//				LOGGER.fine("T11 ON");
//			} else if (command.toString().equals("OFF")){
//				T11.CommandOFF();
//				LOGGER.fine("T11 OFF");
//			}

			//StateTraslator.commandsOHtoSOULISS(T.getType(),command.toString()));
			
			break;
		case Constants.Souliss_T12: 
			//SoulissT12 Typ =  (SoulissT12) T;
			break;
		case Constants.Souliss_T13:
			
			break;
		case Constants.Souliss_T14:
			
			break;
		case Constants.Souliss_T1n_RGB:
			
			break;
		case Constants.Souliss_T16:
			
			break;
		case Constants.Souliss_T18:
			
			break;
		case Constants.Souliss_T19:
			
			break;
		case Constants.Souliss_T21: 
			
			break;
		case Constants.Souliss_T22:
			SoulissT22 T22 =  (SoulissT22) T;
			T22.CommandSEND(StateTraslator.commandsOHtoSOULISS(T.getType(),command.toString()));
			break;
		case Constants.Souliss_T_TemperatureSensor:
			//T=new Souliss_T_TemperatureSensor(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T_HumiditySensor:
			//T=new Souliss_T_HumiditySensor(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T32_IrCom_AirCon:
			//T=new Souliss_T32(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T41_Antitheft_Main:
			//T=new SoulissT41(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T42_Antitheft_Peer:
			//T=new SoulissT42(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T_related:
			//T=new Souliss_T_related(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T51:
			//T=new SoulissT51(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;

		case Constants.Souliss_T52_TemperatureSensor:
		//	SoulissT52 Typ =  (SoulissT52) T;
			break;
		case Constants.Souliss_T53_HumiditySensor:
			//T=new SoulissT53(sSoulissNodeIPAddress, sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot);
			break;
		case Constants.Souliss_T54_LuxSensor:
			//T=new SoulissT54(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T55_VoltageSensor:
			//T=new SoulissT44(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T56_CurrentSensor:
			//T=new SoulissT56(sSoulissNodeIPAddress, sSoulissNodeVNetAddress, iSlot);
			break;
		case Constants.Souliss_T57_PowerSensor:
			//T=new SoulissT57(sSoulissNodeIPAddress, sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot);
			break;
		case Constants.Souliss_TService_NODE_HEALTY:
			//T=new SoulissTServiceNODE_HEALTY(sSoulissNodeIPAddress, sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot);
			break;
		case Constants.Souliss_TService_NODE_TIMESTAMP:
			//T=new SoulissTServiceNODE_TIMESTAMP(sSoulissNodeIPAddress, sSoulissNodeIPAddressOnLAN, iIDNodo, iSlot);
			break;
		default:
			LOGGER.debug("Typical Unknow");	
		}
		
		
	}



	@Override
	protected void internalReceiveCommand(String itemName, Command command) {
		// TODO Auto-generated method stub
		super.internalReceiveCommand(itemName, command);
		LOGGER.info("openHAB Event Bus -> External System - COMMAND. " + itemName + " = " + command );
	}

	@Override
	public void receiveUpdate(String itemName, State newState) {
		// TODO Auto-generated method stub
		super.receiveUpdate(itemName, newState);
		LOGGER.info("receiveUpdate - " + itemName + " = " + newState );
	}

	@Override
	protected boolean providesBindingFor(String itemName) {
		// TODO Auto-generated method stub
		return super.providesBindingFor(itemName);
	}

	@Override
	protected void internalReceiveUpdate(String itemName, State newState) {
		// TODO Auto-generated method stub
		super.internalReceiveUpdate(itemName, newState);
		LOGGER.info("openHAB Event Bus -> External System. " + itemName + " = " + newState );
	    for (SoulissBindingProvider provider : providers) {
	    	LOGGER.info("Checking provider with names " + provider.getItemNames());
	 
	    }
	
			}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	protected long getRefreshInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openhab.core.binding.AbstractActiveBinding#setProperlyConfigured(boolean)
	 */
	@Override
	protected void setProperlyConfigured(boolean properlyConfigured) {
		if (properlyConfigured){
			LOGGER.info("START");
			
			try {
			//passo anche la lista dei tipici (adesso � vuota ma comunque � passata per riferimento
			
			//AVVIO THREAD PER RICEZIONE PACHETTI UDP	
			UDPServerThread Q=null;
			Q=new UDPServerThread(SoulissGenericBindingProvider.SoulissTypicalsRecipients);
			Q.start();
			
			//AVVIO THREAD CHE SI OCCUPA DI INVIARE I PACCHETTI ALLA RETE SOULISS
			new SendDispatcherThread(SoulissNetworkParameter.SEND_DELAY,SoulissNetworkParameter.SEND_MIN_DELAY).start();	
			//AVVIO THREAD CHE SI OCCUPA DI COMUNICARE A OH I TIPICI AGGIORNATI 
			new MonitorThread(SoulissGenericBindingProvider.SoulissTypicalsRecipients, SoulissNetworkParameter.REFRESH_MONITOR_TIME, eventPublisher).start();
			//AVVIO THREAD CHE SI OCCUPA DELE SOTTOSCRIZIONI *******
			
			new RefreshSUBSCRIPTIONThread(Q.getSocket(),SoulissNetworkParameter.IPAddress, SoulissNetworkParameter.IPAddressOnLAN, SoulissNetworkParameter.nodes,SoulissNetworkParameter.REFRESH_SUBSCRIPTION_TIME).start();
			new RefreshHEALTYThread(Q.getSocket(), SoulissNetworkParameter.IPAddress, SoulissNetworkParameter.IPAddressOnLAN, SoulissNetworkParameter.nodes,SoulissNetworkParameter.REFRESH_HEALTY_TIME).start();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.error(e.getMessage());
			}
			
			}
		
	//	super.setProperlyConfigured(properlyConfigured);
			
	}

	public void putOnOHEventBus(String itemName, String value) {
		
		eventPublisher.postUpdate(itemName, new StringType(value));
			}
		
	
	
}
