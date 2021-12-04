# Makefile pour compiler et lancer les projets MapReduce de Eclipse

# fichiers à traiter et dossier de sortie
ENTREES = /user/share/arbres.csv
SORTIE = /user/resultats


# exécution du projet
run:    projet.jar
	@if test $$(hostname) != ubuntu ; then echo "Vous n'êtes pas sur la bonne machine, \033[1mssh hadoop\033[0m oublié ?" ; exit 1 ; fi
	hdfs dfs -rm -r -f $(SORTIE)
	hadoop jar $< $(ENTREES) $(SORTIE) | tee resultats.txt
	hdfs dfs -cat $(SORTIE)/part-r-00000 | tee -a resultats.txt | more


# affichage des résultats
results:
	@if test $$(hostname) != master ; then echo "Vous n'êtes pas sur la bonne machine, \033[1mssh hadoop\033[0m oublié ?" ; exit 1 ; fi
	hdfs dfs -cat $(SORTIE)/part-r-00000 | more


# compilation et création du .jar
projet.jar:     $(shell find src -name *Driver.java) $(shell find src -name *.java)
	@if test $$(hostname) != ubuntu ; then echo "Vous n'êtes pas sur la bonne machine, \033[1mssh hadoop\033[0m oublié ?" ; exit 1 ; fi
	@mkdir -p bin
	javac -cp $(subst $(eval) ,:,$(wildcard ~/lib/hadoop/hadoop/*.jar)) $^ -d bin
	jar cfe $@ $(subst /,.,$(basename $(<:src/%=%))) -C bin $(dir $(<:src/%=%))


# nettoyage du projet
clean:
	rm -fr projet.jar bin


# renommage du projet ModeleMapReduce
rename:
	@if test ! -f src/ModeleMapReduceDriver.java ; then echo "Ce n'est pas une copie directe du projet ModeleMapReduce, impossible de renommer" ; exit 1 ; fi
	@if test "$${PWD##*/}" != $$(echo $${PWD##*/} | tr ' ' '_') ; then echo "Le nom du dossier projet est incorrect et ne pourra pas être renommé : $${PWD##*/}" ; exit 1 ; fi
	sed -i "s/ModeleMapReduce/$${PWD##*/}/g" $$(find src -name *.java) .project
	for f in $$(find src -name *.java) ; do mv "$$f" $$(echo "$$f" | sed s/ModeleMapReduce/$${PWD##*/}/) ; done
