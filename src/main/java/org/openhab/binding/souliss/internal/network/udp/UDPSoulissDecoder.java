package org.openhab.binding.souliss.internal.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.souliss.internal.network.typicals.Constants;
import org.openhab.binding.souliss.internal.network.typicals.SoulissGenericTypical;
import org.openhab.binding.souliss.internal.network.typicals.SoulissNetworkParameter;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT11;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT22;
import org.openhab.binding.souliss.internal.network.typicals.SoulissT57;
import org.openhab.binding.souliss.internal.network.typicals.SoulissTServiceUpdater;
import org.openhab.binding.souliss.internal.network.typicals.SoulissTypicals;


/**
 * Classe per il decode dei pacchetti nativi souliss
 * 
 * This class decodes incoming Souliss packets, starting from decodevNet
 * 
 * @author Ale
 * 
 */
public class UDPSoulissDecoder {

	private SoulissTypicals soulissTypicalsRecipients;
	private static Logger LOGGER = LoggerFactory.getLogger(UDPSoulissDecoder.class);
	  
	public UDPSoulissDecoder(SoulissTypicals typicals) {
		soulissTypicalsRecipients=typicals;
	}

	/**
	 * processa il pacchetto UDP ricevuto e agisce di conseguenza
	 * 
	 * @param packet
	 *            incoming datagram
	 */
	public void decodeVNetDatagram(DatagramPacket packet) {
		int checklen = packet.getLength();

		// Log.d(Constants.TAG, "** Packet received");
		ArrayList<Short> mac = new ArrayList<Short>();
		for (int ig = 7; ig < checklen; ig++) {
			mac.add((short) (packet.getData()[ig] & 0xFF));
		}
		decodeMacaco(mac);
	}

	/**
	 * Decodes lower level MaCaCo packet
	 * 
	 * @param macacoPck
	 */
	private void decodeMacaco(ArrayList<Short> macacoPck) {
		int functionalCode = macacoPck.get(0);
		//System.out.println("received functional code: 0x" + Integer.toHexString(functionalCode) );
		LOGGER.info("Received functional code: 0x" + Integer.toHexString(functionalCode) );
		switch (functionalCode) {
		
		case (byte) ConstantsUDP.Souliss_UDP_function_ping_resp:
			LOGGER.info("function_ping_resp");
			decodePing(macacoPck);
		break;
		//		case ConstantsUDP.Souliss_UDP_function_ping_bcast_resp:
		//			// assertEquals(mac.size(), 8);
		//			//Log.d(Constants.TAG, "** Ping BROADCAST response bytes " + macacoPck.size());
		//			decodePing(macacoPck);
		//			break;
		case (byte) ConstantsUDP.Souliss_UDP_function_subscribe_resp:
		case (byte) ConstantsUDP.Souliss_UDP_function_poll_resp:
			LOGGER.info("Souliss_UDP_function_subscribe_resp / Souliss_UDP_function_poll_resp");
			decodeStateRequest(macacoPck);
		//	processTriggers();
		break;

		//		case ConstantsUDP.Souliss_UDP_function_typreq_resp:// Answer for assigned
		//														// typical logic
		//		//	Log.d(Constants.TAG, "** TypReq answer");
		//			decodeTypRequest(macacoPck);
		//			break;
		case (byte) ConstantsUDP.Souliss_UDP_function_health_resp:// Answer nodes healty
			//	Log.d(Constants.TAG, "** Health answer");
			LOGGER.info("function_health_resp");
			decodeHealthRequest(macacoPck);
			break;
		case (byte) ConstantsUDP.Souliss_UDP_function_db_struct_resp:// Answer nodes
			//assertEquals(macacoPck.size(), 9); // healty
			//	Log.w(Constants.TAG, "** DB Structure answer");
			LOGGER.info("function_db_struct_resp");
			decodeDBStructRequest(macacoPck);
		break;
		case 0x83:
			//Log.e(Constants.TAG, "** (Functional code not supported)");
			LOGGER.info("Functional code not supported");
			break;
		case 0x84:
			//Log.e(Constants.TAG, "** (Data out of range)");
			LOGGER.info("Data out of range");
			break;
		case 0x85:
			//Log.e(Constants.TAG, "** (Subscription refused)");
			LOGGER.info("Subscription refused");
			break;
		default:
			LOGGER.info("Unknown functional code");
			//	Log.e(Constants.TAG, "** Unknown functional code: " + functionalCode);
			break;
		}

	}

