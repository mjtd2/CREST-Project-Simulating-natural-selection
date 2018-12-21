﻿# CREST-Project-Simulating-natural-selection
## EnvironmentOne
Simulates natural selection for a single environment. The genome of every organism is printed every generation, after reproduction but before selection of the survivors. The optimal genome is an array of zeros, making it easy to see evolution progress (as zeros become more common). It is possible to observe [genetic hitchhiking](https://en.wikipedia.org/wiki/Genetic_hitchhiking) with this program.

## MultithreadedRunEnvironmentThreeVariableParameters
Simulates natural selection over multiple environments for a range of values of either genome length, genome base, target fitness or parents per year, where the other variables are user-defined and constant. For each set of independent variables, a large number of repeat tests are used to produce a mean value for the number of years taken for the population to reach the same level of mean fitness, reducing random error. This is made faster by using four threads. The results are written to a file, from which they can be copied to a graphing program (as in Results 18Mar16.xlsx).
