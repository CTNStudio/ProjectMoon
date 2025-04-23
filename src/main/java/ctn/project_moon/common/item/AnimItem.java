package ctn.project_moon.common.item;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.PlayerAnimTool.getAnimationStack;

public interface AnimItem {
    /**
     * 创建动画 可能导致并发修改注意不要连续执行
     */
    static void createAnim(Level level, Player player, String animNameId, int layer) {
        if (level.isClientSide()) {
            AnimationStack animationStack = getAnimationStack(player);
            if (animationStack == null) return;
            ModifierLayer<IAnimation> playerAnimation = new ModifierLayer<>();
            playerAnimation.setAnimation(PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath(MOD_ID, animNameId)).playAnimation());
            animationStack.addAnimLayer(layer, playerAnimation);
        }
    }
    default void executeLeftKeyEmpty(Player player) {}
    default void executeLeftKeyBlock(Player player) {}
    default void executeLeftKeyEntity(Player player) {}
    default void executeRightKeyEmpty(Player player) {}
    default void executeRightKeyBlock(Player player) {}
    default void executeRightKeyEntity(Player player) {}
    default boolean isLeftKeyEmpty() {return false;}
    default boolean isLeftKeyBlock() {return false;}
    default boolean isLeftKeyEntity() {return false;}
    default boolean isRightKeyEmpty() {return false;}
    default boolean isRightKeyBlock() {return false;}
    default boolean isRightKeyEntity() {return false;}
}
