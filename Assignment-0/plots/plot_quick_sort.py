import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import curve_fit

# Read the CSV file
df = pd.read_csv('quick_sort_performance.csv')

# Convert nanoseconds to milliseconds
for col in ['best_case', 'average_case', 'worst_case']:
    df[col] = df[col] / 1_000_000

# Define theoretical complexity functions
def nlogn(x, a, b):
    return a * x * np.log2(x) + b

def n_squared(x, a, b):
    return a * x * x + b

plt.figure(figsize=(12, 8))

# Plot empirical data
plt.scatter(df['size'], df['best_case'], label='Best Case (Empirical)', alpha=0.5)
plt.scatter(df['size'], df['average_case'], label='Average Case (Empirical)', alpha=0.5)
plt.scatter(df['size'], df['worst_case'], label='Worst Case (Empirical)', alpha=0.5)

# Fit and plot theoretical curves
x = df['size'].values

# Best and Average cases: O(n log n)
for case in ['best_case', 'average_case']:
    popt, _ = curve_fit(nlogn, x, df[case].values)
    y_fit = nlogn(x, *popt)
    plt.plot(x, y_fit, '--', label=f'{case.replace("_", " ").title()} (Theoretical O(n log n))')

# Worst case: O(n²)
popt, _ = curve_fit(n_squared, x, df['worst_case'].values)
y_fit = n_squared(x, *popt)
plt.plot(x, y_fit, '--', label='Worst Case (Theoretical O(n²))')

plt.xlabel('Input Size (n)')
plt.ylabel('Time (milliseconds)')
plt.title('Quick Sort Performance Analysis')
plt.legend()
plt.grid(True)

# Calculate and print R-squared values
def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    return 1 - (ss_res / ss_tot)

print("\nR-squared values for theoretical fit:")
# Best and Average cases with n log n
for case in ['best_case', 'average_case']:
    popt, _ = curve_fit(nlogn, x, df[case].values)
    y_fit = nlogn(x, *popt)
    r2 = r_squared(df[case].values, y_fit)
    print(f"{case} (O(n log n)): {r2:.4f}")

# Worst case with n²
popt, _ = curve_fit(n_squared, x, df['worst_case'].values)
y_fit = n_squared(x, *popt)
r2 = r_squared(df['worst_case'].values, y_fit)
print(f"worst_case (O(n²)): {r2:.4f}")

# Print statistical summary
print("\nStatistical Summary:")
print(df.describe())

plt.savefig('quick_sort_analysis.png')
plt.close()