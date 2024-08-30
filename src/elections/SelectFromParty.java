package elections;

import java.util.LinkedList;

public class SelectFromParty {

    public static LinkedList<DistrictPartyCandidates> selectCandidatesFromParty(LinkedList<DistrictPartyCandidates> allCandidates, Party selectedParty) {
        LinkedList<DistrictPartyCandidates> selectedCandidates = new LinkedList<>();
        for (DistrictPartyCandidates partyCandidates : allCandidates) {
            if (partyCandidates.getParty() == selectedParty) {
                selectedCandidates.add(partyCandidates);
            }
        }

        if (selectedCandidates.size() == 1) {
            return selectedCandidates;
        } else if (selectedCandidates.size() == 2) {
            Candidate[] list1 = selectedCandidates.get(0).getPartyCandidates();
            Candidate[] list2 = selectedCandidates.get(1).getPartyCandidates();
            Candidate[] combined = new Candidate[list1.length + list2.length];

            for (int i = 0; i < list1.length; i++) {
                combined[i] = list1[i];
            }
            for (int j = 0; j < list2.length; j++) {
                combined[list1.length + j] = list2[j];
            }

            DistrictPartyCandidates result = new DistrictPartyCandidates(selectedParty, combined);

            LinkedList<DistrictPartyCandidates> resultList = new LinkedList<>();
            resultList.add(result);
            return resultList;
        } else {
            return null;
        }
    }
}
