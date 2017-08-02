Client
======
Client du serveur Eldaria.
Initialement développé pour Keyrisium, la base a été reprise pour Eldaria car le contrat a été rompu avec ces derniers.

Beaucoup de modifications apportées ne sont pas versionnées car je n'ai pas utilisé Git dès le début du projet.

Il s'agit des sources produitent pas MCP réorganisées sous forme de projet Gradle, facilitant ainsi l'intégration continue.

Modifications
-------------
- Beaucoup d'ajouts de blocs/items basique...
- Mise en place d'un "Framework" nommé `McpHandler`, s'inspirant de LiteLoader, pour faciliter l'intégration de mods
 Forge ou autres
	* CraftGuide
	* ArmorHUD
	* EffectHUD
	* BetterSprint
- Mise en place d'un "Framework" de packet customs `CustomPacketHandler`, plus flexible que de créer de réels nouveaux
 packets, il communique par le PluginChannel `EldariaClient`.
- Player intégré de webradio
- Système de macro-key intégré dans la page de contrôls
- F3 simplifié
- Quelques aliments avec effets de potions
- Coffres étentus (avec les minerais moddés)
- Set de nouveaux minerais/armures/outils (classique)
- Système d'orbe de réparation (des armures)
- Ajout de nouveaux explosifs (dynamite, c4)
- Ajout d'une chicha (sorte d'alambic instantané)
- ...

---
![Logo Eldaria v2](https://eldaria.fr/assets/images/logo.png)

Eldaria
=======
Eldaria est un serveur PvP Factions moddé qui a connus deux versions durant la première moitié de 2017.
[Site eldaria.fr](https://eldaria.fr)

Historique
----------
- J'ai rejoins le projet courant janvier 2017 après avoir été contacté par SeR0x42, une connaissance car nous avions un ami en commun.
- Le serveur ouvre sa première version le 1er avril 2017
- La v1 se termine prématurément à cause d'un disfonctionnement interne "du haut staff"
- Le serveur réouvre ses portes pour lancer sa 2e version le 8 juillet 2017
- Fin juillet 2017, le serveur a finalement subit un "flop", le projet est donc abandonné.

Équipe
------
| Pseudo          | Fonction Principale   | Autre activités                                  |
| :-------------: | :-------------------: | :----------------------------------------------- |
| **EnderRaider** | *Administrateur*      | Supervision du site Mineweb (v1), Build...       |
| **iWarez**      | *Administrateur*      | Configuration de plugins tiers, Build...         |
| **SeR0x42**     | *Administrateur* (v1) | Communication, Build...                          |
| **Dabsunter**   | *Administrateur*      | Lead dev client/serveur                          |
| **Uneo7**       | *SysAdmin*            | Ainsi que *WebMaster* durant la v2               |
| **LordApo**     | *Web Host* (v1)       | Avait commencé à travailler sur le site de la v2 |

Il s'agit ici des principales personalités ayant contribué à la réalisation du projet, bien sûr une fois le serveur ouvert, toute une équipe de modération était également présente, ainsi que quelques développeurs de plugins et Community Managers.
