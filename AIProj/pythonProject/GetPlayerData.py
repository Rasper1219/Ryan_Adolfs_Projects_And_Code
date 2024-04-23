from pybaseball import playerid_lookup, statcast_batter, statcast_pitcher, pitching_stats
import pandas as pd
import batters
import pitchers
import Model


# max 9
Lineup = [('Greene', 'Riley'),
          ('Canha', 'Mark'),
          ('Torkelson', 'Spencer'),
          ('Carpenter', 'Kerry'),
          ('McKinstry', 'Zach'),
          ('Keith', 'Colt'),
          ('Báez', 'Javier'),
          ('Meadows', 'Parker'),
          ('Rogers', 'Jake')]

# max 5
Pitchers = [('Dunning', 'Dane'),
            ('Latz', 'Jacob'),
            ('Pruitt', 'Austin'),
            ('Robertson', 'David'),
            ('Yates', 'Kirby')]


# Look up the player ID for a batter (e.g., Javier Baez)

# for names in Lineup:
#     player_id = playerid_lookup(names[0], names[1])['key_mlbam'].iloc[0]
#     batter_data = statcast_batter('2022-01-01', '2023-12-31', player_id)
#     batter_data.to_csv('Batter_' + names[0] + '_data.csv', index=False)
#
# for names in Pitchers:
#     player_id = playerid_lookup(names[0], names[1])['key_mlbam'].iloc[0]
#     batter_data = statcast_pitcher('2022-01-01', '2023-12-31', player_id)
#     batter_data.to_csv('Pitcher_' + names[0] + '_data.csv', index=False)


def readPlayerData(playerList, playerType):
    player_data_list = []  # Initialize an empty list to store DataFrames
    for first, _ in playerList:
        # Update the file path to match how files are named when saved
        file_path = f'{playerType}_{first}_data.csv'  # Prefix is based on player_type and first name
        try:
            data = pd.read_csv(file_path)
            player_data_list.append(data)  # Append the DataFrame to the list
        except FileNotFoundError:
            print(f"No data found for {first}")
    return player_data_list


Batters = readPlayerData(Lineup, 'Batter')
PitchingStaff = readPlayerData(Pitchers, 'Pitcher')


def format_base_state(base_state):
    """ Returns a string describing the runner situation based on the base_state index """
    descriptions = [
        "None", "1st", "2nd", "3rd",
        "1st and 2nd", "1st and 3rd", "2nd and 3rd", "1st, 2nd, and 3rd"
    ]
    return descriptions[base_state]

def main():
    # Load player data
    batter = Model.create_player_from_csv('Greene', 'Riley', 'Batter_Greene_data.csv')
    pitcher = Model.create_player_from_csv('Dunning', 'Dane', 'Pitcher_Dunning_data.csv')


    simulation_results = Model.main_simulation(pitcher, batter, 1000)
    common_sequences = Model.analyze_outcomes(simulation_results)

    print("Recommended pitch sequences for " + pitcher.Last_Name + " V.S. " + batter.Last_Name)
    for outcome, sequence in common_sequences.items():
        print(f"{outcome}: {sequence}")

    print("TESTING...")

    results = Model.testing_simulation(pitcher,batter,common_sequences)
    print("Testing Simulation Results:")
    print(results)

    report = Model.overall_pitcher_performance(pitcher)
    print("VS Pitcher's historical records")
    print(report)

    # Print or analyze the outcome
    # print("At-bat actions and rewards:")
    # for state, pitch_type, reward, outcome_type, next_state in at_bat_outcome:
    #     outs, balls, strikes, base_state = state
    #     base_state_description = format_base_state(base_state)
    #     print(f"Current state: {balls} Balls, {strikes} Strikes, Runners on {base_state_description} with {outs} outs")
    #     print(f"Action: {pitch_type}, Reward: {reward}, Outcome: {outcome_type}\n")


if __name__ == "__main__":
    main()



