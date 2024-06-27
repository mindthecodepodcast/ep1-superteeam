(ns ep1-supeerteem.build
  (:require [std.lang :as l]
            [std.lib :as h]
            [rt.postgres :as pg]
            [ep1-supeerteem.schema :as schema]
            [std.make :as make :refer [def.make]]))

(def +readme+
  {:type :readme.md
   :main ["* Ep1 - Superteem"]})

(def.make BUILD
  {:tag      "db-refactor"
   :build    ".build/db-refactor"
   :github   {:repo   "mindthecode/ep1-superteem-db-refactor"
              :private true
              :description "* Ep1 - Superteem"}
   :sections
   {:setup      [+readme+]}
   :default  [{:type   :module.schema
               :lang   :postgres
               :file   "setup.sql"
               :main   'ep1-supeerteem.schema
               :emit   {:code   {:label true}}}]})
