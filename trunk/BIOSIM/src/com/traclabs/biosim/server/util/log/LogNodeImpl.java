package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * An individual log node contained in the log.
 * @author    Scott Bell
 */

public class LogNodeImpl extends LogNodePOA{
	//The name of this node
	private org.omg.CORBA.Object myValue;
	//The parent of this node
	private LogNodeImpl parent;
	//The children below this node
	private LinkedList childrenList;

	protected LogNodeImpl (org.omg.CORBA.Object pValue, LogNodeImpl pParent){
		myValue = pValue;
		parent = pParent;
	}

	public org.omg.CORBA.Object getValue(){
		return myValue;
	}
	
	public LogNode[] getChildren(){
		LogNode[] theChildrenArray = new LogNode[childrenList.size()];
		int i = 0;
		for (Iterator myIterator = childrenList.iterator(); myIterator.hasNext(); ){
			LogNodeImpl currentNode = (LogNodeImpl)(myIterator.next());
			theChildrenArray[i] = LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(currentNode));
			i++;
		}
		return theChildrenArray;
	}

	public void addChild (org.omg.CORBA.Object pChildValue){
		if (childrenList == null)
			childrenList = new LinkedList();
		childrenList.add(new LogNodeImpl(pChildValue, this));
	}
}
