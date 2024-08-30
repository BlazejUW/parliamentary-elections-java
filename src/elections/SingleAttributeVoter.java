package elections;

import java.util.LinkedList;

public class SingleAttributeVoter extends Voter {

    protected int attribute;
    protected final boolean minimizes; // Example: Justyna C 1 3 1
    protected final boolean maximizes; // Example: Konrad T 1 4 4

    public SingleAttributeVoter(String firstName, String lastName, District district, int attribute, boolean minimizes, boolean maximizes) {
        super(firstName, lastName, district);
        this.attribute = attribute;
        this.minimizes = minimizes;
        this.maximizes = maximizes;
    }

    @Override
    protected LinkedList<Candidate> createSelectedList(LinkedList<DistrictPartyCandidates> allCandidates) {
        LinkedList<Candidate> selectedList = new LinkedList<>();

        int bestAttributeValue = bestAttributeValue(allCandidates);

        for (DistrictPartyCandidates party : allCandidates) {
            for (Candidate candidate : party.getPartyCandidates()) {
                int candidateAttributeValue = candidate.getCandidateAttributes()[attribute - 1];
                if (candidateAttributeValue == bestAttributeValue) {
                    selectedList.add(candidate);
                }
            }
        }
        return selectedList;
    }

    protected int bestAttributeValue(LinkedList<DistrictPartyCandidates> allCandidates) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (DistrictPartyCandidates party : allCandidates) {
            for (Candidate candidate : party.getPartyCandidates()) {
                int candidateAttributeValue = candidate.getCandidateAttributes()[attribute - 1];
                if (maximizes) {
                    if (candidateAttributeValue > max) {
                        max = candidateAttributeValue;
                    }
                } else if (minimizes) {
                    if (candidateAttributeValue < min) {
                        min = candidateAttributeValue;
                    }
                }
            }
        }
        if (maximizes) {
            return max;
        } else {
            return min;
        }
    }
}
