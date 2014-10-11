package org.openhab.binding.souliss.internal.network.udp;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.souliss.internal.network.typicals.SoulissTypicals;
 
public class UDPServerThread extends Thread {
 
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean bExit = false;
    UDPSoulissDecoder decoder=null;
    private static Logger LOGGER = LoggerFactory.getLogger(UDPServerThread.class);
    
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
                LOGGER.debug("Packet received");
                decoder.decodeVNetDatagram(packet);
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
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