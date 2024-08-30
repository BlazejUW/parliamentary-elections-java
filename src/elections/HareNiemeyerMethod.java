package elections;

import java.util.LinkedList;
import java.util.Random;

public class HareNiemeyerMethod implements VotingMethod {

    @Override
    public int[] allocateSeatsInDistrict(VotingResults districtVotingResults, int numberOfDistrictSeats) {

        LinkedList<Float> proportions = new LinkedList<>();
        LinkedList<Float> fractionalParts = new LinkedList<>();

        int[] votesPerPartyInDistrict = districtVotingResults.getVotesPerParty();

        int totalVotesInDistrict = calculateTotalVotesInDistrict(votesPerPartyInDistrict);

        Party[] partyList = districtVotingResults.getParties();
        int[] seatDistribution = new int[partyList.length];
        int allocatedSeats = 0;
        float partyProportion;

        for (int i = 0; i < votesPerPartyInDistrict.length; i++) {
            partyProportion = ((float) votesPerPartyInDistrict[i] * numberOfDistrictSeats) / ((float) totalVotesInDistrict);
            proportions.add(partyProportion);
            seatDistribution[i] = (int) Math.floor(partyProportion);
            allocatedSeats += seatDistribution[i];
            fractionalParts.add(partyProportion - seatDistribution[i]);
        }

        return distributeFractionalParts(seatDistribution, allocatedSeats, numberOfDistrictSeats, fractionalParts);
    }

    private int calculateTotalVotesInDistrict(int[] votesPerPartyInDistrict) {
        int totalVotesInDistrict = 0;
        for (int votes : votesPerPartyInDistrict) {
            totalVotesInDistrict += votes;
        }
        return totalVotesInDistrict;
    }

    private int[] distributeFractionalParts(int[] seatDistribution, int allocatedSeats, int numberOfDistrictSeats, LinkedList<Float> fractionalParts) {
        int[] result = seatDistribution;

        while (allocatedSeats != numberOfDistrictSeats) {
            float largestFractionalPart = 0;
            int partyIndex = 0;
            LinkedList<Integer> selectedParties = new LinkedList<>();
            for (int i = 0; i < fractionalParts.size(); i++) {
                if (fractionalParts.get(i) > largestFractionalPart) {
                    largestFractionalPart = fractionalParts.get(i);
                    partyIndex = i;
                    selectedParties = new LinkedList<>();
                    selectedParties.add(partyIndex);
                } else if (fractionalParts.get(i) == largestFractionalPart) {
                    selectedParties.add(i);
                }
            }
            partyIndex = drawPartyNumber(selectedParties);
            fractionalParts.set(partyIndex, 0f);
            result[partyIndex]++;
            allocatedSeats++;
        }
        return result;
    }

    private int drawPartyNumber(LinkedList<Integer> selectedParties) {
        Random random = new Random();
        int partyIndexInList = random.nextInt(selectedParties.size());
        return selectedParties.get(partyIndexInList);
    }

    @Override
    public String toString() {
        return "Hare-Niemeyer Method\n";
    }
}
