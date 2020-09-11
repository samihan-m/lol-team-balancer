public class TeamRankDiffReport
{
	RankDiffReport topReport;
	RankDiffReport jugReport;
	RankDiffReport midReport;
	RankDiffReport botReport;
	RankDiffReport supReport;
	int teamDiff;
	
	public TeamRankDiffReport(Player top, Player top2, Player jug, Player jug2, Player mid, Player mid2, Player bot, Player bot2, Player sup, Player sup2)
	{
		topReport = new RankDiffReport(top,top2,false);
		jugReport = new RankDiffReport(jug,jug2,false);
		midReport = new RankDiffReport(mid,mid2,false);
		botReport = new RankDiffReport(bot,bot2,false);
		supReport = new RankDiffReport(sup,sup2,false);
		
		teamDiff = topReport.rankDiff + jugReport.rankDiff + midReport.rankDiff + botReport.rankDiff + supReport.rankDiff;
		
	}
	
	public Player[] getTeam1()
	{
		Player[] team1 = new Player[5];
		team1[0] = topReport.p1;
		team1[1] = jugReport.p1;
		team1[2] = midReport.p1;
		team1[3] = botReport.p1;
		team1[4] = supReport.p1;
		return team1;
	}
	
	public Player[] getTeam2()
	{
		Player[] team2 = new Player[5];
		team2[0] = topReport.p2;
		team2[1] = jugReport.p2;
		team2[2] = midReport.p2;
		team2[3] = botReport.p2;
		team2[4] = supReport.p2;
		return team2;
	}
	
	public RankDiffReport[] getReports()
	{
		RankDiffReport[] out = new RankDiffReport[5];
		out[0] = topReport;
		out[1] = jugReport;
		out[2] = midReport;
		out[3] = botReport;
		out[4] = supReport;
		return out;
	}
}
