import pandas as pd
import numpy as np





def calculate_average_spin_rate(data):
    # Filter out rows where release_spin_rate is not available
    valid_data = data.dropna(subset=['release_spin_rate'])
    return valid_data.groupby('pitch_type')['release_spin_rate'].mean()


def calculate_average_velocity(data):
    # Filter out rows where release_speed is not available
    valid_data = data.dropna(subset=['release_speed'])
    return valid_data.groupby('pitch_type')['release_speed'].mean()


def calculate_whiff_rate(data):
    swings_misses = ['swinging_strike', 'swinging_strike_blocked', 'foul_tip']
    swings = swings_misses + ['hit_into_play', 'foul']

    # Filter data based on the description of swings
    pitch_data = data[data['description'].isin(swings)]
    total_swings = pitch_data.groupby('pitch_type').size()
    missed_swings = pitch_data[pitch_data['description'].isin(swings_misses)].groupby('pitch_type').size()

    # Calculate whiff rate as a decimal
    wiff_rate = (missed_swings / total_swings).fillna(0)
    return wiff_rate

