package mineplex.core.projectile;


import java.io.Serializable;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public interface IThrown extends Serializable
{
	public void Collide(LivingEntity target, Block block, ProjectileUser data);
	public void Idle(ProjectileUser data);
	public void Expire(ProjectileUser data);
}