	//	private void processTriggers() {
	//		try {
	//
	//			List<SoulissNode> ref = database.getAllNodes();
	//			List<SoulissTrigger> triggers = database.getAllTriggers(context);
	//			Log.i(Constants.TAG, "checked triggers: " + triggers.size());
	//			// logThings(refreshedNodes);
	//
	//			Map<Short, SoulissNode> refreshedNodes = new HashMap<Short, SoulissNode>();
	//
	//			/* Check antifurto */
	//
	//			for (SoulissNode soulissNode : ref) {
	//				refreshedNodes.put(soulissNode.getId(), soulissNode);
	//				if (opzioni.isAntitheftPresent() && opzioni.isAntitheftNotify()) {// giro
	//																					// i
	//																					// tipici
	//																					// solo
	//																					// se
	//																					// seve
	//					for (SoulissTypical ty : soulissNode.getTypicals()) {
	//						// check Antitheft
	//						if (ty.getTypicalDTO().getTypical() == Souliss_T41_Antitheft_Main
	//								&& ty.getTypicalDTO().getOutput() == Souliss_T4n_InAlarm) {
	//							sendAntiTheftNotification(context, context.getString(R.string.antitheft_notify),
	//									context.getString(R.string.antitheft_notify_desc), R.drawable.shield);
	//							break;
	//						}
	//					}
	//				}
	//			}
	//			for (SoulissTrigger soulissTrigger : triggers) {
	//				SoulissCommandDTO command = soulissTrigger.getCommandDto();
	//				SoulissTriggerDTO src = soulissTrigger.getTriggerDto();
	//				SoulissTypical source = refreshedNodes.get(src.getInputNodeId()).getTypical(src.getInputSlotlot());
	//				// SoulissTypical target =
	//				// refreshedNodes.get(command.getNodeId()).getTypical(command.getSlot());
	//				Calendar now = Calendar.getInstance();
	//				if (!soulissTrigger.getTriggerDto().isActivated()) {
	//					String op = src.getOp();
	//					if (">".compareTo(op) == 0 && source.getTypicalDTO().getOutput() > src.getThreshVal()) {
	//						Log.w(Constants.TAG, "TRIGGERING COMMAND " + command.toString());
	//						soulissTrigger.getTriggerDto().setActive(true);
	//						UDPHelper.issueSoulissCommand("" + command.getNodeId(), "" + command.getSlot(),
	//								SoulissClient.getOpzioni(), command.getType(), "" + command.getCommand());
	//						command.setExecutedTime(now);
	//						soulissTrigger.persist(database);
	//						SoulissDataService.sendNotification(context, command.toString(), soulissTrigger.toString(),
	//								R.drawable.lighthouse);
	//					} else if ("<".compareTo(op) == 0 && source.getTypicalDTO().getOutput() < src.getThreshVal()) {
	//						Log.w(Constants.TAG, "TRIGGERING COMMAND " + command.toString());
	//						soulissTrigger.getTriggerDto().setActive(true);
	//						UDPHelper.issueSoulissCommand("" + command.getNodeId(), "" + command.getSlot(),
	//								SoulissClient.getOpzioni(), command.getType(), "" + command.getCommand());
	//						soulissTrigger.getCommandDto().setExecutedTime(now);
	//						soulissTrigger.persist(database);
	//						SoulissDataService.sendNotification(context, command.toString(), soulissTrigger.toString(),
	//								R.drawable.lighthouse);
	//					} else if ("=".compareTo(op) == 0 && source.getTypicalDTO().getOutput() == src.getThreshVal()) {
	//						Log.w(Constants.TAG, "TRIGGERING COMMAND " + command.toString());
	//						UDPHelper.issueSoulissCommand("" + command.getNodeId(), "" + command.getSlot(),
	//								SoulissClient.getOpzioni(), command.getType(), "" + command.getCommand());
	//						soulissTrigger.getTriggerDto().setActive(true);
	//						soulissTrigger.getCommandDto().setExecutedTime(now);
	//						soulissTrigger.persist(database);
	//						SoulissDataService.sendNotification(context, command.toString(), soulissTrigger.toString(),
	//								R.drawable.lighthouse);
	//					}
	//				}
	//				// vedi se bisogna disattivare
	//				else {
	//					String op = src.getOp();
	//					if (">".compareTo(op) == 0 && source.getTypicalDTO().getOutput() <= src.getThreshVal()) {
	//						Log.w(Constants.TAG, "DEACTIVATE TRIGGER " + command.toString());
	//						soulissTrigger.getTriggerDto().setActive(false);
	//						soulissTrigger.persist(database);
	//					} else if ("<".compareTo(op) == 0 && source.getTypicalDTO().getOutput() >= src.getThreshVal()) {
	//						Log.w(Constants.TAG, "DEACTIVATE TRIGGER " + command.toString());
	//						soulissTrigger.getTriggerDto().setActive(false);
	//						soulissTrigger.persist(database);
	//					} else if ("=".compareTo(op) == 0 && source.getTypicalDTO().getOutput() != src.getThreshVal()) {
	//						Log.w(Constants.TAG, "DEACTIVATE TRIGGER " + command.toString());
	//						soulissTrigger.getTriggerDto().setActive(false);
	//						soulissTrigger.persist(database);
	//					}
	//				}
	//
	//			}
	//		} catch (IllegalStateException e) {
	//			Log.e(Constants.TAG, "DB connection was closed, check trigger impossible");
	//			return;
	//		}
	//
	//	}

