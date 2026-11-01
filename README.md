# Projet Sokoban : TP 1

## Installation et lancement 

Ce projet nécessite l'utilisation de la lib pddl4j.

Install pddl4j (https://github.com/pellierd/pddl4j) in your local maven repo:
```
mvn install:install-file \
   -Dfile=<path-to-file-pddl4j-4.0.0.jar> \
   -DgroupId=fr.uga \
   -DartifactId=pddl4j \
   -Dversion=4.0.0 \
   -Dpackaging=jar \
   -DgeneratePom=true
 ```  
Work with maven: mvn clean, mvn compile, mvn test, mvn package

### Pour lancer le projet : 

1. Saisir le fichier de test pour choisir le niveau souhaité dans `SokobanMain.java`.

```java
...
gameRunner.setTestCase("test2.json");
...
```

2. Pour chaque niveau, il faut dans le terminal, à la racine du projet, compiler le projet avec la commande suivante :

```sh
mvn compile
```
3. Entrer la commande suivante pour calculer le plan avec l'agent et démarrer le serveur pour la démonstration via interface.
Le process permet d'écrire dans un fichier `src/main/java/sokoban/planification/sokoban-problem.pddl`.

````
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes" \
      sokoban.SokobanMain
````
or (after mvn package)
```
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
      sokoban.SokobanMain
```

## Fichiers modifiés et crées 

Après avoir repris le projet sokoban, nous avons intégré le planificateur PDDL4J pour permettre à l'agent de calculer un plan.
Un nouveau dossier `/planification` a été créé pour les fichiers PDDL.

Nous avons modifiés les fichiers suivants dans le projet :

| Fichier | Description |
|---|---|
| `src/main/java/sokoban/Agent.java` | Génère le fichier problem PDDL correspondant, puis calcule un plan. Les actions du plan sont converties en commandes de déplacement (`U`, `D`, `L`, `R`) et transmises au jeu. |
| `src/main/java/sokoban/domains/sokoban-domain.pddl` | Définit le domaine de planification de Sokoban |
| `src/main/java/sokoban/Referee.java` | Temps maximal accordé au premier tour à 25 secondes. Cela a été changé pour certains tests qui prenaient beaucoup de temps à calculer le plan. |
| `pom.xml` | Ajout de la dépendance Javassist afin de permettre l'utilisation du planificateur car problème lors de la compilation. |

### Fichiers créés

| Fichier | Description |
|---|---|
| `src/main/java/sokoban/domains/sokoban-problem.pddl` | Fichier de problème PDDL généré lors du lancement du niveau actuel. |f. |