package com.syric.shores_between.util;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.util.RandomSource;

import java.util.LinkedHashMap;

public class WeightedTable<T> {

    private final LinkedHashMap<T, Integer> table;
    private int total_weight;

    public WeightedTable() {
        table = new LinkedHashMap<>();
        total_weight = 0;
    }

    public WeightedTable<T> add(T t, int i) {
        table.put(t, i);
        this.total_weight += i;
        return this;
    }

    public T getRandom(RandomSource randomSource) {
        if (table.isEmpty() || total_weight <= 0) {
            ShoresBetween.LOGGER.error("WeightedTable was empty");
            return null;
        }
        int k = randomSource.nextInt(total_weight);
        for (T t : table.keySet()) {
            if (k < table.get(t)) {
                return t;
            } else {
                k -= table.get(t);
            }
        }
        ShoresBetween.LOGGER.error("WeightedTable failed to find entry; returning first entry as default");
        return table.firstEntry().getKey();
    }

}
