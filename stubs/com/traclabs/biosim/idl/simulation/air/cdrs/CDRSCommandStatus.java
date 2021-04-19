package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 * Generated from IDL enum "CDRSCommandStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CDRSCommandStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _inhibited = 0;
	public static final CDRSCommandStatus inhibited = new CDRSCommandStatus(_inhibited);
	public static final int _enabled = 1;
	public static final CDRSCommandStatus enabled = new CDRSCommandStatus(_enabled);
	public int value()
	{
		return value;
	}
	public static CDRSCommandStatus from_int(int value)
	{
		switch (value) {
			case _inhibited: return inhibited;
			case _enabled: return enabled;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _inhibited: return "inhibited";
			case _enabled: return "enabled";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSCommandStatus(int i)
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
