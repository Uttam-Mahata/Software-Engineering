import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.ticker import ScalarFormatter

# Read the CSV file
df = pd.read_csv('floyd_warshall_times.csv')

# Calculate theoretical complexities (O(V^3))
# Scale factor to match actual times
scale_best = df['best_case'].max() / (df['size'].max() ** 3)
scale_avg = df['average_case'].max() / (df['size'].max() ** 3)
scale_worst = df['worst_case'].max() / (df['size'].max() ** 3)

theoretical_best = df['size'].apply(lambda x: x**3 * scale_best)
theoretical_avg = df['size'].apply(lambda x: x**3 * scale_avg)
theoretical_worst = df['size'].apply(lambda x: x**3 * scale_worst)

# Create the plot
plt.figure(figsize=(12, 8))

# Plot actual times
plt.plot(df['size'], df['best_case'], 'bo-', label='Best Case (Actual)')
plt.plot(df['size'], df['average_case'], 'go-', label='Average Case (Actual)')
plt.plot(df['size'], df['worst_case'], 'ro-', label='Worst Case (Actual)')

# Plot theoretical curves
plt.plot(df['size'], theoretical_best, 'b--', label='Best Case (Theoretical)')
plt.plot(df['size'], theoretical_avg, 'g--', label='Average Case (Theoretical)')
plt.plot(df['size'], theoretical_worst, 'r--', label='Worst Case (Theoretical)')

plt.xlabel('Input Size (V)')
plt.ylabel('Time (milliseconds)')
plt.title('Floyd-Warshall Algorithm Performance Analysis')
plt.legend()
plt.grid(True)

# Use logarithmic scale for better visualization
plt.yscale('log')
plt.gca().yaxis.set_major_formatter(ScalarFormatter())

plt.savefig('floyd_warshall_analysis.png')
plt.close()

# Calculate correlation between actual and theoretical times
print("\nCorrelation Analysis:")
print(f"Best Case correlation: {np.corrcoef(df['best_case'], theoretical_best)[0,1]:.4f}")
print(f"Average Case correlation: {np.corrcoef(df['average_case'], theoretical_avg)[0,1]:.4f}")
print(f"Worst Case correlation: {np.corrcoef(df['worst_case'], theoretical_worst)[0,1]:.4f}")