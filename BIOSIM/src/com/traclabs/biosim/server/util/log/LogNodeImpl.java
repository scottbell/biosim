package biosim.server.util.log;

import biosim.idl.util.log.*;
import biosim.server.util.*;
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
	
	public LogNodeImpl (String pValue){
		myValue = pValue;
		parent = null;
	}

	public LogNodeImpl (String pValue, LogNodeImpl pParent){
		myValue = pValue;
		parent = pParent;
	}

	public String getValue(){
		return myValue;
	}
	
	public void setValue(String newValue){
		myValue = newValue;
	}
	
	protected void setParent(LogNodeImpl newParent){
		parent = newParent;
	}
	
	public LogNode getHead(){
		if (parent == null)
			return LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(this));
		else 
			return parent.getHead();
	}
	
	public LogNode getChildShallow(String pName){
		if (childrenList == null)
			return null;
		for (Iterator myIterator = childrenList.iterator(); myIterator.hasNext(); ){
			LogNodeImpl currentNode = (LogNodeImpl)(myIterator.next());
			if (currentNode.getValue().equals(pName)){
				return LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(currentNode));
			}
		}
		return null;
	}
	
	public LogNode getChildDeep(String pName){
		if (childrenList == null)
			return null;
		for (Iterator myIterator = childrenList.iterator(); myIterator.hasNext(); ){
			LogNodeImpl currentNode = (LogNodeImpl)(myIterator.next());
			//See if this child is the one we're looking for
			if (currentNode.getValue().equals(pName)){
				return LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(currentNode));
			}
			//Look in this child's children for it then
			LogNode foundNode = currentNode.getChildDeep(pName);
			if (foundNode != null){
				return foundNode;
			}
		}
		return null;
	}
	
	public LogNode[] getChildren(){
		if (childrenList == null){
			LogNode[] zeroChildrenArray = new LogNode[0];
			return zeroChildrenArray;
		}
		LogNode[] theChildrenArray = new LogNode[childrenList.size()];
		int i = 0;
		for (Iterator myIterator = childrenList.iterator(); myIterator.hasNext(); ){
			LogNodeImpl currentNode = (LogNodeImpl)(myIterator.next());
			theChildrenArray[i] = LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(currentNode));
			i++;
		}
		return theChildrenArray;
	}
	
	public boolean hasChildren(){
		return ((childrenList == null) || (childrenList.size() < 1));
	}

	public LogNode addChild(String pChildValue){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl newLogNodeImpl = new LogNodeImpl(pChildValue, this);
		childrenList.add(newLogNodeImpl);
		return  LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(newLogNodeImpl));
	}
	
	protected LogNodeImpl addChildImpl(String pChildValue){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl newLogNodeImpl = new LogNodeImpl(pChildValue, this);
		childrenList.add(newLogNodeImpl);
		return  newLogNodeImpl;
	}
	
	protected LogNode addChild(LogNode pChildNode){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl childNodeImpl = (LogNodeImpl)(OrbUtils.corbaObjToPoa(pChildNode));
		childNodeImpl.setParent(this);
		childrenList.add(childNodeImpl);
		return pChildNode;
	}
}
