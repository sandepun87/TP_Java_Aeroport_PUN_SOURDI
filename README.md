TP1 : Lecture de fichier CSV et création des classes Aeroport et World
Au cours de ce premier TP, nous avons dû créer les classes de base du projet d'aviation et lire un fichier CSV contenant la liste des aéroports mondiaux. Bien que l'objectif était relativement simple, nous avons rencontré plusieurs obstacles qui nous ont obligé à debugger et corriger notre approche. Voici les principaux problèmes que nous avons affrontés.
Problème 1 : Erreur de casse du nom du dossier
Nous avons créé le dossier des données avec une majuscule Data/ alors que le chemin utilisé dans le code était en minuscules data/. Sous Linux, la casse est sensible et ces deux noms sont différents. Le programme ne trouvait pas le fichier CSV à cause de cette petite erreur.
Problème 2 : Mauvais chemin d'accès au fichier
Nous avions écrit le chemin avec ./data/airport-codes_no_comma.csv mais IntelliJ exécute le programme depuis la racine du projet, donc le ./ n'était pas nécessaire et causait une erreur. Le bon chemin était simplement data/airport-codes_no_comma.csv.
Problème 3 : Incompréhension de la structure du fichier CSV
Au début, nous ne savions pas exactement où se trouvaient les coordonnées dans le fichier. Nous avons supposé qu'elles étaient aux mauvais indices, ce qui a entraîné l'échec du parsing. Il fallait vraiment vérifier la structure complète du fichier avant de coder.
Problème 4 : Parser CSV trop basique
Nous avons utilisé un simple split(",") pour diviser les lignes, mais cela ne fonctionne pas quand les données elles-mêmes contiennent des virgules. Les coordonnées "147.22, -9.4" contiennent une virgule, donc le split divisait les données incorrectement.
Problème 5 : Mauvaise gestion des guillemets dans le CSV
Les guillemets dans un fichier CSV ont une signification particulière : ils délimitent les champs contenant des caractères spéciaux. Enlever simplement tous les guillemets ne suffisait pas. Il fallait un parser plus intelligent qui respecte les règles du format CSV.
Problème 6 : Manque de messages de debugging
Nous n'avons pas affiché assez d'informations avec System.out.println() au départ. Cela a rendu très difficile de comprendre où le problème se situait. Un bon debugging aurait permis de localiser le souci plus rapidement.
Problème 7 : Oubli de recompilation
Après avoir modifié le code source, nous n'avons pas toujours recompilé le projet avant de relancer. IntelliJ conserve les anciens fichiers compilés, donc les modifications n'étaient pas appliquées.
Problème 8 : Vérification du fichier dans le mauvais outil
Nous avons d'abord regardé le CSV dans un tableur qui a réarrangé l'affichage des colonnes. Il aurait fallu l'ouvrir directement dans un éditeur de texte simple pour voir la structure réelle du format CSV.

Résultat : 
=== Création de World ===
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

=== Test findByCode ===
Recherche par code CDG:
Aeroport{code='CDG', nom='Charles de Gaulle International Airport', latitude=49.012798, longitude=2.55}

=== Distances ===
Distance à ORY: 0.14866262461027743
Distance à CDG: 0.21254348076238955
Disconnected from the target VM, address: '127.0.0.1:42487', transport: 'socket'

Process finished with exit code 0
