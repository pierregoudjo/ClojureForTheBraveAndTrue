# Chapter 3: Do Things: A Clojure Crash Course

```clj
^{::clerk/visibility {:code :hide}} (ns chapter-3)
```

## Syntax

Clojure's syntax is simple. Like all Lisps, it employs a uniform structure, a handful of special operators and a constant supply of parentheses delivered from the parenthesis mines hidden beneath the Massachusetts Institute of Technology, where Lisp was born.

All Clojure code is written in a uniform structure. Clojure recognizes two kinds of structures:

- Literal representation of data structure (like numbers, strings, maps and vectors)
- Operations

We use the term *form* to refer to valid code. the term *expression* is also used.

These literal representations are all valid forms:

```clj
1
"a string"
["a" "vector" "of" "strings"]
```

### Forms
The code will rarely contain free-floating literals because they don't actually do anything on their own. Instead we will use *operations*. Operations are how you do things. All operations take the form opening parenthesis, operator, operands, closing parenthesis: `(operator operand1 operand2 ... operandn)`

There is no commas. Clojure uses whitespace to separate operands and treats commas as whitespace. Here some example operations:

```clj
(+ 1 2 3)
(str "It was the panda " "in the library " "with a dust buster")
```

Clojure structural uniformity is different from lot of different programming languages. For example, Javascript employs different rules for operations like infix notation, dot operators and parentheses.

```javascript
1 + 2 + 3
"It was the panda ".concat("in the library ", "with a dust buster")
```
Clojure’s structure is very simple and consistent by comparison. No matter the operator or the data operated on, the structure is the same.

### Control flow

Let's look at three basic control flow operators. There is more, but these are a good starting point.

#### if

The general structure for an `if` expression is:

```cljs
(if boolean-form
  then-form
  optional-else-form)
```

A boolean form us just a form that evaluates to a thruthy or falsey value. Here are a couple of example:

```clj
(if true
 	"By Zeus's hammer!"
 	"By Aquaman's trident!")
(if false
 	"By Zeus's hammer!"
 	"By Aquaman's trident!")
```

We can omit the `else` branch. if we do that a the boolean expression is false, Clojure returns `nil`, like this

```clj
(if false
 	"By Odin's Elbow!")
```


#### do

The `do` operator let us wrap multiple forms in parentheses and run each of them.

```clj
(if true
 	(do (println "Success")
  		"By Zeus's hammer!")
 	(do (println "Failure!")
  		"By Aquaman's trident"))
```

This operator lets us do multiple things in each of the if expression’s branches. In this case, two things happen: Success! is printed in the REPL, and "By Zeus's hammer!" is returned as the value of the entire if expression.

#### when

The `when` operator is like a combination of `if` and `do`, but with no `else` branch. Here’s an example:

```clj
(when true
  (println "Success!")
  "abra cadabra")
```

`when` is useful for doing multiple things when some condition is `true`. It always returns `nil` when the condition is `false`.

#### nil, true, false, Truthiness, Equality, and Boolean Expressions

Clojure has `true` and `false` values. `nil` is use to indicate *no value* in Clojure. You can check if a value is `nil`with the `nil?` function:

```clj
(nil? 1)
(nil? nil)
```

`nil` and `false` are used to represent logical falsiness, whereas all other values are logically thruthy.

```clj
(if "bears eat beets"
  "bears beets Battlestar Galactica")
(if nil
  "This won't be the result because nil is falsey"
  "nil is falsey")
```

Clojure equality operator is `=`

```clj
(= 1 1)
(= nil nil)
(= 1 2)
```

Clojure uses boolean operator `or` and `and`. `or` returns the first thruthy value of the last value. `and` returns the first falsey value or the last thruthy value. Let's look at `or` first:

```clj
(or false nil :large_I_mean_venti :why_cant_I_just_say_large)
(or (= 0 1) (= "yes" "no"))
(or nil)
```

Now let's look at `and`:

```clj
(and :free_wifi :hot_coffee)

(and :feelin_super_cool nil false)
```

