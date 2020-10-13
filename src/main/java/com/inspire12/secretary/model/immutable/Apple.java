package com.inspire12.secretary.model.immutable;

import org.immutables.value.Value;

import java.util.List;
import java.util.Set;

@Value.Immutable(builder = true)
//@JsonSerialize(as = ImmutableApple.class)
//@JsonDeserialize(as = ImmutableApple.class)
public interface Apple {
    public  int foo();
    public  String bar();
    public  List<Integer> buz();
    public  Set<Long> crux();
//    class Builder extends ImmutableApple.Builder {}
}
