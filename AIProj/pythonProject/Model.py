import numpy as np
import pandas as pd
import random
import batters
import pitchers

from pybaseball import playerid_lookup

import GetPlayerData

class Player:
    def __init__(self, player_id, Last, First, stats):
        self.player_id = player_id
        self.Last_Name = Last
        self.First_Name = First
        self.data = stats

    def get_stat(self, stat_name):
        return self.stats.get(stat_name, None)


def create_player_from_csv(last_name, first_name, file_path):
    data = pd.read_csv(file_path)
    player_id = playerid_lookup(last_name, first_name)['key_mlbam'].iloc[0]
    return Player(player_id, last_name, first_name, data)


def get_pitch_types(data):
    # Filter the data to exclude specific pitch types
    filtered_data = data[~data['pitch_type'].isin(['PO', 'EP', 'IB'])]
    # Return the unique pitch types after filtering
    return filtered_data['pitch_type'].dropna().unique()



def calculate_wiff_rate(data):
    swings_misses = ['swinging_strike', 'swinging_strike_blocked', 'foul_tip']
    swings = swings_misses + ['hit_into_play', 'foul']

    # Group by pitch type and filter descriptions
    pitch_data = data[data['description'].isin(swings)]
    total_swings = pitch_data.groupby('pitch_type').size()
    missed_swings = pitch_data[pitch_data['description'].isin(swings_misses)].groupby('pitch_type').size()

    # Calculate wiff rate as a decimal
    wiff_rate = (missed_swings / total_swings).fillna(0)
    return wiff_rate


def calculate_wiff_rate_by_count(data):
    wiff_rates = calculate_wiff_rate(data)
    # Create an empty dictionary or a structure to store rates by count
    wiff_rate_by_count = {}
    for pitch_type in data['pitch_type'].unique():
        wiff_rate_by_count[pitch_type] = {}
        for balls in range(4):  # assuming up to 3 balls
            for strikes in range(3):  # assuming up to 2 strikes
                pitch_data = data[(data['pitch_type'] == pitch_type) & (data['balls'] == balls) & (data['strikes'] == strikes)]
                wiff_rate_by_count[pitch_type][(balls, strikes)] = calculate_wiff_rate(pitch_data).get(pitch_type, 0)
    return wiff_rate_by_count


batter = create_player_from_csv('Greene', 'Riley', 'Batter_Greene_data.csv')
pitcher = create_player_from_csv('Dunning', 'Dane', 'Pitcher_Dunning_data.csv')


action_space = get_pitch_types(pitcher.data)
print("Available Pitch Actions:", action_space)


# Constants representing the sizes of each dimension in the state space
NUM_OUTS = 4 # 4 possibilities including 0,1,2, and 3 outs
NUM_BALLS = 4  # Up to 3 balls, since the 4th ball is a walk
NUM_STRIKES = 3  # Up to 2 strikes, since the 3rd strike is a strikeout
NUM_BASE_STATES = 8

# Initialize a 4D numpy array with None to represent the state space
# The value None indicates an invalid state
state_space = np.full((NUM_OUTS, NUM_BALLS, NUM_STRIKES, NUM_BASE_STATES), None, dtype=object)

# Now populate the state space with valid transitions
for out in range(NUM_OUTS):
    for balls in range(NUM_BALLS):
        for strikes in range(NUM_STRIKES):
            for base_state in range(NUM_BASE_STATES):
                # A list of valid next states from the current state
                next_states = []

                # Add the next state for a ball
                if balls < NUM_BALLS - 1:
                    next_states.append((out, balls + 1, strikes, base_state))
                else:
                    # A walk, runners advance and the count resets
                    next_states.append((out, 0, 0, min(base_state + 1, NUM_BASE_STATES - 1)))

                # Add the next state for a strike
                if strikes < NUM_STRIKES - 1:
                    next_states.append((out, balls, strikes + 1, base_state))
                else:
                    # A strikeout, one out is added
                    if out < NUM_OUTS - 1:
                        next_states.append((out + 1, 0, 0, base_state))
                    else:
                        # End of the inning, reset everything
                        next_states.append((0, 0, 0, 0))

                # Assign the list of valid next states to the current state
                state_space[out, balls, strikes, base_state] = next_states


# Dimensions of the Q-table based on state space and action space
num_actions = len(action_space)
q_table = np.zeros((NUM_OUTS, NUM_BALLS, NUM_STRIKES, NUM_BASE_STATES, num_actions))

# To access the Q-value for a specific state and action, you would do something like:
# q_value = q_table[out, balls, strikes, base_state, action_index]
learning_rate = 0.25
discount_factor = 0.95
epsilon = 1.0  # Starting value for epsilon
epsilon_decay = 0.99  # Decay multiplier for epsilon
epsilon_min = 0.01  # Minimum value for epsilon


