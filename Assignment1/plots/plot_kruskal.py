import pandas as pd
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import numpy as np
import os


def plot_kruskal_data(filename):
    """
    Plots Kruskal's algorithm data and theoretical complexity curves.
    """
    # Check if running in a graphical environment
    if 'DISPLAY' not in os.environ:
        # If not, set up a virtual display using Xvfb
        os.environ['DISPLAY'] = ':1'
        try:
           from pyvirtualdisplay import Display
           display = Display(visible=0, size=(1024, 768))
           display.start()

        except ImportError:
           print("Error: Install pyvirtualdisplay with pip3 install pyvirtualdisplay")
           exit()

    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Number of Nodes'] = pd.to_numeric(df['Number of Nodes'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Number of Nodes'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="Kruskal's Measured Time")

    # Generate data for theoretical curves
    nodes = df['Number of Nodes']
    edges = (nodes * (nodes - 1)) / 2  # Maximum possible edges in a complete graph
    n_log_n = edges * np.log2(edges)
    n_squared = nodes**2
    n_squared_log_n = nodes**2 * np.log2(nodes)

    # Scale theoretical curves to align with data
    scale_nlogn = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_log_n.head(10))
    scale_n2 = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_squared.head(10))
    scale_n2logn = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_squared_log_n.head(10))


    # Plot n*log(n), n^2, and n^2*log(n) curves
    plt.plot(nodes, scale_nlogn * n_log_n, linestyle='--', label="E log(E)")
    plt.plot(nodes, scale_n2*n_squared, linestyle = '-.', label = "V^2")
    plt.plot(nodes, scale_n2logn * n_squared_log_n, linestyle = ':', label = "V^2*log(V)")


    #Plot
    plt.title("Kruskal's Algorithm Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Number of Nodes", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.savefig("kruskal_plot.png")
    print("Plot saved to kruskal_plot.png")

    if 'display' in locals() and display:
        display.stop()


if __name__ == "__main__":
    plot_kruskal_data("kruskal_data.csv") # Replace if needed