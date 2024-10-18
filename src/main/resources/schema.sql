-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE "CUSTOMERS" ("ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "CUSTOMER_NAME" VARCHAR(255), "PASSWORD" VARCHAR(255),EMAIL VARCHAR(255));

-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- primary/unique
ALTER TABLE "CUSTOMERS" ADD CONSTRAINT "SQL120325130144011" PRIMARY KEY ("ID");