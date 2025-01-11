import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_knapsack_data(filename):
    """
    Plots Knapsack's algorithm data and theoretical complexity curves.
    """
    df = pd.read_csv(filename)

    # Convert columns to numeric
    df['Number of Items'] = pd.to_numeric(df['Number of Items'])
    df['Average Time (nanoseconds)'] = pd.to_numeric(df['Average Time (nanoseconds)'])

    # Create the plot
    plt.figure(figsize=(12, 8))

    # Plot the measured data
    plt.plot(df['Number of Items'], df['Average Time (nanoseconds)'], marker='o', linestyle='-', markersize=4, label="Knapsack's Measured Time")

     # Generate data for theoretical curves
    num_items = df['Number of Items']
    n_c = num_items*100 # using a constant C as 100, this is a variable that was kept constant in the java code when generating data
    n_c_squared = n_c*n_c

    # Scale theoretical curves
    scale_nc = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_c.head(10))
    scale_nc2 = np.mean(df['Average Time (nanoseconds)'].head(10)) / np.mean(n_c_squared.head(10))

    # Plot theoretical curves
    plt.plot(num_items, scale_nc*n_c, linestyle = '--', label = "n*C")
    plt.plot(num_items, scale_nc2*n_c_squared, linestyle = '-.', label = "(n*C)^2")


    # Plot
    plt.title("Knapsack Algorithm Runtime vs. Input Size", fontsize=16)
    plt.xlabel("Number of Items", fontsize=14)
    plt.ylabel("Average Time (nanoseconds)", fontsize=14)
    plt.grid(True)
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    plot_knapsack_data("knapsack_data.csv")  # Replace if needed