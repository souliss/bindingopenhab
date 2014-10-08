package org.openhab.binding.souliss.internal.network.udp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import org.openhab.binding.souliss.internal.network.typicals.Constants;
import org.openhab.binding.souliss.internal.network.typicals.SoulissTypicals;
 
public class UDPServerThread extends Thread {
 
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean bExit = false;
    UDPSoulissDecoder decoder=null;
    final Logger LOGGER = Logger.getLogger(Constants.LOGNAME);
    
    public UDPServerThread(SoulissTypicals typicals ) throws IOException {
    	super();
        socket = new DatagramSocket(ConstantsUDP.SERVERPORT);
        decoder=new UDPSoulissDecoder(typicals);
        LOGGER.info("Avvio UDPServerThread");
    }
 
    public void run() {
 
        while (!bExit) {
            try {
                byte[] buf = new byte[256];
 
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                 buf= packet.getData();
              //  System.out.println(buf.toString());
                
                //**************** DECODER ********************
                LOGGER.fine("Packet received");
                decoder.decodeVNetDatagram(packet);
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.severe(e.getMessage());
                bExit = true;
            }
        }
        
        socket.close();
    }
 
   
    public DatagramSocket getSocket(){
    	return socket;
    }

	public void closeSocket() {
		bExit=true;
	}
}