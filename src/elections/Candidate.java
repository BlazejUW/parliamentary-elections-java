package elections;

public class Candidate {
    private final String firstName;
    private final String lastName;
    private final Party party;
    private final int listNumber;
    private final int[] candidateAttributes;
    private int numberOfVotes;

    public Candidate(String firstName, String lastName, Party party, int listNumber, int[] candidateAttributes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.party = party;
        this.listNumber = listNumber;
        this.candidateAttributes = candidateAttributes;
        this.numberOfVotes = 0;
    }

    public Party getParty() {
        return party;
    }

    public int getListNumber() {
        return listNumber;
    }

    public int[] getCandidateAttributes() {
        return candidateAttributes;
    }

    public void addVote() {
        this.numberOfVotes++;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public StringBuilder print() {
        StringBuilder result = new StringBuilder();
        result.append(firstName + " " + lastName + " " + party.getPartyName() + " " + listNumber + " " + numberOfVotes + "\n");
        return result;
    }
}