### Naming values with def

We use `def` to *bind* a name to a value in Clojure.

```clj
(def failed-protagonist-names
 	["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])
```

Notice that I’m using the term bind, whereas in other languages you’d say you’re assigning a value to a variable. Those other languages typically encourage you to perform multiple assignments to the same variable.

For example, in Ruby you might perform multiple assignments to a variable to build up its value:

```ruby
severity = :mild
error_message = "OH GOD! IT'S A DISASTER! WE'RE "
if severity == :mild
error_message = error_message + "MILDLY INCONVENIENCED!"
else
error_message = error_message + "DOOOOOOOMED!"
end
```

We might be tempted to do something similar in Clojure:

```cljs
(def severity :mild)
(def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
(if (= severity :mild)
  (def error-message (str error-message "MILDLY INCONVENIENCED!"))
  (def error-message (str error-message "DOOOOOOOMED!")))
```

However, changing the value associated with a name like this can make it harder to understand our program’s behavior because it’s more difficult to know which value is associated with a name or why that value might have changed. Clojure has a set of tools for dealing with change, which we'll tackle in the future. 

```clj
(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
    (if (= severity :mild)
      "MILDLY INCONVENIENCED!"
      "DOOOOOOOMED!")))

(error-message :mild)
```

## Data Structures

Clojure comes with a handful of data structures. All Clojure's data structures are immutable, meaning they can't change in place.

### Numbers

Clojure has a quite sophisticated numerical support. For more details check out the [documentation](http://clojure.org/data_structures#Data%20Structures-Numbers). 

Clojure has support for integers, floats and even ratios, which Clojure can represent directly.

```clj
93
1.2
1/5
```

### Strings

String represents text. Here are some examples of string literals:

```clj
"Lord Voldemort"
"\"he who must not be named\""
"\"Great cow of Moscow! - Hermes Conrad\""
```

Notice that Clojure only allows double quotes to delineate strings. `'Lord Voldemort'`, for example, is not a valid string. Also notice that Clojure doesn't have string interpolation. It only allows concatenation via the `str` function:

```clj
(def author "Chewbacca")
(str "\" Uggllglglglglglglglll\" - " author)
```

### Maps

Maps are similar to dictionaries or hashes in other languages. They are a way of associating some value with some other value. here's an empty map:

```clj
{}
```

In this example, `:first-name` and `:last-name` are keywords (more on that later)

```clj
{:first-name "Charlie"
 :last-name "McFishwich"}
```

her we associate `"string-key"` with the `+` function.

```clj
{"string-key" +}
```

Maps can be nested:

```clj
{:name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}
```

Notice that map values can be of any type - strings, numbers, maps, vectors, even functions.

Besides using literals, we can use the `hash-map` function to create a map:

```clj
(hash-map :a 1 :b 2)
```

We can look up values in map with the `get` function:

```clj
(get {:a 0 :b 1} :b)
(get {:a 0 :b {:c "ho hum"}} :b)
```

`get` will return `nil` if it doesn't find the key. It can also give a default value to return:

```clj
(get {:a 0 :b 0} :c)

(get {:a 0 :b 1} :c "unicorns?")
```

`get-in` will look up values in nested maps:

```clj
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
```

Another way to look up value in a map is to treat the map like a function whith the key as its argument:

```clj
({:name "The Human Coffeepot"} :name)
```

Another cool thing you can do with maps is use keywords as functions to look up their values, which leads to the next subject, keywords.

### Keywords

Clojure keywords are best understood by seeing how they’re used. They’re primarily used as keys in maps. here are some examples of keywords:

```clj
:a
:rumplestiltsken
:34
:_?
```

Keywords can be used as functions that look up the corresponding value in a data structure. For example: 
```clj
(:a {:a 1 :b 2 :c 3})
```

This is equivalent to:
```clj
(get {:a 1 :b 2 :c 3} :a)
```

You can provide default value, as with `get`:

```clj
(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")
```

Using a keyword as a function is pleasantly succinct, and Real Clojurists do it all the time. You should do it too!