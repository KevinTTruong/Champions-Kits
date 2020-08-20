package mineplex.core.common;

import net.minecraft.server.v1_16_R1.Entity;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.World;

public class DummyEntity extends Entity
{
	public DummyEntity(EntityTypes entity, World world)
	{
		super(entity, world);
	}

	@Override
	public Packet<?> O() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initDatawatcher() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void loadData(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void saveData(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}
}
