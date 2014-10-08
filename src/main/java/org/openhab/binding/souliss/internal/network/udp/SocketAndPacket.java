package org.openhab.binding.souliss.internal.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SocketAndPacket {
	public SocketAndPacket(DatagramSocket socketPar, DatagramPacket packetPar) {
		socket=socketPar;
		packet=packetPar;
	}
	public DatagramSocket socket;
	public DatagramPacket packet;
}
