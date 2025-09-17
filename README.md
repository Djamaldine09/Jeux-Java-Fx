https://img.shields.io/badge/Java-17%252B-orange
https://img.shields.io/badge/JavaFX-17-blue
https://img.shields.io/badge/License-MIT-green

Un jeu de stratégie tactique développé en JavaFX où deux joueurs s'affrontent sur un plateau de 5x9 cases. Le jeu implémente des mécaniques de déplacement et de capture avancées.

🎮 Fonctionnalités
Plateau de jeu : Grille de 5 lignes et 9 colonnes

Deux joueurs : Joueur 1 (bleu) et Joueur 2 (rouge)

Jetons : 18 jetons par joueur répartis sur 2 lignes

Déplacements :

Horizontal (gauche/droite)

Vertical (avant/arrière)

Diagonal (toutes directions)

Système de capture : Saut par-dessus un adversaire vers une case vide

Captures multiples : Enchaînement de captures obligatoires

Interface moderne : Design épuré avec effets visuels

Indicateurs : Tour actuel, scores en temps réel

📸 Captures d'écran
https://screenshot.png

🚀 Installation et Exécution
Prérequis
JDK 17 ou supérieur

JavaFX SDK 17

Maven (recommandé)

Méthode 1 : Avec Maven
bash
# Cloner le projet
git clone https://github.com/votre-username/jeu-strategie-javafx.git

# Se déplacer dans le dossier
cd jeu-strategie-javafx

# Compiler et exécuter
mvn clean javafx:run
Méthode 2 : Manuellement
bash
# Compilation
javac --module-path "chemin/vers/javafx-sdk/lib" \
      --add-modules javafx.controls,javafx.fxml \
      -d out/src/main/java/**/*.java

# Exécution
java --module-path "chemin/vers/javafx-sdk/lib" \
     --add-modules javafx.controls,javafx.fxml \
     -cp out com.votrepackage.Main
🎯 Règles du Jeu
Configuration Initiale
Joueur 1 (bleu) : Place ses 18 jetons sur les 2 premières lignes

Joueur 2 (rouge) : Place ses 18 jetons sur les 2 dernières lignes

Déplacements
Déplacement simple : 1 case dans n'importe quelle direction

Capture : 2 cases en sautant par-dessus un adversaire

Conditions de Capture
✅ La case de destination doit être vide

✅ Un adversaire doit se trouver sur la case intermédiaire

❌ Impossible de capturer si la case destination est occupée

Tour de Jeu
Le joueur sélectionne un de ses jetons

Les cases disponibles sont highlightées :

🟢 Vert : Déplacement simple possible

🔴 Rouge : Capture possible

Les captures multiples sont obligatoires

Condition de Victoire
Le joueur qui capture tous les jetons adverses remporte la partie.

🏗️ Architecture du Projet
text
src/
├── main/
│   ├── java/
│   │   ├── Main.java          # Point d'entrée de l'application
│   │   ├── GameController.java # Contrôleur principal
│   │   ├── GameLogic.java     # Logique métier du jeu
│   │   ├── GameBoard.java     # Gestion du plateau de jeu
│   │   ├── Player.java        # Représentation d'un joueur
│   │   └── Token.java         # Représentation d'un jeton
│   └── resources/
│       ├── game-icon.png      # Icône de l'application
│       └── styles/            # Fichiers CSS (optionnel)
└── test/
    └── java/                  # Tests unitaires
🎨 Personnalisation
Modifier l'apparence
Editez la classe GameBoard.java pour changer :

Les couleurs du plateau

La taille des cellules

L'apparence des jetons

Ajouter de nouvelles fonctionnalités
Nouvelles règles : Modifiez GameLogic.java

Nouveaux effets visuels : Ajoutez des méthodes dans GameBoard.java

Sons et animations : Intégrez des MediaPlayer pour les effets sonores

🤝 Contribution
Les contributions sont les bienvenues ! Pour contribuer :

Fork le projet

Créez votre branche feature (git checkout -b feature/AmazingFeature)

Commit vos changements (git commit -m 'Add some AmazingFeature')

Push vers la branche (git push origin feature/AmazingFeature)

Ouvrez une Pull Request

📝 TODO et Améliorations Futures
Ajouter un système de sauvegarde

Implémenter une IA pour jouer contre l'ordinateur

Ajouter des effets sonores

Créer des animations de déplacement

Ajouter un chronomètre de partie

Internationalisation (multi-langues)

Mode réseau pour jouer en ligne

📊 Statistiques
Plateau : 5 × 9 = 45 cases

Jetons : 18 par joueur (36 total)

Déplacements possibles : 8 directions par jeton

Complexité : ~288 mouvements possibles initiaux

🐛 Résolution de Problèmes
Problème d'icône
Si l'icône ne s'affiche pas, vérifiez que le fichier game-icon.png se trouve dans le dossier src/main/resources/.

Erreurs JavaFX
Assurez-vous que JavaFX SDK est correctement configuré dans votre projet.

Problèmes de performance
Le jeu est optimisé pour fonctionner fluidement sur la plupart des machines.

📄 Licence
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.

🙏 Remerciements
Développé avec JavaFX et JDK 17

Icônes par FlatIcon (exemple)

Inspiré par les jeux de dames et de stratégie traditionnels

Note : Ce projet est à but éducatif et démonstratif des capacités de JavaFX pour le développement de jeux.

⭐ N'hésitez pas à donner une étoie au projet si vous l'appréciez !

