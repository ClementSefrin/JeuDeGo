# Jeu de Go

### Application en ligne de commande permettant de jouer une partie de Go.

### Groupe : 206
- Nicolas Estermann
- Clément Galibardy-Séfrin
- Clément Salliot
- Kévin Jiang

## Sprint 1 :

- boardsize : prend en paramètre un nombre. Permet de définir la taille du plateau de jeu
- showboard : ne prend aucun paramètre. Permet d’afficher le tableau
- quit : ne prend aucun paramètre. Permet d’arrêter l’application

## Sprint 2 :

- play : poser une pierre dans le damier, pas sur une pierre -> prendre des pierres ennemies -> interdire le suicide -> sauf si prise -> Ko (facultatif)
- genmvove : gérer moteur de jeu et autre joueur

## Sprint 3 :
- pattern command : transformer chaque commande en classe, pour pouvoir faire des undo
- quand les deux passes, fin de la parties
## Dernier sprint validé : Sprint 1

## Possibilité de rajouté des commandes privé pour débugger (liberties, les libertés du groupe de pierre aux alentours) 
Bonus : 
- 1 point bonus pour le KO 
- 1 point suicide 
- IA aléatoire (joue un coup autorisé), lancer 2 instances soit IA-humain

## remarque sprint 2 :
- Un peu trop d'espace (problème en 19-19)
- Idéalement les noirs doivent commencé (et chacun son tour)
- Fichier de test avec gnugo, ou fourni 
- 

