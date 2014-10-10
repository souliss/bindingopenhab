package org.openhab.binding.souliss.internal.network;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openhab.binding.souliss.internal.network.typicals.Constants;

public class SoulissLogger {

	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	static private FileHandler fileHTML;
	static private MyHtmlFormatter formatterHTML;
	
	static public void setup() throws IOException {

		// get the global logger to configure it
		Logger logger = Logger.getLogger(Constants.LOGNAME);
		// suppress the logging output to the console

		//	    Logger rootLogger = Logger.*getLogger*("");
		//
		//	    Handler[] handlerzs = rootLogger.getHandlers();

		//	    if (handlers[0] instanceof ConsoleHandler) {
		//	      rootLogger.removeHandler(handlers[0]);
		//	    }
		logger.setLevel(Level.ALL);
	
		
		fileTxt = new FileHandler(Constants.LOG_FILE_TXT);
		fileHTML = new FileHandler(Constants.LOG_FILE_HTML);
		
//		fileTxt = new FileHandler("logging.txt");
//		fileHTML = new FileHandler("logging.txt");
		
	
		// create a TXT formatter

	    formatterTxt = new SimpleFormatter();
	    fileTxt.setFormatter(formatterTxt);
	    logger.addHandler(fileTxt);
	    // create an HTML formatter
	    formatterHTML = new MyHtmlFormatter();
	    fileHTML.setFormatter(formatterHTML);
	    logger.addHandler(fileHTML);

	  }

}
