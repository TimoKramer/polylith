(ns polylith.clj.core.validator.datashape.toolsdeps2-project-deployable-config-test
  (:require [clojure.test :refer :all]
            [polylith.clj.core.validator.datashape.toolsdeps2 :as toolsdeps2]))

(def config {:deps '{change {:local/root "../../components/change"}
                     command {:local/root "../../components/command"}
                     poly-cli {:local/root "../../bases/poly-cli"}
                     org.clojure/clojure {:mvn/version "1.10.1"}
                     org.clojure/tools.deps.alpha {:mvn/version "0.8.695"}
                     me.raynes/fs {:mvn/version "1.4.6"}
                     mvxcvi/puget {:mvn/version "1.3.1"}}

             :aliases {:test      {:extra-paths ["../../components/change/test"
                                                 "../../components/creator/test"
                                                 "test"]
                                   :extra-deps  {}}

                       :aot       {:extra-paths ["classes"]
                                     :main-opts ["-e" "(compile,'polylith.clj.core.poly-cli.core)"]}

                       :uberjar   {:extra-deps {'uberdeps {:mvn/version "0.1.10"}}
                                   :main-opts  ["-m" "uberdeps.uberjar"]}}})

(deftest validate-project-deployable-config--valid-config--returns-nil
  (is (= nil
         (toolsdeps2/validate-project-deployable-config config))))

(deftest validate-project-deployable-config--valid-config-withoug-deps--returns-nil
  (is (= nil
         (toolsdeps2/validate-project-deployable-config (dissoc config :deps)))))

(deftest validate-project-deployable-config--invalid-nop-namespace--returns-error-message
  (is (= {:aliases {:test ["invalid type"]}}
         (toolsdeps2/validate-project-deployable-config (assoc-in config [:aliases :test] 1)))))