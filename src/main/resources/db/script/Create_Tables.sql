-- Table: EMPLOYEE

-- DROP TABLE IF EXISTS "EMPLOYEE";

CREATE TABLE IF NOT EXISTS "EMPLOYEE"
(
    "EMP_ID" integer NOT NULL,
    "NATIONAL_IDENTIFIER" character varying(8) COLLATE pg_catalog."default" NOT NULL,
    "FIRST_NAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "LAST_NAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "DOJ" date NOT NULL,
    "DOL" date,
    "STATUS" character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "CREATED_TS" timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT "EMPLOYEE_pkey" PRIMARY KEY ("EMP_ID")
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS "EMPLOYEE"
    OWNER to "local-dev";

-- Table: DEPARTMENT

-- DROP TABLE IF EXISTS "DEPARTMENT";

CREATE TABLE IF NOT EXISTS "DEPARTMENT"
(
    "DEPARTMENT" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "EMP_ID" integer NOT NULL,
    "UNIT" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "POSITION" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "DEPARTMENT_pkey" PRIMARY KEY ("DEPARTMENT", "EMP_ID")
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS "DEPARTMENT"
    OWNER to "local-dev";