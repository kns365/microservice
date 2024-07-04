-- --------------------------------------------------------
-- --  DDL for Table RATE use for rate limit type JPA
-- --------------------------------------------------------
--
-- CREATE TABLE "RATE"
-- (	"RATE_KEY" VARCHAR2(255 CHAR),
--      "EXPIRATION" TIMESTAMP (6),
--      "REMAINING" NUMBER(19,0),
--      "REMAINING_QUOTA" NUMBER(19,0),
--      "RESET" NUMBER(19,0)
-- ) ;
-- --------------------------------------------------------
-- --  DDL for Index SYS_C0068172
-- --------------------------------------------------------
--
-- CREATE UNIQUE INDEX "IDX_RATE" ON "RATE" ("RATE_KEY")
-- ;
-- --------------------------------------------------------
-- --  Constraints for Table RATE
-- --------------------------------------------------------
--
-- ALTER TABLE "RATE" MODIFY ("RATE_KEY" NOT NULL ENABLE);
-- ALTER TABLE "RATE" ADD PRIMARY KEY ("RATE_KEY")
--     USING INDEX  ENABLE;
