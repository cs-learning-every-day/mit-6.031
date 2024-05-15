import assert from 'assert';

import { IntegerExpression, Constant, Plus, Times } from './IntegerExpression';
import { Parser, ParseTree, compile, visualizeAsUrl } from 'parserlib';

// the grammar
const grammar: string = `
@skip whitespace {
    expr ::= sum;
    sum ::= primary ('+' primary)*;
    primary ::= constant | '(' sum ')';
}
constant ::= [0-9]+;
whitespace ::= [ \\t\\r\\n]+;  // <-- note that backslashes must be escaped
`;

// the nonterminals of the grammar
export enum IntegerGrammar {
    Expr, Sum, Primary, Constant, Whitespace
}

// compile the grammar into a parser
export const parser: Parser<IntegerGrammar> = compile(grammar, IntegerGrammar, IntegerGrammar.Expr);

/**
 * Parse a string into an expression.
 * 
 * @param input string to parse
 * @returns IntegerExpression parsed from the string
 * @throws ParseError if the string doesn't match the IntegerExpression grammar
 */
export function parse(input: string): IntegerExpression {
    // parse the example into a parse tree
    const parseTree: ParseTree<IntegerGrammar> = parser.parse(input);
    console.log("parse tree:\n" + parseTree);

    // display the parse tree in a web browser, for debugging only
    console.log(visualizeAsUrl(parseTree, IntegerGrammar));

    // make an AST from the parse tree
    const expression: IntegerExpression = makeAbstractSyntaxTree(parseTree);
    console.log("abstract syntax tree:\n" + expression);
    
    return expression;
}

/**
 * Convert a parse tree into an abstract syntax tree.
 * 
 * @param parseTree constructed according to the IntegerExpression grammar
 * @returns abstract syntax tree corresponding to parseTree
 */
function makeAbstractSyntaxTree(parseTree: ParseTree<IntegerGrammar>): IntegerExpression {
    if (parseTree.name === IntegerGrammar.Expr) {
        // expr ::= sum;
        const child: ParseTree<IntegerGrammar>|undefined = parseTree.children[0];
        assert(child, 'Expr node missing expected child');
        return makeAbstractSyntaxTree(child);
        
    } else if (parseTree.name === IntegerGrammar.Sum) {
        // sum ::= primary ('+' primary)*;
        const children: Array<ParseTree<IntegerGrammar>> = parseTree.childrenByName(IntegerGrammar.Primary);
        assert(children.length, 'Sum node missing expected children');
        const subexprs: Array<IntegerExpression> = children.map(makeAbstractSyntaxTree);
        const expression: IntegerExpression = subexprs.reduce((result, subexpr) => new Plus(result, subexpr));
        return expression;
        
    } else if (parseTree.name === IntegerGrammar.Primary) {
        // primary ::= constant | '(' sum ')';
        const child: ParseTree<IntegerGrammar>|undefined = parseTree.children[0];
        assert(child, 'Primary node missing expected child');
        // check which alternative (constant or sum) was actually matched
        switch (child.name) {
        case IntegerGrammar.Constant:
            return makeAbstractSyntaxTree(child);
        case IntegerGrammar.Sum:
            return makeAbstractSyntaxTree(child); // for this parser, we do the same thing either way
        default:
            assert.fail(`Primary node unexpected child ${IntegerGrammar[child.name]}`);
        }
        
    } else if (parseTree.name === IntegerGrammar.Constant) {
        // constant ::= [0-9]+;
        const n: number = parseInt(parseTree.text);
        return new Constant(n);
        
    } else {
        assert.fail(`cannot make AST for ${IntegerGrammar[parseTree.name]} node`);
    }
}

function main() {
    const input = "54+(2+ 89)";
    // const input = "TODO"; // different parse tree, same AST
    // const input = "TODO"; // different parse tree, different AST, same AST leaf nodes (54, 2, 89 in that order)
    // const input = "TODO"; // parse tree with fewest possible "primary" nodes, same AST leaf nodes in order

    console.log(input);
    const expression: IntegerExpression = parse(input);
    const value: number = expression.value();
    console.log("value " + value);
}

if (require.main === module) {
    main();
}
