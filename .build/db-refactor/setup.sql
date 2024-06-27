CREATE SCHEMA IF NOT EXISTS "superteem/core";

-- ep1-supeerteem.schema/User [12] 
DROP TABLE IF EXISTS "superteem/core"."User" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."User" (
  "id" UUID PRIMARY KEY,
  "name" TEXT NOT NULL,
  "alternate_names" JSONB NOT NULL,
  "image_uri" TEXT NOT NULL,
  "description" TEXT NOT NULL,
  "qualifications" TEXT NOT NULL,
  "approved" BOOLEAN NOT NULL,
  "version" INTEGER NOT NULL
);

-- ep1-supeerteem.schema/Publisher [26] 
DROP TABLE IF EXISTS "superteem/core"."Publisher" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Publisher" (
  "id" UUID PRIMARY KEY,
  "requested" BOOLEAN NOT NULL,
  "request" JSONB,
  "approved" BOOLEAN NOT NULL,
  "publishing" JSONB
);

-- ep1-supeerteem.schema/Organisation [35] 
DROP TABLE IF EXISTS "superteem/core"."Organisation" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Organisation" (
  "id" UUID PRIMARY KEY,
  "name" TEXT NOT NULL,
  "short_name" TEXT NOT NULL,
  "description" TEXT NOT NULL,
  "website" TEXT NOT NULL,
  "publish_approved" TEXT NOT NULL
);

-- ep1-supeerteem.schema/Team [44] 
DROP TABLE IF EXISTS "superteem/core"."Team" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Team" (
  "id" UUID PRIMARY KEY,
  "name" TEXT NOT NULL,
  "short_name" TEXT NOT NULL,
  "alternate_names" JSONB NOT NULL,
  "description" TEXT NOT NULL,
  "website" TEXT NOT NULL,
  "privacy" INTEGER NOT NULL,
  "tags" JSONB NOT NULL,
  "organisation_id" UUID NOT NULL REFERENCES "superteem/core"."Organisation"("id"),
  "version" INTEGER NOT NULL
);

-- ep1-supeerteem.schema/Group [58] 
DROP TABLE IF EXISTS "superteem/core"."Group" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Group" (
  "id" UUID PRIMARY KEY,
  "name" TEXT NOT NULL,
  "description" TEXT NOT NULL,
  "type" TEXT NOT NULL,
  "organisation_id" UUID NOT NULL REFERENCES "superteem/core"."Organisation"("id"),
  "team_id" UUID NOT NULL REFERENCES "superteem/core"."Team"("id"),
  "parent_id" UUID NOT NULL REFERENCES "superteem/core"."Group"("id")
);

-- ep1-supeerteem.schema/Membership [71] 
DROP TABLE IF EXISTS "superteem/core"."Membership" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Membership" (
  "id" UUID PRIMARY KEY,
  "user_id" UUID NOT NULL REFERENCES "superteem/core"."User"("id"),
  "group_id" UUID NOT NULL REFERENCES "superteem/core"."Group"("id"),
  "roles" JSONB NOT NULL,
  "labels" JSONB NOT NULL,
  "version" INTEGER NOT NULL,
  "created" BIGINT NOT NULL,
  "modified" BIGINT NOT NULL,
  "joined" BIGINT NOT NULL
);

-- ep1-supeerteem.schema/Content [89] 
DROP TABLE IF EXISTS "superteem/core"."Content" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Content" (
  "id" UUID PRIMARY KEY,
  "type" TEXT NOT NULL,
  "name" TEXT NOT NULL,
  "description" TEXT NOT NULL
);

-- ep1-supeerteem.schema/ContentBlock [96] 
DROP TABLE IF EXISTS "superteem/core"."ContentBlock" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."ContentBlock" (
  "id" UUID PRIMARY KEY,
  "type" TEXT NOT NULL,
  "form_id" UUID NOT NULL REFERENCES "superteem/core"."Content"("id"),
  "label" TEXT NOT NULL,
  "content" JSONB NOT NULL
);

-- ep1-supeerteem.schema/Certificate [105] 
DROP TABLE IF EXISTS "superteem/core"."Certificate" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Certificate" (
  "id" UUID PRIMARY KEY,
  "content_id" UUID NOT NULL REFERENCES "superteem/core"."Content"("id"),
  "validity" TEXT
);

