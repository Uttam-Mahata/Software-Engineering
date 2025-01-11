import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_fractional_knapsack_data(filename):
    """
    Plots Fractional Knapsack algorithm data and theoretical complexity curves.
    """
    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Number of Items'] = pd.to_numeric(df['Number of Items'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])


    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Number of Items'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="Fractional Knapsack's Measured Time")


    # Generate data for theoretical curves
    num_items = df['Number of Items']
    n_log_n = num_items*np.log2(num_items)
    n = num_items

    # Scale theoretical curves
    scale_n = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n.head(10))
    scale_nlogn = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_log_n.head(10))


    # Plot theoretical curves
    plt.plot(num_items, scale_n*n, linestyle = '--', label = "O(n)")
    plt.plot(num_items, scale_nlogn*n_log_n, linestyle = '-.', label = "O(n log n)")


    # Plot
    plt.title("Fractional Knapsack Algorithm Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Number of Items", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    plot_fractional_knapsack_data("fractional_knapsack_data.csv")  # Replace if needed