select * from vins;
select * from utilisateur;
select * from caveAVin;
select * from etagère;
select * from bouteille;
select * from bouteille_has_vins;

UPDATE vins SET identifiantVin='7' WHERE identifiantVin=2;
DELETE FROM bouteille WHERE identifiantBouteille=1;

DROP TABLE Bouteille_has_Vins;
DROP TABLE vins ;
DROP TABLE Bouteille;
DROP TABLE Etagère;
DROP TABLE CaveAVin;
DROP TABLE Utilisateur;

DROP SCHEMA Cave;
