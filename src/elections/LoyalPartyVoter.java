package elections;

import java.util.LinkedList;

public class LoyalPartyVoter extends Voter {
    // Example: Ewa T 1 1 PartyB

    protected final Party selectedParty;

    public LoyalPartyVoter(String firstName, String lastName, District district, Party selectedParty) {
        super(firstName, lastName, district);
        this.selectedParty = selectedParty;
    }

    @Override
    public void castVote(LinkedList<DistrictPartyCandidates> allCandidates) {
        super.castVote(SelectFromParty.selectCandidatesFromParty(allCandidates, selectedParty));
    }

    @Override
    protected LinkedList<Candidate> createSelectedList(LinkedList<DistrictPartyCandidates> allCandidates) {
        LinkedList<DistrictPartyCandidates> candidateLists = SelectFromParty.selectCandidatesFromParty(allCandidates, selectedParty);

        int selectedCandidateIndex = candidateIndex(candidateLists);

        if (selectedCandidateIndex < electoralDistrict.getNumberOfSeats()) {
            Candidate[] candidates = candidateLists.get(0).getPartyCandidates();
            LinkedList<Candidate> chosenCandidates = new LinkedList<>();
            for (Candidate candidate : candidates) {
                if (candidate.getListPosition() == selectedCandidateIndex + 1) {
                    chosenCandidates.add(candidate);
                    break;
                }
            }
            return chosenCandidates;
        } else {
            if (electoralDistrict.getMergedDistrict().getDistrict1().getNumber() == electoralDistrict.getNumber()) {
                return createSelectedList(electoralDistrict.getMergedDistrict().getDistrict2().getAllDistrictCandidates());
            } else {
                return createSelectedList(electoralDistrict.getMergedDistrict().getDistrict1().getAllDistrictCandidates());
            }
        }
    }

    protected int candidateIndex(LinkedList<DistrictPartyCandidates> partyCandidates) {
        return drawCandidateNumber(partyCandidates.get(0).getPartyCandidates().length);
    }
}
