/* Generated By:JJTree: Do not edit this line. ASTLambdaExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.sourceforge.pmd.lang.java.ast;

public
class ASTLambdaExpression extends AbstractJavaAccessNode {
  public ASTLambdaExpression(int id) {
    super(id);
  }

  public ASTLambdaExpression(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e706de031abe9a22c368b7cb52802f1b (do not edit this line) */
