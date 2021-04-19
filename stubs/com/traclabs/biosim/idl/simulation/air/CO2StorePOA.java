package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "CO2Store".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class CO2StorePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.air.CO2StoreOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "setPipe", Integer.valueOf(0));
		m_opsHash.put ( "setCurrentLevel", Integer.valueOf(1));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(2));
		m_opsHash.put ( "getTickLength", Integer.valueOf(3));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(4));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(5));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(6));
		m_opsHash.put ( "getCurrentCapacity", Integer.valueOf(7));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(8));
		m_opsHash.put ( "getID", Integer.valueOf(9));
		m_opsHash.put ( "setInitialCapacity", Integer.valueOf(10));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(11));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(12));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(13));
		m_opsHash.put ( "getInitialCapacity", Integer.valueOf(14));
		m_opsHash.put ( "reset", Integer.valueOf(15));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(16));
		m_opsHash.put ( "getOverflow", Integer.valueOf(17));
		m_opsHash.put ( "take", Integer.valueOf(18));
		m_opsHash.put ( "getPercentageFilled", Integer.valueOf(19));
		m_opsHash.put ( "getModuleName", Integer.valueOf(20));
		m_opsHash.put ( "randomFilter", Integer.valueOf(21));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(22));
		m_opsHash.put ( "log", Integer.valueOf(23));
		m_opsHash.put ( "setInitialLevel", Integer.valueOf(24));
		m_opsHash.put ( "maintain", Integer.valueOf(25));
		m_opsHash.put ( "getCurrentLevel", Integer.valueOf(26));
		m_opsHash.put ( "setCurrentCapacity", Integer.valueOf(27));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(28));
		m_opsHash.put ( "add", Integer.valueOf(29));
		m_opsHash.put ( "isPipe", Integer.valueOf(30));
		m_opsHash.put ( "getInitialLevel", Integer.valueOf(31));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(32));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(33));
		m_opsHash.put ( "tick", Integer.valueOf(34));
		m_opsHash.put ( "setTickLength", Integer.valueOf(35));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(36));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(37));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(38));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/air/CO2Store:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/PassiveModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Store:1.0"};
	public com.traclabs.biosim.idl.simulation.air.CO2Store _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.air.CO2Store __r = com.traclabs.biosim.idl.simulation.air.CO2StoreHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.air.CO2Store _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.air.CO2Store __r = com.traclabs.biosim.idl.simulation.air.CO2StoreHelper.narrow(__o);
		return __r;
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
			case 0: // setPipe
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setPipe(_arg0);
				break;
			}
			case 1: // setCurrentLevel
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setCurrentLevel(_arg0);
				break;
			}
			case 2: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 3: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 4: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 5: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 6: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 7: // getCurrentCapacity
			{
				_out = handler.createReply();
				_out.write_float(getCurrentCapacity());
				break;
			}
			case 8: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 9: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 10: // setInitialCapacity
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setInitialCapacity(_arg0);
				break;
			}
			case 11: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 12: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 13: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 14: // getInitialCapacity
			{
				_out = handler.createReply();
				_out.write_float(getInitialCapacity());
				break;
			}
			case 15: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 16: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 17: // getOverflow
			{
				_out = handler.createReply();
				_out.write_float(getOverflow());
				break;
			}
			case 18: // take
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(take(_arg0));
				break;
			}
			case 19: // getPercentageFilled
			{
				_out = handler.createReply();
				_out.write_float(getPercentageFilled());
				break;
			}
			case 20: // getModuleName
			{
				_out = handler.createReply();
				java.lang.String tmpResult103 = getModuleName();
_out.write_string( tmpResult103 );
				break;
			}
			case 21: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 22: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 23: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 24: // setInitialLevel
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setInitialLevel(_arg0);
				break;
			}
			case 25: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 26: // getCurrentLevel
			{
				_out = handler.createReply();
				_out.write_float(getCurrentLevel());
				break;
			}
			case 27: // setCurrentCapacity
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setCurrentCapacity(_arg0);
				break;
			}
			case 28: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 29: // add
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(add(_arg0));
				break;
			}
			case 30: // isPipe
			{
				_out = handler.createReply();
				_out.write_boolean(isPipe());
				break;
			}
			case 31: // getInitialLevel
			{
				_out = handler.createReply();
				_out.write_float(getInitialLevel());
				break;
			}
			case 32: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 33: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 34: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 35: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 36: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 37: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 38: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
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
