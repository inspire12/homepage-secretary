package com.inspire12.secretary.private_study.design_pattern.proxy;

import java.io.Serializable;

public class PersonProxy implements Serializable {
    private int id;

    public PersonProxy(int id) {
        this.id = id;
    }

    public Object readResolve() {
        return new Person();
    }
}
