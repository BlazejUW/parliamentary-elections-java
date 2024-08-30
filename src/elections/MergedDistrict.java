package elections;

import java.util.LinkedList;

public class MergedDistrict extends District {

    private final District district1;
    private final District district2;

    public MergedDistrict(int number, LinkedList<Voter> voterList, LinkedList<DistrictPartyCandidates> allDistrictCandidates, District district1, District district2) {
        super(number);
        this.district1 = district1;
        this.district2 = district2;
        this.allDistrictCandidates = allDistrictCandidates;
        this.voterList = voterList;
        this.numberOfVoters = voterList.size();
        this.numberOfSeats = numberOfVoters / 10;
    }

    public District getDistrict1() {
        return district1;
    }

    public District getDistrict2() {
        return district2;
    }

    @Override
    public StringBuilder print(Party[] parties) {
        StringBuilder result = new StringBuilder();
        if (allDistrictCandidates != null && voterList != null && votingResults != null) {
            result.append(district1.getNumber() + " " + district2.getNumber() + "\n");
            for (DistrictPartyCandidates districtPartyCandidates : allDistrictCandidates) {
                result.append(districtPartyCandidates.print());
            }
            for (Voter voter : voterList) {
                result.append(voter.print());
            }
            result.append("\n" + printDistrictSeats(parties));
        }
        return result;
    }

    private StringBuilder printDistrictSeats(Party[] parties) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parties.length; i++) {
            if (parties[i] != null && allocatedSeats != null) {
                result.append(parties[i].getPartyName() + " " + allocatedSeats[i] + '\n');
            }
        }
        return result;
    }
}
