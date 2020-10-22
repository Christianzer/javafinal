create procedure insertCand(photo in CANDIDATS.PHOTO_CANDIDAT%TYPE, nom in CANDIDATS.NOM_CANDIDAT%TYPE,
                            prenoms in CANDIDATS.PRENOM_CANDIDAT%TYPE, dateC in CANDIDATS.DATE_NAISS_CANDIDAT%TYPE,
                            lieu in CANDIDATS.LIEU_NAIS_CANDIDAT%TYPE,
                            nationalite in CANDIDATS.NATIONALITE_CANDIDAT%TYPE,
                            contact in CANDIDATS.CONTACT_CANDIDAT%TYPE, mail in CANDIDATS.MAIL_CANDIDAT%TYPE)
    is
begin
    insert into CANDIDATS(PHOTO_CANDIDAT, NOM_CANDIDAT,
                          PRENOM_CANDIDAT, DATE_NAISS_CANDIDAT,
                          LIEU_NAIS_CANDIDAT, NATIONALITE_CANDIDAT, CONTACT_CANDIDAT,
                          MAIL_CANDIDAT)
    VALUES (photo, nom, prenoms, dateC, lieu, nationalite, contact, mail);
end;
/

create procedure insertPiece(piecesCand in avoir_pieces.pieces%type
    , cand in avoir_pieces.ID_CAND%type, numero in avoir_pieces.NUM_PI%type,
                             image in avoir_pieces.IMG_PI%type)
    is
begin
    insert into AVOIR_PIECES(PIECES, ID_CAND, NUM_PI, IMG_PI) values (piecesCand, cand, numero, image);
end;
/

create procedure insertdiplome(diplomesCand in avoir_diplomes.diplomes%type, cand in avoir_diplomes.id_cand%type,
                               numero in avoir_diplomes.num_dip%type, image in avoir_diplomes.IMG_DIP%type)
    is
begin
    insert into AVOIR_DIPLOMES(DIPLOMES, ID_CAND, NUM_DIP, IMG_DIP) VALUES (diplomesCand, cand, numero, image);
end;
/

create procedure insertplant(lieu in plantation_candidats.localisation_plantation%type,
                             super in plantation_candidats.superficie_plantation%type,
                             imagecer in plantation_candidats.CERTIFICAT_PROPRIETE%type)
    is
begin
    insert into PLANTATION_CANDIDATS(LOCALISATION_PLANTATION, SUPERFICIE_PLANTATION, CERTIFICAT_PROPRIETE)
    VALUES (lieu, super, imagecer);
end;
/

create procedure insertculture(plantid in avoir_cultures.id_plant%type, typeid in avoir_cultures.id_type_cult%type)
    is
begin
    insert into AVOIR_CULTURES(ID_PLANT, ID_TYPE_CULT) values (plantid, typeid);
end;
/

create procedure insertmethode(plantid in avoir_methodes.id_plant%type, methodeid in avoir_methodes.id_methode%type)
    is
begin
    insert into AVOIR_METHODES(ID_PLANT, ID_METHODE) values (plantid, methodeid);
end;
/

create procedure insertemploye(nbreemploye in employe_candidats.nombre_employe%type
                              , nbrefem in employe_candidats.nombre_femme%type
                              , salaire in employe_candidats.salaire_moyen%type, mineurs in employe_candidats.mineur%type
                              , certificat in employe_candidats.CERTIFICAT_EMPLOYE%type)
    is
begin
    insert into EMPLOYE_CANDIDATS(NOMBRE_EMPLOYE, NOMBRE_FEMME, SALAIRE_MOYEN, MINEUR, CERTIFICAT_EMPLOYE)
    values (nbreemploye, nbrefem, salaire, mineurs, certificat);
end;
/

create procedure insertdossierins(identifiant in dossier_inscriptions.identifiant_candidat%type
                                 , mdp in dossier_inscriptions.mot_de_passe%type
                                 , plantid in dossier_inscriptions.id_plant%type,
    candid in dossier_inscriptions.id_cand%type, emplcand in dossier_inscriptions.id_empl_cand%type
                                 , dateins in dossier_inscriptions.date_inscription%type)
    is
