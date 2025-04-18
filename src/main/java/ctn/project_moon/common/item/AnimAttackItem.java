package ctn.project_moon.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface AnimAttackItem extends AnimItem{
    /** 玩家在多久后可以移动 */
    int freMovementTick();
    /** 多久后执行效果 */
    int triggerTick();
    /** 效果 */
    void trigger(Level level, LivingEntity livingEntity, ItemStack stack);
}
