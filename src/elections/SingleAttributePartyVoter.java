package elections;

import java.util.LinkedList;

public class SingleAttributePartyVoter extends SingleAttributeVoter {

    // Example: Pawel G 1 6 4 PartyA
    // Example: Jacek R 1 7 5 PartyA

    private final Party selectedParty;

    public SingleAttributePartyVoter(String firstName, String lastName, District district, int attribute, boolean minimizes, boolean maximizes, Party selectedParty) {
        super(firstName, lastName, district, attribute, minimizes, maximizes);

        this.selectedParty = selectedParty;
    }

    @Override
    public void castVote(LinkedList<DistrictPartyCandidates> candidates) {
        super.castVote(SelectFromParty.selectCandidatesFromParty(candidates, selectedParty));
    }
}
