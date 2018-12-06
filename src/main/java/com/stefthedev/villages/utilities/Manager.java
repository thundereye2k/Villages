package com.stefthedev.villages.utilities;

import java.util.*;

public abstract class Manager<T> {

    private final Set<T> ts;
    private final String name;

    public Manager(String name) {
        this.name = name;
        this.ts = new HashSet<>();
    }

    public void add(T t) {
        ts.add(t);
    }

    public void remove(T t) {
        ts.remove(t);
    }

    protected void clear() {
        ts.clear();
    }

    public Set<T> getSet() {
        return Collections.unmodifiableSet(ts);
    }

    public T getObject(String name) {
        for(T t : ts) {
            if(t.toString().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract void register();
    public abstract void unregister();
}
