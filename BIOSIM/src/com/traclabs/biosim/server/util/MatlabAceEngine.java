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

	public static final int DEFAULT_GET_PORT = 12758;

	public static final int DEFAULT_PUT_PORT = 12759;

	public static final String SERVER_HELLO = "MATLAB_HELLO";

	public static final String CLIENT_HELLO = "BIOSIM_HELLO";

	public static final String PUT_REQUEST = "PUT";

	public static final String GET_REQUEST = "GET";

	public static final String CLIENT_BYE = "BIOSIM_BYE";

	private boolean isInitialized = false;

	//Where PUT requests go to MATLAB
	private Socket myPutSocket;

	private BufferedReader myPutSocketTextReader;

	private BufferedWriter myPutSocketTextWriter;

	private DataOutputStream myPutSocketDataOutputStream;

	//Where GET requests go to MATLAB
	private Socket myGetSocket;

	private BufferedReader myGetSocketTextReader;

	private BufferedWriter myGetSocketTextWriter;

	private DataInputStream myGetSocketDataInputStream;

	private void initialize() {
		if (isInitialized)
			return;
		try {
			myLogger.debug("Connecting to incoming socket at "
					+ DEFAULT_HOSTNAME + " on port " + DEFAULT_GET_PORT);

			//create streams
			myPutSocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_PUT_PORT);
			myPutSocketTextReader = new BufferedReader(new InputStreamReader(
					myPutSocket.getInputStream()));
			myPutSocketTextWriter = new BufferedWriter(new OutputStreamWriter(
					myPutSocket.getOutputStream()));
			myPutSocketDataOutputStream = new DataOutputStream(myPutSocket
					.getOutputStream());
			myGetSocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_PUT_PORT);
			myGetSocketTextReader = new BufferedReader(new InputStreamReader(
					myGetSocket.getInputStream()));
			myGetSocketTextWriter = new BufferedWriter(new OutputStreamWriter(
					myGetSocket.getOutputStream()));
			myGetSocketDataInputStream = new DataInputStream(myGetSocket
					.getInputStream());

			//is this the correct server?
			myLogger.debug("Saying hellos");
			if ((myGetSocketTextReader.readLine().equals(SERVER_HELLO) && (myPutSocketTextReader
					.readLine().equals(SERVER_HELLO)))) {
				//say hello back
				myPutSocketTextWriter.write(CLIENT_HELLO + "\n");
				myPutSocketTextWriter.flush();

				myGetSocketTextWriter.write(CLIENT_HELLO + "\n");
				myGetSocketTextWriter.flush();
				isInitialized = true;
			} else {
				myLogger.error("Connected to the wrong server");
				myPutSocket.close();
				myGetSocket.close();
			}
		} catch (IOException e) {
			myLogger.error(e);
			myLogger.error("Couldn't connect to " + DEFAULT_HOSTNAME);
		}
	}

	public void put(double[] inputVector) {
		initialize();
		myLogger.debug("put method");
		try {
			myLogger.debug("sending PUT request: ");
			myPutSocketTextWriter.write(PUT_REQUEST + "\n");
			myPutSocketTextWriter.flush();
			myLogger.debug("sending vector length: " + inputVector.length);
			myPutSocketDataOutputStream.writeInt(inputVector.length);
			myPutSocketDataOutputStream.flush();
			for (int i = 0; i < inputVector.length; i++) {
				myLogger.debug("sending double: " + inputVector[i]);
				myPutSocketDataOutputStream.writeDouble(inputVector[i]);
				myPutSocketDataOutputStream.flush();
			}
		} catch (IOException e) {
			myLogger.error(e);
			myLogger.error("Had problems sending doubles to server");
		}
	}

	public double[] get() {
		initialize();
		myLogger.debug("get method");
		double[] receivedDoubleVector = null;
		try {
			myLogger.debug("sending GET request: ");
			myGetSocketTextWriter.write(GET_REQUEST + "\n");
			myGetSocketTextWriter.flush();
			myLogger.debug("getting vector length");
			int vectorLength = myGetSocketDataInputStream.readInt();
			receivedDoubleVector = new double[vectorLength];
			for (int i = 0; i < receivedDoubleVector.length; i++) {
				receivedDoubleVector[i] = myGetSocketDataInputStream.readDouble();
				myLogger.debug("getting double: " + receivedDoubleVector[i]);
			}
		} catch (IOException e) {
			myLogger.error(e);
			myLogger.error("Had problems sending doubles to server");
		}
		return receivedDoubleVector;
	}

	public void reset() {
		isInitialized = false;
		try {
			if (myGetSocket != null){
				myLogger.debug("sending GET BYE");
				myGetSocketTextWriter.write(CLIENT_BYE + "\n");
				myGetSocketTextWriter.flush();
			}
			if (myPutSocket != null){
				myLogger.debug("sending PUT BYE");
				myPutSocketTextWriter.write(CLIENT_BYE + "\n");
				myPutSocketTextWriter.flush();
			}
		} catch (IOException e) {
			myLogger.error(e);
			myLogger.error("Had problems closing socket to " + DEFAULT_HOSTNAME);
		}
	}
}