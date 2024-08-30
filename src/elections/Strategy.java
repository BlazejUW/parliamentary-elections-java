package elections;

public interface Strategy {

    public boolean performAction(CampaignAction[] actions, int budget, District[] districts, Party commissioningParty);
}
