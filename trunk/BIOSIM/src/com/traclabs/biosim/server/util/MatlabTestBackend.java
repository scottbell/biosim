/*
 * Created on Oct 27, 2004
 */
package com.traclabs.biosim.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class servers to act as a fake Matlab server that the MatlabAceEngine
 * can talk to until the real Matlab server comes online. It has two sockets,
 * one for PUT requests, and one for GET requests. The PUT socket takes a list
 * of doubles, the GET request sends a list of doubles. To invoke this class,
 * type run-backend-test.sh (located in the bin directory). usage:
 * run-backend-test.sh [put server port] [get server port]
 * 
 * @author Scott Bell
 */
public class MatlabTestBackend {
    private ServerSocket myPutSocket;

    private DataInputStream myPutSocketDataInputStream;

    private BufferedWriter myPutSocketTextWriter;

    private BufferedReader myPutSocketTextReader;

    private ServerSocket myGetSocket;

    private DataOutputStream myGetSocketDataOutputStream;

    private BufferedWriter myGetSocketTextWriter;

    private BufferedReader myGetSocketTextReader;

    private Logger myLogger;

    private double[] myCurrentInputData;

    public MatlabTestBackend(int pPutPortToListen, int pGetPortToListen) {
        myLogger = Logger.getLogger(this.getClass().toString());
        try {
            myPutSocket = new ServerSocket(pPutPortToListen);
            myGetSocket = new ServerSocket(pGetPortToListen);
        } catch (IOException e) {
            myLogger.error("Couldn't create server sockets!");
            myLogger.error(e);
        }
    }

    public static void main(String args[]) {
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

        int getServerPort = MatlabAceEngine.DEFAULT_GET_PORT;
        int putServerPort = MatlabAceEngine.DEFAULT_PUT_PORT;
        if ((args.length > 2) || (args.length == 1)) {
            Logger
                    .getLogger(MatlabTestBackend.class)
                    .error(
                            "usage: java -classpath [path to log4j.jar] com.traclabs.biosim.server.util.MatlabTestBackend [put server port] [get server port]");
            return;
        } else if (args.length == 2) {
            try {
                putServerPort = Integer.parseInt(args[0]);
                getServerPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                Logger.getLogger(MatlabTestBackend.class).error(e);
                Logger.getLogger(MatlabTestBackend.class).error(
                        "Had problems parsing server port you specified");
                return;
            }
        }
        MatlabTestBackend newMatlabTestBackend = new MatlabTestBackend(
                putServerPort, getServerPort);
        newMatlabTestBackend.startServer();
    }

    /**
     *  
     */
    private void startServer() {
        //Spawn put server
        myLogger.debug("Starting put server on " + myPutSocket.getLocalPort());
        PutServer myPutServer = new PutServer();
        myPutServer.start();
        //Spawn get server
        myLogger.debug("Starting get server on " + myGetSocket.getLocalPort());
        GetServer myGetServer = new GetServer();
        myGetServer.start();
    }

    /**
     *  
     */
    private class PutServer extends Thread {
        public void run() {
            while (true) {
                try {
                    Socket clientSocket = myPutSocket.accept();
                    myLogger.debug("Socket connection on put server");
                    handlePutSocketConnection(clientSocket);

                } catch (IOException e) {
                    myLogger.error(e);
                    myLogger.error("Problem with put server socket");
                }
            }
        }
    }

    /**
     *  
     */
    private class GetServer extends Thread {
        public void run() {
            while (true) {
                try {
                    Socket clientSocket = myGetSocket.accept();
                    myLogger.debug("Socket connection on get server");
                    handleGetSocketConnection(clientSocket);

                } catch (IOException e) {
                    myLogger.error(e);
                    myLogger.error("Problem with get server socket");
                }
            }
        }
    }