begin
    insert into DOSSIER_INSCRIPTIONS(IDENTIFIANT_CANDIDAT, MOT_DE_PASSE, ID_PLANT, ID_CAND, ID_EMPL_CAND,
                                     DATE_INSCRIPTION)
    values (identifiant, mdp, plantid, candid, emplcand, dateins);
end;
/

create procedure validation(etatdoc in dossier_inscriptions.validation%type,
                            dossierid in dossier_inscriptions.dossier%type)
    is
begin
    update DOSSIER_INSCRIPTIONS SET VALIDATION = etatdoc WHERE DOSSIER = dossierid;
    if (etatdoc = 2) THEN
        insert into NOTIFICATIONAPI(DOSSIERID, MESSAGE) VALUES (dossierid, 2);
        insert into DOSSIERPRIS(DOSSIER) values (dossierid);
    else
        insert into NOTIFICATIONAPI(DOSSIERID, MESSAGE) VALUES (dossierid, 3);
    end if;
end;
/

create procedure rendezvous(dossierid in dossierpris.dossier%type, daterendezvous in dossierpris.date_rendez_vous%type)
    is
    cursor curs is select IDENTIFIANT_JURY
                   from JURY;
    identifiant jury.identifiant_jury%type ;
begin
    update DOSSIERPRIS SET DATE_RENDEZ_VOUS = daterendezvous WHERE DOSSIER = dossierid;
    insert into NOTIFICATIONAPI(DOSSIERID, MESSAGE, INFO) VALUES (dossierid, 3, daterendezvous);
    open curs;
    loop
        fetch curs into identifiant;
        exit when (curs%notfound);
        insert into VISITE (IDENTIFIANT_JURY, DOSSIER) VALUES (identifiant, dossierid);
    end loop;
    close curs;
end;
/

create procedure controle(moyenne in visite.moyenne_obtenue%type, visiteid in visite.id_visite%type
                         , etatcontr in visite.etat%type, datenote in visite.date_note%type
                         , commentairenote in visite.commentaire%type, appre in visite.appreciation%type)
    is
begin
    update VISITE
    SET MOYENNE_OBTENUE = moyenne,
        ETAT            = etatcontr,
        DATE_NOTE       = datenote,
        COMMENTAIRE     = commentairenote,
        APPRECIATION    = appre
    where ID_VISITE = visiteid;
end;
/

create procedure resultatfin(mg in resultatfinal.moyennefinal%type, doc in resultatfinal.dossier%type,
                             datefin in resultatfinal.date_fin%type)
    is
begin
    insert into RESULTATFINAL(MOYENNEFINAL, DOSSIER, DATE_FIN) VALUES (mg, doc, datefin);
end;
/

create procedure selectDossier(dossierNum in dossier_inscriptions.dossier%type,
                               numerDiplome out avoir_diplomes.num_dip%type,
                               imageDiplomeSimple out avoir_diplomes.img_dip%type,
                               numerPiece out AVOIR_PIECES.NUM_PI%type, imagePiece out AVOIR_PIECES.IMG_PI%type,
                               photoCand out candidats.photo_candidat%type, nomCand out candidats.nom_candidat%type,
                               prenomsCand out candidats.prenom_candidat%type,
                               dateNaiss out candidats.date_naiss_candidat%type,
                               nationaliteCand out candidats.nationalite_candidat%type,
                               methodeCulture out methode_cultures.libelle_methodes_cultures%type,
                               typeCulture out type_cultures.libelle_type_cultures%type,
                               diplome out type_diplomes.libelle_type_diplomes%type,
                               piece out type_pieces.libelle_piece%type,
                               certificatPlant out plantation_candidats.certificat_propriete%type,
                               localisation out plantation_candidats.localisation_plantation%type,
                               superficie out plantation_candidats.superficie_plantation%type
    , mineurEmpl out employe_candidats.mineur%type, certEmpl out employe_candidats.certificat_employe%type,
                               nbreEmpl out employe_candidats.nombre_employe%type,
                               nbreFemme out employe_candidats.nombre_femme%type,
                               salaire out employe_candidats.salaire_moyen%type)
    is
