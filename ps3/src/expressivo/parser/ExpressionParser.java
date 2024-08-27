// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionParser extends Parser {
  static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
    new PredictionContextCache();
  public static final int
    NUMBER=1, VARIABLE=2, PLUS=3, TIMES=4, LPAREN=5, RPAREN=6, SPACES=7;
  public static final int
    RULE_expr = 0, RULE_term = 1, RULE_factor = 2;
  public static final String[] ruleNames = {
    "expr", "term", "factor"
  };

  private static final String[] _LITERAL_NAMES = {
    null, null, null, "'+'", "'*'", "'('", "')'"
  };
  private static final String[] _SYMBOLIC_NAMES = {
    null, "NUMBER", "VARIABLE", "PLUS", "TIMES", "LPAREN", "RPAREN", "SPACES"
  };
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated
  public static final String[] tokenNames;
  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override

  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() { return "Expression.g4"; }

  @Override
  public String[] getRuleNames() { return ruleNames; }

  @Override
  public String getSerializedATN() { return _serializedATN; }

  @Override
  public ATN getATN() { return _ATN; }


      // This method makes the lexer or parser stop running if it encounters
      // invalid input and throw a ParseCancellationException.
      public void reportErrorsAsExceptions() {
          // To prevent any reports to standard error, add this line:
          //removeErrorListeners();
          
          addErrorListener(new BaseErrorListener() {
              public void syntaxError(Recognizer<?, ?> recognizer,
                                      Object offendingSymbol,
                                      int line, int charPositionInLine,
                                      String msg, RecognitionException e) {
                  throw new ParseCancellationException(msg, e);
              }
          });
      }

  public ExpressionParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
  }
  public static class ExprContext extends ParserRuleContext {
    public List<TermContext> term() {
      return getRuleContexts(TermContext.class);
    }
    public TermContext term(int i) {
      return getRuleContext(TermContext.class,i);
    }
    public List<TerminalNode> PLUS() { return getTokens(ExpressionParser.PLUS); }
    public TerminalNode PLUS(int i) {
      return getToken(ExpressionParser.PLUS, i);
    }
    public ExprContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_expr; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpr(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpr(this);
    }
  }

  public final ExprContext expr() throws RecognitionException {
    ExprContext _localctx = new ExprContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_expr);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(6);
      term();
      setState(11);
      _errHandler.sync(this);
      _la = _input.LA(1);
      while (_la==PLUS) {
        {
        {
        setState(7);
        match(PLUS);
        setState(8);
        term();
        }
        }
        setState(13);
        _errHandler.sync(this);
        _la = _input.LA(1);
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class TermContext extends ParserRuleContext {
    public List<FactorContext> factor() {
      return getRuleContexts(FactorContext.class);
    }
    public FactorContext factor(int i) {
      return getRuleContext(FactorContext.class,i);
    }
    public List<TerminalNode> TIMES() { return getTokens(ExpressionParser.TIMES); }
    public TerminalNode TIMES(int i) {
      return getToken(ExpressionParser.TIMES, i);
    }
    public TermContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_term; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterTerm(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitTerm(this);
    }
  }

  public final TermContext term() throws RecognitionException {
    TermContext _localctx = new TermContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_term);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(14);
      factor();
      setState(19);
      _errHandler.sync(this);
      _la = _input.LA(1);
      while (_la==TIMES) {
        {
        {
        setState(15);
        match(TIMES);
        setState(16);
        factor();
        }
        }
        setState(21);
        _errHandler.sync(this);
        _la = _input.LA(1);
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class FactorContext extends ParserRuleContext {
    public TerminalNode NUMBER() { return getToken(ExpressionParser.NUMBER, 0); }
    public TerminalNode VARIABLE() { return getToken(ExpressionParser.VARIABLE, 0); }
    public TerminalNode LPAREN() { return getToken(ExpressionParser.LPAREN, 0); }
    public ExprContext expr() {
      return getRuleContext(ExprContext.class,0);
    }
    public TerminalNode RPAREN() { return getToken(ExpressionParser.RPAREN, 0); }
    public FactorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_factor; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterFactor(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitFactor(this);
    }
  }

  public final FactorContext factor() throws RecognitionException {
    FactorContext _localctx = new FactorContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_factor);
    try {
      setState(28);
      switch (_input.LA(1)) {
      case NUMBER:
        enterOuterAlt(_localctx, 1);
        {
        setState(22);
        match(NUMBER);
        }
        break;
      case VARIABLE:
        enterOuterAlt(_localctx, 2);
        {
        setState(23);
        match(VARIABLE);
        }
        break;
      case LPAREN:
        enterOuterAlt(_localctx, 3);
        {
        setState(24);
        match(LPAREN);
        setState(25);
        expr();
        setState(26);
        match(RPAREN);
        }
        break;
      default:
        throw new NoViableAltException(this);
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static final String _serializedATN =
    "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t!\4\2\t\2\4\3"+
      "\t\3\4\4\t\4\3\2\3\2\3\2\7\2\f\n\2\f\2\16\2\17\13\2\3\3\3\3\3\3\7"+
      "\3\24\n\3\f\3\16\3\27\13\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4\37\n\4\3\4"+
      "\2\2\5\2\4\6\2\2!\2\b\3\2\2\2\4\20\3\2\2\2\6\36\3\2\2\2\b\r\5\4\3"+
      "\2\t\n\7\5\2\2\n\f\5\4\3\2\13\t\3\2\2\2\f\17\3\2\2\2\r\13\3\2\2\2"+
      "\r\16\3\2\2\2\16\3\3\2\2\2\17\r\3\2\2\2\20\25\5\6\4\2\21\22\7\6\2"+
      "\2\22\24\5\6\4\2\23\21\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3"+
      "\2\2\2\26\5\3\2\2\2\27\25\3\2\2\2\30\37\7\3\2\2\31\37\7\4\2\2\32\33"+
      "\7\7\2\2\33\34\5\2\2\2\34\35\7\b\2\2\35\37\3\2\2\2\36\30\3\2\2\2\36"+
      "\31\3\2\2\2\36\32\3\2\2\2\37\7\3\2\2\2\5\r\25\36";
  public static final ATN _ATN =
    new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}