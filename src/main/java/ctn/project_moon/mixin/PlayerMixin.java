package ctn.project_moon.mixin;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.capability_provider.EntitySkillHandler;
import ctn.project_moon.mixin_extend.IModPlayerMixin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IPlayerExtension;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
@Implements(@Interface(iface = IModPlayerMixin.class, prefix = "projectMoonInt$"))
public abstract class PlayerMixin extends LivingEntity implements IPlayerExtension, IModPlayerMixin {
	@Unique
	public ISkillHandler projectMoon$skillHandler = new EntitySkillHandler();
	
	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	public @NotNull Player self() {
		return (Player) (Object) this;
	}
	
	@Unique
	public ISkillHandler projectMoonInt$getSkillHandler() {
		return projectMoon$skillHandler;
	}
	
	@Unique
	public void projectMoonInt$setSkillHandler(ISkillHandler handler) {
		projectMoon$skillHandler = handler;
	}
}