-- ep1-supeerteem.schema/Certification [112] 
DROP TABLE IF EXISTS "superteem/core"."Certification" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Certification" (
  "id" UUID PRIMARY KEY,
  "certificate_id" UUID NOT NULL REFERENCES "superteem/core"."Certificate"("id"),
  "user_id" UUID NOT NULL REFERENCES "superteem/core"."User"("id"),
  "expires" BIGINT
);

-- ep1-supeerteem.schema/Interaction [125] 
DROP TABLE IF EXISTS "superteem/core"."Interaction" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Interaction" (
  "id" UUID PRIMARY KEY,
  "user_id" UUID NOT NULL REFERENCES "superteem/core"."User"("id"),
  "group_id" UUID NOT NULL REFERENCES "superteem/core"."Group"("id"),
  "params" JSONB NOT NULL
);

-- ep1-supeerteem.schema/Study [138] 
DROP TABLE IF EXISTS "superteem/core"."Study" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Study" (
  "id" UUID PRIMARY KEY
);

-- ep1-supeerteem.schema/StudyContent [142] 
DROP TABLE IF EXISTS "superteem/core"."StudyContent" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."StudyContent" (
  "id" UUID PRIMARY KEY,
  "study_id" UUID NOT NULL REFERENCES "superteem/core"."Study"("id"),
  "content_id" UUID NOT NULL REFERENCES "superteem/core"."Content"("id")
);

-- ep1-supeerteem.schema/StudyInteraction [150] 
DROP TABLE IF EXISTS "superteem/core"."StudyInteraction" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."StudyInteraction" (
  "id" UUID PRIMARY KEY,
  "content_id" UUID NOT NULL REFERENCES "superteem/core"."StudyContent"("id"),
  "interaction_id" UUID NOT NULL REFERENCES "superteem/core"."Interaction"("id")
);

-- ep1-supeerteem.schema/Pathway [162] 
DROP TABLE IF EXISTS "superteem/core"."Pathway" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."Pathway" (
  "id" UUID PRIMARY KEY,
  "name" TEXT NOT NULL,
  "description" TEXT NOT NULL,
  "banner_uri" TEXT NOT NULL,
  "logo_uri" TEXT NOT NULL,
  "media_uris" JSONB NOT NULL,
  "publisher_id" UUID NOT NULL REFERENCES "superteem/core"."Publisher"("id")
);

-- ep1-supeerteem.schema/PathwayProgram [174] 
DROP TABLE IF EXISTS "superteem/core"."PathwayProgram" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."PathwayProgram" (
  "id" UUID PRIMARY KEY,
  "pathway_id" UUID NOT NULL REFERENCES "superteem/core"."Pathway"("id")
);

-- ep1-supeerteem.schema/PathwaySection [181] 
DROP TABLE IF EXISTS "superteem/core"."PathwaySection" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."PathwaySection" (
  "id" UUID PRIMARY KEY,
  "pathway_id" UUID NOT NULL REFERENCES "superteem/core"."Pathway"("id")
);

-- ep1-supeerteem.schema/PathwayContent [187] 
DROP TABLE IF EXISTS "superteem/core"."PathwayContent" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."PathwayContent" (
  "id" UUID PRIMARY KEY,
  "section_id" UUID NOT NULL REFERENCES "superteem/core"."PathwaySection"("id"),
  "pathway_id" UUID NOT NULL REFERENCES "superteem/core"."Pathway"("id"),
  "repeat" BOOLEAN NOT NULL,
  "content_id" UUID NOT NULL REFERENCES "superteem/core"."Content"("id")
);

-- ep1-supeerteem.schema/PathwayInteraction [198] 
DROP TABLE IF EXISTS "superteem/core"."PathwayInteraction" CASCADE;
CREATE TABLE IF NOT EXISTS "superteem/core"."PathwayInteraction" (
  "id" UUID PRIMARY KEY,
  "content_id" UUID NOT NULL REFERENCES "superteem/core"."PathwayContent"("id"),
  "interaction_id" UUID NOT NULL REFERENCES "superteem/core"."Interaction"("id")
);