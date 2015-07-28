/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.cpd;

import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.LanguageVersionHandler;
import net.sourceforge.pmd.lang.TokenManager;
import net.sourceforge.pmd.lang.ast.TokenMgrError;
import net.sourceforge.pmd.lang.plsql.PLSQLLanguageModule;
import net.sourceforge.pmd.lang.plsql.ast.PLSQLParserConstants;
import net.sourceforge.pmd.lang.plsql.ast.Token;
import net.sourceforge.pmd.util.IOUtil;

import org.apache.commons.io.IOUtils;



public class PLSQLTokenizer implements Tokenizer{
    private final static Logger LOGGER = Logger.getLogger(PLSQLTokenizer.class.getName());

    public static final String IGNORE_COMMENTS = "ignore_comments";
    public static final String IGNORE_IDENTIFIERS = "ignore_identifiers";
    public static final String IGNORE_LITERALS = "ignore_literals";

    private boolean ignoreComments;
    private boolean ignoreIdentifiers;
    private boolean ignoreLiterals;

    public void setProperties(Properties properties) {
		/* The Tokenizer is derived from PLDoc, in which comments are very important
		 * When looking for duplication, we are probably not interested in comment variation,
		 * so we shall default ignoreComments to true
		*/
        ignoreComments = Boolean.parseBoolean(properties.getProperty(IGNORE_COMMENTS, "true"));
        ignoreIdentifiers = Boolean.parseBoolean(properties.getProperty(IGNORE_IDENTIFIERS, "false"));
        ignoreLiterals = Boolean.parseBoolean(properties.getProperty(IGNORE_LITERALS, "false"));
    }

    public void setIgnoreComments(boolean ignore) {
	this.ignoreComments = ignore;
    }

    public void setIgnoreLiterals(boolean ignore) {
	this.ignoreLiterals = ignore;
    }

    public void setIgnoreIdentifiers(boolean ignore) {
	this.ignoreIdentifiers = ignore;
    }

        /**
         * Read Reader from SourceCode and output an ordered tree of PLSQL tokens.
         * @param sourceCode PLSQL source in file, string or database (any suitable object that can return
         * a Reader).
         * @param tokenEntries  Derived based on PLSQL Abstract Syntax Tree (derived from PLDOc parser.) 
         */
	public void tokenize (SourceCode sourceCode, Tokens tokenEntries )
	{
        long encounteredTokens = 0;
        long addedTokens = 0;

		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "PLSQLTokenizer: ignoreComments=={0}", ignoreComments);
			LOGGER.log(Level.FINE, "PLSQLTokenizer: ignoreIdentifiers=={0}", ignoreIdentifiers);
			LOGGER.log(Level.FINE, "PLSQLTokenizer: ignoreLiterals=={0}", ignoreLiterals);
		}

		String fileName = sourceCode.getFileName();
	        StringBuilder buffer = sourceCode.getCodeBuffer();

		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.log(Level.FINE, "PLSQLTokenizer: fileName={0}", fileName);
			LOGGER.log(Level.FINE, "PLSQLTokenizer: sourceCode.length= {0}", buffer.length());
		}

		Reader reader = null;
		try {
		    LanguageVersionHandler languageVersionHandler = LanguageRegistry.getLanguage(PLSQLLanguageModule.NAME)
			    .getDefaultVersion().getLanguageVersionHandler();
		    reader = new StringReader(buffer.toString());
		    reader = IOUtil.skipBOM(reader);
		    TokenManager tokenManager = languageVersionHandler.getParser(
			    languageVersionHandler.getDefaultParserOptions()).getTokenManager(sourceCode.getFileName(), reader);
		    Token currentToken = (Token) tokenManager.getNextToken();
		    while (currentToken.image.length() > 0) {

			String image = currentToken.image;

                        encounteredTokens++;
			if (ignoreComments && 
			    ( currentToken.kind == PLSQLParserConstants.SINGLE_LINE_COMMENT
			    ||currentToken.kind == PLSQLParserConstants.MULTI_LINE_COMMENT
			    ||currentToken.kind == PLSQLParserConstants.FORMAL_COMMENT
			    ||currentToken.kind == PLSQLParserConstants.COMMENT
			    ||currentToken.kind == PLSQLParserConstants.IN_MULTI_LINE_COMMENT
			    ||currentToken.kind == PLSQLParserConstants.IN_FORMAL_COMMENT
				)
				) {
				image = String.valueOf(currentToken.kind);
			}

			if (ignoreIdentifiers && 
			    currentToken.kind == PLSQLParserConstants.IDENTIFIER
				) {
				image = String.valueOf(currentToken.kind);
			}

			if (ignoreLiterals
				&& (   
					   currentToken.kind == PLSQLParserConstants.UNSIGNED_NUMERIC_LITERAL 
					|| currentToken.kind == PLSQLParserConstants.FLOAT_LITERAL
					|| currentToken.kind == PLSQLParserConstants.INTEGER_LITERAL
					|| currentToken.kind == PLSQLParserConstants.CHARACTER_LITERAL
				    || currentToken.kind == PLSQLParserConstants.STRING_LITERAL
					|| currentToken.kind == PLSQLParserConstants.QUOTED_LITERAL
					)
				) {
				image = String.valueOf(currentToken.kind);
			}

			tokenEntries.add(new TokenEntry(image, fileName, currentToken.beginLine));
                        addedTokens++;
			currentToken = (Token) tokenManager.getNextToken();

		    }
		    tokenEntries.add(TokenEntry.getEOF());
		    System.err.println("Added " + sourceCode);
		} catch (TokenMgrError err) {
		    err.printStackTrace();
		    System.err.println("Skipping " + sourceCode + " due to parse error");
		    tokenEntries.add(TokenEntry.getEOF());
		} finally {
		    IOUtils.closeQuietly(reader);
		}

		if (LOGGER.isLoggable(Level.FINE)) {
		    LOGGER.log(Level.FINE,"{0}: encountered {1}" + " tokens;" + " added {2} tokens"
                       , new Object[]{sourceCode.getFileName(), encounteredTokens, addedTokens});
		}
	}



}


