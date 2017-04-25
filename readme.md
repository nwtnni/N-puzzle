# N-puzzle

## Description

This is a generalized implementation of the [15 Puzzle](https://en.wikipedia.org/wiki/15_puzzle).

It supports an arbitrary m x n grid, and comes with a couple of different
search algorithms to solve the puzzle. You can either watch the algorithm solve
the puzzle in real-time, or run some number of trials and check the average
running time. Board state is drawn in ASCII art.

## Usage

```
java -jar N-puzzle.jar <M> <N> <MOVES> <PLAYER> <AVG>
```

**\<M\>** x **\<N\>** is the dimension of puzzle to create (standard matrix conventions)

**\<MOVES\>** is the number of moves used to randomize the puzzle

**\<PLAYER\>** is one of -b, -h, -i, -m, -o, -r:
* \-b to use naive breadth-first search
* \-h to play manually
* \-i to use iterative depth-first search
* \-m to use A\* search with the Manhattan distance heuristic
* \-o to use A\* search with the out-of-place heuristic
* \-r to play randomly

**\<AVG\>** is one of the following
* 0 to do a step-by-step solve, with pretty-printing
* n to average the solving time taken over n trials

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

### Iterative Depth-first Search

Standard [iterative depth-first search](https://en.wikipedia.org/wiki/Iterative_deepening_depth-first_search).
A hybrid between breadth-first and depth-first search.

### A\* Search

[A\* search](https://en.wikipedia.org/wiki/A*_search_algorithm) is an extension
of [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) which
takes into account extra knowledge of the graph. In this case, since every edge
cost is one (it takes one turn to slide a block), Dijkstra's algorithm reduces
to regular breadth-first search. We use heuristics to improve the performance:

#### Out Of Place 

This heuristic counts how many tiles are out of their final position, and adds it
to the depth of the node. Intuitively, arrangements with more shapes that are away
from their final positions are probably less desirable.

#### Manhattan Distance

This heuristic considers the distance between each tile and its final position, and
adds it to the depth of the node. Since it also accounts for how far away each tile
is, it results in better performance than the out of place heuristic.

## Credits

Inspiration drawn from *Artificial Intelligence: A Modern Approach* by Russell and Norvig.

## Disclaimer

This code was written in one day for fun, and is nowhere near optimized.
Realistically, 5x5 is probably the largest board any of these can handle.

## Example Solution

Here's the output of `java -jar N-puzzle.jar 5 5 100 -m 0`:

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
