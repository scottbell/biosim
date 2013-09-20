package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _init = 0;
	public static final CDRSState init = new CDRSState(_init);
	public static final int _standby = 1;
	public static final CDRSState standby = new CDRSState(_standby);
	public static final int _dual_bed = 2;
	public static final CDRSState dual_bed = new CDRSState(_dual_bed);
	public static final int _single_bed = 3;
	public static final CDRSState single_bed = new CDRSState(_single_bed);
	public static final int _transitioning = 4;
	public static final CDRSState transitioning = new CDRSState(_transitioning);
	public static final int _inactive = 5;
	public static final CDRSState inactive = new CDRSState(_inactive);
	public int value()
	{
		return value;
	}
	public static CDRSState from_int(int value)
	{
		switch (value) {
			case _init: return init;
			case _standby: return standby;
			case _dual_bed: return dual_bed;
			case _single_bed: return single_bed;
			case _transitioning: return transitioning;
			case _inactive: return inactive;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
