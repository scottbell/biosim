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
	
	/**
	* Creates a LogNode with a specified value.  Should be used when creating a root node.
	* @param pValue the value of this LogNode
	*/
	public LogNodeImpl (String pValue){
		myValue = pValue;
		parent = null;
	}
	
	/**
	* Creates a LogNode with a specified value and a specified parent (i.e., this LogNode will be the parent's child node)
	* @param pValue the value of this LogNode
	* @param pParent the parent of this LogNode
	*/
	public LogNodeImpl (String pValue, LogNodeImpl pParent){
		myValue = pValue;
		parent = pParent;
	}
	
	/**
	* Tells the value of this LogNode
	* @return the value of this LogNode
	*/
	public String getValue(){
		return myValue;
	}
	
	/**
	* Sets the value of this LogNode
	* @param newValue the value to set for this LogNode
	*/
	public void setValue(String newValue){
		myValue = newValue;
	}
	
	/**
	* Sets the parent of this LogNode
	* @param newParent the parent to set for this LogNode
	*/
	protected void setParent(LogNodeImpl newParent){
		parent = newParent;
	}
	
	/**
	* Gets the top node of this node by recursively searching the parents of this node.
	* @return the parent of this LogNode
	*/
	public LogNode getHead(){
		if (parent == null)
			return LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(this));
		else 
			return parent.getHead();
	}
	
	/**
	* Searches the immediate children beneath this node (i.e., one level down) and tries to find a match for the value passed in
	* @param The value to search for
	* @return The immediate child with a matching value.
	*/
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
	
	/**
	* Searches all the children beneath this node (i.e., all levels down) and tries to find a match for the value passed in
	* @param The value to search for
	* @return The child with a matching value.
	*/
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
	
	/**
	* Gets all the children underneath this log node
	* @return An array of LogNodes containing all the children of this node
	*/
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
	
	/**
	* Tells if there are any children underneath this LogNode
	* @return if there are >1 child underneath, returns <code>true</code>, else <code>false</code>
	*/
	public boolean hasChildren(){
		return ((childrenList == null) || (childrenList.size() < 1));
	}
	
	/**
	* Creates a child LogNode with a value specified.  Its parent is this LogNode
	* @param pChildValue the value of the child LogNode to create underneath this LogNode
	* @return the newly created child LogNode
	*/
	public LogNode addChild(String pChildValue){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl newLogNodeImpl = new LogNodeImpl(pChildValue, this);
		childrenList.add(newLogNodeImpl);
		return  LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(newLogNodeImpl));
	}
	
	/**
	* Creates a child LogNode with a value specified.  Its parent is this LogNode.  Uses LogNodeImpl's instead (hence it's protected)
	* @param pChildValue the value of the child LogNode to create underneath this LogNode
	* @return the newly created child LogNodeImpl
	*/
	protected LogNodeImpl addChildImpl(String pChildValue){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl newLogNodeImpl = new LogNodeImpl(pChildValue, this);
		childrenList.add(newLogNodeImpl);
		return  newLogNodeImpl;
	}
	
	/**
	* Adds a child LogNode.  Its parent is this LogNode.
	* @param pChildNode the child LogNode to add underneath this LogNode
	* @return the child LogNodeImpl just added (should be the same as the one passed in)
	*/
	protected LogNode addChild(LogNode pChildNode){
		if (childrenList == null)
			childrenList = new LinkedList();
		LogNodeImpl childNodeImpl = (LogNodeImpl)(OrbUtils.corbaObjToPoa(pChildNode));
		childNodeImpl.setParent(this);
		childrenList.add(childNodeImpl);
		return pChildNode;
	}
}
