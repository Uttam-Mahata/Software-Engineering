import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy import stats

# Read the performance data
df = pd.read_csv('knapsack_performance.csv')

# Create theoretical time complexity curves
# O(n*W) where W is proportional to n for our test cases
sizes = df['size']
theoretical_best = sizes * np.log(sizes)  # O(n log n) - best theoretical case
theoretical_avg = sizes * sizes           # O(n^2) - average theoretical case
theoretical_worst = sizes * sizes         # O(n^2) - worst theoretical case

# Scale theoretical values to match actual data scales
scale_best = np.mean(df['best_case']) / np.mean(theoretical_best)
scale_avg = np.mean(df['average_case']) / np.mean(theoretical_avg)
scale_worst = np.mean(df['worst_case']) / np.mean(theoretical_worst)

theoretical_best *= scale_best
theoretical_avg *= scale_avg
theoretical_worst *= scale_worst

# Create the plot
plt.figure(figsize=(12, 8))

# Plot actual running times
plt.plot(sizes, df['best_case'], 'bo-', label='Actual Best Case', alpha=0.6)
plt.plot(sizes, df['average_case'], 'go-', label='Actual Average Case', alpha=0.6)
plt.plot(sizes, df['worst_case'], 'ro-', label='Actual Worst Case', alpha=0.6)

# Plot theoretical curves
plt.plot(sizes, theoretical_best, 'b--', label='Theoretical Best Case', alpha=0.4)
plt.plot(sizes, theoretical_avg, 'g--', label='Theoretical Average Case', alpha=0.4)
plt.plot(sizes, theoretical_worst, 'r--', label='Theoretical Worst Case', alpha=0.4)

plt.xlabel('Input Size (n)')
plt.ylabel('Running Time (microseconds)')
plt.title('Knapsack Algorithm Performance Analysis')
plt.legend()
plt.grid(True, alpha=0.3)




plt.savefig('knapsack_analysis.png')
plt.close()


