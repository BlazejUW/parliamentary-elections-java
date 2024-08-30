package elections;

public class DistrictPartyCandidates {
    private final Party party;
    private final Candidate[] partyCandidates;

    public DistrictPartyCandidates(Party party, Candidate[] partyCandidates) {
        this.party = party;
        this.partyCandidates = partyCandidates;
    }

    public Party getParty() {
        return party;
    }

    public Candidate[] getPartyCandidates() {
        return partyCandidates;
    }

    public StringBuilder print() {
        StringBuilder result = new StringBuilder();
        for (Candidate candidate : partyCandidates) {
            result.append(candidate.print());
        }
        return result;
    }
}
