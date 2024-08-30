package elections;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElectionInterpreter {
    private final Party[] partyArray;
    private final int numberOfAttributes;
    private final int numberOfActions;
    private final District[] districtArray;
    private final CampaignAction[] actionArray;
    private final Scanner scanner;
    private int[] mergedDistrictFirstPairs;
    private int totalVoters = 0;

    public ElectionInterpreter(Scanner scanner, Party[] partyArray, District[] districtArray, int numberOfAttributes, int numberOfActions, int numberOfMergedDistricts) {
        this.scanner = scanner;
        this.partyArray = partyArray;
        this.districtArray = districtArray;
        this.numberOfAttributes = numberOfAttributes;
        this.numberOfActions = numberOfActions;
        this.actionArray = new CampaignAction[numberOfActions];
        this.mergedDistrictFirstPairs = new int[numberOfMergedDistricts];
    }

    public void createElectionWorld() {
        createDistricts();
        createParties();
        loadDistrictSizes();
        loadCandidatesAndAssignToDistricts();
        loadVoters();
        mergeDistricts();
        loadActions();
    }

    public void createDistricts() {
        String mergedDistricts = scanner.nextLine();

        int groupIndex = 0;

        for (int i = 0; i < districtArray.length; i++) {
            districtArray[i] = new District(i + 1);
        }

        Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(mergedDistricts);
        while (matcher.find()) {
            int districtNumber1 = Integer.parseInt(matcher.group(1).split(",")[0]);
            mergedDistrictFirstPairs[groupIndex] = districtNumber1;
            groupIndex++;
        }
    }

    public void createParties() {
        String partyNames = scanner.nextLine();
        String partyBudgets = scanner.nextLine();
        String partyStrategies = scanner.nextLine();
        int partyIndex = 0;
        while (partyIndex < partyArray.length) {
            String partyName = partyNames.split(" ")[partyIndex];
            int partyBudget = Integer.parseInt(partyBudgets.split(" ")[partyIndex]);
            String strategy = partyStrategies.split(" ")[partyIndex];
            Strategy partyStrategy = recognizeStrategy(strategy);

            Party party = new Party(partyName, partyBudget, partyStrategy);
            partyArray[partyIndex] = party;
            partyIndex++;
        }
    }

    public void loadDistrictSizes() {
        String sizes = scanner.nextLine();
        for (int i = 0; i < districtArray.length; i++) {
            int districtSize = Integer.parseInt(sizes.split(" ")[i]);
            totalVoters += districtSize;
            districtArray[i].setNumberOfVoters(districtSize);
            districtArray[i].setNumberOfSeats(districtSize / 10);
        }
    }

    public void loadCandidatesAndAssignToDistricts() {
        for (District district : districtArray) {
            LinkedList<DistrictPartyCandidates> allDistrictCandidates = new LinkedList<>();
            for (Party party : partyArray) {
                Candidate[] districtCandidates = new Candidate[district.getNumberOfSeats()];
                for (int i = 0; i < district.getNumberOfSeats(); i++) {
                    String candidateDescription = scanner.nextLine();
                    String firstName = candidateDescription.split(" ")[0];
                    String lastName = candidateDescription.split(" ")[1];

                    Party candidateParty = findParty(candidateDescription.split(" ")[3]);

                    int positionOnList = Integer.parseInt(candidateDescription.split(" ")[4]);
                    int[] candidateAttributes = new int[numberOfAttributes];
                    for (int j = 0; j < numberOfAttributes; j++) {
                        candidateAttributes[j] = Integer.parseInt(candidateDescription.split(" ")[j + 5]);
                    }
                    Candidate candidate = new Candidate(firstName, lastName, candidateParty, positionOnList, candidateAttributes);
                    districtCandidates[i] = candidate;
                }
                DistrictPartyCandidates districtPartyCandidates = new DistrictPartyCandidates(party, districtCandidates);
                allDistrictCandidates.add(districtPartyCandidates);
            }
            district.setAllDistrictCandidates(allDistrictCandidates);
        }
    }

    public void loadVoters() {
        int lastAddedDistrictNumber = 1;
        LinkedList<Voter> voterList = new LinkedList<>();

        for (int i = 0; i < totalVoters; i++) {
            String voterDescription = scanner.nextLine();
            String firstName = voterDescription.split(" ")[0];
            String lastName = voterDescription.split(" ")[1];
            int districtNumber = Integer.parseInt(voterDescription.split(" ")[2]);
            int voterType = Integer.parseInt(voterDescription.split(" ")[3]);

            Voter voter = createVoter(firstName, lastName, districtArray[districtNumber - 1], voterType, voterDescription.split(" "));

            if (districtNumber != lastAddedDistrictNumber) {
                districtArray[lastAddedDistrictNumber - 1].setVoterList(voterList);
                voterList = new LinkedList<>();
            }
            voterList.add(voter);
            lastAddedDistrictNumber = districtNumber;
        }
        districtArray[lastAddedDistrictNumber - 1].setVoterList(voterList);
    }

    public void mergeDistricts() {
        for (int firstPairNumber : mergedDistrictFirstPairs) {
            districtArray[firstPairNumber - 1].mergeWithDistrict(districtArray[firstPairNumber]);
        }
    }

    public void loadActions() {
        for (int actionNumber = 0; actionNumber < numberOfActions; actionNumber++) {
            int[] attributeChanges = new int[numberOfAttributes];
            String actionDescription = scanner.nextLine();
            for (int i = 0; i < numberOfAttributes; i++) {
                String str = actionDescription.split(" ")[i];
                if (str.equals("")) {
                    attributeChanges[numberOfAttributes - 1] = Integer.parseInt(actionDescription.split(" ")[numberOfAttributes]);
                    continue;
                }
                attributeChanges[i] = Integer.parseInt(str);
            }
            actionArray[actionNumber] = new CampaignAction(attributeChanges);
        }
    }

    public Voter createVoter(String firstName, String lastName, District district, int type, String[] remainingText) {
        if (type == 1) {
            String partyName = remainingText[4];
            Party myParty = findParty(partyName);
            return new LoyalPartyVoter(firstName, lastName, district, myParty);
        }
        if (type == 2) {
            String partyName = remainingText[4];
            int candidateNumber = Integer.parseInt(remainingText[5]);
            Party myParty = findParty(partyName);
            return new LoyalCandidateVoter(firstName, lastName, district, myParty, candidateNumber);
        }

        if (type == 3) {
            int attributeNumber = Integer.parseInt(remainingText[4]);
            return new SingleAttributeVoter(firstName, lastName, district, attributeNumber, true, false);
        }

        if (type == 4) {
            int attributeNumber = Integer.parseInt(remainingText[4]);
            return new SingleAttributeVoter(firstName, lastName, district, attributeNumber, false, true);
        }

        if (type == 5) {
            int[] weightArray = new int[numberOfAttributes];
            for (int i = 0; i < numberOfAttributes; i++) {
                weightArray[i] = Integer.parseInt(remainingText[4 + i]);
            }

            return new VersatileVoter(firstName, lastName, district, weightArray);
        }

        if (type == 6) {
            int attributeNumber = Integer.parseInt(remainingText[4]);
            String partyName = remainingText[5];
            Party myParty = findParty(partyName);
            return new SingleAttributePartyVoter(firstName, lastName, district, attributeNumber, true, false, myParty);
        }

        if (type == 7) {
            int attributeNumber = Integer.parseInt(remainingText[4]);
            String partyName = remainingText[5];
            Party myParty = findParty(partyName);
            return new SingleAttributePartyVoter(firstName, lastName, district, attributeNumber, false, true, myParty);
        }

        if (type == 8) {
            int[] weightArray = new int[numberOfAttributes];
            for (int i = 0; i < numberOfAttributes; i++) {
                weightArray[i] = Integer.parseInt(remainingText[4 + i]);
            }
            String partyName = remainingText[4 + numberOfAttributes];
            Party myParty = findParty(partyName);

            return new VersatilePartyVoter(firstName, lastName, district, weightArray, myParty);
        }
        return null;
    }

    public Party findParty(String name) {
        for (Party party : partyArray) {
            String partyName = party.getPartyName();
            if (partyName.equals(name)) {
                return party;
            }
        }
        return null;
    }

    public Strategy recognizeStrategy(String strategy) {
        if (strategy.equals("R")) {
            return new BoldStrategy();
        }
        if (strategy.equals("S")) {
            return new ModestStrategy();
        }
        if (strategy.equals("W")) {
            return new CustomStrategy();
        }
        if (strategy.equals("Z")) {
            return new GreedyStrategy();
        }
        return null;
    }

    public District[] getDistrictArray() {
        return districtArray;
    }

    public Party[] getPartyArray() {
        return partyArray;
    }

    public CampaignAction[] getActionArray() {
        return actionArray;
    }
}
