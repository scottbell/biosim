package com.traclabs.biosim.util;

import org.apache.log4j.Logger;

public class CommandLineUtils {
	/**
	 * Grabs ID parameter from an array of string
	 * 
	 * @param myArgs
	 *            an array of strings to parse for the ID server switch, "-id".
	 *            Used for setting ID of this instance of the server. example,
	 *            java myServer -id=2
	 */
	public static int getIDfromArgs(String[] myArgs) {
		String idString = getOptionValueFromArgs(myArgs, "id");
		int id = 0;
		try{
			id = Integer.parseInt(idString);
		}
		catch(NumberFormatException e){
		}
		return id;
	}
	
	/**
	 * Grabs name parameter from an array of string
	 * 
	 * @param myArgs
	 *            an array of strings to parse for the name server switch,
	 *            "-name". Used for setting name of this instance of the server.
	 *            example, java myServer -name=MyServer
	 */
	public static String getNamefromArgs(String[] myArgs) {
		return getOptionValueFromArgs(myArgs, "name");
	}
	
	/**
	 * Grabs xml parameter from an array of string
	 * 
	 * @param myArgs
	 *            an array of strings to parse for the name server switch,
	 *            "-xml". Used for setting xml init of this instance of the
	 *            server. example, java myServer -xml=/home/bob/init.xml
	 */
	public static String getXMLfromArgs(String[] myArgs) {
		return getOptionValueFromArgs(myArgs, "xml");
	}
	
	public static String getOptionValueFromArgs(String[] args, String optionName){
		String optionValue = null;
		String commandLineOptionName = "-" + optionName + "=";
		for (int i = 0; i < args.length; i++) {
			try {
				if (args[i].startsWith(commandLineOptionName))
					optionValue = args[i].split("=")[1];
				else if ((args[i].startsWith(commandLineOptionName) && (args.length >= i)))
					optionValue = args[i + 1];
			} catch (ArrayIndexOutOfBoundsException e) {
				Logger
						.getLogger(OrbUtils.class)
						.error(
								"Bad command line arguments while trying to parse configuration file. Should be of form: "+commandLineOptionName+"foo where foo is the value of "+optionName);
			}
		}
		return optionValue;
	}
}
