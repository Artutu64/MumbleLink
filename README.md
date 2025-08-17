<div align="center">
    <br/>
    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Icons_mumble.svg/1200px-Icons_mumble.svg.png" alt="Mumble Logo" width="200"/>
    <h1>Mumble Link v1</h1>
</div>

<p align="center">
	🌐 <a href="https://github.com/Artutu64/MumbleLink">Spigot-MC</a>
	 &#124;
	📓 <a href="https://github.com/Artutu64/MumbleLink">Github</a>
	 &#124;
	🔎 <a href="https://github.com/Artutu64/MumbleLink/issues">Issue</a>
</p>

>💬️ MumbleLink est un plugin a ajouter sur un serveur spigot afin de créer et gérer des serveurs Mumble directement depuis minecraft. Son développement est axé vers l'UHC et cherche donc a répondre à ces besoins mais peut être utilisé dans d'autres projets.

# Images

### Création du serveur
<img src="https://github.com/Artutu64/MumbleLink/raw/refs/heads/main/MenuCreationServer.png">

### Menu des utilisateurs
<img src="https://github.com/Artutu64/MumbleLink/raw/refs/heads/main/MenuWithData.png">

### Commande /mumble
<img src="https://github.com/Artutu64/MumbleLink/raw/refs/heads/main/CmdMumble.png">

### Page web
<img src="https://github.com/Artutu64/MumbleLink/raw/refs/heads/main/WebPage.png">

### Link et pseudo random sur Mumble
<img src="https://github.com/Artutu64/MumbleLink/raw/refs/heads/main/LinkAndMumble.png">

# Installation

*Avant de commencer l'installation, veuillez noter qu'il faut avoir un serveur avec des permissions root pour installer ce plugin.*

1. Installer le [mumble_backend](https://github.com/Artutu64/mumble_backend) sur votre machine (VPS/Serveur dédié).

Et garder de côté les informations suivantes:
<img src="https://raw.githubusercontent.com/Artutu64/MumbleLink/main/Output-installation.png">

2. **Ajouter le plugin**  
   Placez le plugin dans votre dossier `plugins`.


3. **Configurer le backend**  
   Lancez le serveur une première fois, puis éditez le fichier `config.yml` pour y renseigner les informations de votre backend.

- backend : Mettre l'ip du backend
- protocol: (ancienne valeur, laisser http ou https)
- random-names: true/false (pour ajouter de l'anonymat sur le serveur)
- iv: la chaine IV du screenshot
- key: la chaine KEY du screenshot
- state-finder: none/LG_Ph1Lou (none: le détecteur d'état par défaut, LG_Ph1Lou: le détecteur d'état du plugin WereWolf-UHC de Ph1Lou)

4. **Relancer le serveur**  
   Redémarrez le serveur pour appliquer la configuration.


# Modules supportés

- Werewolf-UHC (``Ph1Lou``)
- Les votres (en adaptant votre code à partir de #Documentation)

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
