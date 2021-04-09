package optimization.ir;

import ir.IRBlock;
import ir.IRFunction;
import ir.instruction.*;
import ir.operand.Register;
import misc.pass.IRFunctionPass;

import java.util.HashMap;

public class CommonSubexpElimination extends IRFunctionPass {

    public enum Type {LOCAL, GLOBAL}

    protected static class LookUpTable {
        private final HashMap<String, Register> table = new HashMap<>();

        public static boolean okToHash(IRInst inst) {
            return inst instanceof Binary || inst instanceof Compare || inst instanceof GetElementPtr;
        }

        public static String hash(IRInst inst) {
            if (inst instanceof Binary) {
                return "!" + ((Binary) inst).operand1.tell() + ((Binary) inst).inst + ((Binary) inst).operand2.tell();
            } else if (inst instanceof Compare) {
                return "@" + ((Compare) inst).operand1.tell() + ((Compare) inst).type + ((Compare) inst).operand2.tell();
            } else if (inst instanceof GetElementPtr) {
                return "#" + ((GetElementPtr) inst).base.tell() + "#" + ((GetElementPtr) inst).indexing.tell() +
                        ((((GetElementPtr) inst).offset != null) ? ((GetElementPtr) inst).offset.tell() : "!");
            } else throw new IllegalStateException();
        }

        public boolean tryRegister(IRInst inst) {
            if (okToHash(inst)) {
                var hash = hash(inst);
                if (table.containsKey(hash)) return false;
                table.put(hash, ((IRDestedInst) inst).dest);
            }
            return true;
        }

        public Register getReg(IRInst inst) {
            return table.get(hash(inst));
        }
    }

    private final Type type;

    public CommonSubexpElimination(IRFunction f, Type type) {
        super(f);
        this.type = type;
    }

    @Override
    protected void run() {
       if(type== CommonSubexpElimination.Type.LOCAL)
           local();
       else global();
    }

    private void local(){
        irFunc.blocks.forEach(block -> {
            LookUpTable table = new LookUpTable();
            for (var iter = block.insts.listIterator(); iter.hasNext(); ) {
                var inst = iter.next();
                if (!table.tryRegister(inst)) {
                    iter.set(new Assign(((IRDestedInst) inst).dest, table.getReg(inst)));
                }
            }
        });
    }

    private void global(){
        var mapping=new HashMap<IRBlock,LookUpTable>();
        // todo
    }
}
