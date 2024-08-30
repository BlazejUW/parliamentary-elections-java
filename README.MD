# Parliamentary Elections

This program simulates parliamentary elections in an imaginary country called Byteland. The process consists of the formation of constituencies, an election campaign, voting, and converting votes into seats using three different methods. This program was developed for the Object-Oriented Programming course in 2020. 

## Table of Contents

- [Election Rules in Byteland](#election-rules-in-byteland)
  - [Constituencies](#constituencies)
  - [Candidates for Parliament](#candidates-for-parliament)
  - [Voters](#voters)
- [Election Process](#election-process)
  - [Campaign](#campaign)
- [Implementation Details](#implementation-details)
  - [Class Structure](#class-structure)
  - [Vote Counting Methods](#vote-counting-methods)
  - [Key Classes and Files](#key-classes-and-files)
- [Usage](#usage)
- [Program Functionality](#program-functionality)
  - [Input](#input)
  - [Output](#output)

## Election Rules in Byteland

### Constituencies

Byteland is divided into `n` basic constituencies, numbered 1, 2, 3, ..., n. Each constituency contains a specific number of voters, which is always a multiple of 10. Constituencies can be merged (only those with consecutive numbers and each constituency can be merged with only one other).

### Candidates for Parliament

In each constituency, the number of seats is determined by the formula `n/10`, where `n` is the number of voters in that constituency. Each party in a constituency nominates exactly as many candidates as there are seats to fill, aiming to win all the seats. Each candidate is associated with one constituency and can only be chosen from that constituency. Voters can only vote for candidates from their own constituency.

### Voters

Voters are categorized based on their decision-making criteria for choosing a candidate:

1. **Loyal Party Voter** (`LoyalPartyVoter` in `src/elections/LoyalPartyVoter.java`): Always votes for a random candidate from a chosen party list.
2. **Loyal Candidate Voter** (`LoyalPartyCandidateVoter` in `src/elections/LoyalPartyCandidateVoter.java`): Always votes for a specific candidate.
3. **Single Attribute Minimizer** (`SingleAttributeVoter` in `src/elections/SingleAttributeVoter.java`): Chooses the candidate with the lowest level of a specific attribute.
4. **Single Attribute Maximizer** (`SingleAttributeVoter` in `src/elections/SingleAttributeVoter.java`): Chooses the candidate with the highest level of a specific attribute.
5. **Versatile Voter** (`VersatileVoter` in `src/elections/VersatileVoter.java`): Calculates the weighted sum of attributes for each candidate and chooses based on the highest weighted sum.
6. **Party-restricted Single Attribute Voter** (`SingleAttributePartyVoter` in `src/elections/SingleAttributePartyVoter.java`): Chooses based on a single attribute but restricts choices to one party.
7. **Party-restricted Versatile Voter** (`VersatilePartyVoter` in `src/elections/VersatilePartyVoter.java`): Similar to a versatile voter but restricted to candidates from one party.

## Election Process

Each voter votes for exactly one candidate from their own constituency. Votes are then summed up and converted into seats using three methods:

1. **D'Hondt Method**: Implemented in the `DHondtMethod` class (`src/elections/DHondtMethod.java`).
2. **Sainte-Laguë Method**: Implemented in the `SainteLagueMethod` class (`src/elections/SainteLagueMethod.java`).
3. **Hare-Niemeyer Method**: Implemented in the `HareNiemeyerMethod` class (`src/elections/HareNiemeyerMethod.java`).

These methods are used to allocate seats proportionally based on the number of votes each party receives in each district.

### Campaign

Before the elections, a campaign is conducted where each party has a specific budget. This budget allows parties to undertake actions that influence voters. Each action has a cost and an effect represented by a vector that describes how the weights of attributes change.

Actions can increase or decrease the weight of each attribute by a specific amount within the range of -z to z, without exceeding the minimum and maximum weight values. Actions are performed within a constituency, and their cost is calculated as the sum of the absolute values of the change vector multiplied by the number of voters in that constituency.

Parties continue to take actions as long as their budget allows. Strategies for actions include:

1. **Bold Strategy** (`BoldStrategy` in `src/elections/BoldStrategy.java`): Always chooses the most expensive possible action within the budget.
2. **Modest Strategy** (`ModestStrategy` in `src/elections/ModestStrategy.java`): Always chooses the cheapest possible action.
3. **Greedy Strategy** (`GreedyStrategy` in `src/elections/GreedyStrategy.java`): Chooses the action that maximizes the total weighted sum of attributes for its candidates.

## Implementation Details

### Class Structure

The program is implemented using multiple classes that correspond to different aspects of the election process. The key classes are:

- `District` (`src/elections/District.java`): Represents an electoral district, managing voters and candidates.
- `Candidate` (`src/elections/Candidate.java`): Represents a candidate running for a parliamentary seat.
- `Party` (`src/elections/Party.java`): Represents a political party, including its campaign strategy and budget.
- `CampaignAction` (`src/elections/CampaignAction.java`): Represents an action that can be taken during the campaign to influence voters.
- `Strategy` interface and its implementations (`src/elections/Strategy.java`, `src/elections/ModestStrategy.java`, etc.): Define various strategies that parties use during campaigns.
- `VotingResults` (`src/elections/VotingResults.java`): Stores the results of the voting process, including the number of votes per party.

### Vote Counting Methods

Vote counting and seat allocation methods are implemented as follows:

- **`DHondtMethod`** (`src/elections/DHondtMethod.java`): Implements the D'Hondt method for proportional representation.
- **`SainteLagueMethod`** (`src/elections/SainteLagueMethod.java`): Implements the Sainte-Laguë method for proportional representation.
- **`HareNiemeyerMethod`** (`src/elections/HareNiemeyerMethod.java`): Implements the Hare-Niemeyer method for proportional representation.

These classes are responsible for converting votes into seats based on different mathematical formulas to ensure proportional representation in the parliament.

### Key Classes and Files

- **`VotingResults`** (`src/elections/VotingResults.java`): Holds the results of the voting in each district and overall.
- **`VersatileVoter`** (`src/elections/VersatileVoter.java`): Represents a voter who makes decisions based on a weighted sum of candidate attributes.
- **`SingleAttributeVoter`** (`src/elections/SingleAttributeVoter.java`): Represents voters who decide based on the maximization or minimization of a single attribute.
- **`LoyalPartyVoter`** (`src/elections/LoyalPartyVoter.java`): Represents voters who are loyal to a party and vote for its candidates.
- **`LoyalPartyCandidateVoter`** (`src/elections/LoyalPartyCandidateVoter.java`): Represents voters who are loyal to a specific candidate from a party.
- **`GreedyStrategy`** (`src/elections/GreedyStrategy.java`): Strategy that picks actions to maximize impact within budget constraints.
- **`ModestStrategy`** (`src/elections/ModestStrategy.java`): Strategy that picks the least expensive actions to maximize budget efficiency.

Refer to the specific files for detailed implementations of each class and the election simulation process.

## Usage

The program reads all parameters from input files (the path to the input file is the only argument for the program). For detailed input and output formats, refer to the [Input](#input) and [Output](#output) sections.

---

For more details, please review the source code files located in the `src/elections/` directory.

## Program Functionality

### Input

The program reads all parameters from input files (the path to the input file is the only argument of the program).

**Input file format:**

1. The first line contains **four numbers**:
   - n - number of primary electoral districts
   - p - number of parties
   - d - number of possible actions
   - c - number of candidate attributes

2. The second line contains:
   - The **number of pairs of primary electoral districts** that should be **merged**
   - Followed by the corresponding pairs in the format (o, o+1)

3. The third line contains **p party names**.

4. The fourth line contains **p numbers** representing the **budgets** of each party.

5. The fifth line consists of **p characters** representing the **strategies** of each party:
   - ‘R’ - “Bold” strategy
   - ‘S’ - “Modest” strategy
   - ‘W’ - Additional custom strategy
   - ‘Z’ - “Greedy” strategy

6. The sixth line contains **n numbers** in the format 10k, which represent the **number of voters** in each primary electoral district.

7. The following lines describe each **candidate**:
   - Each candidate is described in a separate line,
   - Candidates are grouped by electoral districts (in increasing order of district numbers), within each district by parties (the order of parties is as provided earlier), and within each party, candidates are listed according to their position on the list.
   - Each line has the format: `first name`, `last name`, `district number`, `party name`, `list position`, `w1 w2 ... wc`
   - `w1 w2 ... wc` are the attribute values (integers in the range [-100, 100]).

8. Subsequent lines describe **voters**:
   - Each line describes a single voter,
   - Voters are listed first for district 1, then for district 2, and so on.
   - Each line includes `first name`, `last name`, `primary electoral district number`, and **voter type**,
   - Voter types are represented as numbers:


- **Loyal Party Voter**: Always votes for a random candidate from a specified party list.
- **Loyal Candidate Voter**: Always votes for a specific candidate.
- **Single-Attribute Minimizer (all parties)**: Chooses a candidate from all parties who has the lowest level of a selected attribute (if there is a tie, the choice is random).
- **Single-Attribute Maximizer (all parties)**: Chooses a candidate from all parties who has the highest level of a selected attribute (if there is a tie, the choice is random).
- **Versatile Voter (all parties)**: Calculates a weighted sum of attributes for each candidate using their own set of weights (which are integers in the range of -100 to 100) and chooses the candidate with the highest weighted sum.
- **Single-Attribute Minimizer (one party)**: Chooses a candidate from a single party who has the lowest level of a selected attribute.
- **Single-Attribute Maximizer (one party)**: Chooses a candidate from a single party who has the highest level of a selected attribute.
- **Versatile Voter (one party)**: Calculates a weighted sum of attributes for each candidate within a single party and chooses the candidate with the highest weighted sum.

   - For voters of types 1 and 2, the line also includes the **party name**,
   - For voters of type 2, the **candidate's position on the party list** is also included,
   - For voters of types 5 and 8, the line includes the **base values of weights** that the voter assigns to each candidate attribute: w1, w2, ..., wc.
   - For voters of type 8, the **party name** should be added at the end (after the weights).
   - For voters of types 3, 4, 6, 7, the line includes a number indicating which candidate attribute should be maximized/minimized, and for types 6 and 7, the **party name** follows.

9. The next **d lines** describe **possible actions**, each line containing **c integers** indicating how the values of each of the c candidate attributes change in the electoral district where the action was applied.

**The input data is assumed to be correct.**

### Output

For each of the three methods of converting votes into seats, the program outputs:

1. The name of the vote-to-seat conversion method.
2. For each electoral district:
   - District number,
   - Voter's first name and last name, the candidate they voted for,
   - Candidate's first name, last name, party affiliation, position on the list, and the total number of votes received,
   - A series of pairs: **party name** and **number of seats** won in that district.
3. In total (for all districts): a series of pairs **party name** and **total number of seats** won across all districts.
