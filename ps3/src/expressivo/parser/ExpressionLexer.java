// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionLexer extends Lexer {
  static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
    new PredictionContextCache();
  public static final int
    NUMBER=1, VARIABLE=2, PLUS=3, TIMES=4, LPAREN=5, RPAREN=6, SPACES=7;
  public static String[] modeNames = {
    "DEFAULT_MODE"
  };

  public static final String[] ruleNames = {
    "NUMBER", "VARIABLE", "PLUS", "TIMES", "LPAREN", "RPAREN", "SPACES"
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


  public ExpressionLexer(CharStream input) {
    super(input);
    _interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
  }

  @Override
  public String getGrammarFileName() { return "Expression.g4"; }

  @Override
  public String[] getRuleNames() { return ruleNames; }

  @Override
  public String getSerializedATN() { return _serializedATN; }

  @Override
  public String[] getModeNames() { return modeNames; }

  @Override
  public ATN getATN() { return _ATN; }

  public static final String _serializedATN =
    "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\t\62\b\1\4\2\t"+
      "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\6\2\23\n\2"+
      "\r\2\16\2\24\3\2\3\2\6\2\31\n\2\r\2\16\2\32\5\2\35\n\2\3\3\6\3 \n"+
      "\3\r\3\16\3!\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\6\b-\n\b\r\b\16\b"+
      ".\3\b\3\b\2\2\t\3\3\5\4\7\5\t\6\13\7\r\b\17\t\3\2\5\3\2\62;\4\2C\\"+
      "c|\3\2\"\"\66\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
      "\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\3\22\3\2\2\2\5\37\3\2\2\2\7#\3\2"+
      "\2\2\t%\3\2\2\2\13\'\3\2\2\2\r)\3\2\2\2\17,\3\2\2\2\21\23\t\2\2\2"+
      "\22\21\3\2\2\2\23\24\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\34\3\2"+
      "\2\2\26\30\7\60\2\2\27\31\t\2\2\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30"+
      "\3\2\2\2\32\33\3\2\2\2\33\35\3\2\2\2\34\26\3\2\2\2\34\35\3\2\2\2\35"+
      "\4\3\2\2\2\36 \t\3\2\2\37\36\3\2\2\2 !\3\2\2\2!\37\3\2\2\2!\"\3\2"+
      "\2\2\"\6\3\2\2\2#$\7-\2\2$\b\3\2\2\2%&\7,\2\2&\n\3\2\2\2\'(\7*\2\2"+
      "(\f\3\2\2\2)*\7+\2\2*\16\3\2\2\2+-\t\4\2\2,+\3\2\2\2-.\3\2\2\2.,\3"+
      "\2\2\2./\3\2\2\2/\60\3\2\2\2\60\61\b\b\2\2\61\20\3\2\2\2\b\2\24\32"+
      "\34!.\3\b\2\2";
  public static final ATN _ATN =
    new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}