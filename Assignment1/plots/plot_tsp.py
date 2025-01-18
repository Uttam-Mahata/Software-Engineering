import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit
from math import factorial

# Read the CSV file
df = pd.read_csv('tsp_performance.csv')

# Define theoretical complexity function (factorial complexity)
def theoretical_complexity(n, a):
    return a * np.array([factorial(int(i)) for i in n])  # O(n!)

# Create plot
plt.figure(figsize=(12, 8))

# Plot actual running times with both points and lines
plt.plot(df['size'], df['execution_time']/1e9, 'o-', label='Actual Running Time', color='blue')

# Fit and plot theoretical curve
x = np.linspace(min(df['size']), max(df['size']), 100)
popt, _ = curve_fit(theoretical_complexity, df['size'], df['execution_time']/1e9)
plt.plot(x, theoretical_complexity(x, popt[0]), '--', color='red', label='Theoretical O(n!)')

plt.xlabel('Number of Cities (n)')
plt.ylabel('Execution Time (seconds)')
plt.title('TSP Algorithm Performance Analysis')
plt.legend()
plt.grid(True)
plt.show()

# Calculate and print R-squared value
def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    return 1 - (ss_res / ss_tot)

print("\nR-squared value (how well theoretical matches actual):")
print(f"R² = {r_squared(df['execution_time']/1e9, theoretical_complexity(df['size'], popt[0])):.4f}")
