package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "IFHXValveCommandStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class IFHXValveCommandStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _enabled = 0;
	public static final IFHXValveCommandStatus enabled = new IFHXValveCommandStatus(_enabled);
	public static final int _inhibited = 1;
	public static final IFHXValveCommandStatus inhibited = new IFHXValveCommandStatus(_inhibited);
	public int value()
	{
		return value;
	}
	public static IFHXValveCommandStatus from_int(int value)
	{
		switch (value) {
			case _enabled: return enabled;
			case _inhibited: return inhibited;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _enabled: return "enabled";
			case _inhibited: return "inhibited";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IFHXValveCommandStatus(int i)
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
