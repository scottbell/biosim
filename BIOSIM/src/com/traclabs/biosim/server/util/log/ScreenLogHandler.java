package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * The ScreenLogHandler takes Logs and outputs them to the screen (via System.out)
 * @author    Scott Bell
 */

public class ScreenLogHandler implements LogHandler{

	public ScreenLogHandler(){
	}
	
	public void writeLog(LogNode logToWrite){
		printTree(logToWrite, 0);
	}
	//revert to recursive
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
	
	public void endLog(){
		System.out.flush();
	}
}
