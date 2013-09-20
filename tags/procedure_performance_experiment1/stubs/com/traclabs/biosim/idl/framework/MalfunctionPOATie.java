package com.traclabs.biosim.idl.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Malfunction"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class MalfunctionPOATie
	extends MalfunctionPOA
{
	private MalfunctionOperations _delegate;

	private POA _poa;
	public MalfunctionPOATie(MalfunctionOperations delegate)
	{
		_delegate = delegate;
	}
	public MalfunctionPOATie(MalfunctionOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.framework.Malfunction _this()
	{
		return com.traclabs.biosim.idl.framework.MalfunctionHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.framework.Malfunction _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.framework.MalfunctionHelper.narrow(_this_object(orb));
	}
	public MalfunctionOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(MalfunctionOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public com.traclabs.biosim.idl.framework.MalfunctionLength getLength()
	{
		return _delegate.getLength();
	}

	public void setPerformed(boolean pPerformed)
	{
_delegate.setPerformed(pPerformed);
	}

	public void doSomeRepairWork()
	{
_delegate.doSomeRepairWork();
	}

	public boolean hasPerformed()
	{
		return _delegate.hasPerformed();
	}

	public long getID()
	{
		return _delegate.getID();
	}

	public com.traclabs.biosim.idl.framework.MalfunctionIntensity getIntensity()
	{
		return _delegate.getIntensity();
	}

	public boolean doneEnoughRepairWork()
	{
		return _delegate.doneEnoughRepairWork();
	}

	public java.lang.String getName()
	{
		return _delegate.getName();
	}

}
