package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL definition of struct "Air"
 *	@author JacORB IDL compiler 
 */

public final class Air
	implements org.omg.CORBA.portable.IDLEntity
{
	public Air(){}
	public float o2Moles;
	public float co2Moles;
	public float otherMoles;
	public float vaporMoles;
	public float nitrogenMoles;
	public Air(float o2Moles, float co2Moles, float otherMoles, float vaporMoles, float nitrogenMoles)
	{
		this.o2Moles = o2Moles;
		this.co2Moles = co2Moles;
		this.otherMoles = otherMoles;
		this.vaporMoles = vaporMoles;
		this.nitrogenMoles = nitrogenMoles;
	}
}
