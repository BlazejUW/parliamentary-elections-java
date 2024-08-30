package elections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner1 = new Scanner(new File(args[0]));
        Scanner scanner2 = new Scanner(new File(args[0]));
        Scanner scanner3 = new Scanner(new File(args[0]));

        createWorld(scanner1, new HareNiemeyerMethod());
        createWorld(scanner2, new DHondtMethod());
        createWorld(scanner3, new SainteLagueMethod());
    }

    public static void conductElectionsWithSelectedMethod(District[] districtArray, Party[] partyArray, CampaignAction[] actionArray, VotingMethod method) {
        Byteland byteland = new Byteland();
        byteland.conductElections(districtArray, partyArray, actionArray, method);
    }

    private static void createWorld(Scanner scanner, VotingMethod method) {
        District[] districtArray = new District[scanner.nextInt()];
        Party[] partyArray = new Party[scanner.nextInt()];

        int numberOfActions = scanner.nextInt();
        int numberOfAttributes = scanner.nextInt();
        int numberOfMergedDistricts = scanner.nextInt();

        ElectionInterpreter interpreter = new ElectionInterpreter(scanner, partyArray, districtArray, numberOfAttributes, numberOfActions, numberOfMergedDistricts);
        interpreter.createElectionWorld();
        districtArray = interpreter.getDistrictArray();
        partyArray = interpreter.getPartyArray();
        CampaignAction[] actionArray = interpreter.getActionArray();
        conductElectionsWithSelectedMethod(districtArray, partyArray, actionArray, method);
        System.out.println("");
    }
}
