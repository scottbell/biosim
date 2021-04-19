package com.traclabs.biosim.idl.simulation.crew;


/**
 * Generated from IDL interface "CrewPerson".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class CrewPersonPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.CrewPersonOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getProductivity", Integer.valueOf(0));
		m_opsHash.put ( "insertActivityInSchedule", Integer.valueOf(1));
		m_opsHash.put ( "getScheduledActivityByOrder", Integer.valueOf(2));
		m_opsHash.put ( "setCurrentActivity", Integer.valueOf(3));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(4));
		m_opsHash.put ( "getPotableWaterConsumed", Integer.valueOf(5));
		m_opsHash.put ( "getActivityByName", Integer.valueOf(6));
		m_opsHash.put ( "getDirtyWaterProduced", Integer.valueOf(7));
		m_opsHash.put ( "isSuffocating", Integer.valueOf(8));
		m_opsHash.put ( "getWeight", Integer.valueOf(9));
		m_opsHash.put ( "setDepartureTick", Integer.valueOf(10));
		m_opsHash.put ( "isStarving", Integer.valueOf(11));
		m_opsHash.put ( "sicken", Integer.valueOf(12));
		m_opsHash.put ( "getName", Integer.valueOf(13));
		m_opsHash.put ( "isSick", Integer.valueOf(14));
		m_opsHash.put ( "isThirsty", Integer.valueOf(15));
		m_opsHash.put ( "getGreyWaterProduced", Integer.valueOf(16));
		m_opsHash.put ( "getO2Consumed", Integer.valueOf(17));
		m_opsHash.put ( "getArrivalTick", Integer.valueOf(18));
		m_opsHash.put ( "reset", Integer.valueOf(19));
		m_opsHash.put ( "getAge", Integer.valueOf(20));
		m_opsHash.put ( "getDepartureTick", Integer.valueOf(21));
		m_opsHash.put ( "getTimeActivityPerformed", Integer.valueOf(22));
		m_opsHash.put ( "insertActivityInScheduleNow", Integer.valueOf(23));
		m_opsHash.put ( "getCO2Produced", Integer.valueOf(24));
		m_opsHash.put ( "getSex", Integer.valueOf(25));
		m_opsHash.put ( "getFoodConsumed", Integer.valueOf(26));
		m_opsHash.put ( "isOnBoard", Integer.valueOf(27));
		m_opsHash.put ( "isDead", Integer.valueOf(28));
		m_opsHash.put ( "isPoisoned", Integer.valueOf(29));
		m_opsHash.put ( "getCurrentActivity", Integer.valueOf(30));
		m_opsHash.put ( "kill", Integer.valueOf(31));
		m_opsHash.put ( "getOrderOfScheduledActivity", Integer.valueOf(32));
		m_opsHash.put ( "tick", Integer.valueOf(33));
		m_opsHash.put ( "setArrivalTick", Integer.valueOf(34));
		m_opsHash.put ( "getCurrentCrewGroup", Integer.valueOf(35));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/CrewPerson:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.CrewPerson _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.CrewPerson __r = com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.CrewPerson _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.CrewPerson __r = com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.narrow(__o);
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
			case 1: // insertActivityInSchedule
			{
				com.traclabs.biosim.idl.simulation.crew.Activity _arg0=com.traclabs.biosim.idl.simulation.crew.ActivityHelper.read(_input);
				int _arg1=_input.read_long();
				_out = handler.createReply();
				insertActivityInSchedule(_arg0,_arg1);
				break;
			}
			case 2: // getScheduledActivityByOrder
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.ActivityHelper.write(_out,getScheduledActivityByOrder(_arg0));
				break;
			}
			case 3: // setCurrentActivity
			{
				com.traclabs.biosim.idl.simulation.crew.Activity _arg0=com.traclabs.biosim.idl.simulation.crew.ActivityHelper.read(_input);
				_out = handler.createReply();
				setCurrentActivity(_arg0);
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
			case 6: // getActivityByName
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.ActivityHelper.write(_out,getActivityByName(_arg0));
				break;
			}
			case 7: // getDirtyWaterProduced
			{
				_out = handler.createReply();
				_out.write_float(getDirtyWaterProduced());
				break;
			}
			case 8: // isSuffocating
			{
				_out = handler.createReply();
				_out.write_boolean(isSuffocating());
				break;
			}
			case 9: // getWeight
			{
				_out = handler.createReply();
				_out.write_float(getWeight());
				break;
			}
			case 10: // setDepartureTick
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setDepartureTick(_arg0);
				break;
			}
			case 11: // isStarving
			{
				_out = handler.createReply();
				_out.write_boolean(isStarving());
				break;
			}
			case 12: // sicken
			{
				_out = handler.createReply();
				sicken();
				break;
			}
			case 13: // getName
			{
				_out = handler.createReply();
				java.lang.String tmpResult33 = getName();
_out.write_string( tmpResult33 );
				break;
			}
			case 14: // isSick
			{
				_out = handler.createReply();
				_out.write_boolean(isSick());
				break;
			}
			case 15: // isThirsty
			{
				_out = handler.createReply();
				_out.write_boolean(isThirsty());
				break;
			}
			case 16: // getGreyWaterProduced
			{
				_out = handler.createReply();
				_out.write_float(getGreyWaterProduced());
				break;
			}
			case 17: // getO2Consumed
			{
				_out = handler.createReply();
				_out.write_float(getO2Consumed());
				break;
			}
			case 18: // getArrivalTick
			{
				_out = handler.createReply();
				_out.write_long(getArrivalTick());
				break;
			}
			case 19: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 20: // getAge
			{
				_out = handler.createReply();
				_out.write_float(getAge());
				break;
			}
			case 21: // getDepartureTick
			{
				_out = handler.createReply();
				_out.write_long(getDepartureTick());
				break;
			}
			case 22: // getTimeActivityPerformed
			{
				_out = handler.createReply();
				_out.write_long(getTimeActivityPerformed());
				break;
			}
			case 23: // insertActivityInScheduleNow
			{
				com.traclabs.biosim.idl.simulation.crew.Activity _arg0=com.traclabs.biosim.idl.simulation.crew.ActivityHelper.read(_input);
				_out = handler.createReply();
				insertActivityInScheduleNow(_arg0);
				break;
			}
			case 24: // getCO2Produced
			{
				_out = handler.createReply();
				_out.write_float(getCO2Produced());
				break;
			}
			case 25: // getSex
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.SexHelper.write(_out,getSex());
				break;
			}
			case 26: // getFoodConsumed
			{
				_out = handler.createReply();
				_out.write_float(getFoodConsumed());
				break;
			}
			case 27: // isOnBoard
			{
				_out = handler.createReply();
				_out.write_boolean(isOnBoard());
				break;
			}
			case 28: // isDead
			{
				_out = handler.createReply();
				_out.write_boolean(isDead());
				break;
			}
			case 29: // isPoisoned
			{
				_out = handler.createReply();
				_out.write_boolean(isPoisoned());
				break;
			}
			case 30: // getCurrentActivity
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.ActivityHelper.write(_out,getCurrentActivity());
				break;
			}
			case 31: // kill
			{
				_out = handler.createReply();
				kill();
				break;
			}
			case 32: // getOrderOfScheduledActivity
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				_out.write_long(getOrderOfScheduledActivity(_arg0));
				break;
			}
			case 33: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 34: // setArrivalTick
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setArrivalTick(_arg0);
				break;
			}
			case 35: // getCurrentCrewGroup
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.write(_out,getCurrentCrewGroup());
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
