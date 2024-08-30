package elections;

import java.util.LinkedList;

public class CampaignAction {

    private final int[] description;

    public CampaignAction(int[] description) {
        this.description = description;
    }

    // Cost of performing an action on a single voter in a district
    private int calculateCost() {
        int totalCost = 0;
        if (description != null) {
            for (int value : description) {
                totalCost += Math.abs(value);
            }
        }
        return totalCost;
    }

    public int[] getDescription() {
        return description;
    }

    public int totalCost(int numberOfVoters) {
        return numberOfVoters * calculateCost();
    }

    public void execute(District district) {
        if (district != null) {
            LinkedList<Voter> voterList = district.getVoterList();
            if (voterList != null) {
                for (Voter voter : voterList) {
                    voter.changeWeights(description);
                }
            }
        }
    }
}
