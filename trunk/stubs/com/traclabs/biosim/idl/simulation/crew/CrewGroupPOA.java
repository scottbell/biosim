package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "CrewGroup"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class CrewGroupPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.CrewGroupOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getProductivity", new java.lang.Integer(0));
		m_opsHash.put ( "clearMalfunction", new java.lang.Integer(1));
		m_opsHash.put ( "getTickLength", new java.lang.Integer(2));
		m_opsHash.put ( "fixAllMalfunctions", new java.lang.Integer(3));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(4));
		m_opsHash.put ( "getPotableWaterConsumed", new java.lang.Integer(5));
		m_opsHash.put ( "getGreyWaterProducerDefinition", new java.lang.Integer(6));
		m_opsHash.put ( "getDirtyWaterProduced", new java.lang.Integer(7));
		m_opsHash.put ( "startMalfunction", new java.lang.Integer(8));
		m_opsHash.put ( "notifyCommandSent", new java.lang.Integer(9));
		m_opsHash.put ( "getID", new java.lang.Integer(10));
		m_opsHash.put ( "getDryWasteProducerDefinition", new java.lang.Integer(11));
		m_opsHash.put ( "scheduleRepair", new java.lang.Integer(12));
		m_opsHash.put ( "killCrew", new java.lang.Integer(13));
		m_opsHash.put ( "createCrewPerson", new java.lang.Integer(14));
		m_opsHash.put ( "fixMalfunction", new java.lang.Integer(15));
		m_opsHash.put ( "attachCrewPerson", new java.lang.Integer(16));
		m_opsHash.put ( "getMyTicks", new java.lang.Integer(17));
		m_opsHash.put ( "getGreyWaterProduced", new java.lang.Integer(18));
		m_opsHash.put ( "getO2Consumed", new java.lang.Integer(19));
		m_opsHash.put ( "anyDead", new java.lang.Integer(20));
		m_opsHash.put ( "getMalfunctions", new java.lang.Integer(21));
		m_opsHash.put ( "getFoodConsumerDefinition", new java.lang.Integer(22));
		m_opsHash.put ( "reset", new java.lang.Integer(23));
		m_opsHash.put ( "registerBioCommandListener", new java.lang.Integer(24));
		m_opsHash.put ( "getCO2Produced", new java.lang.Integer(25));
		m_opsHash.put ( "getModuleName", new java.lang.Integer(26));
		m_opsHash.put ( "randomFilter", new java.lang.Integer(27));
		m_opsHash.put ( "isMalfunctioning", new java.lang.Integer(28));
		m_opsHash.put ( "getPotableWaterConsumerDefinition", new java.lang.Integer(29));
		m_opsHash.put ( "getFoodConsumed", new java.lang.Integer(30));
		m_opsHash.put ( "log", new java.lang.Integer(31));
		m_opsHash.put ( "detachCrewPerson", new java.lang.Integer(32));
		m_opsHash.put ( "getAirProducerDefinition", new java.lang.Integer(33));
		m_opsHash.put ( "maintain", new java.lang.Integer(34));
		m_opsHash.put ( "setDeathEnabled", new java.lang.Integer(35));
		m_opsHash.put ( "isDead", new java.lang.Integer(36));
		m_opsHash.put ( "getDeathEnabled", new java.lang.Integer(37));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(38));
		m_opsHash.put ( "getAirConsumerDefinition", new java.lang.Integer(39));
		m_opsHash.put ( "setEnableFailure", new java.lang.Integer(40));
		m_opsHash.put ( "clearAllMalfunctions", new java.lang.Integer(41));
		m_opsHash.put ( "getCrewPeople", new java.lang.Integer(42));
		m_opsHash.put ( "tick", new java.lang.Integer(43));
		m_opsHash.put ( "setTickLength", new java.lang.Integer(44));
		m_opsHash.put ( "getCrewPerson", new java.lang.Integer(45));
		m_opsHash.put ( "getDirtyWaterProducerDefinition", new java.lang.Integer(46));
		m_opsHash.put ( "getCrewSize", new java.lang.Integer(47));
		m_opsHash.put ( "isFailureEnabled", new java.lang.Integer(48));
		m_opsHash.put ( "getMalfunctionNames", new java.lang.Integer(49));
		m_opsHash.put ( "scheduleMalfunction", new java.lang.Integer(50));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/CrewGroup:1.0","IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/WaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/DirtyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/food/FoodConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirProducer:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.narrow(_this_object(orb));
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
			case 0: // getProductivity
			{
				_out = handler.createReply();
				_out.write_float(getProductivity());
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
			case 5: // getPotableWaterConsumed
			{
				_out = handler.createReply();
				_out.write_float(getPotableWaterConsumed());
				break;
			}
			case 6: // getGreyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionHelper.write(_out,getGreyWaterProducerDefinition());
				break;
			}
			case 7: // getDirtyWaterProduced
			{
				_out = handler.createReply();
				_out.write_float(getDirtyWaterProduced());
				break;
			}
			case 8: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 9: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 10: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 11: // getDryWasteProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionHelper.write(_out,getDryWasteProducerDefinition());
				break;
			}
			case 12: // scheduleRepair
			{
				java.lang.String _arg0=_input.read_string();
				long _arg1=_input.read_longlong();
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleRepair(_arg0,_arg1,_arg2);
				break;
			}
			case 13: // killCrew
			{
				_out = handler.createReply();
				killCrew();
				break;
			}
			case 14: // createCrewPerson
			{
				java.lang.String _arg0=_input.read_string();
				float _arg1=_input.read_float();
				float _arg2=_input.read_float();
				com.traclabs.biosim.idl.simulation.crew.Sex _arg3=com.traclabs.biosim.idl.simulation.crew.SexHelper.read(_input);
				int _arg4=_input.read_long();
				int _arg5=_input.read_long();
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.write(_out,createCrewPerson(_arg0,_arg1,_arg2,_arg3,_arg4,_arg5));
				break;
			}
			case 15: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 16: // attachCrewPerson
			{
				com.traclabs.biosim.idl.simulation.crew.CrewPerson _arg0=com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.read(_input);
				_out = handler.createReply();
				attachCrewPerson(_arg0);
				break;
			}
			case 17: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 18: // getGreyWaterProduced
			{
				_out = handler.createReply();
				_out.write_float(getGreyWaterProduced());
				break;
			}
			case 19: // getO2Consumed
			{
				_out = handler.createReply();
				_out.write_float(getO2Consumed());
				break;
			}
			case 20: // anyDead
			{
				_out = handler.createReply();
				_out.write_boolean(anyDead());
				break;
			}
			case 21: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 22: // getFoodConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinitionHelper.write(_out,getFoodConsumerDefinition());
				break;
			}
			case 23: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 24: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 25: // getCO2Produced
			{
				_out = handler.createReply();
				_out.write_float(getCO2Produced());
				break;
			}
			case 26: // getModuleName
			{
				_out = handler.createReply();
				_out.write_string(getModuleName());
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
			case 29: // getPotableWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinitionHelper.write(_out,getPotableWaterConsumerDefinition());
				break;
			}
			case 30: // getFoodConsumed
			{
				_out = handler.createReply();
				_out.write_float(getFoodConsumed());
				break;
			}
			case 31: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 32: // detachCrewPerson
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				detachCrewPerson(_arg0);
				break;
			}
			case 33: // getAirProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper.write(_out,getAirProducerDefinition());
				break;
			}
			case 34: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 35: // setDeathEnabled
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setDeathEnabled(_arg0);
				break;
			}
			case 36: // isDead
			{
				_out = handler.createReply();
				_out.write_boolean(isDead());
				break;
			}
			case 37: // getDeathEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(getDeathEnabled());
				break;
			}
			case 38: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 39: // getAirConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionHelper.write(_out,getAirConsumerDefinition());
				break;
			}
			case 40: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 41: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 42: // getCrewPeople
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.CrewPersonListHelper.write(_out,getCrewPeople());
				break;
			}
			case 43: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 44: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 45: // getCrewPerson
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.write(_out,getCrewPerson(_arg0));
				break;
			}
			case 46: // getDirtyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinitionHelper.write(_out,getDirtyWaterProducerDefinition());
				break;
			}
			case 47: // getCrewSize
			{
				_out = handler.createReply();
				_out.write_long(getCrewSize());
				break;
			}
			case 48: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 49: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 50: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
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
