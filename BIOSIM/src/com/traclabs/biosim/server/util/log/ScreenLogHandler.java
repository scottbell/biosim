package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * The ScreenLogHandler takes Logs and outputs them to the screen (via System.out)
 * @author    Scott Bell
 */

public class ScreenLogHandler extends LogHandler{
	public void writeLog(Log logToWrite){
		printTree(logToWrite.getHead());
	}
	//revert to recursive
	private void printTree(LogNode rootNode) {
		if (rootNode == null)
			return;  // There is nothing to print in an empty tree.
		Stack myNodeStack = new Stack();
		Stack myChildCountStack = new Stack();
		myNodeStack.push(rootNode);
		while (!myNodeStack.isEmpty()){
			LogNode poppedNode = (LogNode)(myNodeStack.pop());
			LogNode[] childrenNodes =poppedNode.getChildren();
			if (childrenNodes.length > 0){
				myChildCountStack.push(new Integer(childrenNodes.length));
			}
			else{
				myChildCountStack.push(new Integer(0));
			}
			for (int i = 0; i < myChildCountStack.size(); i++){
				System.out.print("+");
			}
			System.out.println(poppedNode.getValue());
			System.out.flush();
			if (!myChildCountStack.isEmpty()){
				Integer poppedCount = (Integer)(myChildCountStack.pop());
				int newCount = poppedCount.intValue() - 1;
				if (poppedCount.intValue() > 0)
					myChildCountStack.push(new Integer(newCount));
				if (poppedCount.intValue() < 0){
					myChildCountStack.pop();
					if (!myChildCountStack.isEmpty())
						myChildCountStack.pop();
				}
			}
			for (int i = 0; i < childrenNodes.length; i++){
				myNodeStack.push(childrenNodes[i]);
			}
		}
	} // end levelOrderPrint()

}
