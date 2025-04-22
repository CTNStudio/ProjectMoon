package ctn.project_moon.common.item.weapon;

import net.minecraft.util.RandomSource;

public interface RandomDamageProcessor {
    int getMaxDamage();
    int getMinDamage();

    static int getDamage(RandomSource randomSource, int max, int min) {
        if (max == min) {
            return max;
        }
        return randomSource.nextInt(min, max + 1);
    }

    default int getDamage(RandomSource randomSource) {
        return getDamage(randomSource, getMaxDamage(), getMinDamage());
    }


}
