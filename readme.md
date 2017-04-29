# N-puzzle

## Description

This is a generalized implementation of the [15 Puzzle](https://en.wikipedia.org/wiki/15_puzzle).

It supports an arbitrary m x n grid, and comes with a couple of different
search algorithms to solve the puzzle. You can either watch the algorithm solve
the puzzle in real-time, or run some number of trials and check the average
running time. Board state is drawn in ASCII art.

## Usage

The jar file can be built with `gradle build`, or you can find it in the `build/libs`
directory.

```
java -jar N-puzzle.jar <M> <N> <MOVES> <PLAYER> <AVG> <RATE>
```

**\<M\>** x **\<N\>** is the dimension of puzzle to create (standard matrix conventions)

**\<MOVES\>** is the number of moves used to randomize the puzzle

**\<PLAYER\>** is one of -b, -h, -i, -mi, -mb, -fmb, -li, -lb, -flb, -ob, -oi, -r:
* \-b to use naive breadth-first search
* \-h to play manually
* \-i to use naive iterative deeping A\* search
* \-mi to use IDA\* search with the Manhattan distance heuristic
* \-ma to use A\* search with the Manhattan distance heuristic
* \-fma to use a non-optimal, fast search with the Manhattan distance plus linear interference heuristic
* \-li to use IDA\* search with the Manhattan distance plus linear interference heuristic
* \-la to use A\* search with the Manhattan distance plus linear interference heuristic
* \-fla to use a non-optimal, fast search with the Manhattan distance plus linear interference heuristic
* \-oa to use A\* search with the out-of-place heuristic
* \-oi to use IDA\* search with the out-of-place distance heuristic
* \-r to play randomly

**\<AVG\>** is one of the following
* 0 to do a step-by-step solve, with pretty-printing
* n to average the solving time taken over n trials

**\<RATE\>** is the number of steps displayed per second. Only applies to
automated solving, if **\<AVG\>** is equal to 0.

## Algorithms

All of these algorithms (except Human and Random) use the PuzzleNode, which 
wraps around an AbstractPuzzle, as the basis of a search tree. Since there can 
be anywhere from 2-4 possible successors of a given board state (corresponding 
to each possible move), each node can have 2-4 branches.

Each node must be explicitly expanded by calling the generate() function, which
constructs each valid child node.

### Human

You can time yourself and try to beat these algorithms... if you want.

### Random

This might be the only one you can beat. It just chooses random moves and hopes for the best.

### Breadth-first Search

Standard [breadth-first search](https://en.wikipedia.org/wiki/Breadth-first_search).
Considers all possible solutions that take 0 turns, then 1 turn, then 2, and so on.
Due to exponential growth of the search space, this algorithm is pretty inefficient
for this problem--even when ignoring board states that we've already seen.

### A\* Search

[A\* search](https://en.wikipedia.org/wiki/A*_search_algorithm) is an extension
of [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) which
takes into account extra knowledge of the graph. In this case, since every edge
cost is one (it takes one turn to slide a block), Dijkstra's algorithm reduces
to regular breadth-first search. A\* improves performance by exploiting prior
knowledge of the graph in the form of heuristics, some of which are explained below.

### Iterative Deepening Search

[Iterative depth-first search](https://en.wikipedia.org/wiki/Iterative_deepening_depth-first_search),
which is itself a hybrid between breadth-first and depth-first search. IDS combines the
low memory footprint of DFS with the optimality of BFS, at the cost of re-running
DFS potentially many times on already searched areas of the graph. IDS has the
same asymptotic running time, but in practice will probably be slower for smaller inputs.

### Iterative Deepening A\*

[IDA\*](https://en.wikipedia.org/wiki/Iterative_deepening_A*) 
is a hybrid of IDS and A\* search that uses also uses heuristics to inform its choices.

### Accidental

The fast versions of Manhattan A\* and Linear Interference A\* were because I accidentally
forgot to add the node's depth to its estimated cost. This results in something like
a greedy algorithm, which doesn't care about the cost required to reach a certain node--only
how close it is to the solution. It's not technically A\* anymore (but it only
differs in one line of code).  These algorithms run much faster at the cost of 
returning a non-optimal solution.

If optimality isn't a problem (i.e. you just want to see big puzzles solved quickly), then
these perform surprisingly well.

## Heuristics

### Out Of Place 

This heuristic counts how many tiles are out of their final position, and adds it
to the depth of the node. Intuitively, arrangements with more shapes that are away
from their final positions are probably less desirable.

### Manhattan Distance

This heuristic considers the distance between each tile and its final position, and
adds it to the depth of the node. Since it also accounts for how far away each tile
is, it results in better performance than the out of place heuristic.

### Manhattan Distance with Linear Interference

To improve upon the Manhattan distance, we account for interference between tiles.
A more detailed explanation can be found in [this paper](https://academiccommons.columbia.edu/catalog/ac:141289).

## Performance Benchmarks

All of the following statistics are collected and averaged over 100 trials for 
each setup. Due to the JIT compiler, code tends to run faster when run for 
longer, so take the Time Taken statistic with a grain of salt. For example, 
BFS on a 3x3 board randomized with 10 moves runs 10x faster on average over 
1000000 trials compared to 100 trials.  

### Iterative Deepening Search

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.23            | 1990.94        | 6.87 x 10^-4     |
| 3 x 3      | 25                  | 7.38            | 958810.90      | 8.09 x 10^-2     |
| 3 x 3      | 30                  | 9.10            | 8531528.09     | 6.96 x 10^-1     |

### Breadth-First Search

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.04            | 64.59          | 5.27 x 10^-4     |
| 3 x 3      | 50                  | 12.22           | 10164.23       | 1.50             |
| 3 x 3      | 100                 | 17.64           | 44840.38       | 8.68             |

### IDA\* With Out-of-Place

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.2             | 21.61          | 7.95 x 10^-5     |
| 3 x 3      | 25                  | 7.38            | 1944.38        | 1.04 x 10^-3     |
| 3 x 3      | 50                  | 13.12           | 2.04 x 10^7    | 2.13 x 10^1      |

### A\* With Out-of-Place

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 3.92            | 6.2            | 1.17 x 10^-4     |
| 3 x 3      | 50                  | 12.22           | 489.18         | 5.71 x 10^-3     |
| 3 x 3      | 100                 | 18.22           | 5907.14        | 3.18 x 10^-1     |
| 3 x 3      | 1000                | 21.80           | 13436.23       | 9.22 x 10^-1     |
| 3 x 3      | 10000               | 22.86           | 18397.93       | 1.55 x 10^0      |
| 4 x 4      | 10                  | 5.12            | 8.18           | 1.53 x 10^-4     |

### IDA\* With Manhattan

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.11            | 11.26          | 7.54 x 10^-5     |
| 3 x 3      | 50                  | 12.56           | 27274.24       | 5.95 x 10^-3     |
| 3 x 3      | 100                 | 18.52           | 274511.06      | 4.01 x 10^-2     |
| 3 x 3      | 1000                | 22.50           | 624618.10      | 9.08 x 10^-2     |
| 3 x 3      | 10000               | 21.84           | 505551.76      | 7.42 x 10^-2     |
| 4 x 4      | 10                  | 4.88            | 13.28          | 1.35 x 10^-4     |
| 4 x 4      | 50                  | 16.57           | 814800.38      | 1.51 x 10^-1     |
| 4 x 4      | 75                  | 22.74           | 1.94 x 10^7    | 3.63 x 10^0      |

### A\* With Manhattan

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 3.86            | 5.29           | 1.16 x 10^-4     |
| 3 x 3      | 50                  | 12.58           | 118.57         | 1.19 x 10^-3     |
| 3 x 3      | 100                 | 17.98           | 514.64         | 4.14 x 10^-3     |
| 3 x 3      | 1000                | 22.58           | 1274.54        | 1.03 x 10^-2     |
| 3 x 3      | 10000               | 22.04           | 1308.41        | 1.10 x 10^-2     |
| 4 x 4      | 10                  | 4.58            | 6.08           | 1.39 x 10^-4     |
| 4 x 4      | 50                  | 17.26           | 383.87         | 5.66 x 10^-3     |
| 4 x 4      | 75                  | 22.44           | 4672.43        | 3.30 x 10^-1     |

### IDA\* With Manhattan and Linear Interference

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.23            | 10.97          | 1.10 x 10^-4     |
| 3 x 3      | 50                  | 12.75           | 8583.44        | 5.63 x 10^-3     |
| 3 x 3      | 100                 | 17.98           | 25074.56       | 1.21 x 10^-2     |
| 3 x 3      | 1000                | 22.34           | 218207.64      | 7.60 x 10^-2     |
| 3 x 3      | 10000               | 21.92           | 153869.54      | 5.26 x 10^-2     |
| 4 x 4      | 10                  | 3.94            | 10.64          | 1.86 x 10^-4     |
| 4 x 4      | 50                  | 16.36           | 16650.93       | 1.19 x 10^-2     |
| 4 x 4      | 75                  | 22.98           | 1674781.34     | 9.72 x 10^-1     |

### A\* With Manhattan and Linear Interference

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 3.72            | 5.24           | 2.38 x 10^-4     |
| 3 x 3      | 50                  | 12.44           | 71.81          | 1.45 x 10^-3     |
| 3 x 3      | 100                 | 18.58           | 313.21         | 4.41 x 10^-3     |
| 3 x 3      | 1000                | 21.86           | 649.55         | 9.69 x 10^-3     |
| 3 x 3      | 10000               | 22.00           | 626.94         | 7.81 x 10^-3     |
| 4 x 4      | 10                  | 4.58            | 6.21           | 3.74 x 10^-4     |
| 4 x 4      | 50                  | 16.7            | 215.46         | 5.76 x 10^-3     |
| 4 x 4      | 75                  | 22.32           | 1138.16        | 4.81 x 10^-2     |
| 4 x 4      | 100                 | 27.64           | 7376.34        | 8.10 x 10^-1     |

### Fast "A\*" With Manhattan

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.12            | 6.23           | 1.46 x 10^-4     |
| 3 x 3      | 50                  | 31.12           | 114.95         | 1.15 x 10^-3     |
| 3 x 3      | 100                 | 43.70           | 169.27         | 1.30 x 10^-3     |
| 3 x 3      | 1000                | 57.46           | 222.54         | 2.02 x 10^-3     |
| 3 x 3      | 10000               | 55.94           | 206.54         | 1.52 x 10^-3     |
| 4 x 4      | 10                  | 4.84            | 6.79           | 2.12 x 10^-4     |
| 4 x 4      | 50                  | 55.86           | 526.58         | 7.66 x 10^-3     |
| 4 x 4      | 75                  | 78.08           | 822.47         | 1.03 x 10^-2     |
| 4 x 4      | 100                 | 109.96          | 1246.23        | 1.44 x 10^-2     |
| 4 x 4      | 1000                | 204.46          | 2137.68        | 2.50 x 10^-2     |
| 4 x 4      | 10000               | 200.36          | 2121.47        | 2.48 x 10^-2     |
| 5 x 5      | 10                  | 4.94            | 7.32           | 1.77 x 10^-4     |
| 5 x 5      | 100                 | 161.56          | 4213.38        | 1.17 x 10^-1     |

### Fast "A\*" With Manhattan and Linear Interference

| Board Size | Randomization Moves | Solution Length | Nodes Explored | Time Taken (sec) |
|:-----------|:--------------------|:----------------|:---------------|:-----------------|
| 3 x 3      | 10                  | 4.16            | 5.86           | 3.67 x 10^-4     |
| 3 x 3      | 50                  | 17.22           | 30.29          | 7.91 x 10^-4     |
| 3 x 3      | 100                 | 28.64           | 53.62          | 1.15 x 10^-3     |
| 3 x 3      | 1000                | 33.78           | 60.77          | 1.09 x 10^-3     |
| 3 x 3      | 10000               | 34.74           | 65.71          | 1.01 x 10^-3     |
| 4 x 4      | 10                  | 4.56            | 5.93           | 4.35 x 10^-4     |
| 4 x 4      | 50                  | 26.20           | 71.38          | 2.68 x 10^-3     |
| 4 x 4      | 75                  | 47.94           | 161.43         | 4.80 x 10^-3     |
| 4 x 4      | 100                 | 62.28           | 222.30         | 6.69 x 10^-3     |
| 4 x 4      | 1000                | 122.28          | 457.83         | 1.23 x 10^-2     |
| 4 x 4      | 10000               | 123.30          | 517.78         | 1.29 x 10^-2     |
| 5 x 5      | 10                  | 5.12            | 6.56           | 6.34 x 10^-4     |
| 5 x 5      | 100                 | 86.10           | 740.54         | 3.69 x 10^-2     |
| 5 x 5      | 1000                | 292.88          | 4566.49        | 3.37 x 10^-1     |
| 5 x 5      | 10000               | 311.42          | 5215.94        | 4.68 x 10^-1     |
| 5 x 5      | 100000              | 314.46          | 4873.29        | 4.67 x 10^-1     |
| 6 x 6      | 10                  | 5.06            | 6.62           | 7.57 x 10^-4     |
| 6 x 6      | 100                 | 105.40          | 2966.27        | 2.66 x 10^-1     |

In general, A\* outperforms all other variations, including IDA\*. The latter would
probably be faster if everything were more optimized.

The Manhattan Distance with Linear Interference is by far the best heuristic, but
due to the high cost of my naive implementation, it takes much longer to process a
node than just Manhattan Distance. So the performance improvement is much more
noticeable on a harder puzzle, where the decrease in exponential node growth outweighs
the extra cost of processing a single node.

Overall, the fastest algorithm for solving a puzzle is -fla, or fast linear/Manhattan A\*,
except it doesn't find an optimal solution.

The fastest optimal solution is either -ma (Manhattan A\*) or -la (linear/Manhatta A\*),
depending on the difficulty of the input.

## Credits

Inspiration drawn from *Artificial Intelligence: A Modern Approach* by Russell and Norvig.

## Disclaimer

This code was written in one day for fun, and is nowhere near optimized.
Realistically, 4x4 (or 5x5 without much randomization) 
is probably the largest board any of these can handle.

## Example Solution

Here's the output of `java -jar N-puzzle.jar 4 4 100 -ma 0`:

```
-------------------------
|     |     |     |     |
|  0  |  2  |  7  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  11 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |     |  13 |  14 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  7  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  11 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |     |  14 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  7  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  11 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |     |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  7  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  7  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  7  |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |     |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  3  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |     |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |  9  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  9  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  10 |     |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  6  |  9  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |     |  10 |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |     |  9  |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  6  |  10 |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  9  |     |  1  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  6  |  10 |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  9  |  1  |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  6  |  10 |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  9  |  1  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  6  |  10 |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  9  |  1  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  6  |     |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  9  |  1  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |     |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |     |  1  |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  1  |     |  5  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  1  |  5  |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |  3  |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  1  |  5  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  2  |     |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  1  |  5  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |     |  2  |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  1  |  5  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  1  |  2  |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |     |  5  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  1  |  2  |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  5  |     |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  6  |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  1  |  2  |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  5  |  6  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |     |  10 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  1  |  2  |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  5  |  6  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  10 |     |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |  11 |
|     |     |     |     |
-------------------------

-------------------------
|     |     |     |     |
|  0  |  1  |  2  |  3  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  4  |  5  |  6  |  7  |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  8  |  9  |  10 |  11 |
|     |     |     |     |
-------------------------
|     |     |     |     |
|  12 |  13 |  14 |     |
|     |     |     |     |
-------------------------
```
