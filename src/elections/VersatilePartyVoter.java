package elections;

import java.util.LinkedList;

public class VersatilePartyVoter extends VersatileVoter {

    // Example: Jacek D 1 8 71 -25 72 -94 88 -73 34 PartyA

    private final Party selectedParty;

    public VersatilePartyVoter(String firstName, String lastName, District district, int[] featureWeights, Party selectedParty) {
        super(firstName, lastName, district, featureWeights);
        this.selectedParty = selectedParty;
    }

    @Override
    public void castVote(LinkedList<DistrictPartyCandidates> allCandidates) {
        super.castVote(SelectFromParty.selectCandidatesFromParty(allCandidates, selectedParty));
    }
}
