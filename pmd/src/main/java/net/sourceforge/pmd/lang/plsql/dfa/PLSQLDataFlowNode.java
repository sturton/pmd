/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.plsql.dfa;

import java.util.List;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.dfa.AbstractDataFlowNode;
import net.sourceforge.pmd.lang.dfa.DataFlowNode;
//import net.sourceforge.pmd.lang.plsql.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.plsql.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.plsql.ast.ASTProgramUnit;
import net.sourceforge.pmd.lang.plsql.ast.ASTTriggerUnit;

public class PLSQLDataFlowNode extends AbstractDataFlowNode {

    public PLSQLDataFlowNode(List<DataFlowNode> dataFlow, Node node) {
	super(dataFlow, node);
    }

    public String toString() {
	String res = "DataFlowNode: line " + this.getLine() + ", ";
	if (node instanceof ASTProgramUnit || node instanceof ASTTriggerUnit /* || node instanceof ASTConstructorDeclaration */) {
	    res += (node instanceof ASTProgramUnit) ? "(method)" : "(trigger)";
	} else {
	    res = super.toString();
	}
	return res;
    }
}