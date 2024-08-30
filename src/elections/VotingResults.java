package elections;

public class VotingResults {
    private final Party[] parties;
    private final int[] votesPerParty;

    public VotingResults(Party[] parties, int[] votesPerParty) {
        this.parties = parties;
        this.votesPerParty = votesPerParty;
    }

    public Party[] getParties() {
        return parties;
    }

    public int[] getVotesPerParty() {
        return votesPerParty;
    }
}
