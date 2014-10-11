package org.openhab.binding.souliss.internal.network.typicals;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateTraslator {
	static Properties propCommands=new Properties();
	static Properties propStates=new Properties();
	static Properties propTypes=new Properties();
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(StateTraslator.class);
	
	
	public static short stringToSOULISSTypicalCode(String sTypeString) {
		String sRes=null;
		sRes=propTypes.getProperty(sTypeString);
  		return  Short.decode(sRes);
	}
	
	public static Short commandsOHtoSOULISS(int typ, String sCommand) {
		String sRes=null;
		sRes=propCommands.getProperty("0x"+Short.decode(Integer.toHexString(typ))+Constants.CONF_FIELD_DELIMITER+sCommand);			
		return Short.decode(sRes);
	}

	public static String statesSoulissToOH(String sOHType, short s, float f) {
		String s1=Integer.toHexString(s); 
		s1= s1.length() < 2 ? "0x0" + s1 : "0x"+s1;
		
		String s2=Integer.toString((int) f);
		s2= s2.length() < 2 ? "0x0" + s2 : "0x"+s2;
		
		
		String sRes=null;
		sRes=propStates.getProperty(sOHType+Constants.CONF_FIELD_DELIMITER+s1+Constants.CONF_FIELD_DELIMITER+s2);			
		return sRes;
	}
	
	public static void loadCommands(String sConfigurationFileName) {
		try {
			LOGGER.info("Load parameter from file: " + sConfigurationFileName);
			FileInputStream fi=new FileInputStream(sConfigurationFileName);
			propCommands.load(fi);
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}
	
	public static void loadStates(String sConfigurationFileName) {
		try {
			LOGGER.info("Load parameter from file: " + sConfigurationFileName);
			FileInputStream fi=new FileInputStream(sConfigurationFileName);
			propStates.load(fi);
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}

	public static void loadItemsType(String sConfigurationFileName) {
		try {
			LOGGER.info("Load parameter from file: " + sConfigurationFileName);
			FileInputStream fi=new FileInputStream(sConfigurationFileName);
			propTypes.load(fi);
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}
}
