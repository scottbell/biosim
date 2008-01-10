#include "biosimC.h"
#include <CosNamingC.h>

static const char* CorbaServerName = "BioDriver";
int
main(int argc,char *argv[])
{
  try {
    // Initialize orb
    CORBA::ORB_var orb = CORBA::ORB_init(argc,argv,"TestClient");

    cerr << "initialized orb" << endl;

    if (argc < 2) {
      cerr << "Usage: " << argv[0]
           << " IOR_string" << endl;
      return 1;
    }

// Use the first argument to create the object reference,
    // in real applications we use the naming service, but let's do
    // the easy part first!

    CORBA::Object_var obj =
      orb->string_to_object (argv[1]); 

    cout << "After resolve_initial_references" << endl;


    /*   CORBA::Object_var obj =
	 orb->string_to_object (argv[1]); */
 
    CosNaming::NamingContext_var inc = CosNaming::NamingContext::_narrow(obj);

    cout << "After NameService narrow" << endl;

    if(CORBA::is_nil(inc)) {
      cerr << "Unable to narrow NameService." << endl;
      throw 0;
    }  // end if couldn't get root naming context
    
    CosNaming::Name name;
    name.length(1);
    name[0].id = CORBA::string_dup(CorbaServerName);
    name[0].kind = CORBA::string_dup("");

    CORBA::Object_var tmp = inc->resolve(name);

    cout << "After resolve name" << endl;

    if(CORBA::is_nil(tmp)) {
      cerr << "Unable to resolve " << CorbaServerName << " from NameService" << endl;
      throw 0;
    }  // end if couldn't find object

    cerr << "got object" << endl;

    // Now downcast the object reference to the appropriate type

    biosim::idl::framework::BioDriver_var biodriver = biosim::idl::framework::BioDriver::_narrow(tmp);


    cerr << "narrowed" << endl;

    // code here

    biodriver->spawnSimulationTillDead();

    cerr << "spawned" << endl;

    orb->destroy ();
  }
  catch (CORBA::Exception &ex) {
    std::cerr << "CORBA exception raised!" << std::endl;
  }
  return 0;
}

