import pandas as pd
import numpy as np

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


# needs fixing
def spin_rate_wiff_rate(data):
    # Define bins for spin rate and speed
    bins_spin = np.array([0, 1000, 1500, 2000, 2500, 3000, np.inf])
    bins_speed = np.array(list(range(70, 104, 2)) + [np.inf])

    # Initialize an empty DataFrame to hold the results
    matrix = pd.DataFrame(index=pd.IntervalIndex.from_breaks(bins_spin, closed='left'),
                          columns=pd.IntervalIndex.from_breaks(bins_speed, closed='left'))

    # Loop through each pitch type in the data
    for pitch in data['pitch_type'].unique():
        filtered_data = data[data['pitch_type'] == pitch]
        for spin_range in matrix.index:
            for speed_range in matrix.columns:
                sub_data = filtered_data[(filtered_data['release_spin_rate'] >= spin_range.left) &
                                         (filtered_data['release_spin_rate'] < spin_range.right) &
                                         (filtered_data['release_speed'] >= speed_range.left) &
                                         (filtered_data['release_speed'] < speed_range.right)]

                # Calculate wiff rate only if there is enough data
                if not sub_data.empty:
                    matrix.loc[spin_range, speed_range] = calculate_wiff_rate(sub_data).get(pitch, 0)
                else:
                    matrix.loc[spin_range, speed_range] = np.nan  # Use NaN for empty data sets

    return matrix


def calculate_hit_type_rates(data):
    # Define the categories and their corresponding descriptions in the data
    hit_types = {
        'ground_ball': ['ground_ball'],
        'fly_ball': ['fly_ball', 'popup'],  # Include popup in the fly_ball category
        'line_drive': ['line_drive']
    }

    hit_data = data[data['description'] == 'hit_into_play']
    total_hits = hit_data.groupby('pitch_type').size()

    results = {}
    for hit_type, descriptions in hit_types.items():
        # Filter data by descriptions for each hit type
        specific_hits = hit_data[hit_data['bb_type'].isin(descriptions)].groupby('pitch_type').size()
        rate = (specific_hits / total_hits).fillna(0)
        results[hit_type] = rate

    return results





def batted_ball_rate_by_pitch(data):
    results_by_count = {}
    hit_type_rates = calculate_hit_type_rates(data)  # This now includes popup under fly_ball
    for pitch_type in data['pitch_type'].unique():
        results_by_count[pitch_type] = {}
        for balls in range(4):  # Assuming up to 3 balls
            for strikes in range(3):  # Assuming up to 2 strikes
                filtered_data = data[
                    (data['pitch_type'] == pitch_type) & (data['balls'] == balls) & (data['strikes'] == strikes)]
                rates = {}
                for hit_type, rates_df in hit_type_rates.items():
                    rates[hit_type] = rates_df.get(pitch_type, 0)
                results_by_count[pitch_type][(balls, strikes)] = rates
    return results_by_count
