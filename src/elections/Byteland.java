package elections;

public class Byteland {

    public void conductElections(District[] districts, Party[] parties, CampaignAction[] campaignActions, VotingMethod method) {
        runCampaign(districts, parties, campaignActions);
        voting(districts);
        countVotes(districts, parties);
        allocateSeats(districts, parties, method);
        printSummary(districts, parties, method);
    }

    private void runCampaign(District[] districts, Party[] parties, CampaignAction[] campaignActions) {
        for (Party party : parties) {
            if (party != null) {
                party.campaign(districts, campaignActions);
            }
        }
    }

    private void voting(District[] districts) {
        for (District district : districts) {
            if (district != null) {
                District currentDistrict = district;
                if (district.mergedDistrict != null) {
                    if (district.getNumber() <= district.getMergedDistrict().getNumber()) {
                        currentDistrict = district.mergedDistrict;
                    } else {
                        continue;
                    }
                }
                currentDistrict.voting();
            }
        }
    }

    private void countVotes(District[] districts, Party[] parties) {
        for (District district : districts) {
            if (district != null
