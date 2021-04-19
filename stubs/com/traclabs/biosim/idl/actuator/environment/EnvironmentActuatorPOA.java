package com.traclabs.biosim.idl.actuator.environment;


/**
 * Generated from IDL interface "EnvironmentActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class EnvironmentActuatorPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.actuator.environment.EnvironmentActuatorOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getTickLength", Integer.valueOf(0));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(1));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(2));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(3));
		m_opsHash.put ( "getMax", Integer.valueOf(4));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(5));
		m_opsHash.put ( "setOutput", Integer.valueOf(6));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(7));
		m_opsHash.put ( "randomFilter", Integer.valueOf(8));
		m_opsHash.put ( "reset", Integer.valueOf(9));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(10));
		m_opsHash.put ( "maintain", Integer.valueOf(11));
		m_opsHash.put ( "getOutput", Integer.valueOf(12));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(13));
		m_opsHash.put ( "getID", Integer.valueOf(14));
		m_opsHash.put ( "getValue", Integer.valueOf(15));
		m_opsHash.put ( "getModuleName", Integer.valueOf(16));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(17));
		m_opsHash.put ( "tick", Integer.valueOf(18));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(19));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(20));
		m_opsHash.put ( "setValue", Integer.valueOf(21));
		m_opsHash.put ( "setTickLength", Integer.valueOf(22));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(23));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(24));
		m_opsHash.put ( "log", Integer.valueOf(25));
		m_opsHash.put ( "getOutputModule", Integer.valueOf(26));
		m_opsHash.put ( "getMin", Integer.valueOf(27));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(28));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(29));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/actuator/environment/EnvironmentActuator:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/actuator/framework/GenericActuator:1.0"};
	public com.traclabs.biosim.idl.actuator.environment.EnvironmentActuator _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.actuator.environment.EnvironmentActuator __r = com.traclabs.biosim.idl.actuator.environment.EnvironmentActuatorHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.actuator.environment.EnvironmentActuator _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.actuator.environment.EnvironmentActuator __r = com.traclabs.biosim.idl.actuator.environment.EnvironmentActuatorHelper.narrow(__o);
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
			case 0: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 1: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 2: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 3: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 4: // getMax
			{
				_out = handler.createReply();
				_out.write_float(getMax());
				break;
			}
			case 5: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 6: // setOutput
			{
				com.traclabs.biosim.idl.simulation.environment.SimEnvironment _arg0=com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.read(_input);
				_out = handler.createReply();
				setOutput(_arg0);
				break;
			}
			case 7: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 8: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 9: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 10: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 11: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 12: // getOutput
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.write(_out,getOutput());
				break;
			}
			case 13: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 14: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 15: // getValue
			{
				_out = handler.createReply();
				_out.write_float(getValue());
				break;
			}
			case 16: // getModuleName
			{
				_out = handler.createReply();
				java.lang.String tmpResult215 = getModuleName();
_out.write_string( tmpResult215 );
				break;
			}
			case 17: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 18: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 19: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 20: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 21: // setValue
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setValue(_arg0);
				break;
			}
			case 22: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 23: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 24: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 25: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 26: // getOutputModule
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleHelper.write(_out,getOutputModule());
				break;
			}
			case 27: // getMin
			{
				_out = handler.createReply();
				_out.write_float(getMin());
				break;
			}
			case 28: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 29: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
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
