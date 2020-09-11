
public class Team
{
	Player top;
	Player jug;
	Player mid;
	Player bot;
	Player sup;
	int totalRankScore;
	
	public Team(Player t, Player j, Player m, Player b, Player s)
	{
		top = t;
		jug = j;
		mid = m;
		bot = b;
		sup = s;
		totalRankScore = top.getRankNum() + jug.getRankNum() + mid.getRankNum() + bot.getRankNum() + sup.getRankNum();
	}
	
	public Team(Player[] team1)
	{
		if(team1.length >= 5)
		{
			top = team1[0];
			jug = team1[1];
			mid = team1[2];
			bot = team1[3];
			sup = team1[4];
			totalRankScore = top.getRankNum() + jug.getRankNum() + mid.getRankNum() + bot.getRankNum() + sup.getRankNum();
		}
		else
			System.out.println("Error in Team creation. Team has less than 5 players.");
	}
	
	public String toString()
	{
		String out = "";
		out += "Top: " + top + " (" + top.getRankNum() + ")\n";
		out += "Jug: " + jug + " (" + jug.getRankNum() + ")\n";
		out += "Mid: " + mid + " (" + mid.getRankNum() + ")\n";
		out += "Bot: " + bot + " (" + bot.getRankNum() + ")\n";
		out += "Sup: " + sup + " (" + sup.getRankNum() + ")\n";
		out += "Total Rank Score: " + totalRankScore; 
		return out;
	}
}
