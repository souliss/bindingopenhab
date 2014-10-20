package org.openhab.binding.souliss.internal.network.typicals;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulissNetworkParameter {
	public static String IPAddress="";
	public static String IPAddressOnLAN="";
	public static int nodes; 
	public static int maxnodes;
	public static int maxTypicalXnode;
	public static int maxrequests ;
	public static int MaCacoIN_s ;
	public static int MaCacoTYP_s ;
	public static int MaCacoOUT_s ;
	static Properties prop=new Properties();
	public static int presetTime=999999;
	public static int REFRESH_DBSTRUCT_TIME=presetTime;
	public static int REFRESH_SUBSCRIPTION_TIME=presetTime;
	public static int REFRESH_HEALTY_TIME=presetTime;
	public static int REFRESH_MONITOR_TIME=presetTime;
	public static int SEND_DELAY=presetTime;
	public static int SEND_MIN_DELAY=presetTime;
	private static Logger LOGGER = LoggerFactory.getLogger(SoulissNetworkParameter.class);
	public static int NodeIndex = 70;
	public static int UserIndex = 133;
	private static boolean bConfigured =false;
	public static Integer serverPort;
	public static DatagramSocket  datagramsocket;
	
	
	/**
	 * @return the bConfigured
	 */
	public static boolean isConfigured() {
		return bConfigured;
	}


	/**
	 * @param bConfigured the bConfigured to set
	 */
	public static void setConfigured(boolean bConfigured) {
		SoulissNetworkParameter.bConfigured = bConfigured;
	}


	public static String getPropTypicalBytes(String sPar) {
		return (String) prop.get("0x"+sPar);
	}

	
	public static void load(InputStream is) {
		try {
			prop.load(is);
			LOGGER.info("ok");
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}

	
}
