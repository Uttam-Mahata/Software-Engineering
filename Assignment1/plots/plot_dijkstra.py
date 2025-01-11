import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_time_data(filename):
    """
    Plots the time data from a CSV file and overlays n^2 and n^2*log(n) curves.

    Args:
        filename (str): The name of the CSV file.
    """
    df = pd.read_csv(filename)

    # Convert 'Number of Nodes' and 'Average Time (nanoseconds)' to numeric
    df['Number of Nodes'] = pd.to_numeric(df['Number of Nodes'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the original data
    plt.plot(df['Number of Nodes'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label='Dijkstra\'s Measured Time')

    # Generate data for n^2 and n^2*log(n)
    nodes = df['Number of Nodes']
    n_squared = (nodes**2)
    n_squared_log_n = (nodes**2 * np.log2(nodes)) #used log2 for better visualization
    n_log_n = (nodes * np.log2(nodes))

    # Plot n^2 curve
    plt.plot(nodes, n_squared, linestyle='--', label='n^2 Scaled')

    # Plot n^2*log(n) curve
    plt.plot(nodes, n_squared_log_n, linestyle='-.', label='n^2*log(n) Scaled')

    plt.plot(nodes, n_log_n, linestyle=':', label='n*log(n) Scaled')


    plt.title("Average Time vs. Number of Nodes with n^2 and n^2*log(n) Curves", fontsize=16)
    plt.xlabel("Number of Nodes", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12) # added legend to identify the lines
    plt.tight_layout()
    plt.show()

if __name__ == "__main__":
    plot_time_data("../dijkstra_data.csv")  # Replace with the actual file name if different