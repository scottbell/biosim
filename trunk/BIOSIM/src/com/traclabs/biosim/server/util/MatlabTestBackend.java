/*
 * Created on Oct 27, 2004
 *
 */
package com.traclabs.biosim.server.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * @author Scott Bell
 */
public class MatlabTestBackend {
	
	private ServerSocket myIncomingSocket;
	private Socket myOutgoingSocket;
	private InetSocketAddress myAddressToConnectTo;
	private Logger myLogger;
	
	public MatlabTestBackend(int pPortToListen, InetSocketAddress pAddressToConnect){
		myLogger = Logger.getLogger(this.getClass());
		myAddressToConnectTo = pAddressToConnect;
		try{
			myIncomingSocket = new ServerSocket(pPortToListen);
		}
		catch (IOException e){
			myLogger.error("Couldn't create server socket!");
			myLogger.error(e);
		}
	}
	
	private void createOutgoingSocket(){
		try{
			myOutgoingSocket = new Socket(myAddressToConnectTo.getAddress(), myAddressToConnectTo.getPort());
		}
		catch (IOException e){
			myLogger.error("Couldn't connect to biosim!");
			myLogger.error(e);
		}
	}
	
	public static void main(String args[]){
		
	}

}
