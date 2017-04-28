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
* \-fma to use a non-optimal, fast search with the Manhattan distance plus linear conflict heuristic
* \-li to use IDA\* search with the Manhattan distance plus linear conflict heuristic
* \-la to use A\* search with the Manhattan distance plus linear conflict heuristic
* \-fla to use a non-optimal, fast search with the Manhattan distance plus linear conflict heuristic
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
forgot to add the node's depth to its estimated cost. For reasons I am not entirely sure
about yet, this improves the speed of the algorithms by orders of magnitude at the cost
of returning non-optimal solutions.

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

//TODO: Performance statistics

In general, A\* outperforms all other variations, including IDA\*. The latter would
probably be faster if everything were more optimized.

The Manhattan Distance with Linear Interference is by far the best heuristic, but
due to the high cost of my naive implementation, it takes much longer to process a
node than just Manhattan Distance. So the performance improvement is much more
noticeable on a harder puzzle, where the decrease in exponential node growth outweighs
the extra cost of processing a single node.

Overall, the fastest algorithm for solving a puzzle is -flb, or fast linear/Manhattan A\*,
except it doesn't find an optimal solution.

The fastest optimal solution is either -mb (Manhattan A\*) or -lb (linear/Manhatta A\*),
depending on the difficulty of the input.

## Credits

Inspiration drawn from *Artificial Intelligence: A Modern Approach* by Russell and Norvig.

## Disclaimer

This code was written in one day for fun, and is nowhere near optimized.
Realistically, 4x4 (or 5x5 without much randomization) 
is probably the largest board any of these can handle.

## Example Solution

Here's the output of `java -jar N-puzzle.jar 4 4 100 -m 0`:

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
