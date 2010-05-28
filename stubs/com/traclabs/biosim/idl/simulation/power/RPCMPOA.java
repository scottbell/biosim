package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "RPCM"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class RPCMPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.power.RPCMOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getSwitchStatuses", new java.lang.Integer(0));
		m_opsHash.put ( "getTickLength", new java.lang.Integer(1));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(2));
		m_opsHash.put ( "clearMalfunction", new java.lang.Integer(3));
		m_opsHash.put ( "clearTrips", new java.lang.Integer(4));
		m_opsHash.put ( "isUndertripped", new java.lang.Integer(5));
		m_opsHash.put ( "isMalfunctioning", new java.lang.Integer(6));
		m_opsHash.put ( "clearAllMalfunctions", new java.lang.Integer(7));
		m_opsHash.put ( "setEnableFailure", new java.lang.Integer(8));
		m_opsHash.put ( "randomFilter", new java.lang.Integer(9));
		m_opsHash.put ( "getMyTicks", new java.lang.Integer(10));
		m_opsHash.put ( "reset", new java.lang.Integer(11));
		m_opsHash.put ( "maintain", new java.lang.Integer(12));
		m_opsHash.put ( "getPowerConsumerDefinition", new java.lang.Integer(13));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(14));
		m_opsHash.put ( "getID", new java.lang.Integer(15));
		m_opsHash.put ( "isOvertripped", new java.lang.Integer(16));
		m_opsHash.put ( "setSwitches", new java.lang.Integer(17));
		m_opsHash.put ( "scheduleMalfunction", new java.lang.Integer(18));
		m_opsHash.put ( "getModuleName", new java.lang.Integer(19));
		m_opsHash.put ( "overtrip", new java.lang.Integer(20));
		m_opsHash.put ( "setSwitch", new java.lang.Integer(21));
		m_opsHash.put ( "tick", new java.lang.Integer(22));
		m_opsHash.put ( "fixMalfunction", new java.lang.Integer(23));
		m_opsHash.put ( "getMalfunctionNames", new java.lang.Integer(24));
		m_opsHash.put ( "setTickLength", new java.lang.Integer(25));
		m_opsHash.put ( "isFailureEnabled", new java.lang.Integer(26));
		m_opsHash.put ( "startMalfunction", new java.lang.Integer(27));
		m_opsHash.put ( "getPowerProducerDefinition", new java.lang.Integer(28));
		m_opsHash.put ( "log", new java.lang.Integer(29));
		m_opsHash.put ( "setInitialSwitches", new java.lang.Integer(30));
		m_opsHash.put ( "getMalfunctions", new java.lang.Integer(31));
		m_opsHash.put ( "fixAllMalfunctions", new java.lang.Integer(32));
		m_opsHash.put ( "undertrip", new java.lang.Integer(33));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/power/RPCM:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0"};
	public com.traclabs.biosim.idl.simulation.power.RPCM _this()
	{
		return com.traclabs.biosim.idl.simulation.power.RPCMHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.power.RPCM _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.power.RPCMHelper.narrow(_this_object(orb));
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
			case 0: // getSwitchStatuses
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.BooleanListHelper.write(_out,getSwitchStatuses());
				break;
			}
			case 1: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 2: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 3: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 4: // clearTrips
			{
				_out = handler.createReply();
				clearTrips();
				break;
			}
			case 5: // isUndertripped
			{
				_out = handler.createReply();
				_out.write_boolean(isUndertripped());
				break;
			}
			case 6: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 7: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 8: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 9: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 10: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 11: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 12: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 13: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
				break;
			}
			case 14: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 15: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 16: // isOvertripped
			{
				_out = handler.createReply();
				_out.write_boolean(isOvertripped());
				break;
			}
			case 17: // setSwitches
			{
				boolean[] _arg0=com.traclabs.biosim.idl.BooleanListHelper.read(_input);
				_out = handler.createReply();
				setSwitches(_arg0);
				break;
			}
			case 18: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 19: // getModuleName
			{
				_out = handler.createReply();
				_out.write_string(getModuleName());
				break;
			}
			case 20: // overtrip
			{
				_out = handler.createReply();
				overtrip();
				break;
			}
			case 21: // setSwitch
			{
				int _arg0=_input.read_long();
				boolean _arg1=_input.read_boolean();
				_out = handler.createReply();
				setSwitch(_arg0,_arg1);
				break;
			}
			case 22: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 23: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 24: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 25: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 26: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 27: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 28: // getPowerProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerProducerDefinitionHelper.write(_out,getPowerProducerDefinition());
				break;
			}
			case 29: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 30: // setInitialSwitches
			{
				boolean[] _arg0=com.traclabs.biosim.idl.BooleanListHelper.read(_input);
				_out = handler.createReply();
				setInitialSwitches(_arg0);
				break;
			}
			case 31: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 32: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 33: // undertrip
			{
				_out = handler.createReply();
				undertrip();
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
