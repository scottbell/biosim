package com.traclabs.biosim.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MatlabAceEngine extends Engine {
	
	private static final String DEFAULT_HOSTNAME = "localhost";
	public static final int DEFAULT_PORT = 12759;
	public static final String SERVER_HELLO = "MATLAB_HELLO";
	public static final String CLIENT_HELLO = "BIOSIM_HELLO";
	public static final String PUT_REQUEST = "PUT";
	public static final String GET_REQUEST = "GET";
	public static final String CLIENT_BYE = "BIOSIM_BYE";
	
	private boolean isInitialized = false;
	private Socket mySocket;
	private BufferedReader mySocketTextReader;
	private DataInputStream mySocketDataInputStream;
	private DataOutputStream mySocketDataOutputStream;
	private BufferedWriter mySocketTextWriter;
	
    private void initialize() {
    	if (isInitialized)
    		return;
    	try{
    		//create streams
    		mySocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
    		mySocketTextReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream())) ;
    		mySocketDataInputStream = new DataInputStream(mySocket.getInputStream());
    		mySocketTextWriter = new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream()));
    		mySocketDataOutputStream =  new DataOutputStream(mySocket.getOutputStream());
		    
    		//is this the correct server?
        	myLogger.debug("Checking to see if server said hello");
    		if (mySocketTextReader.readLine().equals(SERVER_HELLO)){
    			//say hello
            	myLogger.debug("It did say hello.  Saying hello back");
    			mySocketTextWriter.write(CLIENT_HELLO + "\n");
    			mySocketTextWriter.flush();
    			isInitialized = true;
    		}
    		else{
    			myLogger.error("Connected to the wrong server");
    			mySocket.close();
    		}
    	}
    	catch (IOException e){
    		myLogger.error(e);
    		myLogger.error("Couldn't connect to "+DEFAULT_HOSTNAME+" on port "+DEFAULT_PORT);
    	}
    }

    public void put(double[] inputVector) {
    	initialize();
        myLogger.debug("put method");
        try{
    		myLogger.debug("sending PUT request: ");
    		mySocketTextWriter.write(PUT_REQUEST + "\n");
			mySocketTextWriter.flush();
    		myLogger.debug("sending double length: "+inputVector.length);
    		mySocketDataOutputStream.writeInt(inputVector.length);
        	for (int i = 0; i < inputVector.length; i++){
        		myLogger.debug("sending double: "+inputVector[i]);
        		mySocketDataOutputStream.writeDouble(inputVector[i]);
        	}
        }
        catch (IOException e){
        	myLogger.error(e);
        	myLogger.error("Had problems sending doubles to server");
        }
    }

    public double[] get() {
    	initialize();
        myLogger.debug("get method");
        double[] receivedDoubleVector = null;
        try{
    		myLogger.debug("sending GET request: ");
    		mySocketTextWriter.write(GET_REQUEST + "\n");
			mySocketTextWriter.flush();
        	myLogger.debug("getting double length");
    		int vectorLength = mySocketDataInputStream.readInt();
    		receivedDoubleVector = new double[vectorLength];
        	for (int i = 0; i < receivedDoubleVector.length; i++){
        		myLogger.debug("sending double: "+receivedDoubleVector[i]);
        		receivedDoubleVector[i] = mySocketDataInputStream.readDouble();
        	}
        }
        catch (IOException e){
        	myLogger.error(e);
        	myLogger.error("Had problems sending doubles to server");
        }
        return receivedDoubleVector;
    }

    public void reset() {
    	isInitialized = false;
    	if (mySocket == null)
    		return;
    	try{
        	myLogger.debug("sending BYE");
        	mySocketTextWriter.write(CLIENT_BYE + "\n");
    		mySocketTextWriter.flush();
    	}
    	catch (IOException e){
    		myLogger.error(e);
    		myLogger.error("Had problems closing socket to "+DEFAULT_HOSTNAME+" on port "+DEFAULT_PORT);
    	}
    }
}