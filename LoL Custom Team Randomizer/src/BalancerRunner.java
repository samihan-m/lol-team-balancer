import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BalancerRunner
{
	static String directory = "D:\\Samihan\\Desktop\\Notes\\Code IO\\PlayerDataIO.txt";
	static ArrayList<Player> playerList = new ArrayList<Player>();
	static ArrayList<Player> availablePlayers = new ArrayList<Player>();
	static ArrayList<Player> availableTopPlayers = new ArrayList<Player>();
	static ArrayList<Player> topPlayers = new ArrayList<Player>();
	static ArrayList<Player> availableJugPlayers = new ArrayList<Player>();
	static ArrayList<Player> jugPlayers = new ArrayList<Player>();
	static ArrayList<Player> availableMidPlayers = new ArrayList<Player>();
	static ArrayList<Player> midPlayers = new ArrayList<Player>();
	static ArrayList<Player> availableBotPlayers = new ArrayList<Player>();
	static ArrayList<Player> botPlayers = new ArrayList<Player>();
	static ArrayList<Player> availableSupPlayers = new ArrayList<Player>();
	static ArrayList<Player> supPlayers = new ArrayList<Player>();
	static ArrayList<Player> playersOnTeams = new ArrayList<Player>();
	static Team team1;
	static Team team2;
	
	public static void main(String[] args) throws IOException
	{
		dataReader();
		dataProcessor();
		matchmaker();
		balanceTeams();
		output();
	}
	
	//reads the file at the directory above, using / as delimiters
	public static void dataReader() throws IOException
	{
		File input = new File(directory);
		Scanner scanner = new Scanner(input);
		String part = "";
		ArrayList<String> lines = new ArrayList<String>();
		while(scanner.hasNext()) 
		{
			part = scanner.nextLine();
			lines.add(part);
		}
		String name = "";
		String rank = "";
		String role = "";
		for(String l: lines)
		{
			part = l;
			Scanner partScanner = new Scanner(part).useDelimiter("/");
			name = partScanner.next();
			//System.out.println(name);
			rank = partScanner.next();
			//System.out.println(rank);
			role = partScanner.next();
			//System.out.println(role);
			playerList.add(new Player(name, rank, role));
			//System.out.println("player added");
			partScanner.reset();
		}
		scanner.close();
	}
	
	//will only scan for name and role numbers
	//might become obsolete when i write the bot to scan the discord channel
	public static void newDataReader() throws IOException
	{
		
	}
	
		
	
	//takes the read data and fills availableXYZPlayer where XYZ is a role (Top, Jug, etc.) based on
	//given player role prio
	public static void dataProcessor()
	{
		boolean hasEnough;
		//do each role separately 0-4
		//set prio to 1. search for players who want to play that role at that prio by comparing 
		//the value in their role int to the prio we are searching for atm
		//if it is a match add them to the appropriate player pool
		for(int position = 0; position < 5; position++)
		{
			hasEnough = false;
			for(int prio = 1; prio < 6; prio++)
			{
				for(Player p : playerList)
				{
					if(p.getRolePrio(position)==prio)
					{
						switch(position)
						{
						case 0: availableTopPlayers.add(p);
//						System.out.println("Top Pool +1");
						break;
						case 1: availableJugPlayers.add(p); 
//						System.out.println("Jug Pool +1");
						break;
						case 2: availableMidPlayers.add(p); 
//						System.out.println("Mid Pool +1");
						break;
						case 3: availableBotPlayers.add(p); 
//						System.out.println("Bot Pool +1");
						break;
						case 4: availableSupPlayers.add(p); 
//						System.out.println("Sup Pool +1");
						break;
						default:
						System.out.println("default: im sad. error in dataProcessor");
						break;
						}
						if(availablePlayers.indexOf(p)==-1)
							availablePlayers.add(p);
					}
				}
//				System.out.println(availablePlayers.toString());
				//this is prio block
				//check if the groups are full- if they are, then break from prio block
				switch(position)
				{
				case 0: 
					if(availableTopPlayers.size() >= 2)
					{
						System.out.println("Top Pool Size: "+availableTopPlayers.size());
						hasEnough = true;
						break;
					}
				case 1: 
					if(availableJugPlayers.size() >= 2)
					{
						System.out.println("Jug Pool Size: "+availableJugPlayers.size());
						hasEnough = true;
						break;
					}
				case 2: 
					if(availableMidPlayers.size() >= 2)
					{
						System.out.println("Mid Pool Size: "+availableMidPlayers.size());
						hasEnough = true;
						break;
					}
				case 3: 
					if(availableBotPlayers.size() >= 2)
					{
						System.out.println("Bot Pool Size: "+availableBotPlayers.size());
						hasEnough = true;
						break;
					}
				case 4: 
					if(availableSupPlayers.size() >= 2)
					{
						System.out.println("Sup Pool Size: "+availableSupPlayers.size());
						hasEnough = true;
						break;
					}
				}	
				if(hasEnough)
					break;
			}
		}
	}
	
	//determines order in which to start matching players (smallest to largest role pool)
	//and matches them by calling matchRole()
	//lastly, calls checkRole() to see if a role isnt filled due to lack of players in a pool 
	//autofill protocol grabs the two remaining
	//players and puts them into the role that isnt full.
	public static void matchmaker()
	{
		int topSize = availableTopPlayers.size();
		int jugSize = availableJugPlayers.size();
		int midSize = availableMidPlayers.size();
		int botSize = availableBotPlayers.size();
		int supSize = availableSupPlayers.size();
		ArrayList<Integer> sizes = new ArrayList<Integer>();		
		sizes.add(topSize);
		sizes.add(jugSize);
		sizes.add(midSize);
		sizes.add(botSize);
		sizes.add(supSize);
		ArrayList<Integer> roles = new ArrayList<Integer>();
		roles.add(0);
		roles.add(1);
		roles.add(2);
		roles.add(3);
		roles.add(4);
		String roleOrder = "";
		while(!roles.isEmpty())
		{
			int mindex = findMinIndex(convertIntegers(sizes));
//			System.out.println(sizes);
//			System.out.println(roles);
//			System.out.println("mindex: "+mindex);	
//			System.out.println(roles.get(mindex));
			roleOrder += roles.get(mindex);
			sizes.remove(mindex);
			roles.remove(mindex);
		}
		System.out.println("Role Matchmaking Order: " + roleOrder);
		//in order of smallest to largest player pool
		//starting matching
		switch(Character.getNumericValue(roleOrder.charAt(0)))
		{
		case 0: matchRole(availableTopPlayers, 0);
			break;
		case 1: matchRole(availableJugPlayers, 1);
			break;
		case 2: matchRole(availableMidPlayers, 2);
			break;
		case 3: matchRole(availableBotPlayers, 3);
			break;
		case 4: matchRole(availableSupPlayers, 4);
			break;
		}
		//roleOrder.indexOf('1')
		switch(Character.getNumericValue(roleOrder.charAt(1)))
		{
		case 0: matchRole(availableTopPlayers, 0);
			break;
		case 1: matchRole(availableJugPlayers, 1);
			break;
		case 2: matchRole(availableMidPlayers, 2);
			break;
		case 3: matchRole(availableBotPlayers, 3);
			break;
		case 4: matchRole(availableSupPlayers, 4);
			break;
		}
		switch(Character.getNumericValue(roleOrder.charAt(2)))
		{
		case 0: matchRole(availableTopPlayers, 0);
			break;
		case 1: matchRole(availableJugPlayers, 1);
			break;
		case 2: matchRole(availableMidPlayers, 2);
			break;
		case 3: matchRole(availableBotPlayers, 3);
			break;
		case 4: matchRole(availableSupPlayers, 4);
			break;
		}
		switch(Character.getNumericValue(roleOrder.charAt(3)))
		{
		case 0: matchRole(availableTopPlayers, 0);
			break;
		case 1: matchRole(availableJugPlayers, 1);
			break;
		case 2: matchRole(availableMidPlayers, 2);
			break;
		case 3: matchRole(availableBotPlayers, 3);
			break;
		case 4: matchRole(availableSupPlayers, 4);
			break;
		}
		switch(Character.getNumericValue(roleOrder.charAt(4)))
		{
		case 0: matchRole(availableTopPlayers, 0);
			break;
		case 1: matchRole(availableJugPlayers, 1);
			break;
		case 2: matchRole(availableMidPlayers, 2);
			break;
		case 3: matchRole(availableBotPlayers, 3);
			break;
		case 4: matchRole(availableSupPlayers, 4);
			break;
		}
		autofill();
	}
	
	//autofill protocol
	public static void autofill()
	{
		int toFix = checkRoleFull();
		if(toFix != -1)
		{
			System.out.println("toFix: " + toFix);
			Player selectP1 = availablePlayers.get(0);
			Player selectP2 = availablePlayers.get(inverseIndex(0));
			Player[] selected = new Player[2];
			selected[0] = selectP1;
			selected[1] = selectP2;
			RankDiffReport skillGap = new RankDiffReport(selectP1, selectP2);
			int skillDiff = skillGap.rankDiff;
			switch(toFix)
			{
			case 0:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to top. Skill Diff: " + skillDiff);
				addPlayers(topPlayers, selected);
				break;
			case 1:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to jug. Skill Diff: " + skillDiff);
				addPlayers(jugPlayers, selected);
				break;
			case 2:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to mid. Skill Diff: " + skillDiff);
				addPlayers(midPlayers, selected);
				break;
			case 3:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to bot. Skill Diff: " + skillDiff);
				addPlayers(botPlayers, selected);
				break;
			case 4:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to sup. Skill Diff: " + skillDiff);
				addPlayers(supPlayers, selected);
				break;
			}
		}
	}
	
	//checks every possible player matchup for the smallest skill gap, then picks that matchup
	public static void matchRole(ArrayList<Player> rolePlayers, int role)
	{
		
		ArrayList<RankDiffReport> reports = new ArrayList<RankDiffReport>();
		int size = rolePlayers.size();
		Player p1;
		Player p2;
		for(int i = 0; i < size; i++)
		{
			p1 = rolePlayers.get(i);
			for(int n = i+1; n < size; n++)
			{
				p2 = rolePlayers.get(n);
				System.out.println(p1.name + " compared to " + p2.name);
				
				RankDiffReport report = new RankDiffReport(p1,p2);
				reports.add(report);
			}
		}
		int reportCount = reports.size();
		RankDiffReport selected;
		if(reportCount != 0)
		{
			selected = reports.get(0);
			for(int k = 0; k < reportCount; k++)
			{
				RankDiffReport test = reports.get(k);
				//> for most balanced matchup, < for least
				if(selected.rankDiff > test.rankDiff)
					selected = test;
			}
			Player selectP1 = selected.p1;
			Player selectP2 = selected.p2;
			Player[] selectedPlayers = new Player[2];
			selectedPlayers[0] = selectP1;
			selectedPlayers[1] = selectP2;
			int rankDiff = selected.rankDiff;
			switch(role)
			{
			case 0:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to top. Skill Diff: " + rankDiff);
				addPlayers(topPlayers, selectedPlayers);
				break;
			case 1:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to jug. Skill Diff: " + rankDiff);
				addPlayers(jugPlayers, selectedPlayers);
				break;
			case 2:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to mid. Skill Diff: " + rankDiff);
				addPlayers(midPlayers, selectedPlayers);
				break;
			case 3:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to bot. Skill Diff: " + rankDiff);
				addPlayers(botPlayers, selectedPlayers);
				break;
			case 4:
				System.out.println("Adding " + selectP1 + " and " + selectP2 + " to sup. Skill Diff: " + rankDiff);
				addPlayers(supPlayers, selectedPlayers);
				break;
			}
			removePlayer(selectP1);
			removePlayer(selectP2);
//			System.out.println("Top list after removals: " + availableTopPlayers.toString());
//			System.out.println("Jug list after removals: " + availableJugPlayers.toString());
//			System.out.println("Mid list after removals: " + availableMidPlayers.toString());
//			System.out.println("Bot list after removals: " + availableBotPlayers.toString());
//			System.out.println("Sup list after removals: " + availableSupPlayers.toString());
		}
		else
		{
			String roleName;
			switch(role)
			{
			case 0: roleName = "top";
			break;
			case 1: roleName = "jug";
			break;
			case 2: roleName = "mid";
			break;
			case 3: roleName = "bot";
			break;
			case 4: roleName = "support";
			break;
			default:
				roleName = "unknown";
			}
			System.out.println("Error when determining matchup for the " + roleName + " role. Moving onto matching for the next role.");
		}
	}
	
	//checks if a role doesnt have 2 players (2 being a "full" role)
	//returns the value of the role that isnt full
	public static int checkRoleFull()
	{
		int out = -1;
		if(topPlayers.size()<2)
			out = 0;
		else if(jugPlayers.size()<2)
			out = 1;
		else if(midPlayers.size()<2)
			out = 2;
		else if(botPlayers.size()<2)
			out = 3;
		else if(supPlayers.size()<2)
			out = 4;
		return out;
	}
	
	//checks every possible team combination (32) and picks the one with the smallest skill gap 
	public static void balanceTeams()
	{
		ArrayList<TeamRankDiffReport> teamCombos = new ArrayList<TeamRankDiffReport>();
		Player top;
		Player jug;
		Player mid;
		Player bot;
		Player sup;
		Player top2;
		Player jug2;
		Player mid2;
		Player bot2;
		Player sup2;
		for(int t = 0; t < 2; t++)
		{
			top = topPlayers.get(t);
			top2 = topPlayers.get(inverseIndex(t));
//			RankDiffReport topGap = new RankDiffReport(top,top2);
			for(int j = 0; j < 2; j++)
			{
				jug = jugPlayers.get(j);
				jug2 = jugPlayers.get(inverseIndex(j));
//				RankDiffReport jugGap = new RankDiffReport(jug,jug2);
				for(int m = 0; m < 2; m++)
				{
					mid = midPlayers.get(m);
					mid2 = midPlayers.get(inverseIndex(m));
//					RankDiffReport midGap = new RankDiffReport(mid,mid2);
					for(int b = 0; b < 2; b++)
					{
						bot = botPlayers.get(b);
						bot2 = botPlayers.get(inverseIndex(b));
//						RankDiffReport botGap = new RankDiffReport(bot,bot2);

						for(int s = 0; s < 2; s++)
						{
							sup = supPlayers.get(s);
							sup2 = supPlayers.get(inverseIndex(s));
//							RankDiffReport supGap = new RankDiffReport(sup,sup2);
							TeamRankDiffReport report = 
									new TeamRankDiffReport(top,top2, jug, jug2, mid, mid2, 
											bot, bot2, sup, sup2);
							teamCombos.add(report);
						}
					}
				}
			}
		}
		int reportCount = teamCombos.size();
		TeamRankDiffReport selected = teamCombos.get(0);
		for(int k = 0; k < reportCount; k++)
		{
			TeamRankDiffReport test = teamCombos.get(k);
			//System.out.println(test.teamDiff);
			// > for most balanced team, < for least balanced team
			if(Math.abs(selected.teamDiff) > Math.abs(test.teamDiff))
				selected = test;
		}
		team1 = new Team(selected.getTeam1());
		team2 = new Team(selected.getTeam2());
		System.out.println("Team Diff: " + selected.teamDiff);
	}
	
	//adds players from the array players to the ArrayList list
	public static void addPlayers(ArrayList<Player> list, Player[] players)
	{
		for(Player p : players)
		{
			list.add(p);
		}
	}
		
	//inverts the two binary digits. used when calculating every possible team combination
	public static int inverseIndex(int input)
	{
		int out = -1;
		if(input == 0)
			out = 1;
		if(input == 1)
			out = 0;
		return out;
	}
	
	//prints the two teams determined by balanceTeams()
	public static void output()
	{
		System.out.println(team1);
		System.out.println();
		System.out.println(team2);
	}
	
	//removes a player from all availablePlayer pools
	public static void removePlayer(Player p)
	{
		availableTopPlayers.remove(p);
		availableJugPlayers.remove(p);
		availableMidPlayers.remove(p);
		availableBotPlayers.remove(p);
		availableSupPlayers.remove(p);
		availablePlayers.remove(p);
	}
	
	//returns the index of the smallest element in an array
	//used when determining the order in which to start matchmaking
	public static int findMinIndex(int[] all)
	{
		int index = 0;
		int min = all[index];
		for(int i = 0; i < all.length; i++)
		{
			int test = all[i];
			if(min > test)
			{
				min = test;
				index = i;
			}
		}
		return index;
	}
	
	//imported from stack exchange :)
	//turns an arraylist of Integers into a primitive int array
	public static int[] convertIntegers(ArrayList<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
}
