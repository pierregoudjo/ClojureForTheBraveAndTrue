(ns user
  (:require [nextjournal.clerk :as clerk]
            [clojure.repl :as repl]))

(defn serve! []
  (clerk/serve! {:browse true :port 6677 :watch-paths ["notebooks"]}))

(comment
  (serve!)
  (clerk/halt!)
  (repl/doc clerk/serve!))