package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * An individual log node contained in the log.
 * @author    Scott Bell
 */

public class LogNodeImpl extends LogNodePOA{
	//The name of this node
	private String myValue;
	//The parent of this node
	private LogNodeImpl parent;
	//The children below this node
	private LinkedList childrenList;

	protected LogNodeImpl (String pValue, LogNodeImpl pParent){
		myValue = pValue;
		parent = pParent;
	}

	public String getValue(){
		return myValue;
	}
	
	public void setValue(String newValue){
		myValue = newValue;
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

	public LogNode addChild (String pChildValue){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl newLogNodeImpl = new LogNodeImpl(pChildValue, this);
		childrenList.add(newLogNodeImpl);
		return  LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(newLogNodeImpl));
	}
}
