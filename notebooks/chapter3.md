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

### Vectors
A vector is similar to an array, i that it is a 0-indexed collection. For example, here's a vector literal:

```clj
[3 2 1]
```

Here we're returning the 0th element of a vector:
```clj
(get [3 2 1] 0)
```

Here's another example of getting by index:

```clj
(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)
```

Vector elements can be of any type, and you can mix types.

We can create vectors with the vecto function:

```clj
(vector "creepy" "full" "moon")
```

`conj` is used to *add* aditional elements to a vector. Elements are added to the end of the vector.

```clj
(conj [1 2 3] 1)
```

Vectors aren’t the only way to store sequences; Clojure also has lists.

### Lists

List are similar to vectors in that they're linear collections of values. but there are some differences. We can't retrieve list elements with `get`. To write a list literal, just insert the elements into parentheses and use a single quote at the beginning:

```clj
'(1 2 3 4)
```

Notice that when the REPL prints out the list, it doesn't include the single quote. We'll come back to why later. To retrieve an element from a list, use the `nth` function.

```clj
(nth '(:a :b :c) 0)
(nth '(:a :b :c) 2)
```

It is good to know that using `nth` is slower than `get` because it has to traverse all *n* elements of a list to get the *n*th, whereas it only takes a few hops at most to access a vector element by ts index.

List values can be of any type and lists can be created with the `list` function:

```clj
(list 1 "two" {3 4})
```

Elements can be added to the beginning of a list:

```clj
(conj '(1 2 3) 4)
```

A good rule of thumb to know when to use list or vector:
- for macros or a need to add items at the beginning of a sequence use **list**
- otherwise, use **vector**

### Sets

Sets are collection of unique values. Clojure has two kinds of sets: hash sets and sorted sets. We'll focus on hashed set as they are used more often. Here an example of literal notation for a hash set:

```clj
#{"kurt vonnegut" 20 :icicle}
```

We can also use `hash-set` to create a set:

```clj
(hash-set 1 1 2 2)
```

Note that multiple instances of a value become one unique value in the set. trying to add a value already contained in the set, will still lead to only one of that value:

```clj
(conj #{:a :b} :b)
```

We can create set from existing vectors and lists by using the `set` function:

```clj
(set [3 3 3 4 4])
```

We can check membership using the `contains?`or `get` functions or by using a keyword as a function with the set as its argument:

```clj
(contains? #{:a :b} :a)
(contains? #{:a :b} 3)
(contains? #{nil} nil)

(:a #{:a :b})

(get #{:a :b} :a)
(get #{:a nil} nil)
(get #{:a :b} "kurt vonnegut")
```

Notice that using get to test whether a set contains nil will always return nil, which is confusing. contains? may be the better option when you’re testing specifically for set membership.

### Simplicity

You may have noticed that the treatment of data structures so far doesn’t include a description of how to create new types or classes. The reason is that Clojure’s emphasis on simplicity encourages you to reach for the built-in data structures first.


> It is better to have 100 functions operate on one data structure than 10 functions on 10 data structures.
> 	—Alan Perlis

You’ll learn more about this aspect of Clojure’s philosophy in the coming chapters. For now, keep an eye out for the ways that you gain code reusability by sticking to basic data structures.

## Functions

### Calling functions

We've seen many examples of function calls:

```clj
(+ 1 2 3 4)
(* 1 2 3 4)
(first [1 2 3 4])
```

All Clojure operations have the same syntax: *opening parenthesis, operator, operands, closing parentheses*. *Function call* is another term for an operation where the operation is a function or a *function expression* (an expression that returns a function).

This lets you write some pretty interesting code. Here's a function expression that returns the `+` (addition) function:

```clj
(or + -)
```

That return value is the string representation of the addition function. You can also use this expression as the operator in another expression:

```clj
((or + -) 1 2 3)
```

Because `(or + -)` returns `+`, this expression evaluates to the sum of `1`, `2`, `3`, returning `6`.

here are a couple more valid functions calls that each retun `6`:

```clj
((and (= 1 1) +) 1 2 3)
((first [+ 0]) 1 2 3)
```

In the first example, the return value of `and` is the first falsey value or the last thruthy value. In this case, `+` is returned because it's the last thruthy value, and is then applied to the arguments `1 2 3`, returning `6`. In the second example, the return value of `first` is the first element in a sequence, which is `+` in this case.

However, these aren't valide function calls, because numbers and strings aren't functions:

```cljs
(1 2 3 4)
("test" 1 2 3)
```

If you run these in your REPL, you'll get something like this:

```cljs
ClassCastException: class java.lang.Long cannot be cast to class clojure.lang.IFn (java.lang.Long is in module java.base of loader 'bootstrap'; clojure.lang.IFn is in unnamed module of loader 'app')
```

You’re likely to see this error many times as you continue with Clojure: <x> cannot be cast to clojure.lang.IFn just means that you’re trying to use something as a function when it’s not.

