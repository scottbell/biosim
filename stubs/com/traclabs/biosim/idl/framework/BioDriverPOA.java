package com.traclabs.biosim.idl.framework;


/**
 * Generated from IDL interface "BioDriver".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class BioDriverPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.framework.BioDriverOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "setLooping", Integer.valueOf(0));
		m_opsHash.put ( "getActiveSimModuleNames", Integer.valueOf(1));
		m_opsHash.put ( "getTickLength", Integer.valueOf(2));
		m_opsHash.put ( "getModuleNames", Integer.valueOf(3));
		m_opsHash.put ( "startSimulation", Integer.valueOf(4));
		m_opsHash.put ( "getSensorNames", Integer.valueOf(5));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(6));
		m_opsHash.put ( "isDone", Integer.valueOf(7));
		m_opsHash.put ( "getPrioritySimModules", Integer.valueOf(8));
		m_opsHash.put ( "getSensors", Integer.valueOf(9));
		m_opsHash.put ( "setActuators", Integer.valueOf(10));
		m_opsHash.put ( "getPassiveSimModuleNames", Integer.valueOf(11));
		m_opsHash.put ( "setActiveSimModules", Integer.valueOf(12));
		m_opsHash.put ( "getActuatorNames", Integer.valueOf(13));
		m_opsHash.put ( "getDriverStutterLength", Integer.valueOf(14));
		m_opsHash.put ( "endSimulation", Integer.valueOf(15));
		m_opsHash.put ( "getSimModuleNames", Integer.valueOf(16));
		m_opsHash.put ( "getName", Integer.valueOf(17));
		m_opsHash.put ( "setPauseSimulation", Integer.valueOf(18));
		m_opsHash.put ( "setRunTillPlantDeath", Integer.valueOf(19));
		m_opsHash.put ( "setSensors", Integer.valueOf(20));
		m_opsHash.put ( "getSimModules", Integer.valueOf(21));
		m_opsHash.put ( "reset", Integer.valueOf(22));
		m_opsHash.put ( "getModule", Integer.valueOf(23));
		m_opsHash.put ( "setLoopSimulation", Integer.valueOf(24));
		m_opsHash.put ( "setCrewsToWatch", Integer.valueOf(25));
		m_opsHash.put ( "setPrioritySimModules", Integer.valueOf(26));
		m_opsHash.put ( "setDriverStutterLength", Integer.valueOf(27));
		m_opsHash.put ( "isPaused", Integer.valueOf(28));
		m_opsHash.put ( "setModules", Integer.valueOf(29));
		m_opsHash.put ( "setRunTillCrewDeath", Integer.valueOf(30));
		m_opsHash.put ( "getActuators", Integer.valueOf(31));
		m_opsHash.put ( "getPassiveSimModules", Integer.valueOf(32));
		m_opsHash.put ( "setRunTillN", Integer.valueOf(33));
		m_opsHash.put ( "getPrioritySimModuleNames", Integer.valueOf(34));
		m_opsHash.put ( "advanceOneTick", Integer.valueOf(35));
		m_opsHash.put ( "setPassiveSimModules", Integer.valueOf(36));
		m_opsHash.put ( "setPlantsToWatch", Integer.valueOf(37));
		m_opsHash.put ( "setTickLength", Integer.valueOf(38));
		m_opsHash.put ( "isLooping", Integer.valueOf(39));
		m_opsHash.put ( "isStarted", Integer.valueOf(40));
		m_opsHash.put ( "getActiveSimModules", Integer.valueOf(41));
		m_opsHash.put ( "getTicks", Integer.valueOf(42));
		m_opsHash.put ( "getModules", Integer.valueOf(43));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/framework/BioDriver:1.0"};
	public com.traclabs.biosim.idl.framework.BioDriver _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.framework.BioDriver __r = com.traclabs.biosim.idl.framework.BioDriverHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.framework.BioDriver _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.framework.BioDriver __r = com.traclabs.biosim.idl.framework.BioDriverHelper.narrow(__o);
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
			case 0: // setLooping
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setLooping(_arg0);
				break;
			}
			case 1: // getActiveSimModuleNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getActiveSimModuleNames());
				break;
			}
			case 2: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 3: // getModuleNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getModuleNames());
				break;
			}
			case 4: // startSimulation
			{
				_out = handler.createReply();
				startSimulation();
				break;
			}
			case 5: // getSensorNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getSensorNames());
				break;
			}
			case 6: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				startMalfunction(_arg0,_arg1);
				break;
			}
			case 7: // isDone
			{
				_out = handler.createReply();
				_out.write_boolean(isDone());
				break;
			}
			case 8: // getPrioritySimModules
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getPrioritySimModules());
				break;
			}
			case 9: // getSensors
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getSensors());
				break;
			}
			case 10: // setActuators
			{
				com.traclabs.biosim.idl.framework.BioModule[] _arg0=com.traclabs.biosim.idl.framework.BioModuleListHelper.read(_input);
				_out = handler.createReply();
				setActuators(_arg0);
				break;
			}
			case 11: // getPassiveSimModuleNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getPassiveSimModuleNames());
				break;
			}
			case 12: // setActiveSimModules
			{
				com.traclabs.biosim.idl.framework.BioModule[] _arg0=com.traclabs.biosim.idl.framework.BioModuleListHelper.read(_input);
				_out = handler.createReply();
				setActiveSimModules(_arg0);
				break;
			}
			case 13: // getActuatorNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getActuatorNames());
				break;
			}
			case 14: // getDriverStutterLength
			{
				_out = handler.createReply();
				_out.write_long(getDriverStutterLength());
				break;
			}
			case 15: // endSimulation
			{
				_out = handler.createReply();
				endSimulation();
				break;
			}
			case 16: // getSimModuleNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getSimModuleNames());
				break;
			}
			case 17: // getName
			{
				_out = handler.createReply();
				java.lang.String tmpResult5 = getName();
_out.write_string( tmpResult5 );
				break;
			}
			case 18: // setPauseSimulation
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setPauseSimulation(_arg0);
				break;
			}
			case 19: // setRunTillPlantDeath
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setRunTillPlantDeath(_arg0);
				break;
			}
			case 20: // setSensors
			{
				com.traclabs.biosim.idl.framework.BioModule[] _arg0=com.traclabs.biosim.idl.framework.BioModuleListHelper.read(_input);
				_out = handler.createReply();
				setSensors(_arg0);
				break;
			}
			case 21: // getSimModules
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getSimModules());
				break;
			}
			case 22: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 23: // getModule
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleHelper.write(_out,getModule(_arg0));
				break;
			}
			case 24: // setLoopSimulation
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setLoopSimulation(_arg0);
				break;
			}
			case 25: // setCrewsToWatch
			{
				com.traclabs.biosim.idl.simulation.crew.CrewGroup[] _arg0=com.traclabs.biosim.idl.simulation.crew.CrewGroupListHelper.read(_input);
				_out = handler.createReply();
				setCrewsToWatch(_arg0);
				break;
			}
			case 26: // setPrioritySimModules
			{
				com.traclabs.biosim.idl.framework.BioModule[] _arg0=com.traclabs.biosim.idl.framework.BioModuleListHelper.read(_input);
				_out = handler.createReply();
				setPrioritySimModules(_arg0);
				break;
			}
			case 27: // setDriverStutterLength
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setDriverStutterLength(_arg0);
				break;
			}
			case 28: // isPaused
			{
				_out = handler.createReply();
				_out.write_boolean(isPaused());
				break;
			}
			case 29: // setModules
			{
				com.traclabs.biosim.idl.framework.BioModule[] _arg0=com.traclabs.biosim.idl.framework.BioModuleListHelper.read(_input);
				_out = handler.createReply();
				setModules(_arg0);
				break;
			}
			case 30: // setRunTillCrewDeath
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setRunTillCrewDeath(_arg0);
				break;
			}
			case 31: // getActuators
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getActuators());
				break;
			}
			case 32: // getPassiveSimModules
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getPassiveSimModules());
				break;
			}
			case 33: // setRunTillN
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setRunTillN(_arg0);
				break;
			}
			case 34: // getPrioritySimModuleNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getPrioritySimModuleNames());
				break;
			}
			case 35: // advanceOneTick
			{
				_out = handler.createReply();
				advanceOneTick();
				break;
			}
			case 36: // setPassiveSimModules
			{
				com.traclabs.biosim.idl.framework.BioModule[] _arg0=com.traclabs.biosim.idl.framework.BioModuleListHelper.read(_input);
				_out = handler.createReply();
				setPassiveSimModules(_arg0);
				break;
			}
			case 37: // setPlantsToWatch
			{
				com.traclabs.biosim.idl.simulation.food.BiomassPS[] _arg0=com.traclabs.biosim.idl.simulation.food.BiomassPSListHelper.read(_input);
				_out = handler.createReply();
				setPlantsToWatch(_arg0);
				break;
			}
			case 38: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 39: // isLooping
			{
				_out = handler.createReply();
				_out.write_boolean(isLooping());
				break;
			}
			case 40: // isStarted
			{
				_out = handler.createReply();
				_out.write_boolean(isStarted());
				break;
			}
			case 41: // getActiveSimModules
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getActiveSimModules());
				break;
			}
			case 42: // getTicks
			{
				_out = handler.createReply();
				_out.write_long(getTicks());
				break;
			}
			case 43: // getModules
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.BioModuleListHelper.write(_out,getModules());
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
