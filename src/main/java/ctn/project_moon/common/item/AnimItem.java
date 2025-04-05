package ctn.project_moon.common.item;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import static ctn.project_moon.PmMain.MOD_ID;

public interface AnimItem {
    default void executeLeftKeyEmpty() {

    }

    default void executeLeftKeyBlock() {

    }

    default void executeLeftKeyEntity() {

    }

    default void executeRightKeyEmpty() {

    }

    default void executeRightKeyBlock() {

    }

    default void executeRightKeyEntity() {

    }

    default boolean isLeftKeyEmpty() {
        return false;
    }

    default boolean isLeftKeyBlock() {
        return false;
    }

    default boolean isLeftKeyEntity() {
        return false;
    }

    default boolean isRightKeyEmpty() {
        return false;
    }

    default boolean isRightKeyBlock() {
        return false;
    }

    default boolean isRightKeyEntity() {
        return false;
    }

    static void createAnim(Player player, String animNameId, int layer) {
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            ModifierLayer<IAnimation> playerAnimation = new ModifierLayer<>();
            playerAnimation.setAnimation(PlayerAnimationRegistry
                    .getAnimation(ResourceLocation.fromNamespaceAndPath(MOD_ID, animNameId))
                    .playAnimation());
            animationStack.addAnimLayer(layer, playerAnimation);
        }
    }
}
