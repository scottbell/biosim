package com.traclabs.biosim.idl.simulation.crew;


/**
 * Generated from IDL interface "CrewGroup".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class CrewGroupPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.CrewGroupOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getProductivity", Integer.valueOf(0));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(1));
		m_opsHash.put ( "getTickLength", Integer.valueOf(2));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(3));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(4));
		m_opsHash.put ( "getPotableWaterConsumed", Integer.valueOf(5));
		m_opsHash.put ( "getGreyWaterProducerDefinition", Integer.valueOf(6));
		m_opsHash.put ( "getDirtyWaterProduced", Integer.valueOf(7));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(8));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(9));
		m_opsHash.put ( "getID", Integer.valueOf(10));
		m_opsHash.put ( "getDryWasteProducerDefinition", Integer.valueOf(11));
		m_opsHash.put ( "scheduleRepair", Integer.valueOf(12));
		m_opsHash.put ( "killCrew", Integer.valueOf(13));
		m_opsHash.put ( "createCrewPerson", Integer.valueOf(14));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(15));
		m_opsHash.put ( "attachCrewPerson", Integer.valueOf(16));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(17));
		m_opsHash.put ( "getGreyWaterProduced", Integer.valueOf(18));
		m_opsHash.put ( "getO2Consumed", Integer.valueOf(19));
		m_opsHash.put ( "anyDead", Integer.valueOf(20));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(21));
		m_opsHash.put ( "getFoodConsumerDefinition", Integer.valueOf(22));
		m_opsHash.put ( "reset", Integer.valueOf(23));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(24));
		m_opsHash.put ( "getCO2Produced", Integer.valueOf(25));
		m_opsHash.put ( "getModuleName", Integer.valueOf(26));
		m_opsHash.put ( "randomFilter", Integer.valueOf(27));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(28));
		m_opsHash.put ( "getPotableWaterConsumerDefinition", Integer.valueOf(29));
		m_opsHash.put ( "getFoodConsumed", Integer.valueOf(30));
		m_opsHash.put ( "log", Integer.valueOf(31));
		m_opsHash.put ( "detachCrewPerson", Integer.valueOf(32));
		m_opsHash.put ( "getAirProducerDefinition", Integer.valueOf(33));
		m_opsHash.put ( "maintain", Integer.valueOf(34));
		m_opsHash.put ( "setDeathEnabled", Integer.valueOf(35));
		m_opsHash.put ( "isDead", Integer.valueOf(36));
		m_opsHash.put ( "getDeathEnabled", Integer.valueOf(37));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(38));
		m_opsHash.put ( "getAirConsumerDefinition", Integer.valueOf(39));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(40));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(41));
		m_opsHash.put ( "getCrewPeople", Integer.valueOf(42));
		m_opsHash.put ( "tick", Integer.valueOf(43));
		m_opsHash.put ( "setTickLength", Integer.valueOf(44));
		m_opsHash.put ( "getCrewPerson", Integer.valueOf(45));
		m_opsHash.put ( "getDirtyWaterProducerDefinition", Integer.valueOf(46));
		m_opsHash.put ( "getCrewSize", Integer.valueOf(47));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(48));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(49));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(50));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/CrewGroup:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/DirtyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/FoodConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirProducer:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.CrewGroup __r = com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.CrewGroup __r = com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.narrow(__o);
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
				java.lang.String tmpResult39 = getModuleName();
_out.write_string( tmpResult39 );
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
