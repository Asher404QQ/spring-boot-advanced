DROP TABLE IF EXISTS author;
DROP SEQUENCE IF EXISTS author_seq;
CREATE TABLE author(
    id bigint not null primary key,
    name text not null,
    email text not null
);

CREATE SEQUENCE author_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

insert into author(id, name, email) values (nextval('author_seq'), 'Tamara27', 'Jules_Fisher@hotmail.com'),
                                           (nextval('author_seq'), 'Cristal4', 'Ford.Prosacco@hotmail.com'),
                                           (nextval('author_seq'), 'Ezequiel.Morar', 'Maribel_Barton22@yahoo.com');