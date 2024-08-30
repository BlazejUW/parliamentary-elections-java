package elections;

import java.util.LinkedList;

public class VersatileVoter extends Voter {

    // Example: Krzysztof L 1 5 weights: 58 86 69 -2 14 47 -35

    protected int[] featureWeights;

    public VersatileVoter(String firstName, String lastName, District district, int[] featureWeights) {
        super(firstName, lastName, district);
        this.featureWeights = featureWeights;
    }

    @Override
    public int[] getFeatureWeights() {
        return featureWeights;
    }

    @Override
    public void changeWeights(int[] description) {
        for (int i = 0; i < featureWeights.length; i++) {
            featureWeights[i] += description[i];
            featureWeights[i] = Math.max(-100, Math.min(100, featureWeights[i]));
        }
    }

    @Override
    protected LinkedList<Candidate> createSelectedList(LinkedList<DistrictPartyCandidates> allCandidates) {
        LinkedList<Candidate> selectedCandidatesList = new LinkedList<>();
        int maximumWeightedSum = maximumWeightedSum(allCandidates);
        for (DistrictPartyCandidates party : allCandidates) {
            for (Candidate candidate : party.getPartyCandidates()) {
                int currentWeightedSum = weightedSum(candidate);
                if (currentWeightedSum == maximumWeightedSum) {
                    selectedCandidatesList.add(candidate);
                }
            }
        }
        return selectedCandidatesList;
    }

    protected int maximumWeightedSum(LinkedList<DistrictPartyCandidates> allCandidates) {
        int maxWeightedSum = Integer.MIN_VALUE;
        for (DistrictPartyCandidates party : allCandidates) {
            for (Candidate candidate : party.getPartyCandidates()) {
                int currentWeightedSum = weightedSum(candidate);
                if (currentWeightedSum > maxWeightedSum) {
                    maxWeightedSum = currentWeightedSum;
                }
            }
        }
        return maxWeightedSum;
    }

    protected int weightedSum(Candidate candidate) {
        int[] candidateAttributes = candidate.getCandidateAttributes();
        int sum = 0;
        for (int i = 0; i < featureWeights.length; i++) {
            sum += candidateAttributes[i] * featureWeights[i];
        }
        return sum;
    }
}
