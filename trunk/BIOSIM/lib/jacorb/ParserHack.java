import org.jacorb.idl.parser;

/*
 * Workaround to ghetto System.exit call in Jacorb's main method.
 * @author    Scott Bell
 */

public class ParserHack{
	public static void main(String args[]){
		parser.compileAndHandle(args);
	}
}