Function flexibility doesn’t end with the function expression! Syntactically, functions can take any expressions as arguments—including other functions. Functions that can either take a function as an argument or return a function are called higher-order functions. Programming languages with higher-order functions are said to support first-class functions because you can treat functions as values in the same way you treat more familiar data types like numbers and vectors.

Take the `map` function (not to be confused with the map data structure), for instance. `map` creates a new list by applying a function to each member of a collection. Here, the `inc` function increments a number by 1:

```clj
(inc 1.1)
(map inc [0 1 2 3])
```

(Note that `map` doesn’t return a vector, even though we supplied a vector as an argument. For now, just trust that this is okay and expected.)

Clojure (and all Lisps) allows you to create functions that generalize over processes. `map` allows you to generalize the process of transforming a collection by applying a function—any function—over any collection.

The last detail that you need know about function calls is that Clojure evaluates all function arguments recursively before passing them to the function. Here’s how Clojure would evaluate a function call whose arguments are also function calls:

```cljs
(+ (inc 199) (/ 100 (- 7 2)))
(+ 200 (/ 100 (- 7 2))) ; evaluated "(inc 199)"
(+ 200 (/ 100 5)) ; evaluated (- 7 2)
(+ 200 20) ; evaluated (/ 100 5)
220 ; final evaluation
```

The function call kicks off the evaluation process, and all subforms are evaluated before applying the `+` function.


### Function Calls, Macro Calls, and Special Forms

In the previous section, you learned that function calls are expressions that have a function expression as the operator. The two other kinds of expressions are macro calls and special forms. You’ve already seen a couple of special forms: definitions and `if` expressions.

You’ll learn everything there is to know about macro calls and special forms in Chapter 7. For now, the main feature that makes special forms “special” is that, unlike function calls, *they don’t always evaluate all of their operands*.

Take `if`, for example. This is its general structure:

```cljs
(if boolean-form
  then-form
  optional-else-form)
```

Now imagine you had an if statement like this:

```cljs
(if good-mood
  (tweet walking-on-sunshine-lyrics)
  (tweet mopey-country-song-lyrics))
```

Clearly, in an `if` expression like this, we want Clojure to evaluate only one of the two branches. If Clojure evaluated both `tweet` function calls, your Twitter followers would end up very confused.

Another feature that differentiates special forms is that you can’t use them as arguments to functions. In general, special forms implement core Clojure functionality that just can’t be implemented with functions. Clojure has only a handful of special forms, and it’s pretty amazing that such a rich language is implemented with such a small set of building blocks.

Macros are similar to special forms in that they evaluate their operands differently from function calls, and they also can’t be passed as arguments to functions. But this detour has taken long enough; it’s time to learn how to define functions!

### Defining Functions

Function definitions are composed of five main parts:

- defn
- Function name
- A docstring describing the function (optional)
- Parameters listed in brackets
- Function body

Here’s an example of a function definition and a sample call of the function:

```clj
(defn too-enthusiastic ; too-enthusiastic is the name of the function
  "Return a cheer that might be a bit too enthusiastic" ; here is the docstring
  [name] ; the parameter list
  (str "OH. MY. GOD! " name " YOU ARE MOST DEFINITELY LIKE THE BEST " "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"))

(too-enthusiastic "Zelda")
```

#### The Docstring

The *docstring* is a useful way to describe and document your code. You can view the docstring for a function in the REPL with `(doc fn-name)`—for example, `(doc map)`. The docstring also comes into play if you use a tool to generate documentation for your code.

#### Parameters and Arity
Clojure functions can be defined with zero or more parameters. The values you pass to functions are called *arguments*, and the arguments can be of any type. The number of parameters is the function’s arity. Here are some function definitions with different arities:

```clj
(defn no-params
  []
  "I take no parameters!")
(defn one-param
  [x]
  (str "I take one parameter: " x))
(defn two-params
  [x y]
  (str "Two parameters! That's nothing! Pah! I will smoosh them "
  "together to spite you! " x y))
```

Functions also support arity overloading. This means that you can define a function so a different function body will run depending on the arity. Here’s the general form of a multiple-arity function definition. Notice that each arity definition is enclosed in parentheses and has an argument list:

```cljs
(defn multi-arity
  ;; 3-arity arguments and body
  ([first-arg second-arg third-arg]
     (do-things first-arg second-arg third-arg))
  ;; 2-arity arguments and body
  ([first-arg second-arg]
     (do-things first-arg second-arg))
  ;; 1-arity arguments and body
  ([first-arg]
     (do-things first-arg)))
```

Arity overloading is one way to provide default values for arguments. In the following example, "karate" is the default argument for the chop-type parameter:

```clj
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
     (str "I " chop-type " chop " name "! Take that!"))
  ([name]
     (x-chop name "karate")))
```

We can call the `x-chop` function with two arguments:
```clj
(x-chop "Kanye West" "slap")
```

And we can also call `x-chop` with only one arguments and `karate` will be used as default `chop-type`:

```clj
(x-chop "Kanye East")
```

We can also make each arity do something completely unrelated:

