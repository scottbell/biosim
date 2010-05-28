package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "CrewPerson"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class CrewPersonPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.CrewPersonOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getProductivity", new java.lang.Integer(0));
		m_opsHash.put ( "insertActivityInSchedule", new java.lang.Integer(1));
		m_opsHash.put ( "getScheduledActivityByOrder", new java.lang.Integer(2));
		m_opsHash.put ( "setCurrentActivity", new java.lang.Integer(3));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(4));
		m_opsHash.put ( "getPotableWaterConsumed", new java.lang.Integer(5));
		m_opsHash.put ( "getActivityByName", new java.lang.Integer(6));
		m_opsHash.put ( "getDirtyWaterProduced", new java.lang.Integer(7));
		m_opsHash.put ( "isSuffocating", new java.lang.Integer(8));
		m_opsHash.put ( "getWeight", new java.lang.Integer(9));
		m_opsHash.put ( "setDepartureTick", new java.lang.Integer(10));
		m_opsHash.put ( "isStarving", new java.lang.Integer(11));
		m_opsHash.put ( "sicken", new java.lang.Integer(12));
		m_opsHash.put ( "getName", new java.lang.Integer(13));
		m_opsHash.put ( "isSick", new java.lang.Integer(14));
		m_opsHash.put ( "isThirsty", new java.lang.Integer(15));
		m_opsHash.put ( "getGreyWaterProduced", new java.lang.Integer(16));
		m_opsHash.put ( "getO2Consumed", new java.lang.Integer(17));
		m_opsHash.put ( "getArrivalTick", new java.lang.Integer(18));
		m_opsHash.put ( "reset", new java.lang.Integer(19));
		m_opsHash.put ( "getAge", new java.lang.Integer(20));
		m_opsHash.put ( "getDepartureTick", new java.lang.Integer(21));
		m_opsHash.put ( "getTimeActivityPerformed", new java.lang.Integer(22));
		m_opsHash.put ( "insertActivityInScheduleNow", new java.lang.Integer(23));
		m_opsHash.put ( "getCO2Produced", new java.lang.Integer(24));
		m_opsHash.put ( "getSex", new java.lang.Integer(25));
		m_opsHash.put ( "getFoodConsumed", new java.lang.Integer(26));
		m_opsHash.put ( "isOnBoard", new java.lang.Integer(27));
		m_opsHash.put ( "isDead", new java.lang.Integer(28));
		m_opsHash.put ( "isPoisoned", new java.lang.Integer(29));
		m_opsHash.put ( "getCurrentActivity", new java.lang.Integer(30));
		m_opsHash.put ( "kill", new java.lang.Integer(31));
		m_opsHash.put ( "getOrderOfScheduledActivity", new java.lang.Integer(32));
		m_opsHash.put ( "tick", new java.lang.Integer(33));
		m_opsHash.put ( "setArrivalTick", new java.lang.Integer(34));
		m_opsHash.put ( "getCurrentCrewGroup", new java.lang.Integer(35));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/CrewPerson:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.CrewPerson _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.CrewPerson _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper.narrow(_this_object(orb));
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
				_out.write_string(getName());
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
