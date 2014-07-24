package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IATCSState"
 *	@author JacORB IDL compiler 
 */

public final class IATCSState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _idle = 0;
	public static final IATCSState idle = new IATCSState(_idle);
	public static final int _operational = 1;
	public static final IATCSState operational = new IATCSState(_operational);
	public static final int _armed = 2;
	public static final IATCSState armed = new IATCSState(_armed);
	public static final int _transitioning = 3;
	public static final IATCSState transitioning = new IATCSState(_transitioning);
	public int value()
	{
		return value;
	}
	public static IATCSState from_int(int value)
	{
		switch (value) {
			case _idle: return idle;
			case _operational: return operational;
			case _armed: return armed;
			case _transitioning: return transitioning;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IATCSState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
