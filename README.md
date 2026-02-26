# SAE JAVA - Jeu d'échec

[cite_start]Il s'agit d'un jeu d'échecs simplifié et fonctionnel développé en Java, fonctionnant directement dans la console. Ce projet a été réalisé dans le cadre d'une SAE (Situation d'Apprentissage et d'Évaluation) universitaire pour mettre en pratique la programmation orientée objet[cite: 1, 77]. 

## Fonctionnalités

* **Tour par tour** : Le jeu permet aux joueurs blanc et noir de jouer à tour de rôle.
* **Déplacements spécifiques** : Le projet met en place une structure d'objet où chaque type de pièce possède sa propre méthode de déplacement.
* **Capture de pièces** : La capture d'une pièce adverse retire correctement la pièce du plateau et de la liste du joueur.
* **Affichage console** : L'échiquier s'affiche de manière claire sous forme de tableau, utilisant des majuscules pour les pièces blanches et des minuscules pour les pièces noires.
* **Historique des coups** : Les coups joués sont affichés avec une notation simple (ex: c2 c4, e5xf6).
* **Horloge de jeu** : Le temps cumulé passé par chaque joueur est mesuré en millisecondes et affiché au format minutes:secondes.

## Limites connues

* **Règles avancées manquantes** : Le roque et la prise en passant n'ont pas été implémentés.
* **Validation des déplacements** : La vérification d'un coup non autorisé, comme le fait de traverser une autre pièce, fonctionne partiellement.
* **Fin de partie manuelle** : Il est nécessaire d'utiliser la commande `Ctrl+C` pour forcer la fin de la partie.
* **Interface** : L'interface textuelle peut s'avérer difficile à comprendre pour un débutant.

## Installation et Exécution

Pour jouer à ce jeu sur votre machine locale, suivez ces étapes :

1. Assurez-vous d'avoir installé un JDK (Java Development Kit) depuis le site officiel d'Oracle.
2. Ouvrez votre terminal (ou invite de commandes) dans le dossier contenant les fichiers sources.
3. Compilez tous les fichiers Java avec la commande : `javac *.java`.
4. Lancez le jeu avec la commande : `java partie`.
5. Entrez les noms des deux joueurs pour commencer la partie.

## Auteurs

Ce projet a été réalisé par:
* BOUSARGHINI Mohamed 
* El MAHFOUDI Ali 
