package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.item.RequestItems;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.common.item.weapon.Weapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;

public class LoveHateItem extends SpecialEgoWeapon implements RequestItems {
    public LoveHateItem(Weapon.Builder builder) {
        super(builder.build().component(ITEM_COLOR_USAGE_REQ, ItemColorUsageReq.empty()
                .setValue(ItemColorUsageReq.Type.FORTITUDE, ItemColorUsageReq.Rating.III)
                .setValue(ItemColorUsageReq.Type.JUSTICE, ItemColorUsageReq.Rating.III)
                .setValue(ItemColorUsageReq.Type.COMPOSITE_RATING, ItemColorUsageReq.Rating.IV)
        ), builder);
    }

    /**
     * 使用物品时触发

     */
    @Override
    public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {
        
    }

    /**
     * 攻击时触发

     */
    @Override
    public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 在手上时触发

     */
    @Override
    public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 物品在背包时里触发

     */
    @Override
    public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 在装备里时触发，如盔甲，饰品

     */
    @Override
    public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }
}
