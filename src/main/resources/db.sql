CREATE TABLE GRAPH (
	ID INTEGER NOT NULL PRIMARY KEY,
	CODE VARCHAR (128) NOT NULL,
	VERTEX INTEGER NOT NULL,
	EDGE INTEGER,
	EDGECON INTEGER
);
ALTER TABLE GRAPH ADD COLUMN vertcon INTEGER ;
ALTER TABLE GRAPH ADD COLUMN conn INTEGER ;
ALTER TABLE GRAPH ADD COLUMN radius INTEGER ;
ALTER TABLE GRAPH ADD COLUMN diametr INTEGER ;

ALTER TABLE GRAPH ADD COLUMN components INTEGER ;
ALTER TABLE GRAPH ADD COLUMN girth INTEGER ;
ALTER TABLE GRAPH ADD COLUMN primitive INTEGER ;
ALTER TABLE GRAPH ADD COLUMN exp INTEGER ;
ALTER TABLE GRAPH ADD COLUMN twoPartial INTEGER ;

create index order_index on graph (vertex);
create index edge_index on graph (edge);
create index edgec_index on graph (EDGECON);
create index c_index on graph (conn);
create index rad_index on graph (radius);
create index diam_index on graph (diametr);