def pitch_outcome(pitcher, batter, state, pitch_type):
    outs, balls, strikes, base_state = state
    done = False
    reward = 0
    next_state = state

    # Calculate the outcome probabilities for the current pitch
    prob_strike = calculate_strike_probability(pitcher, batter, pitch_type)
    prob_groundball = calculate_combined_batted_ball_probability(pitcher, batter, pitch_type, 'ground_ball')
    prob_flyball = calculate_combined_batted_ball_probability(pitcher, batter, pitch_type, 'fly_ball')
    prob_line_drive = calculate_combined_batted_ball_probability(pitcher, batter, 'FF', 'line_drive')
    prob_ball = calculate_ball_probability(pitcher, pitch_type)

    total_prob = prob_strike + prob_ball + prob_groundball + prob_flyball + prob_line_drive
    probabilities = [prob_strike, prob_ball, prob_groundball, prob_flyball,
                     prob_line_drive] / total_prob

    # Determine the outcome
    # print(probabilities)
    outcome = np.random.choice(['strike', 'ball', 'groundball', 'flyball', 'line_drive'],
                               p=probabilities)

    # Handle the outcomes according to your existing logic
    if outcome == 'strike':
        if strikes == 2:
            reward = 10  # Reward for a strikeout
            outs += 1
            outcome = 'strikeout'
            if outs >= 3:
                done = True  # Inning is over
                next_state = (0, 0, 0, 0)
            else:
                next_state = (outs, 0, 0, base_state)
        else:
            strikes += 1
            reward = 3  # Reward for a strike
            next_state = (outs, balls, strikes, base_state)

    elif outcome == 'ball':
        if balls == 3:
            reward = -20  # Penalty for a walk
            outcome = 'walk'
            next_state = (outs, 0, 0, min(base_state + 1, NUM_BASE_STATES - 1))
        else:
            balls += 1
            reward = -1  # Penalty for a ball
            next_state = (outs, balls, strikes, base_state)

    elif outcome == 'groundball':
        outs += 1
        reward = 7  # Reward for inducing a groundball
        if outs >= 3:
            done = True
            next_state = (0, 0, 0, 0)
        else:
            next_state = (outs, 0, 0, base_state)

    elif outcome == 'flyball':
        outs += 1
        reward = 5  # Reward for inducing a flyball
        if outs >= 3:
            done = True
            next_state = (0, 0, 0, 0)
        else:
            next_state = (outs, 0, 0, base_state)

    elif outcome == 'line_drive':
        reward = -10  # Penalty for inducing a line drive (assuming it's a negative outcome)
        # No automatic out added for line drives
        next_state = (outs, 0, 0, base_state)

    return next_state, reward, done, outcome



def choose_action(state, epsilon=0.2):
    if np.random.random() < epsilon:
        return np.random.choice(range(num_actions))  # Explore
    else:
        return np.argmax(q_table[state])  # Exploit


def update_q_table(state, action, reward, next_state):
    old_value = q_table[state + (action,)]
    future_optimal_value = np.max(q_table[next_state])
    new_value = old_value + learning_rate * (reward + discount_factor * future_optimal_value - old_value)
    q_table[state + (action,)] = new_value


def simulate_at_bat(pitcher, batter):
    current_state = (0, 0, 0, 0)  # Initial state with no outs, no balls, no strikes, no runners
    results = []  # To store outcomes and states
    pitch_sequence = []  # To track the sequence of pitches in the current at-bat

    while True:
        action_index = choose_action(current_state, epsilon)
        pitch_type = action_space[action_index]
        next_state, reward, inning_over, outcome = pitch_outcome(pitcher, batter, current_state, pitch_type)

        pitch_sequence.append(pitch_type)  # Add the pitch type to the sequence

        if outcome in ['strikeout', 'walk', 'line_drive', 'groundball', 'flyball']:
            # If a terminal at-bat event occurs, record the sequence and outcome
            results.append((tuple(pitch_sequence), outcome))
            pitch_sequence = []  # Reset the sequence for the next at-bat

            if inning_over:
                break  # If the inning is over, exit the simulation

            # Reset state for new batter due to end of at-bat, but not inning
            current_state = (next_state[0], 0, 0, next_state[3])  # Reset balls and strikes, maintain outs and base state
        else:
            current_state = next_state  # Continue the at-bat with the updated state

        update_q_table(current_state, action_index, reward, next_state)  # Update Q-table based on action taken

    return q_table, results

