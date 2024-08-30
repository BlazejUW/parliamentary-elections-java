package elections;

import java.util.LinkedList;
import java.util.Random;

public abstract class Voter {
    protected final String firstName;
    protected final String lastName;
    protected final District electoralDistrict;
    protected District mergedWithDistrict;

    protected Candidate candidateVotedFor;

    public Voter(String firstName, String lastName, District electoralDistrict) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.electoralDistrict = electoralDistrict;
        this.mergedWithDistrict = electoralDistrict.getMergedDistrict();
    }

    public int[] getFeatureWeights() {
        // Works only for voters who have feature weights
        return null;
    }

    public Candidate getCandidateVotedFor() {
        return candidateVotedFor;
    }

    public void changeWeights(int[] description) {
        // Works only for voters who have feature weights
    }

    public void castVote(LinkedList<DistrictPartyCandidates> allCandidates) {
        LinkedList<Candidate> selectedCandidatesList = createSelectedList(allCandidates);
        if (selectedCandidatesList.size() > 0) {
            int number = drawCandidateNumber(selectedCandidatesList.size());
            candidateVotedFor = selectedCandidatesList.get(number);
        }
    }

    abstract protected LinkedList<Candidate> createSelectedList(LinkedList<DistrictPartyCandidates> allCandidates);

    protected int drawCandidateNumber(int range) {
        Random random = new Random();
        return random.nextInt(range);
    }

    public StringBuilder print() {
        StringBuilder result = new StringBuilder();
        if (candidateVotedFor != null) {
            return result.append(firstName + ' ' + lastName + ' ' + candidateVotedFor.getFullName() + "\n");
        }
        return result;
    }
}
