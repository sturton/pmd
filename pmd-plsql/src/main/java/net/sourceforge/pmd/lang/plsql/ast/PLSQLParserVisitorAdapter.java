/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.plsql.ast;

public class PLSQLParserVisitorAdapter implements PLSQLParserVisitor {

	@Override
	public Object visit(PLSQLNode node, Object data) {
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTInput node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDDLCommand node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlPlusCommand node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTGlobal node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTBlock node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPackageSpecification node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPackageBody node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDeclarativeUnit node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDeclarativeSection node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCompilationDeclarationFragment node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTProgramUnit node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTObjectNameDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTFormalParameter node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTMethodDeclarator node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTFormalParameters node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTVariableOrConstantDeclarator node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTVariableOrConstantDeclaratorId node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTVariableOrConstantInitializer node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDatatype node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCompilationDataType node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCollectionTypeName node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTScalarDataTypeName node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDateTimeLiteral node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTExceptionHandler node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSkip2NextTerminator node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSkip2NextOccurrence node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSkipPastNextOccurrence node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSkip2NextTokenOccurrence node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSkipPastNextTokenOccurrence node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTRead2NextOccurrence node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTReadPastNextOccurrence node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTWrappedObject node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTUnlabelledStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTLabelledStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCaseStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCaseWhenClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTElseClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTElsifClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTLoopStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTForStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTIfStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTForIndex node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTForAllIndex node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTForAllStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTGotoStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTReturnStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTContinueStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTExitStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTRaiseStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCloseStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTOpenStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTFetchStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTEmbeddedSqlStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPipelineStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTConditionalCompilationStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSubTypeDefinition node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCollectionTypeDefinition node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCollectionDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTObjectDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCallSpecTail node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCursorUnit node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCursorSpecification node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCursorBody node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCompilationExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTAssignment node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCaseExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTLikeExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTrimExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTObjectExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTEqualityExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTRelationalExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTStringExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTUnaryExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPrimaryExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPrimarySuffix node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTLiteral node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTStringLiteral node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTBooleanLiteral node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTNullLiteral node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTMultiSetCondition node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTNumericLiteral node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTLabel node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTName node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTQualifiedName node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTArguments node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTArgumentList node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTArgument node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTVariableOrConstantDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDatatypeDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPragma node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTInlinePragma node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTExceptionDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTParallelClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTable node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTableColumn node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTView node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSynonym node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDirectory node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDatabaseLink node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTViewColumn node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTComment node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTypeMethod node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTypeSpecification node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTAlterTypeSpec node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTAttributeDeclaration node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTAttribute node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTPragmaClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTriggerUnit node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTriggerTimingPointSection node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTCompoundTriggerBlock node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTNonDMLTrigger node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDDLEvent node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTDatabaseEvent node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTNonDMLEvent node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTAlterTrigger node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTKEYWORD_RESERVED node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTKEYWORD_UNRESERVED node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTID node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTUnqualifiedID node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTQualifiedID node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTTypeKeyword node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTJavaInterfaceClass node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTEqualsOldIDNewID node, Object data) {
		return visit((PLSQLNode) node, data);
	}

        @Override
        public Object visit(ASTAccessibleByClause node, Object data) {
                return visit((PLSQLNode) node, data);
        }

	// SQL Parser 
	// 
   


        @Override
        public Object visit(ASTCommitStatement node, Object data) {
                return visit((PLSQLNode) node, data);
        }


        @Override
        public Object visit(ASTRollbackStatement node, Object data) {
                return visit((PLSQLNode) node, data);
        }


        @Override
        public Object visit(ASTSavepointStatement node, Object data) {
                return visit((PLSQLNode) node, data);
        }


        @Override
        public Object visit(ASTSetTransactionStatement node, Object data) {
                return visit((PLSQLNode) node, data);
        }


        @Override
        public Object visit(ASTLockTableStatement node, Object data) {
                return visit((PLSQLNode) node, data);
        }

	@Override
	public Object visit(ASTSqlSelectStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlDeleteStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlDmlTableExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlMergeStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlErrorLoggingClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlInsertStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlInsertIntoClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlValuesClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlUpdateStatement node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlColumnAssignment node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlReturningClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlForUpdateClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSubquery node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlQueryBlock node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSelectClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlTableClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlWithClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlPLSQLDeclarations node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSubqueryRefactoringClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSearchClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlCycleClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSelectList node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlTableReference node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlFlashbackQueryClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlQueryTableExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlPivotClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlReferencedColumns node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlPivotForClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlPivotInClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlUnpivotClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlUnpivotInClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSampleClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlPartitionExtensionClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSubqueryRestrictionClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlTableCollectionExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlCollectionExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlContainersClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlJoinClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlQueryPartitionClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlColumnExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlOuterJoinType node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlCrossOuterApplyClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlWhereClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlHierarchicalQueryClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlGroupByClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRollupCubeClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlGroupingSetsClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlGroupingExpressionList node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlExpressionList node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlModelClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlCellReferenceOptions node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlReturnRowsClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlReferenceModel node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlMainModel node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlModelColumnClauses node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlModelColumn node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlModelRulesClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlModelIterateClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlCellAssignment node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlCondition node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlSingleColumnForLoop node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlMultiColumnForLoop node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlOrderByClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlColumnOrderingClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowLimitingClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternPartitionBy node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternOrderBy node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternMeasures node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternRowsPerMatch node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternSkipTo node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPattern node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternTerm node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternFactor node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternPrimary node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternPermute node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternQuantifier node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternSubsetClause node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternSubsetItem node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternDefinitionList node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternDefinition node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternRecFunction node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternNavigationFunction node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlRowPatternAggregateFunction node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlAggregateFunction node, Object data) {
		return visit((PLSQLNode) node, data);
	}

	@Override
	public Object visit(ASTSqlExpression node, Object data) {
		return visit((PLSQLNode) node, data);
	}

}