def analyze_outcomes(simulation_results):
    outcome_sequences = {'strikeout': [], 'walk': [], 'line_drive': [], 'groundball': [], 'flyball': []}

    # Collect all sequences for each outcome
    for simulation in simulation_results:
        for pitch_sequence, outcome in simulation:
            if outcome in outcome_sequences:
                outcome_sequences[outcome].append(pitch_sequence)

    # Find the most common pitch for each position in the sequence for each outcome
    recommended_sequences = {}
    for outcome, sequences in outcome_sequences.items():
        if sequences:
            # Determine the maximum length of sequences for this outcome
            max_length = max(len(seq) for seq in sequences)
            # Initialize list to store the most common pitches for each position
            most_common_pitches = []

            # Iterate over each pitch position
            for i in range(max_length):
                pitch_count = {}
                # Collect pitches for the current position across all sequences
                for seq in sequences:
                    if i < len(seq):
                        pitch = seq[i]
                        if pitch not in pitch_count:
                            pitch_count[pitch] = 0
                        pitch_count[pitch] += 1

                # Find the most common pitch for the current position
                if pitch_count:
                    most_common_pitch = max(pitch_count, key=pitch_count.get)
                    most_common_pitches.append(most_common_pitch)

            recommended_sequences[outcome] = tuple(most_common_pitches)

    return recommended_sequences

def get_pitch_data(player, pitch_type):
    """Retrieve rows from player data for a specific pitch type."""
    return player.data[player.data['pitch_type'] == pitch_type]

def calculate_percentage(condition, total):
    """Calculate percentage of a condition given the total occurrences."""
    if total > 0:
        return condition.sum() / total
    else:
        return 0


def calculate_strike_probability(pitcher, batter, pitch_type):
    # Get the pitch data specific to the pitch type from the pitcher's dataset
    pitcher_data = pitcher.data[pitcher.data['pitch_type'] == pitch_type]
    # Calculate the basic strike probability from called strikes, swinging strikes, and fouls
    basic_strikes = pitcher_data['description'].isin(['called_strike', 'swinging_strike', 'foul', 'foul_tip'])
    basic_strike_rate = basic_strikes.mean()

    # Calculate whiff rate for this pitch type from batter's perspective
    if pitch_type in batter.data['pitch_type'].unique():
        batter_pitch_data = batter.data[batter.data['pitch_type'] == pitch_type]
        batter_wiff_rate = calculate_wiff_rate(batter_pitch_data)
        batter_wiff_rate_specific = batter_wiff_rate.get(pitch_type, 0)
    else:
        batter_wiff_rate_specific = 0

    # Combine the probabilities, adjusting weights as necessary
    # Here, we are simply averaging them; adjust the weights based on model testing and domain knowledge
    combined_strike_probability = (basic_strike_rate + batter_wiff_rate_specific) / 2
    return combined_strike_probability


def calculate_strikeout_probability(pitcher, batter, pitch_type, pitcher_weight=0.6, batter_weight=0.4):
    # Define terminal events that signify the end of an at-bat
    terminal_events = ['single', 'double', 'triple', 'home_run', 'strikeout', 'walk', 'hit_by_pitch',
                       'groundout', 'flyout', 'lineout', 'pop_out', 'double_play', 'fielders_choice_out',
                       'caught_stealing', 'sac_fly', 'sac_bunt', 'sac_fly_double_play', 'sac_bunt_double_play']

    # Extract pitcher data for the specific pitch type
    pitcher_data = pitcher.data[pitcher.data['pitch_type'] == pitch_type]
    pitcher_terminal_at_bats = pitcher_data[pitcher_data['events'].isin(terminal_events)]

    # Calculate the pitcher's strikeout probability
    pitcher_strikeouts = pitcher_terminal_at_bats[pitcher_terminal_at_bats['events'] == 'strikeout']
    pitcher_strikeout_at_bats = pitcher_strikeouts['events'].count()
    pitcher_total_at_bats = pitcher_terminal_at_bats['events'].count()

    pitcher_strikeout_prob = pitcher_strikeout_at_bats / max(pitcher_total_at_bats, 1)  # Avoid division by zero

    # Extract batter data for the same pitch type
    batter_data = batter.data[batter.data['pitch_type'] == pitch_type]
    batter_terminal_at_bats = batter_data[batter_data['events'].isin(terminal_events)]

    # Calculate the batter's strikeout probability
    batter_strikeouts = batter_terminal_at_bats[batter_terminal_at_bats['events'] == 'strikeout']
    batter_strikeout_at_bats = batter_strikeouts['events'].count()
    batter_total_at_bats = batter_terminal_at_bats['events'].count()

    batter_strikeout_prob = batter_strikeout_at_bats / max(batter_total_at_bats, 1)  # Avoid division by zero

    # Weighted average of pitcher's and batter's strikeout probabilities
    combined_prob = (pitcher_strikeout_prob * pitcher_weight) + (batter_strikeout_prob * batter_weight)
    return combined_prob


def calculate_ball_probability(pitcher, pitch_type):
    data = get_pitch_data(pitcher, pitch_type)
    balls = data['description'].isin(['ball'])
    return calculate_percentage(balls, len(data))


