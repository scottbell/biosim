package com.traclabs.biosim.idl.simulation.thermal;

/**
 *	Generated from IDL interface "IATCS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class IATCSPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.thermal.IATCSOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getActivateState", new java.lang.Integer(0));
		m_opsHash.put ( "clearMalfunction", new java.lang.Integer(1));
		m_opsHash.put ( "getTickLength", new java.lang.Integer(2));
		m_opsHash.put ( "fixAllMalfunctions", new java.lang.Integer(3));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(4));
		m_opsHash.put ( "getGreyWaterProducerDefinition", new java.lang.Integer(5));
		m_opsHash.put ( "startMalfunction", new java.lang.Integer(6));
		m_opsHash.put ( "setPumpSpeed", new java.lang.Integer(7));
		m_opsHash.put ( "notifyCommandSent", new java.lang.Integer(8));
		m_opsHash.put ( "getPpaPumpSpeedCommandStatus", new java.lang.Integer(9));
		m_opsHash.put ( "getID", new java.lang.Integer(10));
		m_opsHash.put ( "fixMalfunction", new java.lang.Integer(11));
		m_opsHash.put ( "getIsolationValveCommandStatus", new java.lang.Integer(12));
		m_opsHash.put ( "getGreyWaterConsumerDefinition", new java.lang.Integer(13));
		m_opsHash.put ( "setPpaPumpSpeedCommandStatus", new java.lang.Integer(14));
		m_opsHash.put ( "setIsloationValveState", new java.lang.Integer(15));
		m_opsHash.put ( "setIatcsState", new java.lang.Integer(16));
		m_opsHash.put ( "getMyTicks", new java.lang.Integer(17));
		m_opsHash.put ( "setBypassValveCommandStatus", new java.lang.Integer(18));
		m_opsHash.put ( "getBypassValveState", new java.lang.Integer(19));
		m_opsHash.put ( "getMalfunctions", new java.lang.Integer(20));
		m_opsHash.put ( "getSfcaSoftwareState", new java.lang.Integer(21));
		m_opsHash.put ( "reset", new java.lang.Integer(22));
		m_opsHash.put ( "registerBioCommandListener", new java.lang.Integer(23));
		m_opsHash.put ( "getModuleName", new java.lang.Integer(24));
		m_opsHash.put ( "setBypassValveState", new java.lang.Integer(25));
		m_opsHash.put ( "setHeaterSoftwareState", new java.lang.Integer(26));
		m_opsHash.put ( "randomFilter", new java.lang.Integer(27));
		m_opsHash.put ( "isMalfunctioning", new java.lang.Integer(28));
		m_opsHash.put ( "log", new java.lang.Integer(29));
		m_opsHash.put ( "setActivateState", new java.lang.Integer(30));
		m_opsHash.put ( "setTwvmSoftwareState", new java.lang.Integer(31));
		m_opsHash.put ( "getPumpSpeed", new java.lang.Integer(32));
		m_opsHash.put ( "maintain", new java.lang.Integer(33));
		m_opsHash.put ( "getBypassValveCommandStatus", new java.lang.Integer(34));
		m_opsHash.put ( "setIsolationValveCommandStatus", new java.lang.Integer(35));
		m_opsHash.put ( "setSfcaSoftwareState", new java.lang.Integer(36));
		m_opsHash.put ( "getHeaterSoftwareState", new java.lang.Integer(37));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(38));
		m_opsHash.put ( "setEnableFailure", new java.lang.Integer(39));
		m_opsHash.put ( "clearAllMalfunctions", new java.lang.Integer(40));
		m_opsHash.put ( "getPowerConsumerDefinition", new java.lang.Integer(41));
		m_opsHash.put ( "tick", new java.lang.Integer(42));
		m_opsHash.put ( "setTickLength", new java.lang.Integer(43));
		m_opsHash.put ( "getIatcsState", new java.lang.Integer(44));
		m_opsHash.put ( "getIsloationValveState", new java.lang.Integer(45));
		m_opsHash.put ( "isFailureEnabled", new java.lang.Integer(46));
		m_opsHash.put ( "getMalfunctionNames", new java.lang.Integer(47));
		m_opsHash.put ( "scheduleMalfunction", new java.lang.Integer(48));
		m_opsHash.put ( "getTwvmSoftwareState", new java.lang.Integer(49));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/thermal/IATCS:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/WaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0"};
	public com.traclabs.biosim.idl.simulation.thermal.IATCS _this()
	{
		return com.traclabs.biosim.idl.simulation.thermal.IATCSHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.thermal.IATCS _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.thermal.IATCSHelper.narrow(_this_object(orb));
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
			case 0: // getActivateState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IATCSActivationHelper.write(_out,getActivateState());
				break;
			}
			case 1: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 2: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 3: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 4: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 5: // getGreyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionHelper.write(_out,getGreyWaterProducerDefinition());
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
			case 7: // setPumpSpeed
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setPumpSpeed(_arg0);
				break;
			}
			case 8: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 9: // getPpaPumpSpeedCommandStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatusHelper.write(_out,getPpaPumpSpeedCommandStatus());
				break;
			}
			case 10: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 11: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 12: // getIsolationValveCommandStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.write(_out,getIsolationValveCommandStatus());
				break;
			}
			case 13: // getGreyWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionHelper.write(_out,getGreyWaterConsumerDefinition());
				break;
			}
			case 14: // setPpaPumpSpeedCommandStatus
			{
				com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus _arg0=com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatusHelper.read(_input);
				_out = handler.createReply();
				setPpaPumpSpeedCommandStatus(_arg0);
				break;
			}
			case 15: // setIsloationValveState
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveState _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXValveStateHelper.read(_input);
				_out = handler.createReply();
				setIsloationValveState(_arg0);
				break;
			}
			case 16: // setIatcsState
			{
				com.traclabs.biosim.idl.simulation.thermal.IATCSState _arg0=com.traclabs.biosim.idl.simulation.thermal.IATCSStateHelper.read(_input);
				_out = handler.createReply();
				setIatcsState(_arg0);
				break;
			}
			case 17: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 18: // setBypassValveCommandStatus
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setBypassValveCommandStatus(_arg0);
				break;
			}
			case 19: // getBypassValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXBypassStateHelper.write(_out,getBypassValveState());
				break;
			}
			case 20: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 21: // getSfcaSoftwareState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.write(_out,getSfcaSoftwareState());
				break;
			}
			case 22: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 23: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 24: // getModuleName
			{
				_out = handler.createReply();
				_out.write_string(getModuleName());
				break;
			}
			case 25: // setBypassValveState
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXBypassStateHelper.read(_input);
				_out = handler.createReply();
				setBypassValveState(_arg0);
				break;
			}
			case 26: // setHeaterSoftwareState
			{
				com.traclabs.biosim.idl.simulation.thermal.SoftwareState _arg0=com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.read(_input);
				_out = handler.createReply();
				setHeaterSoftwareState(_arg0);
				break;
			}
			case 27: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 28: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 29: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 30: // setActivateState
			{
				com.traclabs.biosim.idl.simulation.thermal.IATCSActivation _arg0=com.traclabs.biosim.idl.simulation.thermal.IATCSActivationHelper.read(_input);
				_out = handler.createReply();
				setActivateState(_arg0);
				break;
			}
			case 31: // setTwvmSoftwareState
			{
				com.traclabs.biosim.idl.simulation.thermal.SoftwareState _arg0=com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.read(_input);
				_out = handler.createReply();
				setTwvmSoftwareState(_arg0);
				break;
			}
			case 32: // getPumpSpeed
			{
				_out = handler.createReply();
				_out.write_float(getPumpSpeed());
				break;
			}
			case 33: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 34: // getBypassValveCommandStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.write(_out,getBypassValveCommandStatus());
				break;
			}
			case 35: // setIsolationValveCommandStatus
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setIsolationValveCommandStatus(_arg0);
				break;
			}
			case 36: // setSfcaSoftwareState
			{
				com.traclabs.biosim.idl.simulation.thermal.SoftwareState _arg0=com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.read(_input);
				_out = handler.createReply();
				setSfcaSoftwareState(_arg0);
				break;
			}
			case 37: // getHeaterSoftwareState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.write(_out,getHeaterSoftwareState());
				break;
			}
			case 38: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 39: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 40: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 41: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
				break;
			}
			case 42: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 43: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 44: // getIatcsState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IATCSStateHelper.write(_out,getIatcsState());
				break;
			}
			case 45: // getIsloationValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveStateHelper.write(_out,getIsloationValveState());
				break;
			}
			case 46: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 47: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 48: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 49: // getTwvmSoftwareState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.write(_out,getTwvmSoftwareState());
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
