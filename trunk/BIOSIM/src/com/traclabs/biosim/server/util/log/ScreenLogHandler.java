package biosim.server.util.log;

import biosim.idl.util.log.*;
/**
 * The ScreenLogHandler takes Logs and outputs them to the screen (via System.out)
 * @author    Scott Bell
 */

public class ScreenLogHandler implements LogHandler{

	/**
	* Creates a ScreenLogHandler to output to System.out
	*/
	public ScreenLogHandler(){
	}
	
	/**
	* Prints the LogNode to the screen (doesn't flush immediately)
	* @param logToWrite the LogNode to write
	*/
	public void writeLog(LogNode logToWrite){
		printTree(logToWrite, 0);
	}
	
	public LogHandlerType getType(){
		return LogHandlerType.XML;
	}
	
	/**
	* Prints the LogNode to the screen nicely using tabs (recursive)
	* @param currentNode the LogNode to currently being written
	* @param currentDepth how far we've descended into the tree, used for tabbing (to make it look nice)
	*/
	private void printTree(LogNode currentNode, int currentDepth) {
		if (currentNode == null){
			return;  // There is nothing to print in an empty tree.
		}
		for (int i = 0; i < currentDepth; i ++){
			System.out.print("\t");
		}
		System.out.println(currentNode.getValue());
		LogNode[] childrenNodes =currentNode.getChildren();
		for (int i = 0; i < childrenNodes.length; i++){
			printTree(childrenNodes[i], currentDepth + 1);
		}
	}
	
	/**
	* Finishes printing to the screen by flushing the output
	*/
	public void endLog(){
		System.out.flush();
	}
}
