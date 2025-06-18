package ctn.project_moon.capability;

import net.minecraft.util.RandomSource;

/** 提供给PM伤害系统的随机伤害能力接口 */
public interface IRandomDamage {
	static int countDamageValue(RandomSource randomSource, int maxDamage, int minDamage) {
		if (maxDamage < minDamage) {
			int i = maxDamage;
			maxDamage = minDamage;
			minDamage = i;
		}
		if (maxDamage == minDamage) {
			return maxDamage;
		}
		return randomSource.nextInt(minDamage, maxDamage + 1);
	}
	
	int getMaxDamage();
	
	int getMinDamage();
	
	default int getDamageValue(RandomSource randomSource) {
		return countDamageValue(randomSource, getMaxDamage(), getMinDamage());
	}
}
