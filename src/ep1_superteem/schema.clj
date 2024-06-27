(ns ep1-supeerteem.schema
  (:require [std.lang :as l]
            [std.lib :as h]
            [rt.postgres :as pg]))

(l/script :postgres
  {:require [[rt.postgres :as pg]]
   :static {:application ["superteem"]
            :seed        ["superteem/core"]
            :all         {:schema   ["superteem/core"]}}})

(deftype.pg ^{:public true}
  User
  [:id                 {:type :uuid   :primary true}
   :name               {:type :text   :required true}
   :alternate-names    {:type :jsonb  :required true}
   :image-uri          {:type :text   :required true}
   :description        {:type :text   :required true}
   :qualifications     {:type :text   :required true}
   #_
   <job_types>
   :approved	       {:type :boolean :required true}
   :version            {:type :integer :required true}])


(deftype.pg ^{:public true}
  Publisher
  [:id           {:type :uuid :primary true}
   :requested	 {:type :boolean :required true}
   :request	 {:type :jsonb}
   
   :approved	 {:type :boolean :required true}
   :publishing	 {:type :jsonb}])

(deftype.pg ^{:public true}
  Organisation
  [:id           {:type :uuid :primary true}
   :name         {:type :text :required true}
   :short-name   {:type :text :required true}
   :description  {:type :text :required true}
   :website      {:type :text :required true}
   :publish-approved {:type :text :required true}])

(deftype.pg ^{:public true}
  Team
  [:id           {:type :uuid :primary true}
   :name         {:type :text :required true}
   :short-name   {:type :text :required true}
   :alternate-names    {:type :jsonb  :required true}
   :description  {:type :text :required true}
   :website      {:type :text :required true}
   :privacy      {:type :integer :required true}
   :tags         {:type :jsonb  :required true}
   :organisation {:type :ref :required true
                  :ref {:ns -/Organisation}}
   :version            {:type :integer :required true}])

(deftype.pg ^{:public true}
  Group
  [:id           {:type :uuid :primary true}
   :name         {:type :text :required true}
   :description  {:type :text :required true}
   :type         {:type :text :required true}
   :organisation {:type :ref :required true
                  :ref {:ns -/Organisation}}
   :team         {:type :ref :required true
                  :ref {:ns -/Team}}
   :parent       {:type :ref :required true
                  :ref {:ns -/Group}}])

(deftype.pg ^{:public true}
  Membership
  [:id           {:type :uuid :primary true}
   :user         {:type :ref :required true
                  :ref {:ns -/User}}
   :group        {:type :ref :required true
                  :ref {:ns -/Group}}
   :roles         {:type :jsonb  :required true}
   :labels        {:type :jsonb  :required true}
   :version       {:type :integer :required true}
   :created       {:type :long :required true}
   :modified      {:type :long :required true}
   :joined        {:type :long :required true}])

;;
;; Survey or Observation, Video, Book, Quiz or Assessment
;;

(deftype.pg ^{:public true}
  Content ;; Form, Video, 
  [:id           {:type :uuid :primary true}
   :type         {:type :text :required true}
   :name         {:type :text :required true}
   :description  {:type :text :required true}])

(deftype.pg ^{:public true}
  ContentBlock
  [:id           {:type :uuid :primary true}
   :type         {:type :text :required true}
   :form         {:type :ref :required true
                  :ref {:ns -/Content}}
   :label        {:type :text :required true}
   :content      {:type :jsonb :required true}])

(deftype.pg ^{:public true}
  Certificate
  [:id           {:type :uuid :primary true}
   :content      {:type :ref :required true
                  :ref {:ns -/Content}}
   :validity     {:type :text}])

(deftype.pg ^{:public true}
  Certification
  [:id           {:type :uuid :primary true}
   :certificate      {:type :ref :required true
                  :ref {:ns -/Certificate}}
   :user         {:type :ref :required true
                  :ref {:ns -/User}}
   :expires      {:type :long}])

;;
;; Interactions
;;

(deftype.pg ^{:public true}
  Interaction
  [:id           {:type :uuid :primary true}
   :user         {:type :ref :required true
                  :ref {:ns -/User}}
   :group         {:type :ref :required true
                  :ref {:ns -/Group}}
   :params       {:type :jsonb  :required true}])

;;
;; Study
;;

(deftype.pg ^{:public true}
  Study
  [:id           {:type :uuid :primary true}])

(deftype.pg ^{:public true}
  StudyContent
  [:id           {:type :uuid :primary true}
   :study       {:type :ref :required true
                  :ref {:ns -/Study}}
   :content      {:type :ref :required true
                  :ref {:ns -/Content}}])

(deftype.pg ^{:public true}
  StudyInteraction
  [:id           {:type :uuid :primary true}
   :content      {:type :ref :required true
                  :ref {:ns -/StudyContent}}
   :interaction      {:type :ref :required true
                      :ref {:ns -/Interaction}}])

;;
;; Pathway
;;

(deftype.pg ^{:public true}
  Pathway
  [:id           {:type :uuid :primary true}
   :name         {:type :text :required true}
   :description  {:type :text :required true}
   :banner-uri   {:type :text :required true} 
   :logo-uri     {:type :text :required true} 
   :media-uris   {:type :jsonb  :required true}
   :publisher    {:type :ref :required true
                  :ref {:ns -/Publisher}}])


(deftype.pg ^{:public true}
  PathwayProgram
  [:id           {:type :uuid :primary true}
   :pathway       {:type :ref :required true
                  :ref {:ns -/Pathway}}])


(deftype.pg ^{:public true}
  PathwaySection
  [:id           {:type :uuid :primary true}
   :pathway       {:type :ref :required true
                  :ref {:ns -/Pathway}}])

(deftype.pg ^{:public true}
  PathwayContent
  [:id           {:type :uuid :primary true}
   :section       {:type :ref :required true
                   :ref {:ns -/PathwaySection}}
   :pathway       {:type :ref :required true
                  :ref {:ns -/Pathway}}
   :repeat       {:type :boolean :required true}
   :content      {:type :ref :required true
                  :ref {:ns -/Content}}])

(deftype.pg ^{:public true}
  PathwayInteraction
  [:id           {:type :uuid :primary true}
   :content      {:type :ref :required true
                  :ref {:ns -/PathwayContent}}
   :interaction      {:type :ref :required true
                      :ref {:ns -/Interaction}}])
