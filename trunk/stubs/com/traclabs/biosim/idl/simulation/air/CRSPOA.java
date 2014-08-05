package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CRS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class CRSPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.air.CRSOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getTickLength", new java.lang.Integer(0));
		m_opsHash.put ( "registerBioCommandListener", new java.lang.Integer(1));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(2));
		m_opsHash.put ( "clearMalfunction", new java.lang.Integer(3));
		m_opsHash.put ( "notifyCommandSent", new java.lang.Integer(4));
		m_opsHash.put ( "isMalfunctioning", new java.lang.Integer(5));
		m_opsHash.put ( "clearAllMalfunctions", new java.lang.Integer(6));
		m_opsHash.put ( "setEnableFailure", new java.lang.Integer(7));
		m_opsHash.put ( "randomFilter", new java.lang.Integer(8));
		m_opsHash.put ( "reset", new java.lang.Integer(9));
		m_opsHash.put ( "getMyTicks", new java.lang.Integer(10));
		m_opsHash.put ( "maintain", new java.lang.Integer(11));
		m_opsHash.put ( "getPowerConsumerDefinition", new java.lang.Integer(12));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(13));
		m_opsHash.put ( "getID", new java.lang.Integer(14));
		m_opsHash.put ( "getH2ConsumerDefinition", new java.lang.Integer(15));
		m_opsHash.put ( "getModuleName", new java.lang.Integer(16));
		m_opsHash.put ( "scheduleMalfunction", new java.lang.Integer(17));
		m_opsHash.put ( "tick", new java.lang.Integer(18));
		m_opsHash.put ( "fixMalfunction", new java.lang.Integer(19));
		m_opsHash.put ( "getMalfunctionNames", new java.lang.Integer(20));
		m_opsHash.put ( "setTickLength", new java.lang.Integer(21));
		m_opsHash.put ( "getPotableWaterProducerDefinition", new java.lang.Integer(22));
		m_opsHash.put ( "isFailureEnabled", new java.lang.Integer(23));
		m_opsHash.put ( "startMalfunction", new java.lang.Integer(24));
		m_opsHash.put ( "log", new java.lang.Integer(25));
		m_opsHash.put ( "getCO2ConsumerDefinition", new java.lang.Integer(26));
		m_opsHash.put ( "getMethaneProducerDefinition", new java.lang.Integer(27));
		m_opsHash.put ( "getMalfunctions", new java.lang.Integer(28));
		m_opsHash.put ( "fixAllMalfunctions", new java.lang.Integer(29));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/air/CRS:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/air/H2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/MethaneProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0"};
	public com.traclabs.biosim.idl.simulation.air.CRS _this()
	{
		return com.traclabs.biosim.idl.simulation.air.CRSHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.air.CRS _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.air.CRSHelper.narrow(_this_object(orb));
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
			case 1: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
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
			case 4: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 5: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 6: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
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
			case 12: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
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
			case 15: // getH2ConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinitionHelper.write(_out,getH2ConsumerDefinition());
				break;
			}
			case 16: // getModuleName
			{
				_out = handler.createReply();
				_out.write_string(getModuleName());
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
			case 21: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 22: // getPotableWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinitionHelper.write(_out,getPotableWaterProducerDefinition());
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
			case 26: // getCO2ConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinitionHelper.write(_out,getCO2ConsumerDefinition());
				break;
			}
			case 27: // getMethaneProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinitionHelper.write(_out,getMethaneProducerDefinition());
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
