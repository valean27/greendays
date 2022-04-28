package com.greendays.greendays.service.page;

import com.greendays.greendays.model.dto.GarbageDto;
import com.greendays.greendays.model.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class GarbageComparator {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<GarbageDto>> map = new HashMap<>();

    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(GarbageDto::getGarbageName));
        map.put(new Key("name", Direction.desc), Comparator.comparing(GarbageDto::getGarbageName)
                .reversed());

        map.put(new Key("id", Direction.asc), Comparator.comparing(GarbageDto::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(GarbageDto::getId)
                .reversed());

        map.put(new Key("code", Direction.asc), Comparator.comparing(GarbageDto::getGarbageCode));
        map.put(new Key("code", Direction.desc), Comparator.comparing(GarbageDto::getGarbageCode)
                .reversed());
    }

    public static Comparator<GarbageDto> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private GarbageComparator() {
    }
}