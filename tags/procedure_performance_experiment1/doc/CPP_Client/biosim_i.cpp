#include "biosim_i.h"

Biosim_Biodriver_i::Biosim_Biodriver_i (CORBA::ORB_ptr orb)
  : orb_ (CORBA::ORB::_duplicate (orb))
{
  //  printf("Corba2BvsApi: creating BvsApi object\n");
  // api_ = new BvsApi();
}

bool 
Biosim_Biodriver_i::isPaused() throw (CORBA::SystemException)
{
  cerr << "is paused" << endl;
  return(1);
}

bool 
Biosim_Biodriver_i::isStarted() throw (CORBA::SystemException)
{
  cerr << "is started" << endl;
  return(1);
}

void 
Biosim_Biodriver_i::spawnSimulation() throw (CORBA::SystemException)
{
  cerr << "spawn simulation" << endl;
}

void 
Biosim_Biodriver_i::spawnSimulationTillDead() throw (CORBA::SystemException)
{
  cerr << "spawn simulation till dead" << endl;
}

void 
Biosim_Biodriver_i::spawnSimulationTillN(CORBA::Long nTicks) throw (CORBA::SystemException)
{
  cerr << "spawn simulation till" << nTicks << endl;
}

void 
Biosim_Biodriver_i::pauseSimulation() throw (CORBA::SystemException)
{
   cerr << "pause simulation" << endl;
}

void 
Biosim_Biodriver_i::endSimulation() throw (CORBA::SystemException)
{
  cerr << "end simulation" << endl;
}
  
bool 
Biosim_Biodriver_i::simulationHasStarted() throw (CORBA::SystemException)
{
  cerr << "simulation has started" << endl;
  return(1);
}

void 
Biosim_Biodriver_i::advanceOneTick() throw (CORBA::SystemException)
{
  cerr << "advance one tick" << endl;
}

void 
Biosim_Biodriver_i::resumeSimulation() throw (CORBA::SystemException) {
    cerr << "resume simulation" << endl;
}

void 
Biosim_Biodriver_i::setLogging(CORBA::Boolean pLogSim) throw (CORBA::SystemException)
{
  cerr << "set logging" << endl;
}

bool 
Biosim_Biodriver_i::isLogging() throw (CORBA::SystemException)
{
  cerr << "is logging" << endl;
  return(1);
}

void 
Biosim_Biodriver_i::setDriverPauseLength(CORBA::Long pDriverPauseLength) throw (CORBA::SystemException)
{
  cerr << "driver length is " << pDriverPauseLength << endl;
}

long 
Biosim_Biodriver_i::getDriverPauseLength() throw (CORBA::SystemException)
{
  cerr << "getDriverPauseLength" << endl;
  return(1);
}

