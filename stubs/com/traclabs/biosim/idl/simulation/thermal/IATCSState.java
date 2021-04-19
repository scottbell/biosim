package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "IATCSState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class IATCSState
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
	public String toString()
	{
		switch (value) {
			case _idle: return "idle";
			case _operational: return "operational";
			case _armed: return "armed";
			case _transitioning: return "transitioning";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IATCSState(int i)
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
