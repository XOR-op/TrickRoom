package misc.tools;

import java.util.HashMap;

public class DisjointSet<T> {
    private final HashMap<T, T> unionFind = new HashMap<>();

    public T query(T child) {
        if (!unionFind.containsKey(child))
            return child;
        var root = query(unionFind.get(child));
        unionFind.put(child, root);
        return root;
    }

    public void put(T child, T parent) {
        unionFind.put(child, parent);
    }
}

