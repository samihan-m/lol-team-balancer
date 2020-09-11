public class Player
{
	String name;
	int rank;
	//rank: XY where X is 0-9 for iron, bronze, silver, gold, plat, diamond, master, grandmaster, challenger
	//Y is 1-4 for division, 0 for X=7,8,9
	String role;
	//role: VWXYZ where each are 1-5, no repeating values, with 1 indicating highest priority for role
	//V=top,W=jg,X=mid,Y=bot,Z=sup
	
	public Player(String nameIn, String rankIn, String roleIn)
	{
		name = nameIn;
		rank = Integer.valueOf(rankIn);
		role = roleIn;
	}
	
	public int getRankWhole()
	{
		return rank;
	}
	
	public int getRankNum()
	{
		return getRankMetal()*4+(4-getRankDiv())+1;
	}
	
	public int getRankMetal()
	{
		return rank/10;
	}
	
	public int getRankDiv()
	{
		return rank%10;
	}
	
	public int getRolePrio(int role)
	{
		int out;
		switch(role)
		{
		case 0: out = getTopPrio();
			break;
		case 1: out = getJugPrio();
			break;
		case 2: out = getMidPrio();
			break;
		case 3: out = getBotPrio();
			break;
		case 4: out = getSupPrio();
			break;
		default: out = 0;
			break;
		}
		return out;
	}
	
	public int getTopPrio()
	{
		char prio = role.charAt(0);
		int out = Character.getNumericValue(prio);
		return out;
	}
	
	public int getJugPrio()
	{
		char prio = role.charAt(1);
		int out = Character.getNumericValue(prio);
		return out;
	}

	public int getMidPrio()
	{
		char prio = role.charAt(2);
		int out = Character.getNumericValue(prio);
		return out;
	}
	
	public int getBotPrio()
	{
		char prio = role.charAt(3);
		int out = Character.getNumericValue(prio);
		return out;
	}
	
	public int getSupPrio()
	{
		char prio = role.charAt(4);
		int out = Character.getNumericValue(prio);
		return out;
	}
	
	public String toString()
	{
		String out = "";
		out += name;
		return out;
	}
}
