package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * An individual log node contained in the log.
 * @author    Scott Bell
 */

public class LogNodeImpl extends LogNodePOA{
	//The name of this node
	private String myName;
	//The parent of this node
	private LogNodeImpl parent;
	//The children below this node
	private LinkedList childrenList;

	protected LogNodeImpl (String pName, LogNodeImpl pParent){
		myName = pName;
		parent = pParent;
	}

	public String getName(){
		return myName;
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

	public void addChild (String pChildName){
		if (childrenList == null)
			childrenList = new LinkedList();
		childrenList.add(new LogNodeImpl(pChildName, this));
	}
}
