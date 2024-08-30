package elections;

public class BoldStrategy implements Strategy {
    @Override
    public boolean performAction(CampaignAction[] actions, int budget, District[] districts, Party commissioningParty) {
        CampaignAction selectedAction = null;
        District selectedDistrict = null;
        int maxCost = 0;

        for (CampaignAction action : actions) {
            for (District district : districts) {
                District currentDistrict = district;
                if (district.mergedDistrict != null) {
                    if (district.getNumber() <= district.getMergedDistrict().getNumber()) {
                        currentDistrict = district.mergedDistrict;
                    } else {
                        continue;
                    }
                }
                int cost = action.totalCost(district.getNumberOfVoters());
                if (cost > maxCost && cost <= budget) {
                    maxCost = cost;
                    selectedAction = action;
                    selectedDistrict = currentDistrict;
                }
            }
        }
        if (selectedAction != null) {
            commissioningParty.payForAction(maxCost);
            selectedAction.execute(selectedDistrict);
            return true;
        }
        return false;
    }
}