```clj
(defn weird-arity
  ([]
     "Destiny dressed you this morning, my friend, and now Fear is
     trying to pull off your pants. If you give up, if you give in,
     you're gonna end up naked with Fear just standing there laughing
     at your dangling unmentionables! - the Tick")
  ([number]
     (inc number)))
```

The 0-arity body returns a wise quote the 1-arity body increments a number.

Clojure also allows you to define variable-arity functions by including a *rest parameter*, as in “put the rest of these arguments in a list with the following name.” The rest parameter is indicated by an ampersand (&):

```clj
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(codger "Billy" "Anne-Marie" "The Incredible Bulk")
```

We can mix normal parameters and rest parameters but the rest parameter has to come last:

```clj
(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")
```

#### Destructuring

The basic idea behind destructuring is that it lets you concisely bind names to values within a collection.

```clj
(defn my-first
  [[first-thing]]
  first-thing)

(my-first ["oven" "bike" "war-axe"])
```

The `my-first` function associates the symbol `first-thing` with the first element of the vector that was passed in as an argument.

When destructuring a vector or a list, you can name as many elementsas you want and also use rest parameters:

```clj
(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (clojure.string/join "\n" [(str "Your first choice is: " first-choice)
                            (str "Your second choice is: " second-choice)
                            (str "We're ignoring the rest of your choices."
                                 "Here they are in case you need to cry over them: "
                                 (clojure.string/join ", " unimportant-choices))]))

(chooser ["Marmalade" "Handsome Jack" "Pigpen" "Aquaman"])
```

Here, the rest parameter unimportant-choices handles any number of additional choices from the user after the first and second.

We can also destructure maps by providing a map as a parameter:

```clj
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (clojure.string/join "\n" [(str "Treasure lat: " lat)
                             (str "Treasure lng: " lng)]))

(announce-treasure-location {:lat 28.22 :lng 81.33})
```

The previous function definition associates the name `lat` with the value corresponding to the key `:lat` and the name `lng` with the value corresponding to the key `:lng`.

To simply break keywords out of a map, there's a shorter syntax for that. This as the same result as the previous example:

```clj

(defn announce-treasure-location2
  [{:keys [lat lng]}]
  (clojure.string/join "\n" [(str "Treasure lat: " lat)
                             (str "Treasure lng: " lng)]))

(announce-treasure-location2 {:lat 28.22 :lng 81.33})
```

We can retain access to the original map argument by using the :as keyword. In the following example, the original map is accessed with `treasure-location`:

```cljs
(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))

  ;; One would assume that this would put in new coordinates for your ship
  (steer-ship! treasure-location))
```

Destructuring is a way of instructing Clojure on how to associate names with vakues in a list, map, set or vector. Now, on to the part of the function that actually does something: the function body!

```clj
(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")

(illustrative-function)
```

Here's another function body, which uses an `if` expression:

```clj
(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh! What a big number!"
    "Thats number's OK, I guess"))

(number-comment 5)
(number-comment 7)
```

#### All Functions Are Created Equal
One final note: Clojure has no privileged functions. + is just a function, - is just a function, and inc and map are just functions. They’re no better than the functions you define yourself.

#### Anonymous Functions

In Clojure, functions doesn't need to have names. It is possible to create anonymous functions with the `fn` form:

```cljs
(fn [param-list]
  function-body)
```

Looks a lot like `defn`, doesn't it? Here some examples:

```clj
(map (fn [name] (str "Hi, " name))
     ["Dath Vader" "Mr Magoo"])

((fn [x] (* x 3)) 8)
```

We could treat `fn` nearly identically to the way we treat `defn`: the parameter lists, the function body, the argument destructuring, the rest parameters work the same. We could even associate an anonymous function to a name, which shouldn't come as a surprise.

```clj
(def my-special-multiplier (fn [x] (* x 3)))
(my-special-multiplier 12)
```

Clojure also offers another, more compact way to create anonymous functions. Here what it looks like:

```clj
#(* % 3)
```

Whoa, that looks weird. Go ahead and apply that weird-looking function:

```clj
(#(* % 3) 8)
```

Here's an example of passing an anonymous function as an argument to `map`.

```clj
(map #(str "Hi, " % ) ["Darth Vader" "Mr. Magoo"])
```

The percent sign, `%`, indicates the argument passed to the function. If the anonymous function takes multiples arguments, we distinguish theml like this: `%1`, `%2`, `%3`, and so on. `%` is equivalent to `%1`:

```clj
(#(str %1 " and " %2) "cornbread" "butter beans")
```

We can also pass a rest parameter with `%&`:

```clj
(#(identity %&) 1 "blarg" :yip)
```

Here, `identity` retuns the argument it's given without altering it. Rest arguments are stored as lists, so the function application returns a list of all parameters.

For simple anonymous function, this style is the best because it's visually compact. On the other hand, it can become unreadable for longer more complex function. In this case, best using the `fn` style function.

#### Returning function

Functions can return other functions. Here's an example:

```clj
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)
```

Here, `inc-by` is in scope, so the returned function has access to it even when the returned function is used outside `inc-maker`.
