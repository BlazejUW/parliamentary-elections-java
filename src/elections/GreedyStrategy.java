package elections;

import java.util.Arrays;
import java.util.LinkedList;

public class GreedyStrategy implements Strategy {

    @Override
    public boolean performAction(CampaignAction[] actions, int budget, District[] districts, Party commissioningParty) {
        CampaignAction bestAction = null;
        District bestDistrict = null;
        int actionCost = 0;
        int highestScore = Integer.MIN_VALUE;

        for (District district : districts) {
            District currentDistrict = district;
            if (district.mergedDistrict != null) {
                if (district.getNumber() <= district.getMergedDistrict().getNumber()) {
                    currentDistrict = district.mergedDistrict;
                } else {
                    continue;
                }
            }

            LinkedList<Voter> votersInDistrict = currentDistrict.getVoterList();
            Candidate[] candidatesInDistrict = SelectFromParty.selectCandidatesFromParty(currentDistrict.getAllDistrictCandidates(), commissioningParty).get(0).getPartyCandidates();

            int originalSum = 0;
            int currentSum = 0;

            int[] actionResults = new int[actions.length];
            Arrays.fill(actionResults, Integer.MIN_VALUE);

            for (Voter voter : votersInDistrict) {
                int[] originalWeights = voter.getFeatureWeights();
                if (originalWeights != null) {

                    for (Candidate candidate : candidatesInDistrict) {
                        originalSum += weightedSum(candidate, originalWeights);
                    }

                    int i = 0;
                    for (CampaignAction action : actions) {
                        actionCost = action.totalCost(district.getNumberOfVoters());
                        if (actionCost <= budget) {
                            int[] weightsCopy = copyWeights(originalWeights);
                            applyAction(action.getDescription(), weightsCopy);

                            for (Candidate candidate : candidatesInDistrict) {
                                actionResults[i] += weightedSum(candidate, weightsCopy);
                            }
                        } else {
                            actionResults[i] = Integer.MIN_VALUE;
                        }
                        i++;
                    }
                }
            }

            int max = Integer.MIN_VALUE;
            int actionIndex = 0;
            for (int i = 0; i < actionResults.length; i++) {
                if (actionResults[i] > max && actions[i].totalCost(district.getNumberOfVoters()) <= budget) {
                    currentSum = actionResults[i];
                    actionIndex = i;
                }
            }

            int currentScore = originalSum - currentSum;
            if (currentScore > highestScore) {
                highestScore = currentScore;
                bestAction = actions[actionIndex];
                bestDistrict = currentDistrict;
            }
        }

        if (bestAction != null) {
            actionCost = bestAction.totalCost(bestDistrict.getNumberOfVoters());
            if (actionCost <= budget) {
                bestAction.execute(bestDistrict);
                commissioningParty.payForAction(actionCost);
                return true;
            }
        }
        return false;
    }

    private int[] copyWeights(int[] originalWeights) {
        int[] result = new int[originalWeights.length];
        for (int i = 0; i < originalWeights.length; i++) {
            result[i] = originalWeights[i];
        }
        return result;
    }

    public void applyAction(int[] description, int[] featureWeights) {
        for (int i = 0; i < featureWeights.length; i++) {
            featureWeights[i] += description[i];
            featureWeights[i] = Math.max(-100, Math.min(100, featureWeights[i]));
        }
    }

    protected int weightedSum(Candidate candidate, int[] featureWeights) {
        int[] candidateAttributes = candidate.getCandidateAttributes();
        int sum = 0;
        for (int i = 0; i < featureWeights.length; i++) {
            sum += candidateAttributes[i] * featureWeights[i];
        }
        return sum;
    }
}
