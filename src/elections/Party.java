package elections;

public class Party {
    private final String partyName;
    private int budget;
    private final Strategy partyStrategy;

    private int totalVotes;
    private int totalSeats;

    public Party(String partyName, int budget, Strategy partyStrategy) {
        this.partyName = partyName;
        this.budget = budget;
        this.partyStrategy = partyStrategy;
        this.totalVotes = 0;
        this.totalSeats = 0;
    }

    public String getPartyName() {
        return partyName;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void addVote() {
        totalVotes++;
    }

    public void addSeats(int seatsWon) {
        this.totalSeats += seatsWon;
    }

    public void payForAction(int cost) {
        this.budget -= cost;
    }

    public void campaign(District[] districts, CampaignAction[] campaignActions) {
        boolean actionPerformed = true;
        while (actionPerformed) {
            actionPerformed = partyStrategy.performAction(campaignActions, budget, districts, this);
        }
    }

    public StringBuilder printSeats() {
        StringBuilder result = new StringBuilder();
        result.append(partyName + " " + totalSeats + "\n");
        return result;
    }
}
