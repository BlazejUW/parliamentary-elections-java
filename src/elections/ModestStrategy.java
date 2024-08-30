package elections;

public class ModestStrategy implements Strategy {

    @Override
    public boolean performAction(CampaignAction[] actions, int budget, District[] districts, Party commissioningParty) {
        District smallestDistrict = findSmallestDistrict(districts);
        if (smallestDistrict != null) {
            CampaignAction action = findCheapestAction(actions, smallestDistrict);
            if (action != null) {
                int cost = action.totalCost(smallestDistrict.getNumberOfVoters());
                if (cost <= budget) {
                    action.execute(smallestDistrict);
                    commissioningParty.payForAction(cost);
                    return true;
                }
            }
        }
        return false;
    }

    private CampaignAction findCheapestAction(CampaignAction[] campaignActions, District district) {
        CampaignAction cheapestAction = null;
        int minCost = Integer.MAX_VALUE;
        District currentDistrict = district;
        if (district.mergedDistrict != null) {
            if (district.getNumber() <= district.getMergedDistrict().getNumber()) {
                currentDistrict = district.mergedDistrict;
            } else {
                return cheapestAction;
            }
        }
        for (CampaignAction action : campaignActions) {
            if (action.totalCost(currentDistrict.getNumberOfVoters()) < minCost) {
                cheapestAction = action;
                minCost = action.totalCost(currentDistrict.getNumberOfVoters());
            }
        }
        return cheapestAction;
    }

    private District findSmallestDistrict(District[] districts) {
        District result = null;
        int fewestVoters = Integer.MAX_VALUE;
        for (District district : districts) {
            District currentDistrict = district;
            if (district.mergedDistrict != null) {
                if (district.getNumber() < district.getMergedDistrict().getNumber()) {
                    currentDistrict = district.mergedDistrict;
                } else {
                    continue;
                }
            }
            if (currentDistrict.getNumberOfVoters() < fewestVoters) {
                result = currentDistrict;
                fewestVoters = currentDistrict.getNumberOfVoters();
            }
        }
        return result;
    }
}
