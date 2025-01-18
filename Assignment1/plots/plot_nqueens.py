import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.ticker import ScalarFormatter
from math import factorial

# Read the CSV file
df = pd.read_csv('nqueen_performance.csv')

# Convert nanoseconds to milliseconds
df['execution_time'] = df['execution_time'] / 1_000_000

# Calculate theoretical complexity (O(N!))
# Scale factor to match actual times
scale = df['execution_time'].max() / factorial(df['size'].max())
theoretical_time = df['size'].apply(lambda x: factorial(x) * scale)

# Create the plot
plt.figure(figsize=(12, 8))

# Plot actual times
plt.plot(df['size'], df['execution_time'], 'bo-', label='Actual Time')
plt.plot(df['size'], theoretical_time, 'r--', label='Theoretical Time (O(N!))')

plt.xlabel('Board Size (N)')
plt.ylabel('Time (milliseconds)')
plt.title('N-Queens Algorithm Performance Analysis')
plt.legend()
plt.grid(True)

# Use logarithmic scale for better visualization
plt.yscale('log')
plt.gca().yaxis.set_major_formatter(ScalarFormatter())

plt.savefig('nqueens_analysis.png')
plt.close()

# Calculate correlation between actual and theoretical times
correlation = np.corrcoef(df['execution_time'], theoretical_time)[0,1]
print("\nCorrelation Analysis:")
print(f"Correlation between actual and theoretical times: {correlation:.4f}")