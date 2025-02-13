import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_nqueens_data(filename):
    """
    Plots N-Queens algorithm data and theoretical complexity curves.
    """
    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Input Size'] = pd.to_numeric(df['Input Size'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Input Size'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="N-Queens Measured Time")

    # Generate data for theoretical curves
    input_sizes = df['Input Size']
    two_power_n = 2**input_sizes
    n_factorial = [np.math.factorial(n) for n in input_sizes]

    # Scale theoretical curves
    scale_2n = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(two_power_n[:10]) # scaling first 10 rows
    scale_nfactorial = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_factorial[:10])


    # Plot theoretical curves
    plt.plot(input_sizes, scale_2n * two_power_n, linestyle='--', label="O(2^n)")
    plt.plot(input_sizes, scale_nfactorial, linestyle='-.', label="O(n!) Scaled")


    # Plot
    plt.title("N-Queens Algorithm Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Input Size (N)", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.yscale('log') # to visualize exponential curves properly
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    plot_nqueens_data("nqueens_data.csv")  # Replace if needed