package codegen;

import ast.*;
import compnent.basic.ClassType;
import compnent.basic.TypeConst;
import exception.UnimplementedError;
import ir.BasicBlock;
import ir.Function;
import ir.instruction.Binary;
import ir.instruction.Compare;
import ir.instruction.IRInst;
import ir.operand.IROperand;
import ir.operand.Register;
import ir.typesystem.BoolType;
import ir.typesystem.IntegerType;

public class IRBuilder implements ASTVisitor {
    private Function currentFunction;
    private BasicBlock currentBlock;

    @Override
    public Object visit(RootNode node) {
        node.classes.forEach(cls -> cls.accept(this));
        node.functions.forEach(func -> func.accept(this));
        // todo
        return null;
    }


    @Override
    public IROperand visit(BinaryExprNode node) {
        if(node.lhs.type instanceof ClassType){
            // implicit function
            // todo
        }else{
            var binst=Binary.getIntBinOpEnum(node.lexerSign);
            if(binst!=null){
                // binary instruction
                var inst=new Binary(binst,
                        (IROperand) node.lhs.accept(this),(IROperand) node.rhs.accept(this),
                        new Register(new IntegerType()));
                currentBlock.appendInst(inst);
                return inst.dest;
            }else {
                // compare instruction
                var cinst= Compare.getCmpOpEnum(node.lexerSign);
                assert cinst!=null;
                var inst=new Compare(cinst,(IROperand) node.lhs.accept(this),(IROperand) node.rhs.accept(this),
                        new Register(new BoolType()));
                currentBlock.appendInst(inst);
                return inst.dest;
            }
        }
    }

    @Override
    public IROperand visit(UnaryExprNode node) {
        
    }
}







