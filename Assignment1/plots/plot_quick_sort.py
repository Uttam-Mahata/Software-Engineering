import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_quick_sort_data(filename):
    """
    Plots Quick Sort algorithm data and theoretical complexity curves.
    """
    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Input Size'] = pd.to_numeric(df['Input Size'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Input Size'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="Quick Sort's Measured Time")

    # Generate data for theoretical curves
    input_sizes = df['Input Size']
    n_log_n = input_sizes * np.log2(input_sizes)
    n_squared = input_sizes**2

    # Scale theoretical curves
    scale_nlogn = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_log_n.head(10))
    scale_n2 = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_squared.head(10))


    # Plot theoretical curves
    plt.plot(input_sizes, scale_nlogn * n_log_n, linestyle='--', label="n log(n)")
    plt.plot(input_sizes, scale_n2 * n_squared, linestyle='-.', label="n^2")


    # Plot
    plt.title("Quick Sort Algorithm Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Input Size", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    plot_quick_sort_data("quick_sort_data.csv")  # Replace if needed