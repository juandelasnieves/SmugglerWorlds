package com.pineapplemelon.smugglerworlds.utils;

import com.pineapplemelon.smugglerworlds.enums.DisparoType;
import com.pineapplemelon.smugglerworlds.enums.EnemyType;

import java.util.Random;


/**
 * Created by juandelasnieves on 9/1/16.
 */
public class RandomUtils {

    public static EnemyType getRandomEnemyType() {
        RandomEnum<EnemyType> randomEnum = new RandomEnum<EnemyType>(EnemyType.class);
        return randomEnum.random();
    }

    public static DisparoType getRandomDisparoType() {
        RandomEnum<DisparoType> randomEnum = new RandomEnum<DisparoType>(DisparoType.class);
        return randomEnum.random();
    }

    /**
     * @see [Stack Overflow](http://stackoverflow.com/a/1973018)
     * @param <E>
     */

    private static class RandomEnum<E extends Enum> {

        private static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }

}
