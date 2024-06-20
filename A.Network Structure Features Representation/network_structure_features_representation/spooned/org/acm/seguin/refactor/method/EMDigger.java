package org.acm.seguin.refactor.method;
class EMDigger {
    org.acm.seguin.parser.Node dig(org.acm.seguin.parser.ast.ASTMethodDeclaration start) {
        org.acm.seguin.parser.ast.ASTBlock block = ((org.acm.seguin.parser.ast.ASTBlock) (start.jjtGetChild(start.jjtGetNumChildren() - 1)));
        org.acm.seguin.parser.Node current = block.jjtGetChild(0);
        while (current.jjtGetNumChildren() == 1) {
            current = current.jjtGetChild(0);
        } 
        return current;
    }

    org.acm.seguin.parser.Node last(org.acm.seguin.parser.ast.ASTMethodDeclaration start) {
        org.acm.seguin.parser.ast.ASTBlock block = ((org.acm.seguin.parser.ast.ASTBlock) (start.jjtGetChild(start.jjtGetNumChildren() - 1)));
        return block;
    }
}