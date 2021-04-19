package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "SoftwareState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class SoftwareState
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _shutdown = 0;
	public static final SoftwareState shutdown = new SoftwareState(_shutdown);
	public static final int _softwareArmed = 1;
	public static final SoftwareState softwareArmed = new SoftwareState(_softwareArmed);
	public static final int _running = 2;
	public static final SoftwareState running = new SoftwareState(_running);
	public int value()
	{
		return value;
	}
	public static SoftwareState from_int(int value)
	{
		switch (value) {
			case _shutdown: return shutdown;
			case _softwareArmed: return softwareArmed;
			case _running: return running;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _shutdown: return "shutdown";
			case _softwareArmed: return "softwareArmed";
			case _running: return "running";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected SoftwareState(int i)
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
