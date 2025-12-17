TP1 : Lecture de fichier CSV et création des classes Aeroport et World
Au cours de ce premier TP, nous avons dû créer les classes de base du projet d'aviation et lire un fichier CSV contenant la liste des aéroports mondiaux. Bien que l'objectif était relativement simple, nous avons rencontré plusieurs obstacles qui nous ont obligé à debugger et corriger notre approche. Voici les principaux problèmes que nous avons affrontés.

*Problème 1 : Erreur de casse du nom du dossier
Nous avons créé le dossier des données avec une majuscule Data/ alors que le chemin utilisé dans le code était en minuscules data/. Sous Linux, la casse est sensible et ces deux noms sont différents. Le programme ne trouvait pas le fichier CSV à cause de cette petite erreur.

*Problème 2 : Mauvais chemin d'accès au fichier
Nous avions écrit le chemin avec ./data/airport-codes_no_comma.csv mais IntelliJ exécute le programme depuis la racine du projet, donc le ./ n'était pas nécessaire et causait une erreur. Le bon chemin était simplement data/airport-codes_no_comma.csv.

*Problème 3 : Incompréhension de la structure du fichier CSV
Au début, nous ne savions pas exactement où se trouvaient les coordonnées dans le fichier. Nous avons supposé qu'elles étaient aux mauvais indices, ce qui a entraîné l'échec du parsing. Il fallait vraiment vérifier la structure complète du fichier avant de coder.

*Problème 4 : Parser CSV trop basique
Nous avons utilisé un simple split(",") pour diviser les lignes, mais cela ne fonctionne pas quand les données elles-mêmes contiennent des virgules. Les coordonnées "147.22, -9.4" contiennent une virgule, donc le split divisait les données incorrectement.

*Problème 5 : Mauvaise gestion des guillemets dans le CSV
Les guillemets dans un fichier CSV ont une signification particulière : ils délimitent les champs contenant des caractères spéciaux. Enlever simplement tous les guillemets ne suffisait pas. Il fallait un parser plus intelligent qui respecte les règles du format CSV.

*Problème 6 : Manque de messages de debugging
Nous n'avons pas affiché assez d'informations avec System.out.println() au départ. Cela a rendu très difficile de comprendre où le problème se situait. Un bon debugging aurait permis de localiser le souci plus rapidement.

*Problème 7 : Oubli de recompilation
Après avoir modifié le code source, nous n'avons pas toujours recompilé le projet avant de relancer. IntelliJ conserve les anciens fichiers compilés, donc les modifications n'étaient pas appliquées.

*Problème 8 : Vérification du fichier dans le mauvais outil
Nous avons d'abord regardé le CSV dans un tableur qui a réarrangé l'affichage des colonnes. Il aurait fallu l'ouvrir directement dans un éditeur de texte simple pour voir la structure réelle du format CSV.

Résultat : 
      Création de World
Header trouvé: ident,type,name,elevation_ft,continent,iso_country,iso_region,municipality,gps_code,iata_code,local_code,coordinates,,

Ligne 1: 14 champs
  [1] type: heliport
  [2] name: Total Rf Heliport
  [9] iata_code: 
  [11] coordinates: -74.93360137939453, 40.07080078125

Ligne 2: 14 champs
  [1] type: small_airport
  [2] name: Aero B Ranch Airport
  [9] iata_code: 
  [11] coordinates: -101.473911, 38.704022

Ligne 3: 14 champs
  [1] type: small_airport
  [2] name: Lowell Field
  [9] iata_code: 
  [11] coordinates: -151.695999146, 59.94919968

 RÉSUMÉ
Total lignes lues: 57420
Aéroports chargés: 606
-World créé

-Test findNearestAirport 
Aéroport le plus proche de Paris (2.316, 48.866):
Aeroport{code='ORY', nom='Paris-Orly Airport', latitude=48.7233333, longitude=2.3794444}

   Test findByCode 
Recherche par code CDG:
Aeroport{code='CDG', nom='Charles de Gaulle International Airport', latitude=49.012798, longitude=2.55}

   Distances 
