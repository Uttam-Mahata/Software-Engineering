import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_nearest_neighbor_tsp_data(filename):
    """
    Plots Nearest Neighbor TSP algorithm data and theoretical complexity curves.
    """
    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Number of Cities'] = pd.to_numeric(df['Number of Cities'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Number of Cities'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="Nearest Neighbor TSP Measured Time")

    # Generate data for theoretical curves
    num_cities = df['Number of Cities']
    n_squared = num_cities**2
    n_log_n = num_cities* np.log2(num_cities)


    # Scale theoretical curves
    scale_n2 = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_squared.head(10))
    scale_nlogn = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_log_n.head(10))


    # Plot theoretical curves
    plt.plot(num_cities, scale_n2 * n_squared, linestyle='--', label="O(n^2)")
    plt.plot(num_cities, scale_nlogn * n_log_n, linestyle = '-.', label = "O(n log(n))")


    # Plot
    plt.title("Nearest Neighbor TSP Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Number of Cities", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    plot_nearest_neighbor_tsp_data("nearest_neighbor_tsp_data.csv")  # Replace if needed