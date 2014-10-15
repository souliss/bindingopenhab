package org.openhab.binding.souliss.internal;

import java.io.File;
import java.io.IOException;


//import org.openhab.binding.souliss.internal.network.SoulissLogger;
import org.openhab.binding.souliss.internal.network.typicals.Constants;
import org.openhab.binding.souliss.internal.network.typicals.MonitorThread;
import org.openhab.binding.souliss.internal.network.typicals.RefreshDBSTRUCTThread;
import org.openhab.binding.souliss.internal.network.typicals.RefreshHEALTYThread;
import org.openhab.binding.souliss.internal.network.typicals.RefreshSUBSCRIPTIONThread;
import org.openhab.binding.souliss.internal.network.typicals.SoulissGenericTypical;
import org.openhab.binding.souliss.internal.network.typicals.SoulissNetworkParameter;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT11;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT22;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT57;
import org.openhab.binding.souliss.internal.network.typicals.SoulissTypicals;
import org.openhab.binding.souliss.internal.network.typicals.StateTraslator;
import org.openhab.binding.souliss.internal.network.typicals.TypicalFactory;
import org.openhab.binding.souliss.internal.network.udp.SendDispatcherThread;
import org.openhab.binding.souliss.internal.network.udp.UDPServerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulissNetworkStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// PARAMETRI RETE SOULISS
		String sIPAddress="192.168.1.105";
		//String sIPAddress="78.12.99.62";
		String sIPAddressOnLAN="192.168.1.105";
		
		
		String sConfigurationFileName=".."+ File.separator  +"properties"+ File.separator  +"typicals_value_bytes.properties";
		String sConfigurationFileName_commands_OHtoSOULISS= ".." + File.separator  +"properties"+ File.separator  +"commands_OHtoSOULISS.properties";
		String sConfigurationFileName_states_SOULISStoOH= ".."+ File.separator  +"properties"+ File.separator  +"states_SOULISStoOH.properties";
		//time in mills
		final int REFRESH_DBSTRUCT_TIME=600000;
		final int REFRESH_SUBSCRIPTION_TIME=120000;
		final int REFRESH_HEALTY_TIME=10000;
		final int REFRESH_MONITOR_TIME=500;
		final int SEND_DELAY=1500;
		final int SEND_MIN_DELAY=10;

		Logger LOGGER = LoggerFactory.getLogger(SoulissNetworkStart.class);

	
		//Definizione dell'array che contiene i tipici
		SoulissTypicals soulissTypicalsRecipients= new SoulissTypicals();
		//**********************************************************
		//**********************************************************
		//**********************************************************
		//******* S T A R T ****************************************
		//**********************************************************
		//DOPO LA CREAZIONE DELLA RETE SOULISS, AVVIARE IL THREAD CHE SI OCCUPA DELLA SOTTOSCRIZIONE, AL QUALE SERVE IL NUMERO DI NODI. VEDERE ALLA FINE 
						UDPServerThread Q=null;
						try {
						//	SoulissLogger.setup();
							LOGGER.info("START");
							//continuare logger
							//http://www.vogella.com/tutorials/Logging/article.html
							SoulissNetworkParameter.IPAddress=sIPAddress;
							SoulissNetworkParameter.IPAddressOnLAN=sIPAddressOnLAN;
							SoulissNetworkParameter.load(sConfigurationFileName);
							StateTraslator.loadCommands(sConfigurationFileName_commands_OHtoSOULISS);
							StateTraslator.loadStates(sConfigurationFileName_states_SOULISStoOH);
							//passo anche la lista dei tipici (adesso � vuota ma comunque � passata per riferimento
							Q=new UDPServerThread(soulissTypicalsRecipients);
							Q.start();
							new SendDispatcherThread(SEND_DELAY, SEND_MIN_DELAY).start();	
							//� necessario passare il socket, gi� aperto sulla porta 230, perch� DBSTRUCT risponde sulla porta 230
						//	new RefreshDBSTRUCTThread(Q.getSocket(), sIPAddress, sIPAddressOnLAN, REFRESH_DBSTRUCT_TIME).start();
							new MonitorThread(soulissTypicalsRecipients, REFRESH_MONITOR_TIME, null).start();
									
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		
	    //SoulissCommGate.sendDBStructFrame(Q.getSocket() ,sIPAddress, sIPAddressOnLAN);
	    
	    
		//String sNodo4="0x6514";
//		int iIDNodo=3;
//		
//		int iIDNodoReadFloat=0;
//		int iT11_slot3=3;
//		int iT11_slot4=4;
//		int iSLOT_Watt=1;
//		
//	    int iSLOT_Curtain1=1;
	    
	    
	    
//NOTA: IMMEDIATAMENTE, OPPURE DOPO LA CREAZIONE DEL PRIMO TIPICO E' NECESSARIO INVIARE DBSTRUCT
//DBSTRUCT E' DISPONIBILE sia dal tipico che da SoulissCommGate. Il FRAME DBSTRUCT ricevuto riempirà la classe SoulissNetworkParameter
//ESEMPIO: invio comando DBSTRUCT
	  		//SoulissCommGate.sendDBStructFrame(Q.getSocket() ,soulissTypicalNew.getSoulissNodeIPAddress());
	  		//soulissTypicalT22_slot1.sendDBStructFrame(Q.getSocket());
//**********************************************************
//**********************************************************
		
		//ATTENZIONE: NELL'INTERFACCIA VERSO OPENHAB RICORDARSI DI GESTIRE I TIPICI DI SERVIZIO: HEALTY E TIMESTAMP
						//CHE SE MAPPATI NEL FILE DI CONFIGURAZIONE DI OPENHAB, NON DOVRANNO ESSERE CREATI QUI, MA SOLTANTO IGNORATI PERCHE' VERRANO CREATI A PARTE, DALLA CLASSE SoulissTServiceUpdater
						
						
		SoulissGenericTypical soulissTypicalNew;
		//DEFINIZIONE RETE SOULISS
		LOGGER.info("Creazione tipico: Souliss_T57_PowerSensor");
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T57_PowerSensor, SoulissNetworkParameter.datagramsocket, sIPAddress, sIPAddressOnLAN, 0, 4,"");
		soulissTypicalsRecipients.addTypical("consumo", soulissTypicalNew);
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T53_HumiditySensor, SoulissNetworkParameter.datagramsocket, sIPAddress, sIPAddressOnLAN, 0, 2,"");
		soulissTypicalsRecipients.addTypical("temperaturaSoggiorno", soulissTypicalNew);
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T52_TemperatureSensor,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 0, 0,"");
		soulissTypicalsRecipients.addTypical("umidit�Soggiorno", soulissTypicalNew);
				
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T11,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 0, 6,"");
		soulissTypicalsRecipients.addTypical("cancello", soulissTypicalNew);
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T11,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 0, 6,"");
		soulissTypicalsRecipients.addTypical("cancello", soulissTypicalNew);
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T52_TemperatureSensor,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 2, 0,"");
		soulissTypicalsRecipients.addTypical("temperaturaGiardino", soulissTypicalNew);
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T53_HumiditySensor,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 2, 2,"");
		soulissTypicalsRecipients.addTypical("umidit�Giardino", soulissTypicalNew);
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T22,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 3, 0,"");
		soulissTypicalsRecipients.addTypical("tenda1", soulissTypicalNew);
		((SoulissT22) soulissTypicalNew).CommandOPEN();
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T22,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 3, 1,"");
		soulissTypicalsRecipients.addTypical("tenda2", soulissTypicalNew);
		
		
		((SoulissT22) soulissTypicalNew).CommandOPEN();
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T22,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 3, 2,"");
		soulissTypicalsRecipients.addTypical("tenda3", soulissTypicalNew);
		
		
		((SoulissT22) soulissTypicalNew).CommandOPEN();
		
		((SoulissT22) soulissTypicalNew).CommandSTOP();
		
		((SoulissT22) soulissTypicalNew).CommandCLOSE();
		
		((SoulissT22) soulissTypicalNew).CommandSTOP();
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T11,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 3, 3,"");
		soulissTypicalsRecipients.addTypical("TYP11_1", soulissTypicalNew);
		((SoulissT11) soulissTypicalNew).CommandON();
		
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T11, SoulissNetworkParameter.datagramsocket, sIPAddress, sIPAddressOnLAN, 3, 4,"");
		soulissTypicalsRecipients.addTypical("TYP11_2", soulissTypicalNew);
		((SoulissT11) soulissTypicalNew).CommandON();
		
		//DEFINIZIONE TIPICI FITTIZI, PER COMUNICAZIONE DATI RETE
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_HEALTY,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 0,998,"");
		soulissTypicalsRecipients.addTypical("HEALTY NODO 0", soulissTypicalNew);
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_HEALTY,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 1,998,"");
		soulissTypicalsRecipients.addTypical("HEALTY NODO 1", soulissTypicalNew);
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_HEALTY,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 2,998,"");
		soulissTypicalsRecipients.addTypical("HEALTY NODO 2", soulissTypicalNew);
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_HEALTY,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 3,998,"");
		soulissTypicalsRecipients.addTypical("HEALTY NODO 3", soulissTypicalNew);
		
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_TIMESTAMP,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 0,999,"");
		soulissTypicalsRecipients.addTypical("TIMESTAMP NODO 0", soulissTypicalNew);
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_TIMESTAMP,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 1,999,"");
		soulissTypicalsRecipients.addTypical("TIMESTAMP NODO 1", soulissTypicalNew);
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_TIMESTAMP,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 2,999,"");
		soulissTypicalsRecipients.addTypical("TIMESTAMP NODO 2", soulissTypicalNew);
		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_TService_NODE_TIMESTAMP,SoulissNetworkParameter.datagramsocket,sIPAddress, sIPAddressOnLAN, 3,999,"");
		soulissTypicalsRecipients.addTypical("TIMESTAMP NODO 3", soulissTypicalNew);
		
		
		//******* S T A R T ****************************************
		//*** AVVIO THREAD CHE SI OCCUPA DELE SOTTOSCRIZIONI *******	
		int nodes=soulissTypicalsRecipients.getNodeNumbers();
		new RefreshSUBSCRIPTIONThread(Q.getSocket(), sIPAddress, sIPAddressOnLAN, nodes, REFRESH_SUBSCRIPTION_TIME).start();
		new RefreshHEALTYThread(Q.getSocket(), sIPAddress, sIPAddressOnLAN, nodes, REFRESH_HEALTY_TIME).start();
	}



}
//**********************************************************
		//*********** ESEMPI VARI **********************************
		
		//Aggiunta di un tipico T11 all'Array
		//item.getName() è il nome dell'item openhab
		//SoulissGenericTypical soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T11,sIPAddress, sIPAddressOnLAN, iIDNodo, iT11_slot3);
			
		
