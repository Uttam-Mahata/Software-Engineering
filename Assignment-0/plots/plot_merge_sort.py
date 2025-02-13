import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import curve_fit

# Read the CSV file
df = pd.read_csv('merge_sort_performance.csv')

# Convert nanoseconds to milliseconds
for col in ['best_case', 'average_case', 'worst_case']:
    df[col] = df[col] / 1_000_000

# Create theoretical time complexity curves
def nlogn(x, a, b):
    return a * x * np.log2(x) + b

# Fit curves to the data
x = df['size'].values
plt.figure(figsize=(12, 8))

# Plot empirical data
plt.scatter(df['size'], df['best_case'], label='Best Case (Empirical)', alpha=0.5)
plt.scatter(df['size'], df['average_case'], label='Average Case (Empirical)', alpha=0.5)
plt.scatter(df['size'], df['worst_case'], label='Worst Case (Empirical)', alpha=0.5)

# Fit and plot theoretical curves
for case in ['best_case', 'average_case', 'worst_case']:
    popt, _ = curve_fit(nlogn, x, df[case].values)
    y_fit = nlogn(x, *popt)
    plt.plot(x, y_fit, '--', label=f'{case.replace("_", " ").title()} (Theoretical)')

plt.xlabel('Input Size (n)')
plt.ylabel('Time (milliseconds)')
plt.title('Merge Sort Performance Analysis')
plt.legend()
plt.grid(True)

# Calculate and print R-squared values
def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    return 1 - (ss_res / ss_tot)

print("R-squared values for theoretical fit:")
for case in ['best_case', 'average_case', 'worst_case']:
    popt, _ = curve_fit(nlogn, x, df[case].values)
    y_fit = nlogn(x, *popt)
    r2 = r_squared(df[case].values, y_fit)
    print(f"{case}: {r2:.4f}")

plt.savefig('merge_sort_analysis.png')
plt.close()

# Print statistical summary
print("\nStatistical Summary:")
print(df.describe())