#include "biosim.h"

Biosim_Biodriver::Biosim_Biodriver (CORBA::ORB_ptr orb)
  : orb_ (CORBA::ORB::_duplicate (orb))
{
  //  printf("Corba2BvsApi: creating BvsApi object\n");
  // api_ = new BvsApi();
}

CORBA::Boolean 
Biosim_Biodriver::isPaused() throw (CORBA::SystemException)
{
  cerr << "is paused" << endl;

  bool ret_val = true;

  return((CORBA::Boolean) ret_val);
}

CORBA::Boolean
Biosim_Biodriver::isStarted() throw (CORBA::SystemException)
{
  cerr << "is started" << endl;
  bool ret_val = true;

  return((CORBA::Boolean) ret_val);
}

void
Biosim_Biodriver::spawnSimulation() throw (CORBA::SystemException)
{
  cerr << "spawn simulation" << endl;
}

void 
Biosim_Biodriver::spawnSimulationTillDead() throw (CORBA::SystemException)
{
  cerr << "spawn simulation till dead" << endl;
}

void 
Biosim_Biodriver::spawnSimulationTillN(CORBA::Long nTicks) throw (CORBA::SystemException)
{
  cerr << "spawn simulation till" << nTicks << endl;
}

void 
Biosim_Biodriver::pauseSimulation() throw (CORBA::SystemException)
{
   cerr << "pause simulation" << endl;
}

void 
Biosim_Biodriver::endSimulation() throw (CORBA::SystemException)
{
  cerr << "end simulation" << endl;
}
  
CORBA::Boolean 
Biosim_Biodriver::simulationHasStarted() throw (CORBA::SystemException)
{
  cerr << "simulation has started" << endl;

  bool ret_val = true;

  return((CORBA::Boolean) ret_val);
}

void 
Biosim_Biodriver::advanceOneTick() throw (CORBA::SystemException)
{
  cerr << "advance one tick" << endl;
}

void 
Biosim_Biodriver::resumeSimulation() throw (CORBA::SystemException) {
    cerr << "resume simulation" << endl;
}

void 
Biosim_Biodriver::setLogging(CORBA::Boolean pLogSim) throw (CORBA::SystemException)
{
  cerr << "set logging" << endl;
}

CORBA::Boolean
Biosim_Biodriver::isLogging() throw (CORBA::SystemException)
{
  cerr << "is logging" << endl;

  bool ret_val = true;

  return((CORBA::Boolean) ret_val);
}

void 
Biosim_Biodriver::setDriverPauseLength(CORBA::Long pDriverPauseLength) throw (CORBA::SystemException)
{
  cerr << "driver length is " << pDriverPauseLength << endl;
}

CORBA::Long 
Biosim_Biodriver::getDriverPauseLength() throw (CORBA::SystemException)
{
  cerr << "getDriverPauseLength" << endl;
  return(1);
}
