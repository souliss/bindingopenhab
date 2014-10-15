package org.openhab.binding.souliss.internal.network.udp;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.souliss.internal.network.typicals.SoulissNetworkParameter;
import org.openhab.binding.souliss.internal.network.typicals.SoulissTypicals;
 
public class UDPServerThread extends Thread {
 
   // protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean bExit = false;
    UDPSoulissDecoder decoder=null;
    private static Logger LOGGER = LoggerFactory.getLogger(UDPServerThread.class);
    
    public UDPServerThread(SoulissTypicals typicals ) throws IOException {
    	super();
     //   socket = new DatagramSocket(ConstantsUDP.SERVERPORT);
    	if (SoulissNetworkParameter.serverPort!= null){
    		SoulissNetworkParameter.datagramsocket = new DatagramSocket(SoulissNetworkParameter.serverPort);
    	}
    		else{
    			SoulissNetworkParameter.datagramsocket = new DatagramSocket();
    		}
    			
    	
    	decoder=new UDPSoulissDecoder(typicals);
        LOGGER.info("Avvio UDPServerThread - Server in ascolto sulla porta " + SoulissNetworkParameter.datagramsocket.getLocalPort());
    }
 
    public void run() {
 
        while (!bExit) {
            try {
                byte[] buf = new byte[256];
 
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                SoulissNetworkParameter.datagramsocket.receive(packet);
                 buf= packet.getData();
              //  System.out.println(buf.toString());
                
                //**************** DECODER ********************
                LOGGER.debug("Packet received");
                decoder.decodeVNetDatagram(packet);
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
                bExit = true;
            }
        }
        
        SoulissNetworkParameter.datagramsocket.close();
    }
 
   
    public DatagramSocket getSocket(){
    	return SoulissNetworkParameter.datagramsocket;
    }

	public void closeSocket() {
		SoulissNetworkParameter.datagramsocket.close();
		bExit=true;
	}
}