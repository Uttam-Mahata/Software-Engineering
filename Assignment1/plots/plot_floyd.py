import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_floyd_warshall_data(filename):
    """
    Plots Floyd-Warshall algorithm data and theoretical complexity curves.
    """
    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Number of Nodes'] = pd.to_numeric(df['Number of Nodes'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Number of Nodes'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="Floyd-Warshall's Measured Time")

     # Generate data for theoretical curves
    nodes = df['Number of Nodes']
    n_cubed = nodes**3

    # Scale theoretical curves
    scale_ncubed = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_cubed.head(10))


    # Plot theoretical curves
    plt.plot(nodes, scale_ncubed* n_cubed, linestyle = '--', label = "O(n^3)")



    # Plot
    plt.title("Floyd-Warshall Algorithm Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Number of Nodes", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    plot_floyd_warshall_data("floyd_warshall_data.csv")  # Replace if needed