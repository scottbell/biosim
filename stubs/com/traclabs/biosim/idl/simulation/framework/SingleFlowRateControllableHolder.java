package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "SingleFlowRateControllable".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class SingleFlowRateControllableHolder	implements org.omg.CORBA.portable.Streamable{
	 public SingleFlowRateControllable value;
	public SingleFlowRateControllableHolder()
	{
	}
	public SingleFlowRateControllableHolder (final SingleFlowRateControllable initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return SingleFlowRateControllableHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SingleFlowRateControllableHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		SingleFlowRateControllableHelper.write (_out,value);
	}
}
