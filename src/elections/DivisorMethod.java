package elections;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

abstract public class DivisorMethod implements VotingMethod {

    abstract protected int getDivisor();

    @Override
    public int[] allocateSeatsInDistrict(VotingResults districtVotingResults, int numberOfDistrictSeats) {

        Party[] parties = districtVotingResults.getParties();

        int[] votesPerParty = districtVotingResults.getVotesPerParty();
        int[] seatDistribution = new int[parties.length];
        int allocatedSeats = 0;

        // Key is the party number, value is the divisor
        Map<Integer, Integer> currentDivision = new HashMap<>();

        // Initial fill of the map
        for (int i = 0; i < parties.length; i++) {
            currentDivision.put(i, 1);
            seatDistribution[i] = 0;
        }

        while (allocatedSeats != numberOfDistrictSeats) {
            float maxValue = 0;

            int selectedPartyNumber = determineSelectedParty(parties, votesPerParty, currentDivision, maxValue);

            int divisorValue = getDivisor();
            currentDivision.replace(selectedPartyNumber, currentDivision.get(selectedPartyNumber) + divisorValue);
            seatDistribution[selectedPartyNumber]++;
            allocatedSeats++;
        }
        return seatDistribution;
    }

    protected int determineSelectedParty(Party[] parties, int[] votesPerParty, Map<Integer, Integer> currentDivision, float maxValue) {

        LinkedList<Integer> selectedParties = new LinkedList<>();
        for (int i = 0; i < parties.length; i++) {
            float votesDividedByCurrent = (float) votesPerParty[i] / currentDivision.get(i);
            int partyNumber = -1;
            if (votesDividedByCurrent > maxValue) {
                maxValue = votesDividedByCurrent;
                partyNumber = i;
                selectedParties = new LinkedList<>();
                selectedParties.add(partyNumber);
            } else if (votesDividedByCurrent == maxValue) {
                selectedParties.add(i);
            }
        }
        return drawPartyNumber(selectedParties);
    }

    protected int drawPartyNumber(LinkedList<Integer> selectedParties) {
        Random random = new Random();
        int partyIndexInList = random.nextInt(selectedParties.size());
        return selectedParties.get(partyIndexInList);
    }

}