	//	/**
	//	 * Alla ricezione di una risposta ping, aggiorna il cached address F e`
	//	 * locale, se trovo B e` Remoto
	//	 * 
	//	 * @param mac
	//	 */
	private void decodePing(ArrayList<Short> mac) {
		// int nodes = mac.get(5);
		int putIn_1 = mac.get(1);
		int putIn_2 = mac.get(2);
		System.out.println("putIn code: " + putIn_1 + ", "+ putIn_2);

	}

	/**
	 * Sovrascrive la struttura I nodi e la struttura dei tipici e richiama
	 * UDPHelper.typicalRequest(opzioni, nodes, 0);
	 * 
	 * @param mac
	 */
	private void decodeDBStructRequest(ArrayList<Short> mac) {
		// Threee static bytes
		//assertEquals(4, (short) mac.get(4));
		try {
			int nodes = mac.get(5);
			int maxnodes = mac.get(6);
			int maxTypicalXnode = mac.get(7);
			int maxrequests = mac.get(8);
			int MaCaco_IN_S =mac.get(9);
			int MaCaco_TYP_S = mac.get(10);
			int MaCaco_OUT_S=mac.get(11);

		SoulissNetworkParameter.nodes=nodes;
		SoulissNetworkParameter.maxnodes=maxnodes;
		SoulissNetworkParameter.maxTypicalXnode=maxTypicalXnode;
		SoulissNetworkParameter.maxrequests=maxrequests;
		SoulissNetworkParameter.MaCacoIN_s=MaCaco_IN_S;
		SoulissNetworkParameter.MaCacoTYP_s=MaCaco_TYP_S;
		SoulissNetworkParameter.MaCacoOUT_s=MaCaco_OUT_S;
		} catch (Exception e) {
			System.out.println ("SoulissNetworkParameter update ERROR");
		}
		System.out.println ("SoulissNetworkParameter updated");
		//		System.out.println ("-Nodes: "+ nodes);
		//		System.out.println ("-maxnodes: "+ maxnodes);
		//		System.out.println ("-maxTypicalXnode: "+ maxTypicalXnode);
		//		System.out.println ("-maxrequests: "+ maxrequests);
		//		System.out.println ("-MaCaco_IN_S: "+ MaCaco_IN_S);
		//		System.out.println ("-MaCaco_TYP_S: "+ MaCaco_TYP_S);
		//		System.out.println ("-MaCaco_OUT_S: "+ MaCaco_OUT_S);
	}

	/**
	 * Definizione dei tipici
	 * 
	 * @param mac
	 */
	//	private void decodeTypRequest(ArrayList<Short> mac) {
	//		try {
	//			assertEquals(Constants.Souliss_UDP_function_typreq_resp, (short) mac.get(0));
	//			SharedPreferences.Editor editor = soulissSharedPreference.edit();
	//			short tgtnode = mac.get(3);
	//			int numberOf = mac.get(4);
	//			int done = 0;
	//			// SoulissNode node = database.getSoulissNode(tgtnode);
	//			int typXnodo = soulissSharedPreference.getInt("TipiciXNodo", 1);
	//			Log.i(Constants.TAG, "--DECODE MACACO OFFSET:" + tgtnode + " NUMOF:" + numberOf + " TYPICALSXNODE: "
	//					+ typXnodo);
	//			// creates Souliss nodes
	//			for (int j = 0; j < numberOf; j++) {
	//				if (mac.get(5 + j) != 0) {// create only not-empty typicals
	//					SoulissTypicalDTO dto = new SoulissTypicalDTO();
	//					dto.setTypical(mac.get(5 + j));
	//					dto.setSlot(((short) (j % typXnodo)));// magia
	//					dto.setNodeId((short) (j / typXnodo + tgtnode));
	//					// conta solo i master
	//					if (mac.get(5 + j) != it.angelic.soulissclient.model.typicals.Constants.Souliss_T_related)
	//						done++;
	//					Log.d(Constants.TAG, "---PERSISTING TYPICAL ON NODE:" + ((short) (j / typXnodo + tgtnode))
	//							+ " SLOT:" + ((short) (j % typXnodo)) + " TYP:" + (mac.get(5 + j)));
	//					dto.persist();
	//				}
	//			}
	//			if (soulissSharedPreference.contains("numTipici"))
	//				editor.remove("numTipici");// unused
	//			editor.putInt("numTipici", done);
	//			editor.commit();
	//			Log.i(Constants.TAG, "Refreshed " + numberOf + " typicals for node " + tgtnode);
	//		} catch (Exception uy) {
	//			Log.e(Constants.TAG, "decodeTypRequest ERROR", uy);
	//		}
	//	}

