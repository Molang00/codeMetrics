# Explanation for Maintainability Index

## Definition

**Maintainability Index** is a software metric which measures how **maintainable** (easy to support and change) the source code is. 

**Maintainability Index** (MI) Calculating Formula:

1. The Original Formula:

   $$MI=171-5.2\times ln(V)-0.23\times (G)-16.2\times ln(LOC)$$ 

2. The Formula Used by [Software Engineering Institute](https://www.sei.cmu.edu/):

   $$MI=171-5.2\times log_2(V)-0.23\times (G)-16.2\times log_2(LOC)+50\times sin(\sqrt{2.4\times CM})$$ 

3. The Formula Used by Microsoft Visual Studio:

   $$MI=MAX(0,(171-5/2\times ln(V)-0.23\times (G)-16.2\times ln(LOC))\times 100\div 171)$$ 

Parameters in such formulas would be shown as follows. 

Since there is an academic paper can be referenced, to make our calculator more academia can credible, it would be reasonable for us to consider the formula used by Software Engineering Institute. 

**Ref**: 

[Project Code Meter](http://www.projectcodemeter.com/cost_estimation/help/GL_maintainability.htm) 

Coleman, Don, et al. “Using metrics to evaluate software system maintainability.” Computer 27.8 (1994): 44-49. 

## Explanation for Parameters

### Halstead's Volume

**Halstead's volume** (V) describes the size of the implementation of an algorithm. The computation of V is based on **the number of operations performed and operands handled** in the algorithm. Therefore V is less sensitive to code layout than the lines-of-code measures.

**Methods**:

1. Cal these criteria: 
   1. $\eta$ = the number of distinct operators & operands
   2. $N$ = the total number of operators & operands
2. Cal the volume: $V=N\times log_2 \eta $ 

**Ref**: 

1. [Verify soft](https://www.verifysoft.com/en_halstead_metrics.html) 

2. [Wiki](https://en.wikipedia.org/wiki/Halstead_complexity_measures) 



### Cyclomatic Complexity

**Cyclomatic complexity** (G)  is a software metric used to indicate the *complexity* of a program. It is a quantitative measure of the number of linearly independent paths through a program's source code.

**Methods**: 

1. Graph the source code to control flow graph
2. Cal these criteria:
     1. *E* = the number of edges of the graph.
     2. *N* = the number of nodes of the graph.
     3. *P* = the number of [connected components](https://en.wikipedia.org/wiki/Connected_component_(graph_theory)).
3. Cal the cyclomatic complexity: $$M=E-N+2P$$ / $$M=E-N+P$$ (These two formulas depend on different methods of graphing)

**N.B.** *Connected Components* is a definition of *Graph Theory*. P can be 2 or higher in multithreaded programming, but in single thread programming, P is always 1. As a result, if we just calculate the MI of single thread programming, we can use the formula of calculating G: $$M=E-N+2$$.

**Ref**:

1. [Wiki](https://en.wikipedia.org/wiki/Cyclomatic_complexity)



### Source Lines of Code

**Source lines of code** (SLOC) is a software metric used to measure the size of a computer program by counting the number of lines in the text of the program's source code.

**Methods**: 

There are two main types of SLOC, to get the definition of them, please check the references.

1. Physical Line of Code (LOC)
2. Logical Line of Code (LLOC)

**N.B.** Besides, there is another definition named CLOC, which means the lines of code including blank lines and comments lines. 

**Ref**: 

1. [Wiki](https://en.wikipedia.org/wiki/Source_lines_of_code) 

### Percent of Comments Lines

**Percent of Comments Lines** (CM)

There is a standard about the percent of comments lines: 

> At least 30 percent and at most 75 percent of a file should be **comments**.
> If less than one third of a file is comments the file is either very trivial or poorly explained.
> If more than 75% of a file are comments, the file is not a program but a document. In a well-documented header file percentage of comments may sometimes exceed 75%.

**Ref**: 

1. [Verify Soft](https://www.verifysoft.com/en_linesofcode_metrics.html) 