Distance à ORY: 0.14866262461027743
Distance à CDG: 0.21254348076238955
Disconnected from the target VM, address: '127.0.0.1:42487', transport: 'socket'

Process finished with exit code 0

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
TP2 - Interface JavaFX et Affichage 3D

OBJECTIFS:
Durant la TP2, nous avons appliqué une interface JavaFX affichant une Terre 3D interactive. 
La Terre tourne sur elle-même, dispose d'une texture réaliste, et permet à l'utilisateur d'interagir 
via la souris pour zoomer et cliquer sur des aéroports. Les sphères positionnées correctement en 3D 
marquent les aéroports, avec une sphère rouge indiquant l'aéroport sélectionné au clic droit.


DIFFICULTÉS RENCONTRÉES ET SOLUTIONS


PROBLEME 1 : JavaFX non reconnu
CAUSE : Librairie JavaFX pas configurée
SOLUTION : File → Project Structure → Libraries + ajouter SDK JavaFX + VM options

PROBLEME 2 : Classe "Interface" génère une erreur
CAUSE : "Interface" est un mot-clé réservé en Java
SOLUTION : Renommer en "AviationApp"

PROBLEME 3 : Sphères invisibles sur la Terre
CAUSE : Formules de conversion 3D incorrectes
SOLUTION : Implémenter les formules exactes du sujet
          X = R × cos(Θ) × sin(Φ)
          Y = -R × sin(Θ)
          Z = -R × cos(Θ) × sin(Φ)

PROBLEME 4 : Sphères mal positionnées (Z incorrect)
CAUSE : Utilisation de cos(Φ) au lieu de sin(Φ)
SOLUTION : Correction : Z = -R × cos(Θ) × sin(Φ)

PROBLEME 5 : Rayon des sphères trop grand
CAUSE : Utilisation de rayon 5 ou 8 pixels
SOLUTION : Changer à 2 pixels (conforme au sujet)

PROBLEME 6 : 0 aéroports chargés depuis le CSV
CAUSE : Parser CSV ne gérait pas les guillemets
SOLUTION : Ajouter replaceAll("\"","") et parser robuste

PROBLEME 7 : Sphères rouges non visibles au clic droit
CAUSE : Clic captait le Pane au lieu de la Sphere
SOLUTION : Utiliser directement Earth sans intermédiaire

PROBLEME 8 : Sphères mal positionnées au clic
CAUSE : Calcul des coordonnées approximatif
SOLUTION : Intégrer l'angle de rotation actuel de la Terre


FORMULES UTILISÉES

Conversion Mercator (Texture 2D → Lat/Lon) :
Θ = 180 × (0.5 - Y)
Φ = 360 × (X - 0.5)

Conversion GPS -> 3D :
X = R × cos(Θ) × sin(Φ)
Y = -R × sin(Θ)
Z = -R × cos(Θ) × sin(Φ)


ÉTAT FINAL

 Terre 3D qui tourne - OK
Zoom à la souris fonctionnel - OK
 Sphères rouges s'affichent au clic droit - OK
 Aéroport le plus proche affiché - OK
 Code respecte exactement le sujet - OK

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
TP3 - Intégration API aviationstack.com



OBJECTIFS : Durant la TP3, nous avons intégré l'API aviationstack.com pour afficher en temps réel les vols 
en destination d'un aéroport sélectionné. Après un clic droit sur la Terre, une sphère rouge 
marque l'aéroport choisi, et des sphères jaunes apparaissent pour indiquer les aéroports d'origine 
des vols. L'exécution se fait dans un thread séparé pour éviter le blocage de l'interface.


DIFFICULTÉS RENCONTRÉES ET SOLUTIONS:

PROBLEME 1 : javax.json non trouvé
CAUSE : Bibliothèque JSON pas ajoutée au projet
SOLUTION : Télécharger javax.json et javax.json-api depuis l'université
           Ajouter les 2 fichiers .jar dans Project Structure → Libraries

