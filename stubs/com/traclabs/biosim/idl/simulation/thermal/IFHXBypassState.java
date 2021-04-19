package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "IFHXBypassState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class IFHXBypassState
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _bypass = 0;
	public static final IFHXBypassState bypass = new IFHXBypassState(_bypass);
	public static final int _flowthrough = 1;
	public static final IFHXBypassState flowthrough = new IFHXBypassState(_flowthrough);
	public int value()
	{
		return value;
	}
	public static IFHXBypassState from_int(int value)
	{
		switch (value) {
			case _bypass: return bypass;
			case _flowthrough: return flowthrough;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _bypass: return "bypass";
			case _flowthrough: return "flowthrough";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IFHXBypassState(int i)
	{
		value = i;
	}
	/**
	 * Designate replacement object when deserialized from stream. See
	 * http://www.omg.org/docs/ptc/02-01-03.htm#Issue4271
	 *
	 * @throws java.io.ObjectStreamException
	 */
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
