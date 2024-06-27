(require 'cemerick.pomegranate.aether)
(require 'clojure.string)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(defproject xyz.mindthecode/ep1-superteem "0.1.0-SNAPSHOT"
  :description "relational mapping for superteem"
  :url "https://www.github.com/mindthecodepodcast/ep1-superteem"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :aliases
  {"test"  ["exec" "-ep"
                (clojure.string/join
                 " "
                 '[(use 'code.test)
                   (def res (run :all))
                   (System/exit (+ (:failed res) (:thrown res)))])]
   "incomplete" ["exec" "-ep"
                (clojure.string/join
                 " "
                 '[(use 'code.manage)
                   (incomplete :all)
                   (System/exit 0)])]}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [xyz.zcaudate/code.test           "4.0.3"]
                 [xyz.zcaudate/code.manage         "4.0.3"]
                 [xyz.zcaudate/code.java           "4.0.3"]
                 [xyz.zcaudate/code.maven          "4.0.3"]
                 [xyz.zcaudate/code.doc            "4.0.3"]
                 [xyz.zcaudate/code.dev            "4.0.3"]
		         [xyz.zcaudate/lib.postgres        "4.0.3"]
                 [xyz.zcaudate/net.http            "4.0.3"]
               
                 [xyz.zcaudate/rt.basic            "4.0.3"]
                 [xyz.zcaudate/rt.postgres         "4.0.3"]
                 [xyz.zcaudate/std.lib             "4.0.3"]
                 [xyz.zcaudate/std.log             "4.0.3"]
                 [xyz.zcaudate/std.lang            "4.0.3"]
                 [xyz.zcaudate/std.text            "4.0.3"]
                 [xyz.zcaudate/std.make            "4.0.3"]
                 [xyz.zcaudate/xtalk.lang          "4.0.3"]]
  :profiles {:dev {:plugins [[lein-ancient "0.6.15"]
                             [lein-exec "0.3.7"]
                             [lein-cljfmt "0.7.0"]]}
             :repl {:injections [(try (require 'jvm.tool)
                                      (require '[std.lib :as h])
                                      (create-ns '.)
                                      (catch Throwable t (.printStackTrace t)))]}}
  :jvm-opts
  [;;
   ;; JVM
   ;;
   "-Djdk.tls.client.protocols=\"TLSv1,TLSv1.1,TLSv1.2\""
   "-Djdk.attach.allowAttachSelf=true"
   "--add-opens" "javafx.graphics/com.sun.javafx.util=ALL-UNNAMED"
   "--add-opens" "java.base/java.lang=ALL-UNNAMED"
   "--illegal-access=permit"])

