package org.openhab.binding.souliss.internal.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendDispatcherThread  extends Thread {

	static ArrayList<SocketAndPacket> packetsList = new ArrayList<SocketAndPacket>();	
	protected boolean bExit = false;
	static int iDelay=0; //equal to 0 if array is empty
	int SEND_DELAY;
	int SEND_MIN_DELAY;
	private static Logger LOGGER = LoggerFactory.getLogger(SendDispatcherThread.class);
	
	private static boolean bCheck=true;		
	
	public SendDispatcherThread(int SEND_DELAY, int SEND_MIN_DELAY) throws IOException {
	    this("SendDispatcher");
	    this.SEND_DELAY=SEND_DELAY;
	    this.SEND_MIN_DELAY=SEND_MIN_DELAY;
	    LOGGER.info("Avvio SendDispatcherThread");
	}
	
	public SendDispatcherThread(String name) {
		  super(name);

	}

	public synchronized static void put(DatagramSocket socket, DatagramPacket packetToPUT) {
		if(bCheck){	
			bCheck=false;
			LOGGER.debug("Ottimizzazione frame");
			//OTTIMIZZAZIONE FRAME
			boolean bPacchettoGestito=false;
			int node=getNode(packetToPUT);
			if (packetsList.size()==0 || node < 0) {
				bPacchettoGestito=true;
				LOGGER.debug("Aggiunto frame UPD in lista");
				packetsList.add( new SocketAndPacket(socket, packetToPUT));
			} else{
				LOGGER.debug("Frame UPD per nodo " + node + " già presente il lista. Esecuzione ottimizzazione.");
			for(int i=0; i<packetsList.size();i++){
				//estraggo il nodo indirizzato dal pacchetto in ingresso
				//node=getNode(packetToPUT);
				//se il nodo da inserire � un comando FORCE restituisce valore > -1 uguale al numero di nodo indirizzato
				//e se il nodo indirizzato � uguale a quello packetsList.get(i).packet presente in lista
				//e se il socket � lo stesso uguale a quello packetsList.get(i).socket presente in lista
				//if(node >=0 && getNode(packetsList.get(i).packet)==node && packetsList.get(i).socket==socket)
				if(node >=0 && getNode(packetsList.get(i).packet)==node ) {
					bPacchettoGestito=true;
					//se il pacchetto da inserire � pi� corto (o uguale) di quello in lista allora sovrascrivo i byte del pacchetto presente in lista
					if(packetToPUT.getData().length<=packetsList.get(i).packet.getData().length){
						//scorre i byte di comando e se il byte � diverso da zero sovrascrive il byte presente nel pacchetto in lista
						for (int j=12;j<packetToPUT.getData().length;j++){
							//se il j-esimo byte � diverso da zero allora lo sovrascrivo al byte del pacchetto gi� presente 
							if(packetToPUT.getData()[j]!=0){
								packetsList.get(i).packet.getData()[j]=packetToPUT.getData()[j];
							}
							
						}
				//		System.out.println("Ottimizzazione frame: aggiunti byte a pacchetto gi� esistente in lista");
					}else {
						//se il pacchetto da inserire  è più lungo di quello in lista allora sovrascrivo i byte del pacchetto da inserire, poi elimino quello in lista ed inserisco quello nuovo 
						if(packetToPUT.getData().length>packetsList.get(i).packet.getData().length){
							for (int j=12;j<packetsList.get(i).packet.getData().length;j++){
								//se il j-esimo byte � diverso da zero allora lo sovrascrivo al byte del pacchetto gi� presente 
								if(packetsList.get(i).packet.getData()[j]!=0){
									if(packetToPUT.getData()[j]==0){
										//sovrascrive i byte dell'ultimo frame soltanto se il byte � uguale a zero. Se � diverso da zero l'ultimo frame ha la precedenza e deve sovrascrivere
									packetToPUT.getData()[j]=packetsList.get(i).packet.getData()[j];
									}
								}
							}
							//rimuove il pacchetto
							packetsList.remove(i);
							//inserisce il nuovo
							packetsList.add( new SocketAndPacket(socket, packetToPUT));
			//				System.out.println("Ottimizzazione frame: aggiunti byte a pacchetto da inserire, eliminato quello in lista ed aggiunto quello nuovo");
						}
					}
				}
			}
			}
			
			if(!bPacchettoGestito){
				packetsList.add( new SocketAndPacket(socket, packetToPUT));
			}
			bCheck=true;
		}
		
	}

	private static int getNode(DatagramPacket packet) {
		//7 � il byte del frame VNet al quale trovo il codice comando
		//10 � il byte del frame VNet al quale trovo l'ID del nodo
		if (packet.getData()[7]==(byte) ConstantsUDP.Souliss_UDP_function_force){
			return packet.getData()[10];
		}
		return -1;
	}

	private synchronized SocketAndPacket pop(){
		if(bCheck){	
			bCheck=false;
			if (packetsList.size()<=1) 
				iDelay=SEND_MIN_DELAY; 
			else 
				iDelay=SEND_DELAY;

			if (packetsList.size()>0){
				bCheck=true;
				return packetsList.remove(0);
			}
		}
		bCheck=true;
		return null;
	}
	
	
	public void run() {

		while (!bExit) {

			try {
				Thread.sleep(iDelay);		  
				SocketAndPacket sp= pop();
				if(sp!=null) {
					LOGGER.debug("SendDispatcherThread - Functional Code 0x" + Integer.toHexString(sp.packet.getData()[7]) + " - Packet: " + MaCacoToString(sp.packet.getData()) +  " - Elementi rimanenti in lista: " + packetsList.size());	
					sp.socket.send(sp.packet);
				}
			} catch (IOException |InterruptedException e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage());
				bExit = true;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.error(e.getMessage());
			}
		}
	}
	
	private String MaCacoToString(byte[] frame) {
		StringBuilder sb = new StringBuilder();
		sb.append("HEX: [");
	    for (byte b : frame) {
	        sb.append(String.format("%02X ", b));
	    }
	    sb.append("]");
	    return sb.toString();
	}
}


