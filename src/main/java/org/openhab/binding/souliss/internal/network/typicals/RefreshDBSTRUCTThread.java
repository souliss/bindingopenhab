package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;

public class RefreshDBSTRUCTThread extends Thread {

	int REFRESH_TIME;
	DatagramSocket socket=null;
	String SoulissNodeIPAddress="";
	String soulissNodeIPAddressOnLAN="";
	private static Logger LOGGER = LoggerFactory.getLogger(RefreshDBSTRUCTThread.class);

	public RefreshDBSTRUCTThread(DatagramSocket datagramsocket, String soulissNodeIPAddress, String soulissNodeIPAddressOnLAN, int iRefreshTime) {
		// TODO Auto-generated constructor stub
		REFRESH_TIME=iRefreshTime;
		this.socket=datagramsocket;
		this.SoulissNodeIPAddress=soulissNodeIPAddress;
		this.soulissNodeIPAddressOnLAN=soulissNodeIPAddressOnLAN;
		LOGGER.info("Avvio RefreshDBSTRUCTThread");
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				LOGGER.info("sendDBStructFrame");
				SoulissCommGate.sendDBStructFrame(socket, SoulissNodeIPAddress, soulissNodeIPAddressOnLAN);
				Thread.sleep(REFRESH_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.error(e.getMessage());
			}
			super.run();



		}
	}

}
