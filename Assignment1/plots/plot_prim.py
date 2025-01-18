import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit

# Read the CSV file
df = pd.read_csv('prims_performance.csv')

# Create separate dataframes for different densities
best_case = df[df['density'] == 0.2]
avg_case = df[df['density'] == 0.5]
worst_case = df[df['density'] == 0.8]

# Define theoretical complexity functions
def theoretical_complexity(n, a):
    return a * n * np.log(n)  # O(V log V) for binary heap implementation

# Create plot
plt.figure(figsize=(12, 8))

# Plot actual running times with both points and lines
plt.plot(best_case['vertices'], best_case['execution_time'], 'o-', label='Best Case (Sparse)', color='green')
plt.plot(avg_case['vertices'], avg_case['execution_time'], 'o-', label='Average Case', color='blue')
plt.plot(worst_case['vertices'], worst_case['execution_time'], 'o-', label='Worst Case (Dense)', color='red')

# Fit and plot theoretical curves
x = np.linspace(min(df['vertices']), max(df['vertices']), 100)

# Best case
popt_best, _ = curve_fit(theoretical_complexity, best_case['vertices'], best_case['execution_time'])
plt.plot(x, theoretical_complexity(x, popt_best[0]), '--', color='green', label='Theoretical Best Case')

# Average case
popt_avg, _ = curve_fit(theoretical_complexity, avg_case['vertices'], avg_case['execution_time'])
plt.plot(x, theoretical_complexity(x, popt_avg[0]), '--', color='blue', label='Theoretical Average Case')

# Worst case
popt_worst, _ = curve_fit(theoretical_complexity, worst_case['vertices'], worst_case['execution_time'])
plt.plot(x, theoretical_complexity(x, popt_worst[0]), '--', color='red', label='Theoretical Worst Case')

plt.xlabel('Number of Vertices (V)')
plt.ylabel('Execution Time (ms)')
plt.title("Prim's Algorithm Performance Analysis")
plt.legend()
plt.grid(True)
plt.show()

# Calculate and print R-squared values to measure how well theoretical matches actual
def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    return 1 - (ss_res / ss_tot)

print("\nR-squared values (how well theoretical matches actual):")
print(f"Best case: {r_squared(best_case['execution_time'], theoretical_complexity(best_case['vertices'], popt_best[0])):.4f}")
print(f"Average case: {r_squared(avg_case['execution_time'], theoretical_complexity(avg_case['vertices'], popt_avg[0])):.4f}")
print(f"Worst case: {r_squared(worst_case['execution_time'], theoretical_complexity(worst_case['vertices'], popt_worst[0])):.4f}")