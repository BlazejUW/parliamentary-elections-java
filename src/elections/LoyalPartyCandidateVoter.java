package elections;

import java.util.LinkedList;

public class LoyalPartyCandidateVoter extends LoyalPartyVoter {

    // Example: Marta D 1 2 PartyA 16

    private final int candidateNumber;

    public LoyalPartyCandidateVoter(String firstName, String lastName, District district, Party selectedParty, int candidateNumber) {
        super(firstName, lastName, district, selectedParty);
        this.candidateNumber = candidateNumber;
    }

    @Override
    public void castVote(LinkedList<DistrictPartyCandidates> allCandidates) {
        LinkedList<DistrictPartyCandidates> candidatesFromDistrict = this.electoralDistrict.getAllDistrictCandidates();
        super.castVote(SelectFromParty.selectCandidatesFromParty(candidatesFromDistrict, selectedParty));
    }

    @Override
    protected int candidateIndex(LinkedList<DistrictPartyCandidates> partyCandidates) {
        return candidateNumber - 1;
    }
}