    /**
     * @param clientSocket
     */
    private void handleGetSocketConnection(Socket clientSocket) {
        try {

            myGetSocketTextReader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            myGetSocketTextWriter = new BufferedWriter(new OutputStreamWriter(
                    clientSocket.getOutputStream()));
            myGetSocketDataOutputStream = new DataOutputStream(clientSocket
                    .getOutputStream());
            if (isBiosimGetClient()) {
                myLogger.debug("Client connected to get server!");
                boolean clientWantsToLeave = false;
                while (!clientWantsToLeave) {
                    String operationRequested = myGetSocketTextReader
                            .readLine();
                    if (operationRequested == null) {
                        myLogger.warn("null opertation requested");
                        return;
                    } else if (operationRequested
                            .equals(MatlabAceEngine.GET_REQUEST))
                        handleGetRequest();
                    else if (operationRequested
                            .equals(MatlabAceEngine.CLIENT_BYE))
                        clientWantsToLeave = true;
                    else
                        myLogger.warn("Unknown operation requested: "
                                + operationRequested);
                }
            } else {
                myLogger.debug("Non-biosim client attempted to connect");
            }
            myLogger.debug("closing socket");
            clientSocket.close();

        } catch (IOException e) {
            myLogger.error(e);
            myLogger.error("Problem handling socket request");
        }
    }

    /**
     * @param clientSocket
     */
    private void handlePutSocketConnection(Socket clientSocket) {
        try {

            myPutSocketTextReader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            myPutSocketTextWriter = new BufferedWriter(new OutputStreamWriter(
                    clientSocket.getOutputStream()));
            myPutSocketDataInputStream = new DataInputStream(clientSocket
                    .getInputStream());
            if (isBiosimPutClient()) {
                myLogger.debug("Client connected on put server!");
                boolean clientWantsToLeave = false;
                while (!clientWantsToLeave) {
                    String operationRequested = myPutSocketTextReader
                            .readLine();
                    if (operationRequested == null) {
                        myLogger.warn("null opertation requested");
                        return;
                    } else if (operationRequested
                            .equals(MatlabAceEngine.PUT_REQUEST))
                        handlePutRequest();
                    else if (operationRequested
                            .equals(MatlabAceEngine.CLIENT_BYE))
                        clientWantsToLeave = true;
                    else
                        myLogger.warn("Unknown operation requested: "
                                + operationRequested);
                }
            } else {
                myLogger.debug("Non-biosim client attempted to connect");
            }
            myLogger.debug("closing socket");
            clientSocket.close();

        } catch (IOException e) {
            myLogger.error(e);
            myLogger.error("Problem handling socket request");
        }
    }

    /**
     *  
     */
    private void handlePutRequest() throws IOException {
        myLogger.debug("handling put request");
        int vectorLength = myPutSocketDataInputStream.readInt();
        myLogger.debug("vector length was: " + vectorLength);
        myCurrentInputData = new double[vectorLength];
        for (int i = 0; i < myCurrentInputData.length; i++) {
            myCurrentInputData[i] = myPutSocketDataInputStream.readDouble();
            myLogger.debug("getting double: " + myCurrentInputData[i]);
        }
    }

    /**
     *  
     */
    private void handleGetRequest() throws IOException {
        double[] outputVector = { 6d, 7.54d, Math.PI * 2d, 5.9d, 8d, 9.23322d };
        myLogger.debug("handling get request");
        myLogger.debug("sending vector length: " + outputVector.length);
        myGetSocketDataOutputStream.writeInt(outputVector.length);
        myGetSocketDataOutputStream.flush();
        for (int i = 0; i < outputVector.length; i++) {
            myLogger.debug("sending double: " + outputVector[i]);
            myGetSocketDataOutputStream.writeDouble(outputVector[i]);
            myGetSocketDataOutputStream.flush();
        }
    }

    /**
     * @return
     */
    private boolean isBiosimPutClient() throws IOException {
        //Say Hello
        myPutSocketTextWriter.write(MatlabAceEngine.SERVER_HELLO + "\n");
        myPutSocketTextWriter.flush();
        String response = myPutSocketTextReader.readLine();
        if (response == null)
            return false;
        else
            return response.equals(MatlabAceEngine.CLIENT_HELLO);
    }

    /**
     * @return
     */
    private boolean isBiosimGetClient() throws IOException {
        //Say Hello
        myGetSocketTextWriter.write(MatlabAceEngine.SERVER_HELLO + "\n");
        myGetSocketTextWriter.flush();
        String response = myGetSocketTextReader.readLine();
        if (response == null)
            return false;
        else
            return response.equals(MatlabAceEngine.CLIENT_HELLO);
    }

}