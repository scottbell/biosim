package com.traclabs.biosim.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 
 * This class handles data communication for Biosim modules that have Matlab
 * implementations. It connects to a Matlab server (by default running on
 * localhost). The Matlab server is expected to have two sockets, one for PUT
 * requests (defaults to port 12759), one for GET requests (defaults to port
 * 12758). The Engine connects to both ports in the same manner, by: 1) Wait for
 * server to say hello 2) Say hello back 3) send a command
 * 
 * @author Scott Bell
 */

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
            //create put streams
            myLogger.debug("Connecting to PUT socket at " + DEFAULT_HOSTNAME
                    + " on port " + DEFAULT_PUT_PORT);
            myPutSocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_PUT_PORT);
            myPutSocketTextReader = new BufferedReader(new InputStreamReader(
                    myPutSocket.getInputStream()));
            myPutSocketTextWriter = new BufferedWriter(new OutputStreamWriter(
                    myPutSocket.getOutputStream()));
            myPutSocketDataOutputStream = new DataOutputStream(myPutSocket
                    .getOutputStream());

            //create get streams
            myLogger.debug("Connecting to GET socket at " + DEFAULT_HOSTNAME
                    + " on port " + DEFAULT_GET_PORT);
            myGetSocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_GET_PORT);
            myGetSocketTextReader = new BufferedReader(new InputStreamReader(
                    myGetSocket.getInputStream()));
            myGetSocketTextWriter = new BufferedWriter(new OutputStreamWriter(
                    myGetSocket.getOutputStream()));
            myGetSocketDataInputStream = new DataInputStream(myGetSocket
                    .getInputStream());

            //is this the correct server?
            myLogger.debug("Saying hellos");
            String getServerHello = myGetSocketTextReader.readLine();
            String putServerHello = myPutSocketTextReader.readLine();
            if ((getServerHello.equals(SERVER_HELLO) && (putServerHello
                    .equals(SERVER_HELLO)))) {
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
                receivedDoubleVector[i] = myGetSocketDataInputStream
                        .readDouble();
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
            if (myGetSocket != null) {
                myLogger.debug("sending GET BYE");
                myGetSocketTextWriter.write(CLIENT_BYE + "\n");
                myGetSocketTextWriter.flush();
            }
            if (myPutSocket != null) {
                myLogger.debug("sending PUT BYE");
                myPutSocketTextWriter.write(CLIENT_BYE + "\n");
                myPutSocketTextWriter.flush();
            }
        } catch (IOException e) {
            myLogger.error(e);
            myLogger
                    .error("Had problems closing socket to " + DEFAULT_HOSTNAME);
        }
    }
}