PROBLEME 2 : Erreur 429 (trop de requêtes)
CAUSE : Plan gratuit limité à 100 requêtes/mois
SOLUTION : Utiliser fichier test.txt pour les tests
           Ajouter délai entre requêtes (REQUEST_DELAY_MS = 500ms)

PROBLEME 3 : Erreur 401 (clé API invalide)
CAUSE : Clé API expirée ou incorrecte
SOLUTION : Utiliser la nouvelle clé depuis le dashboard aviationstack.com
           9935378ccc86219961f13c777b47d3116

PROBLEME 4 : Erreur "Not on FX application thread"
CAUSE : Modification UI depuis un thread séparé (API call)
SOLUTION : Utiliser Platform.runLater() pour revenir au thread JavaFX

PROBLEME 5 : Sphères jaunes ne s'affichent pas
CAUSE : JsonFlightFiller ne parsait pas correctement le JSON
SOLUTION : Extraire les champs flight.iata, departure.iata, arrival.iata du JSON
           Utiliser world.findByCode() pour récupérer les Aeroport objects
           Créer des Flight objects et les ajouter à la liste

PROBLEME 6 : L'interface freeze lors de l'appel API
CAUSE : Appel HTTP effectué sur le thread principal
SOLUTION : Lancer recupererVols() dans un nouveau Thread séparé

PROBLEME 7 : Zéro vols retournés par l'API
CAUSE : Les aéroports du JSON ne correspondaient pas à la base World
SOLUTION : Vérifier que les codes IATA existent dans la liste World
           Ajouter des vérifications null et try/catch


CLASSE FLIGHT:

Attributs :
- Aeroport departure (aéroport d'origine)
- Aeroport arrival (aéroport de destination)
- String aircraftType (type d'avion)
- String flightNumber (numéro du vol)

Méthodes :
- getDeparture(), getArrival(), getAircraftType(), getFlightNumber()


CLASSE JSONFLIGHTFILLER


Responsabilité : Parser le JSON reçu de l'API aviationstack.com

Processus :
1. Recevoir le JSON en String
2. Créer un JsonObject et récupérer l'array "data"
3. Pour chaque vol dans "data" :
   - Extraire flight.iata (numéro de vol)
   - Extraire departure.iata (aéroport départ)
   - Extraire arrival.iata (aéroport arrivée)
   - Extraire aircraft.iata (type d'avion)
4. Chercher les Aeroport objects dans World avec findByCode()
5. Créer un Flight object et l'ajouter à la liste
6. Retourner la liste des vols


CLASSE AVIATIONAPP:

Modifications principales :
- Ajouter attribut privé World world
- Ajouter attribut privé String API_KEY (clé aviationstack.com)
- Ajouter attribut privé long lastRequestTime (gestion des délais)

Méthodes :
- recupererVols(Aeroport) : Lance l'appel API dans un thread séparé
  * Construit l'URL avec access_key et arr_iata
  * Effectue HttpURLConnection GET
  * Lit la réponse JSON
  * Crée JsonFlightFiller pour parser
  * Affiche les sphères jaunes

- afficherVolsAvionSurGlobe(JsonFlightFiller) : Affiche les résultats
  * Boucle sur la liste des vols
  * Récupère l'aéroport de départ
  * Appelle earth.displayYellowSphere() avec Platform.runLater()


CLASSE EARTH:

Ajouts par rapport à TP2 :
- Méthode displayYellowSphere(Aeroport a)
  * Crée une sphère jaune (rayon 2)
  * La positionne en 3D sur l'aéroport
  * L'ajoute au groupe Earth


ÉTAT FINAL:
[OK] Classe Flight fonctionnelle
[OK] Classe JsonFlightFiller parse le JSON correctement
[OK] Appel API aviationstack.com fonctionne
[OK] Sphère rouge s'affiche à l'aéroport cliqué
[OK] Sphères jaunes s'affichent pour les vols
[OK] Thread séparé pour éviter le freeze
[OK] Platform.runLater() pour les modifications UI
[OK] Gestion d'erreurs (401, 429, exceptions)
[OK] Code respecte exactement le sujet




