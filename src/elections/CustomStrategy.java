package elections;

public class CustomStrategy extends BoldStrategy implements Strategy {

    // This strategy selects the most expensive action but with caution. The budget is limited to half the total budget.
    // If half the budget is not enough to perform any action, it chooses the most expensive action using the full budget.

    public CustomStrategy() {
    }

    @Override
    public boolean performAction(CampaignAction[] actions, int budget, District[] districts, Party commissioningParty) {
        int halfBudget = budget / 2;

        boolean isActionPossible = super.performAction(actions, halfBudget, districts, commissioningParty);
        if (isActionPossible) {
            return true;
        } else {
            return super.performAction(actions, budget, districts, commissioningParty);
        }
    }
}