	/**
	 * puo giungere in seguito a state request oppure come subscription data
	 * della publish. Semantica = a typical request. Aggiorna il DB solo se il
	 * tipico esiste
	 * 
	 * @param mac
	 */
	private void decodeStateRequest(ArrayList<Short> mac) {

		int tgtnode = mac.get(3);
//		QUI. AGGIORNAMENTO DEL TIMESTAMP PER OGNI NODO. DA FARE USANDO NODI FITTIZI
		SoulissTServiceUpdater.updateTIMESTAMP(soulissTypicalsRecipients, tgtnode);

		//int numberOf = mac.get(4);
		//int typXnodo = SoulissNetworkParameter.maxTypicalXnode;

		//sfoglio hashtable e scelgo tipici nodo indicato nel frame
		// leggo valore tipico in base allo slot

		Iterator<Entry<String, SoulissGenericTypical>> iteratorTypicals= soulissTypicalsRecipients.getIterator();
		synchronized (iteratorTypicals) {
			while (iteratorTypicals.hasNext()){
				SoulissGenericTypical typ=iteratorTypicals.next().getValue();
				//se il tipico estratto appartiene al nodo che il frame deve aggiornare...

				if(typ.getSoulissNodeID()==tgtnode){

					//...allora controllo lo slot
					int slot=typ.getSlot();
					//...ed aggiorno lo stato in base al tipo
					int iNumBytes=0;

				try {
						String sHex=Integer.toHexString(typ.getType());
						String sRes=SoulissNetworkParameter.getPropTypicalBytes(sHex);
						if (sRes!=null) iNumBytes=Integer.parseInt(sRes);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						iNumBytes=0;
					}

					if(iNumBytes==1){
						//caso valori digitali
						float val=getByteAtSlot( mac, slot);
						typ.setState(val);
						//System.out.println("Nodo " + tgtnode + ", Slot: " + slot + ", Byte val: " + getByteAtSlot( mac, slot) );
					}else if(iNumBytes==2){
						//caso valori float
						float val=getFloatAtSlot(mac, slot);
						typ.setState(val);
						//System.out.println("Nodo " + tgtnode + ", Slot: " + slot + ", Float val: " + getFloatAtSlot( mac, slot) );
					}
				}
			}
		}
	}

	private Short getByteAtSlot(ArrayList<Short> mac, int slot) {
		return mac.get(5 + slot);
		
	}
	
	private float getFloatAtSlot(ArrayList<Short> mac, int slot) {
			// refresh typicals
			//for (short j = 0; j < numberOf; j=(short) (j+2)) {

				int iOutput=mac.get(5 + slot);
				//int iSlot = (short) (j % typXnodo);
				//int iNodeId = (short) (j / typXnodo + tgtnode);

//				System.out.println("-iNodeId: "+ iNodeId);
//				System.out.println("-iSlot: "+ iSlot);
//				//System.out.println("iOutput: "+ iOutput);

				int iOutput2=mac.get(5 + slot+1);
				//ora ho i due bytes, li converto
				int shifted = iOutput2 << 8;
				float ret = HalfFloatUtils.toFloat(shifted + iOutput);
			//}
		//}

		return ret;
	}

/**
	 * Decodes a souliss nodes health request
	 * 
	 * @param macaco
	 *            packet
	 */
	private void decodeHealthRequest(ArrayList<Short> mac) {
		// Threee static bytes
		int tgtnode = mac.get(3);
		int numberOf = mac.get(4);

		ArrayList<Short> healths = new ArrayList<Short>();
		// build an array containing healths
		for (int i = 5; i < 5 + numberOf; i++) {
			//healths.add(Short.valueOf(mac.get(i)));
			SoulissTServiceUpdater.updateHEALTY(soulissTypicalsRecipients, i-5,Short.valueOf(mac.get(i)) );
			LOGGER.debug("node " + (i-5) + " = "+ Short.valueOf(mac.get(i)));
		}
	}
}