def calculate_batted_ball_probability(data, pitch_type, event_type):
    # Filter data for the specific pitch type
    pitch_data = data[data['pitch_type'] == pitch_type]
    # Consider only terminal at-bats
    terminal_events = ['single', 'double', 'triple', 'home_run', 'strikeout', 'walk', 'hit_by_pitch',
                       'groundout', 'flyout', 'lineout', 'pop_out', 'double_play', 'fielders_choice_out',
                       'caught_stealing', 'sac_fly', 'sac_bunt', 'sac_fly_double_play', 'sac_bunt_double_play']
    terminal_at_bats = pitch_data[pitch_data['events'].isin(terminal_events)]

    # Filter data for specific batted ball types
    batted_ball_events = terminal_at_bats[terminal_at_bats['bb_type'] == event_type]
    num_batted_ball_events = batted_ball_events['bb_type'].count()
    total_terminal_at_bats = terminal_at_bats['events'].count()

    # Calculate probability
    batted_ball_prob = num_batted_ball_events / max(total_terminal_at_bats, 1)  # Avoid division by zero
    return batted_ball_prob


def calculate_combined_batted_ball_probability(pitcher, batter, pitch_type, event_type, pitcher_weight=0.6, batter_weight=0.4):
    pitcher_prob = calculate_batted_ball_probability(pitcher.data, pitch_type, event_type)
    batter_prob = calculate_batted_ball_probability(batter.data, pitch_type, event_type)
    combined_prob = (pitcher_prob * pitcher_weight) + (batter_prob * batter_weight)
    return combined_prob

def testing_simulation(pitcher, batter, pitch_sequences, num_trials=200):
    outcome_results = {key: 0 for key in pitch_sequences.keys()}  # Initialize result dictionary

    # Iterate over each type of pitch sequence
    for outcome, sequence in pitch_sequences.items():
        print(f"Testing sequence for {outcome}: {sequence}")
        for _ in range(num_trials):
            current_state = (0, 0, 0, 0)  # Reset state for each at-bat
            at_bat_active = True

            # Simulate the at-bat using the prescribed sequence
            for pitch_type in sequence:
                if not at_bat_active:
                    break
                next_state, reward, done, sim_outcome = pitch_outcome(pitcher, batter, current_state, pitch_type)
                # Check if the at-bat is over due to any outcome
                if sim_outcome in ['strikeout', 'walk', 'line_drive', 'groundball', 'flyball']:
                    if sim_outcome == outcome:
                        outcome_results[outcome] += 1
                    break  # End the at-bat after any conclusive outcome
                current_state = next_state

    # Calculate and print the effectiveness of each sequence
    for outcome, count in outcome_results.items():
        effectiveness = count / num_trials * 100  # percentage of desired outcome
        print(f"Effectiveness of the {outcome} sequence: {effectiveness:.2f}%")

    return outcome_results

def main_simulation(pitcher, batter, num_episodes=1000):
    global epsilon  # Use the global epsilon variable
    simulation_results = []

    for episode in range(num_episodes):
        _, results = simulate_at_bat(pitcher, batter)
        simulation_results.append(results)
        # Decay epsilon
        epsilon = max(epsilon_min, epsilon * epsilon_decay)
        if episode % 100 == 0:
            print(f"Episode {episode+1}, Epsilon: {epsilon}")

    return simulation_results


def overall_pitcher_performance(pitcher):
    # Consider all events related to pitcher's data
    terminal_events = [
        'single', 'double', 'triple', 'home_run', 'strikeout', 'walk',
        'hit_by_pitch', 'groundout', 'flyout', 'lineout', 'pop_out',
        'double_play', 'fielders_choice_out', 'caught_stealing',
        'sac_fly', 'sac_bunt', 'sac_fly_double_play', 'sac_bunt_double_play'
    ]

    # Filter pitcher data for terminal events
    terminal_data = pitcher.data[pitcher.data['events'].isin(terminal_events)]
    total_terminal_at_bats = len(terminal_data)

    # Calculate probabilities
    strikeout_prob = len(terminal_data[terminal_data['events'] == 'strikeout']) / total_terminal_at_bats
    groundball_prob = len(terminal_data[terminal_data['bb_type'] == 'ground_ball']) / total_terminal_at_bats
    line_drive_prob = len(terminal_data[terminal_data['bb_type'] == 'line_drive']) / total_terminal_at_bats
    flyball_prob = len(terminal_data[terminal_data['bb_type'] == 'fly_ball']) / total_terminal_at_bats

    report = {
        'Overall Strikeout Probability': strikeout_prob,
        'Overall Groundball Probability': groundball_prob,
        'Overall Line Drive Probability': line_drive_prob,
        'Overall Flyball Probability': flyball_prob
    }

    return report