# Chapter 1: Building, Running and the REPL
```clj
^{::clerk/visibility {:code :hide}} (ns chapter-1)
```
In this chapter, we will invest a small amount of time to get familiar with a quick way to run Clojure programs.

We will learn how to run code within a clojure process using a Read-Eval-Loop-Print (REPL) and how to use Clerk to get a literate programming environment.

## REPL

A Clojure REPL is a programming environment which enables the programmer to interact with a running Clojure program and modify it, by evaluating one code expression at a time.

![repl](https://clojure.org/images/content/guides/repl/show-terminal-repl.gif)

It is a tool for experimenting with code, interact with a running program and quickly try out new ideas. 

To launch a clojure REPL, run the following command in a terminal:
```sh
clj
```

The outpout should look like the picture below and you could start interacting with the program at the command line:

![repl](https://github.com/pierregoudjo/ClojureForTheBraveAndTrue/assets/1331326/c642d90d-dd80-4923-8385-fb918c3566a2)


You could also use the Socket REPL to interact with the REPL over the wire or from another program. To launch a socket REPL on a specific port, run the following command:

```sh
clj -X clojure.core.server/start-server :name repl :port 5555 :accept clojure.core.server/repl :server-daemon false
```

You can now connect to the REPL via a socket by using the `nc` command line tool for example:

```sh
nc localhost 5555
```

You can also use your code editor to connect to the REPl and send forms to the clojure program to be executed. I personally use [Sublime Text](https://www.sublimetext.com) with [Clojure-Sublimed](https://github.com/tonsky/Clojure-Sublimed) extension.

Other connection methods (like nREPL or pREPL) and code editors (Emacs + Clojure Mode, VSCode + Calva). However they won't be covered here.

## Clerk

[Clerk](https://clerk.vision) is a notebook library for Clojure. It provides an computing environment allowing the mixing of prose and executable code. It is similar to Jupyter Notebook and aims to address some of its problems. See the [Clerk Rationale](https://book.clerk.vision/#rationale) for more information.

Clerk notebooks are either regular Clojure namespaces (interspersed with Markdown comments) or regular markdown files (interspersed with Clojure comments). In fact, this very markdown file is a Clerk notebook.

To use Clerk, you can invoke the following function from the REPL:

```bash
(clerk/serve! {:browse true :port 6677 :watch-paths ["notebooks"]})
```

In the `dev/user.clj` file, a function `(serve!)` is also available to start Clerk on port `6677`. The function `(clerk/halt!)` can be used to stop a Clerk notebook.

## Evaluate code

Let's try a few basic Clojure functions at the REPL:

```clojure
(+ 1 2 3 4 )
```

```clojure
(* 1 2 3 4)
```

```clojure
(first [1 2 3 4])
```

Awesome! We just addedd some numbers, multiplied some numbers and took the first element from a vector.

```clojure
(println "no prompt here")
```

The previous code block won't show "no prompt here" in the Clerk notebook because Clerk will only display he return of functions. The `println` function return `nil and is called a side-effect. The side effects like printing in the terminal won't be reflected in the notebook but will be shown in the REPL.
