package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 * Generated from IDL enum "CDRSPowerState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CDRSPowerState
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _on = 0;
	public static final CDRSPowerState on = new CDRSPowerState(_on);
	public static final int _off = 1;
	public static final CDRSPowerState off = new CDRSPowerState(_off);
	public int value()
	{
		return value;
	}
	public static CDRSPowerState from_int(int value)
	{
		switch (value) {
			case _on: return on;
			case _off: return off;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _on: return "on";
			case _off: return "off";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSPowerState(int i)
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
