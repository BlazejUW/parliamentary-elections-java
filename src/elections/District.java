package elections;

import java.util.LinkedList;

public class District {
    protected final int number;

    protected int numberOfVoters;
    protected LinkedList<Voter> voterList;

    protected int numberOfSeats;
    protected LinkedList<DistrictPartyCandidates> allDistrictCandidates;

    protected VotingResults votingResults;
    protected int[] allocatedSeats;
    protected MergedDistrict mergedDistrict;

    public District(int number) {
        this.number = number;
        this.numberOfVoters = 0;
        this.voterList = null;
        this.numberOfSeats = numberOfVoters / 10;
        this.allDistrictCandidates = null;

        this.votingResults = null;
        this.allocatedSeats = null;
        this.mergedDistrict = null;
    }

    public int getNumberOfVoters() {
        return numberOfVoters;
    }

    public void setNumberOfVoters(int numberOfVoters) {
        this.numberOfVoters = numberOfVoters;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public LinkedList<Voter> getVoterList() {
        return voterList;
    }

    public LinkedList<DistrictPartyCandidates> getAllDistrictCandidates() {
        return allDistrictCandidates;
    }

    public MergedDistrict getMergedDistrict() {
        return mergedDistrict;
    }

    protected int getNumber() {
        return number;
    }

    public void setMergedDistrict(MergedDistrict mergedDistrict) {
        this.mergedDistrict = mergedDistrict;
    }

    public void setAllocatedSeats(int[] allocatedSeats) {
        this.allocatedSeats = allocatedSeats;
    }

    public void setAllDistrictCandidates(LinkedList<DistrictPartyCandidates> allDistrictCandidates) {
        this.allDistrictCandidates = allDistrictCandidates;
    }

    public void setVoterList(LinkedList<Voter> voterList) {
        this.voterList = voterList;
    }

    public void mergeWithDistrict(District district2) {

        LinkedList<Voter> newVoterList = new LinkedList<>();
        newVoterList.addAll(this.voterList);
        newVoterList.addAll(district2.voterList);

        LinkedList<DistrictPartyCandidates> newAllDistrictCandidates = new LinkedList<>();
        newAllDistrictCandidates.addAll(this.allDistrictCandidates);
        newAllDistrictCandidates.addAll(district2.allDistrictCandidates);

        this.mergedDistrict = new MergedDistrict(this.number, newVoterList, newAllDistrictCandidates, this, district2);
        district2.setMergedDistrict(this.mergedDistrict);
    }

    // ELECTIONS

    public void voting() {
        for (Voter voter : voterList) {
            voter.castVote(allDistrictCandidates);
            voter.getCandidateVotedFor().addVote();
            voter.getCandidateVotedFor().getParty().addVote();
        }
    }

    public void countVotes(Party[] parties) {
        int[] votesPerParty = new int[parties.length];

        for (int i = 0; i < parties.length; i++) {
            votesPerParty[i] = parties[i].getTotalVotes();
        }
        this.votingResults = new VotingResults(parties, votesPerParty);
    }

    public StringBuilder print(Party[] parties) {
        StringBuilder result = new StringBuilder();
        if (allDistrictCandidates != null && voterList != null && votingResults != null) {
            result.append(number + "\n");
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
