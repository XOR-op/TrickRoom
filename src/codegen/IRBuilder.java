package codegen;

import ast.*;
import compnent.basic.ClassType;
import exception.UnimplementedError;
import ir.BasicBlock;
import ir.Function;
import ir.instruction.Binary;
import ir.instruction.Compare;
import ir.operand.BoolConstant;
import ir.operand.IROperand;
import ir.operand.IntConstant;
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
        var target=(IROperand) node.expr.accept(this);
        Binary inst;
        if(node.isPrefix){
            // !/~/-/+
            switch (node.lexerSign){
                case "+"->{
                    // no instruction
                    return target;
                }
                case "-"->{
                    inst=new Binary(Binary.BinInstEnum.sub,new IntConstant(0),target,new Register(new IntegerType()));
                }
                case "!"->{
                    inst=new Binary(Binary.BinInstEnum.xor,new BoolConstant(true),target,new Register(new BoolType()));
                }
                case "~"->{
                    inst=new Binary(Binary.BinInstEnum.xor,new IntConstant(Integer.MAX_VALUE),target,new Register(new IntegerType()));
                }
                default -> {
                    throw new UnimplementedError();
                }
            }
            currentBlock.appendInst(inst);
            return inst.dest;
        }else {
            // must be suffix ++/--
            // record original value
            var inst1=new Binary(Binary.BinInstEnum.add,target,new IntConstant(0), new Register(new IntegerType()));
            // incremental operation
            var inst2=new Binary(node.lexerSign.equals("++")? Binary.BinInstEnum.add: Binary.BinInstEnum.sub,target,new IntConstant(1),target);
            currentBlock.appendInst(inst1);
            currentBlock.appendInst(inst2);
            return inst1.dest;
        }
    }

    @Override
    public IROperand visit(PrefixLeftValueNode node) {
        var target=(IROperand) node.expr.accept(this);
        var inst=new Binary(node.sign.equals("+")? Binary.BinInstEnum.add: Binary.BinInstEnum.sub,target,new IntConstant(1),target);
        currentBlock.appendInst(inst);
        return inst.dest;
    }

    @Override
    public IROperand visit(FuncCallNode node) {
        if(node.func.isGlobal()){

        }else {
            // method
        }
    }

    @Override
    public Object visit(MemberNode node) {
        // todo
        /*
        1) get pointer
        2) load
        3) return? # left value?
         */
    }


}







