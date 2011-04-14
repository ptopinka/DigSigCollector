
create table t_documents (
   docid int not null,
   docurl VARCHAR(100) not null,
   validstart DATE not null,
   validend   DATE not null,
   CONSTRAINT t_documents_pk PRIMARY KEY (docid)

)





create table t_signatures(
    idpodpis INT not null,
    iddoc       INT not null,
    CN          VARCHAR2(100),
    login        VARCHAR2(100),
    CONSTRAINTS fk_t_documents
    FOREIGN KEY (iddoc)
    REFERENCES t_documents(docid)
)



#mysql
mysql> create table t_documents (docid  INT NOT NULL AUTO_INCREMENT, docurl VARCHAR(100) not nul
l, validstart DATE not null, validend DATE not null, primary key(docid);

