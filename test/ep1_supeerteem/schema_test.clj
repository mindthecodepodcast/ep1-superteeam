(ns ep1-supeerteem.schema-test
  (:use code.test)
  (:require [std.lang :as l]
            [std.lib :as h]
            [rt.postgres :as pg]))

(l/script- :postgres
  {:runtime :jdbc.client
   :config  {:rt/id :test.scratch
             :dbname "superteem-scratch"
             :temp :create}
   :require [[ep1-supeerteem.schema :as schema]
             [rt.postgres :as pg]]})

(fact:global
 {:setup    [(l/rt:restart)
             (l/rt:teardown :postgres)
             (l/rt:setup :postgres)]
  :teardown [(l/rt:stop)]})

