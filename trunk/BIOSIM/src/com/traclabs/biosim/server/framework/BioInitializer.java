package biosim.server.framework;

import org.xml.sax.*;
import java.io.*;

/*
 *
 * @author    Scott Bell
 */

public class BioInitializer extends DefaultHandler implements ContentHandler, DTDHandler, ErrorHandler, DeclHandler, LexicalHandler
{
	
	public static void main(String args[]){
	}
	
	/**
	* Attempts to discover the machine type we're running on.  If it's windows, set a driver pause between ticks
	* to keep from starving windows GUI.  Checked by looking at the Java System Property "MACHINE_TYPE"
	*/
	private void checkMachineType(){
		String machineType = null;
		machineType = System.getProperty("MACHINE_TYPE");
		if (machineType != null){
			if (machineType.indexOf("CYGWIN") != -1){
				myBioDriverImpl.setDriverPauseLength(5);
			}
			else{
				myBioDriverImpl.setDriverPauseLength(0);
			}
		}
		else{
			myBioDriverImpl.setDriverPauseLength(0);
		}
	}
	
}
