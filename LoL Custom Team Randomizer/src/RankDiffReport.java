public class RankDiffReport
{
	
	Player p1;
	Player p2;
	int rankDiff;
	boolean p1greater;
	
	public RankDiffReport(Player p1, Player p2)
	{
		this.p1 = p1;
		this.p2 = p2;
		rankDiff = getDiff(p1,p2,true);
	}
	
	public RankDiffReport(Player p1, Player p2, boolean abs)
	{
		this.p1 = p1;
		this.p2 = p2;
		rankDiff = getDiff(p1,p2,abs);
	}
	
	public int getDiff(Player p1, Player p2, boolean abs)
	{
		this.p1 = p1;
		this.p2 = p2;
		int totalDiff = 0;
		if(abs)
		{
			if(p1.getRankNum() > p2.getRankNum())
				p1greater = true;
			else
				p1greater = false;
						
			if(p1greater)
			{
				totalDiff = p1.getRankNum()-p2.getRankNum();
			}
			else
			{
				totalDiff = p2.getRankNum()-p1.getRankNum();
			}
		}
		else 
		{
			totalDiff = p1.getRankNum()-p2.getRankNum();
		}
		return totalDiff;
	}
}
