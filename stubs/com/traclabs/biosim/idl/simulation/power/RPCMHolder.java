package com.traclabs.biosim.idl.simulation.power;

/**
 * Generated from IDL interface "RPCM".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class RPCMHolder	implements org.omg.CORBA.portable.Streamable{
	 public RPCM value;
	public RPCMHolder()
	{
	}
	public RPCMHolder (final RPCM initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return RPCMHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = RPCMHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		RPCMHelper.write (_out,value);
	}
}
