package assembly.operand;

import java.util.ArrayList;
import java.util.HashMap;

public class PhysicalRegister extends RVRegister {
    private int numbering;
    private PhysicalRegister(int numbering, String name) {
        this.numbering = numbering;
        this.name = name;
        color = this;
    }


    @Override
    public String tell() {
        return name;
    }

    public static PhysicalRegister Zero() {
        return nameMapping.get("zero");
    }

    public static PhysicalRegister get(String name) {
        assert nameMapping.get(name) != null;
        return nameMapping.get(name);
    }

    public static PhysicalRegister get(int numbering) {
        assert numbering >= 0 && numbering <= 31;
        return numberingMapping.get(numbering);
    }

    private static HashMap<String, PhysicalRegister> nameMapping = new HashMap<>();
    private static ArrayList<PhysicalRegister> numberingMapping = new ArrayList<>();

    private static int count = 0;

    private static void put(String name) {
        var reg = new PhysicalRegister(count, name);
        nameMapping.put(name, reg);
        numberingMapping.add(reg);
        count++;
    }

    static {
        put("zero");
        put("ra");
        put("sp");
        put("gp");
        put("tp");
        for (int i = 0; i <= 2; ++i)
            put("t" + i);
        put("s0");
        put("s1");
        for (int i = 0; i <= 7; ++i)
            put("a" + i);
        for (int i = 2; i <= 11; ++i)
            put("s" + i);
        for (int i = 3; i <= 6; ++i)
            put("t" + i);
        assert count == 32;
    }

}
