package com.traclabs.biosim.idl.simulation.power;
/**
 * Generated from IDL enum "RPCMSwitchState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class RPCMSwitchState
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _open = 0;
	public static final RPCMSwitchState open = new RPCMSwitchState(_open);
	public static final int _closed = 1;
	public static final RPCMSwitchState closed = new RPCMSwitchState(_closed);
	public int value()
	{
		return value;
	}
	public static RPCMSwitchState from_int(int value)
	{
		switch (value) {
			case _open: return open;
			case _closed: return closed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _open: return "open";
			case _closed: return "closed";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected RPCMSwitchState(int i)
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
