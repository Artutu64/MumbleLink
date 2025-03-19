# Installation

  1. Installer le backend (vous pouvez le trouver sur le lien suivant: https://github.com/Artutu64/mumble_backend)
  2. Ajouter le plugin dans votre dossier plugins
  3. Lancer le serveur puis configurer le fichier config.yml afin de mettre les informations du backend
  4. Relancer le serveur

# Modules supportés

- Werewolf-UHC (``Ph1Lou``)

# Documentation

Vous pouvez modifier le plugin dynamiquement grâce à votre plugin UHC avec les méthodes suivantes:

## Changement de state de la partie
Le state représente ou en est votre partie (par défaut: WAITING, TELEPORTING, PLAYING, ENDING). Vous pouvez modifier dynamiquement cela en ajoutant votre propre state finder:

```java
MumbleLink.addStateFinder(StateFinder stateFinder);
```

Vous pouvez aussi choisir de modifier manuellement le state (par exemple depuis une méthode changeState) grâce à la fonction suivante:
```java
MumbleLink.setGameState(GameState gameState);
```

## Check des joueurs en jeu
Le plugin a une fonction par défaut pour détecter les joueurs en jeu (tous ceux n'étant pas en ``GameMode.SPECTATOR``). Vous pouvez ajouter votre propre méthode grâce à :
```java
MumbleLink.setInGameChecker(InGameChecker inGameChecker);
```

## Check de permission
Vous pouvez modifier la detection des joueurs ayant le droit d'administrer mumble (le menu /mumble config) par défaut ce sont uniquement les joueurs op.

```java
MumbleLink.setPermChecker(PermChecker permCheck);
```
