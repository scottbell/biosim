package com.traclabs.biosim.server.util;

public class MatlabAceEngine implements Engine{
    public void open(){

    }

    public void put(double[] inputVector){
	System.out.println("matlabAceEngine::put");
    }

    public double[] get() {
	System.out.println("matlabAceEngine::get");
	double[] out = null;
	return out;
    }

    public void close(){
    }
}