//		soulissTypicalsRecipients.addTypical("Tipico11UNO", soulissTypicalNew );
		
//		soulissTypicalNew = TypicalFactory.getClass(Constants.Souliss_T11,sIPAddress,sNodo4,iT11_slot4);
//		soulissTypicalsRecipients.addTypical("Tipico11DUE", soulissTypicalNew );
		
		//ESEMPIO: RECUPERO tipico conoscendo IP, VNET, SLOT
//		soulissTypicalNew=soulissTypicalsRecipients.getTypicalFromAddress(sIPAddress, sNodo4, iT11_slot3);
		
		//ESEMPIO: RECUPERO tipico conoscendo il nome item
//		soulissTypicalNew=soulissTypicalsRecipients.getTypicalFromItem("Tipico11UNO");
		
			
		//((SoulissT11) soulissTypicalNew).CommandTOGGLE();
		
		
//		SoulissT11 soulissTypicalT11_slot4 = (SoulissT11) TypicalFactory.getClass(Constants.Souliss_T11,sIPAddress,sIPAddressOnLAN,"", iIDNodo, iT11_slot4);
//		SoulissT11 soulissTypicalT11_slot3 = (SoulissT11) TypicalFactory.getClass(Constants.Souliss_T11,sIPAddress,sIPAddressOnLAN, "", iIDNodo, iT11_slot3);
//		
//		soulissTypicalT11_slot4.CommandON();

		//SoulissT22 soulissTypicalT22_slot1 = (SoulissT22) TypicalFactory.getClass(Constants.Souliss_T21, sIPAddress, sIPAddressOnLAN, "", iIDNodo, 1);
		//soulissTypicalT22_slot1.CommandOPEN();
