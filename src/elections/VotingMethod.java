package elections;

public interface VotingMethod {
    public int[] allocateSeatsInDistrict(VotingResults districtVotingResults, int numberOfDistrictSeats);
}
