import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_time_data(filename):
    """
    Plots the time data from a CSV file and overlays a 2^n curve.

    Args:
        filename (str): The name of the CSV file.
    """
    df = pd.read_csv(filename)

    # Convert 'Input Size' and 'Time (nanoseconds)' to numeric
    df['Input Size'] = pd.to_numeric(df['Input Size'])
    df['Time (nanoseconds)'] = pd.to_numeric(df['Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the original data
    plt.plot(df['Input Size'], df['Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label='Measured Time')

    # Generate data for 2^n
    input_sizes = df['Input Size']
    two_power_n = 2**input_sizes

     # Scale 2^n to align with measured data (just for visualization)
    scale_2n = np.mean(df['Time (nanoseconds)'].head(10))/np.mean(two_power_n.head(10))

     # Plot 2^n curve
    plt.plot(input_sizes, scale_2n * two_power_n, linestyle='--', label='2^n Scaled')

    plt.title("Time vs. Input Size with 2^n Curve", fontsize=16)
    plt.xlabel("Input Size", fontsize=14)
    plt.ylabel("Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.yscale('log') # set the y scale to log
    plt.tight_layout()
    plt.show()

if __name__ == "__main__":
    plot_time_data("../tower_of_hanoi_data.csv")  # Replace with the actual file name if different