begin
    SELECT AD.NUM_DIP,
           AD.IMG_DIP,
           AP.NUM_PI,
           AP.IMG_PI,
           CA.PHOTO_CANDIDAT,
           CA.NOM_CANDIDAT,
           CA.PRENOM_CANDIDAT,
           CA.DATE_NAISS_CANDIDAT,
           CA.NATIONALITE_CANDIDAT,
           MC.LIBELLE_METHODES_CULTURES,
           TC.LIBELLE_TYPE_CULTURES,
           TD.LIBELLE_TYPE_DIPLOMES,
           TP.LIBELLE_PIECE,
           PL.CERTIFICAT_PROPRIETE,
           PL.LOCALISATION_PLANTATION,
           PL.SUPERFICIE_PLANTATION,
           EC.MINEUR,
           EC.CERTIFICAT_EMPLOYE,
           EC.NOMBRE_EMPLOYE,
           EC.NOMBRE_FEMME,
           EC.SALAIRE_MOYEN
    INTO
        numerDiplome,imageDiplomeSimple,
        numerPiece,imagePiece,
        photoCand,nomCand,prenomsCand,dateNaiss,nationaliteCand,
        methodeCulture,typeCulture,diplome,piece,
        certificatPlant,localisation,superficie,
        mineurEmpl,certEmpl,nbreEmpl,nbreFemme,salaire

    FROM DOSSIER_INSCRIPTIONS DI
             JOIN CANDIDATS CA ON CA.ID_CAND = DI.ID_CAND
             JOIN PLANTATION_CANDIDATS PL ON DI.ID_PLANT = PL.ID_PLANT
             JOIN EMPLOYE_CANDIDATS EC ON EC.ID_EMPL_CAND = DI.ID_EMPL_CAND
             JOIN AVOIR_METHODES AM ON AM.ID_PLANT = DI.ID_PLANT
             JOIN AVOIR_PIECES AP ON AP.ID_CAND = DI.ID_CAND
             JOIN AVOIR_CULTURES AC ON AC.ID_PLANT = DI.ID_PLANT
             JOIN AVOIR_DIPLOMES AD ON AD.ID_CAND = DI.ID_CAND
             JOIN TYPE_CULTURES TC ON TC.ID_TYPE_CULTURES = AC.ID_TYPE_CULT
             JOIN METHODE_CULTURES MC ON MC.ID_METHODES_CULTURES = AM.ID_METHODE
             JOIN TYPE_PIECES TP ON TP.ID_PIECE = AP.PIECES
             JOIN TYPE_DIPLOMES TD ON TD.ID_TYPE_DIPLOMES = AD.DIPLOMES
    WHERE DI.DOSSIER = dossierNum;
end;
/

create procedure afficherdossierpris(C_CHRISTIAN OUT SYS_REFCURSOR)
    is
begin
    OPEN C_CHRISTIAN FOR
        select DI.DOSSIER, C2.NOM_CANDIDAT, C2.PRENOM_CANDIDAT, DO.DATE_RENDEZ_VOUS, TC.LIBELLE_TYPE_CULTURES
        from DOSSIERPRIS DO
                 JOIN DOSSIER_INSCRIPTIONS DI on DO.DOSSIER = DI.DOSSIER
                 JOIN CANDIDATS C2 on DI.ID_CAND = C2.ID_CAND
                 JOIN AVOIR_CULTURES AC on DI.ID_PLANT = AC.ID_PLANT
                 JOIN TYPE_CULTURES TC ON TC.ID_TYPE_CULTURES = AC.ID_TYPE_CULT;
end;
/


