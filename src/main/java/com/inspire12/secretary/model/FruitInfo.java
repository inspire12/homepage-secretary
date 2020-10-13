package com.inspire12.secretary.model;

import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ToString
public class FruitInfo {
    private final List<String> distinctFruits;
    private final Map<String, Long> countFruits;

    public FruitInfo(List<String> distinctFruits, Map<String, Long> countFruits) {
        this.distinctFruits = distinctFruits;
        this.countFruits = countFruits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FruitInfo fruitInfo = (FruitInfo) o;

        if (!Objects.equals(distinctFruits, fruitInfo.distinctFruits))
            return false;
        return Objects.equals(countFruits, fruitInfo.countFruits);
    }

    @Override
    public int hashCode() {
        int result = distinctFruits != null ? distinctFruits.hashCode() : 0;
        result = 31 * result + (countFruits != null ? countFruits.hashCode() : 0);
        return result;
    }
}