//		soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandCLOSE();
//		soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandOPEN();
//	soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandCLOSE();
//		soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandOPEN();
//		soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandCLOSE();
//		soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandOPEN();
//		soulissTypicalT22_slot1.CommandSTOP();
//		soulissTypicalT22_slot1.CommandCLOSE();
//		soulissTypicalT22_slot1.CommandSTOP();
////		System.out.print(a);
//		soulissTypicalT11_slot4.CommandOFF();
//		soulissTypicalT11_slot4.CommandON();
//		
//		soulissTypicalT11_slot4.CommandOFF();
//
//		soulissTypicalT11_slot3.CommandON();
//		soulissTypicalT11_slot4.CommandON();
//		soulissTypicalT11_slot4.CommandOFF();
//		soulissTypicalT11_slot4.CommandON();
//		soulissTypicalT11_slot3.CommandOFF();
//		soulissTypicalT11_slot4.CommandOFF();
//		soulissTypicalT11_slot4.CommandON();
//		soulissTypicalT11_slot4.CommandOFF();
//		soulissTypicalT11_slot3.CommandON();
//		soulissTypicalT11_slot4.CommandON();
//		soulissTypicalT11_slot4.CommandOFF();
//		soulissTypicalT11_slot3.CommandOFF();
		
		
		//ESEMPIO: invio comando MULTICAST
		//soulissTypicalT11_slot4.CommandMulticastOFF();
	//	soulissTypicalT21_slot1.CommandMulticastCLOSE();
		
	//	soulissTypicalT22_slot1.ping(Q.getSocket(), (short) 343423, (short) 452345);
			
		//SoulissT57 soulissTypicalT57_slot1 = (SoulissT57) TypicalFactory.getClass(Constants.Souliss_T57_PowerSensor, sIPAddress, sIPAddressOnLAN, "", iIDNodoReadFloat, iSLOT_Watt);
		
	//	soulissTypicalT57_slot1.CommandREADANALOG(Q.getSocket());
		
		
//		try {
//			Thread.sleep(8000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	//	Q.closeSocket();


