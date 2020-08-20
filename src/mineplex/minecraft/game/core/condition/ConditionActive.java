package mineplex.minecraft.game.core.condition;

import java.io.Serializable;

public class ConditionActive implements Serializable
{	
	private Condition _condition;

	public ConditionActive(Condition condition)
	{
		SetCondition(condition);
	}

	public Condition GetCondition()
	{
		return _condition;
	}

	public void SetCondition(Condition newCon) 
	{
		_condition = newCon;
		newCon.Apply();
	}
}
