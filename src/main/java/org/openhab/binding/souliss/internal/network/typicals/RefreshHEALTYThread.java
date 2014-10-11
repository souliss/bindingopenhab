package org.openhab.binding.souliss.internal.network.typicals;

import java.net.DatagramSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.souliss.internal.network.udp.SoulissCommGate;

public class RefreshHEALTYThread extends Thread {

	int REFRESH_TIME;
	DatagramSocket socket=null;
	String SoulissNodeIPAddress="";
	String soulissNodeIPAddressOnLAN="";
int iNodes=0;
private static Logger LOGGER = LoggerFactory.getLogger(RefreshHEALTYThread.class);

	public RefreshHEALTYThread(DatagramSocket datagramsocket, String soulissNodeIPAddress, String soulissNodeIPAddressOnLAN, int nodes, int iRefreshTime) {
		// TODO Auto-generated constructor stub
		REFRESH_TIME=iRefreshTime;
		this.socket=datagramsocket;
		this.SoulissNodeIPAddress=soulissNodeIPAddress;
		this.soulissNodeIPAddressOnLAN=soulissNodeIPAddressOnLAN;
		iNodes=nodes;
		LOGGER.info("Avvio RefreshDBSTRUCTThread");
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				LOGGER.info("sendHEALTY_REQUESTframe");
				SoulissCommGate.sendHEALTY_REQUESTframe(socket, SoulissNodeIPAddress, soulissNodeIPAddressOnLAN, iNodes);
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
