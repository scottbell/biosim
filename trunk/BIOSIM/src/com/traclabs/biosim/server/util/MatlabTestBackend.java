/*
 * Created on Oct 27, 2004
 *
 */
package com.traclabs.biosim.server.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Scott Bell
 */
public class MatlabTestBackend {
	private static final int DEFAULT_SERVER_PORT = 12759;
	private ServerSocket myIncomingSocket;
	private Logger myLogger;
	
	public MatlabTestBackend(int pPortToListen){
		myLogger = Logger.getLogger(this.getClass().toString());
		try{
			myIncomingSocket = new ServerSocket(pPortToListen);
		}
		catch (IOException e){
			myLogger.error("Couldn't create server socket!");
			myLogger.error(e);
		}
	}
	
	public static void main(String args[]){
		Properties logProps = new Properties();
        logProps.setProperty("log4j.appender.matlabAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.matlabAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.matlabAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        logProps.setProperty("log4j.logger." + MatlabTestBackend.class,
                "DEBUG, matlabAppender");
        PropertyConfigurator.configure(logProps);
        
		int serverPort = DEFAULT_SERVER_PORT;
		if (args.length > 1){
			Logger.getLogger(MatlabTestBackend.class).error("usage: java com.traclabs.biosim.server.util.MatlabTestBackend [server port]");
			return;
		}
		else if (args.length == 1){
			try{
				serverPort = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e){
				Logger.getLogger(MatlabTestBackend.class).error(e);
				Logger.getLogger(MatlabTestBackend.class).error("Had problems parsing server port you specified: "+args[0]);
				return;
			}
		}
		MatlabTestBackend newMatlabTestBackend = new MatlabTestBackend(serverPort);
		newMatlabTestBackend.startServer();
	}

	/**
	 * 
	 */
	private void startServer() {
		myLogger.info("Starting server...");
			while (true){
				try{
					Socket clientSocket = myIncomingSocket.accept();
					myLogger.info("Client connected!");
					BufferedReader socketTextReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())) ;
					DataInputStream  socketInputDataStream = new DataInputStream(clientSocket.getInputStream());
				    PrintWriter socketTextWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "8859_1"), true );
				    DataOutputStream socketOutputDataStream =  new DataOutputStream(clientSocket.getOutputStream());
				    
				    socketTextWriter.print("Test!");
				    float readFloat = socketInputDataStream.readFloat();
				    //send it back multiplied by 2
				    socketOutputDataStream.writeFloat(readFloat * 2f);
				}
				catch (IOException e){
					myLogger.error(e);
				}
			}
	}

}
