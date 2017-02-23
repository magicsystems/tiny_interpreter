Tiny interpreter with graphical editor.


Supported grammar:

  expr ::= expr op expr | (expr) | identifier | { expr, expr } | number |
  map(expr, identifier -> expr) | reduce(expr, expr, identifier identifier -> expr)

  op ::= + | - | * | / | ^

  stmt ::= var identifier = expr | out expr | print “string”

  program ::= stmt | program stmt


Program example:

var n = 5
var sequence = map({1, n}, i -> 2^i + 1)
var val = reduce(sequence, 0, x y -> x + y)
print "val = "
out val

Output for example program:

val =
67.0


To start Editor: run Main.java class

