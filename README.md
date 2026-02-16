Automate Builder

Automate Builder est un projet Kotlin permettant de créer et manipuler des automates finis à partir de fichiers texte.
Il permet également de visualiser le graphe de l’automate au format PNG en utilisant Graphviz.

---

Fonctionnalités

- Lire un fichier .txt décrivant un automate (états, alphabet, transitions, état initial et états finaux)
- Construire dynamiquement l’automate en mémoire
- Vérifier si un mot appartient au langage de l’automate (validation)
- Détecter si l’automate est connexe
- Générer un graphe de l’automate au format .dot et .png pour visualisation
- Gérer un état "puits" pour les transitions non définies

---

Structure du projet

src/
├─ automate/                # Fonctions utilitaires
│   └─ automateBuilder.kt
├─ automate/model/          # Modèles d'automates et états
│   ├─ Automate.kt
│   ├─ Etat.kt
│   └─ Puit.kt
├─ automate/data/txt/       # Fichiers texte décrivant les automates
├─ automate/data/dot/       # Fichiers dot générés
└─ automate/data/png/       # Images PNG des automates

---

Format du fichier texte

Chaque automate est décrit dans un fichier .txt. Exemple :

init q0
finals q2 q3
alphabet letters digits a b
q0 q1 a b
q1 q2 0
q2 q3 1

- init : état initial
- finals : états finaux
- alphabet : symboles autorisés (letters, digits ou caractères précis)
- Les autres lignes définissent les transitions (état_source état_destination symbole(s))

---

Utilisation

1. Prérequis : Kotlin et Graphviz installés.
   - Pour Graphviz, ajoute le chemin de dot à ton PATH ou modifie le chemin dans Automate.kt.
2. Place ton fichier .txt dans src/automate/data/txt/.
3. Compile et lance le projet :

kotlinc src/automate/*.kt -include-runtime -d AutomateBuilder.jar
java -jar AutomateBuilder.jar

4. Le programme propose un menu pour :
   - Sélectionner un automate
   - Entrer un mot pour validation
   - Générer et ouvrir le graphe de l’automate

---

Exemple d'utilisation

- Menu principal :

--------------- Menu des Automates -------------------------
0. exempleAutomate
Stop. Arrêt de l’application
-> 0

- Validation d’un mot :

Alphabet autorisé : [ a, b, 0, 1, ... ]
Veuillez écrire votre exempleAutomate : a0b
Lecture du caractère 'a' à la position 1 : q0 -> q1
Lecture du caractère '0' à la position 2 : q1 -> q2
OK : mot accepté

- Génération du graphe :

Le fichier PNG est ouvert automatiquement avec l’image de l’automate

> Exemple de graphe :
> (mettre ici ./automate/data/png/exempleAutomate.png si disponible)

---

Remarques

- L’automate doit être connexe pour être accepté
- Les états non définis redirigent automatiquement vers un puits
- Les fichiers générés .dot et .png sont créés dans src/automate/data/dot/ et src/automate/data/png/

---

Licence

Ce projet est open-source et libre d’utilisation
