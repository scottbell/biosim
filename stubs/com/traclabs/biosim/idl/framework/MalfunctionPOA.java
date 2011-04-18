package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL interface "Malfunction"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class MalfunctionPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.framework.MalfunctionOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getLength", new java.lang.Integer(0));
		m_opsHash.put ( "setPerformed", new java.lang.Integer(1));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(2));
		m_opsHash.put ( "hasPerformed", new java.lang.Integer(3));
		m_opsHash.put ( "getID", new java.lang.Integer(4));
		m_opsHash.put ( "getIntensity", new java.lang.Integer(5));
		m_opsHash.put ( "doneEnoughRepairWork", new java.lang.Integer(6));
		m_opsHash.put ( "getName", new java.lang.Integer(7));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/framework/Malfunction:1.0"};
	public com.traclabs.biosim.idl.framework.Malfunction _this()
	{
		return com.traclabs.biosim.idl.framework.MalfunctionHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.framework.Malfunction _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.framework.MalfunctionHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // getLength
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.write(_out,getLength());
				break;
			}
			case 1: // setPerformed
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setPerformed(_arg0);
				break;
			}
			case 2: // doSomeRepairWork
			{
				_out = handler.createReply();
				doSomeRepairWork();
				break;
			}
			case 3: // hasPerformed
			{
				_out = handler.createReply();
				_out.write_boolean(hasPerformed());
				break;
			}
			case 4: // getID
			{
				_out = handler.createReply();
				_out.write_longlong(getID());
				break;
			}
			case 5: // getIntensity
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.write(_out,getIntensity());
				break;
			}
			case 6: // doneEnoughRepairWork
			{
				_out = handler.createReply();
				_out.write_boolean(doneEnoughRepairWork());
				break;
			}
			case 7: // getName
			{
				_out = handler.createReply();
				_out.write_string(getName());
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
