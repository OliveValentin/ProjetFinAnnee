USE cave;

INSERT INTO Vins VALUES(1,'alsace','zind','chateauvin1','rouge','syrah');
INSERT INTO Vins VALUES(2,'beaujolais','domainevin2','montmelas','blanc','grenache');
INSERT INTO Vins VALUES(3,'jura','domainevin3','arlay','rose','muscat');
INSERT INTO Vins VALUES(4,'bordeaux','chevalier','chateauvin4','rose','riesling');


INSERT INTO Utilisateur VALUES(1, 'nomuser1', MD5('mdpuser1'));

INSERT INTO CaveAVin VALUES('caveMagasin', 3, 'blabla', 1);

INSERT INTO Etagère VALUES(1, 'sous la fenêtre nord','caveMagasin', 1);
INSERT INTO Etagère VALUES(2, 'sous escalier sud','caveMagasin', 1);
INSERT INTO Etagère VALUES(3, 'à droite de entrée','caveMagasin', 1);


INSERT INTO Bouteille VALUES(1, 150);
INSERT INTO Bouteille VALUES(2, 225);
INSERT INTO Bouteille VALUES(3, 250);
INSERT INTO Bouteille VALUES(4, 150);

INSERT INTO Bouteille_has_Vins VALUES(1, 1, 1956, 1);
INSERT INTO Bouteille_has_Vins VALUES(2, 4, 1822, 2);
INSERT INTO Bouteille_has_Vins VALUES(3, 2, 1963, 2);
INSERT INTO Bouteille_has_Vins VALUES(4, 3, 1974, 3);