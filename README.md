# RicercaOperativa
Implementazione dell’ algoritmo euristico di Clarke &amp; Wright nelle sue versioni parallela e sequenziale

Realizzare una implementazione dell’ algoritmo euristico di Clarke & Wright nelle sue versioni
parallela e sequenziale, per problemi di CVRP(simmetrici) con Linehaul e Backhaul.
Vincoli aggiuntivi.
1)Per ogni rotta : Prima si fanno le consegne(Linehaul), e poi si fanno i ritiri(Backhaul).
2)Non possono esserci rotte con soli Backhaul.
3)Il numero dei mezzi (k) richiesti nell'istanza deve essere rispettato. Modificate l'algoritmo C-W
per risolvere questo problema
2) Migliorare la soluzione con il local search di tipo Best Improvement. Usando mosse di tipo
relocate e exchange
Scrivere una mappa strutturale per risolvere il dato problema (CVRPB). (Vi consiglio di farci vedere prima la mappa e dopo avuto il consenso da parte nostra, mettervi ad implementare il codice)
Iterare per 10 volte i due Algoritmi a due fasi per ogni istanza e scegliere solo la migliore.
Algoritmo1:
1) creare delle rotte iniziali usando il C&W (parallelo)
2) fase di miglioramento best Relocate
3) fase di miglioramento best Exchange
4) ripetere il passo 1 e 2 e 3 per 10 volte e scegliere solo la soluzione migliore
Algoritmo 2:
1) creare delle rotte iniziali usando il C&W(Sequenziale)
2) fase di miglioramento best Exchange
3) fase di miglioramento best Relocate
4) ripetere il passo 1 e 2 e 3 per 10 volte e scegliere solo la soluzione migliore
per poter iterare gli algoritimi, la fase 1) non deve produrre sempre le stesse rotte iniziali
Vincoli da rispettare :
il mezzo non può superare la capacità di carico.
Il numero di mezzi richiesti nell’istanza deve essere rispettato.
Non ci possono essere viaggi con soli backhaul
Ogni rotta deve partire dal deposito ,e servire prima i Linehaul e poi i Backhaul
Ci possono essere rotte con soli Linehaul
verrà fornita una cartella contenente varie istanze di benchmark con le relative migliori soluzioni.
Al fine di confrontare il proprio lavoro con i risultati in letteratura, è richiesta una breve
presentazione dei risultati ottenuti. Al termine dell’ elaborazione, l’ algoritmo deve produrre un file
.txt contenente l’ elenco delle route calcolate, e per ogni route il relativo costo. Deve essere inoltre
salvato sul file il valore della funzione obiettivo, ottenuta dalla sommatoria dei costi delle singole
route, e inoltre richiesto di riportare nel file un parametro che indichi il tempo di elaborazione.
Tale implementazione dovrà essere realizzata in C++, utilizzando QT Creator, e realizzando una
classe principale chiamata Euristca2Fasi, da sviluppare e documentare(o JAVA).
Aiuto:
Mossa Relocate : spostare un nodo da una route in un altra posizione : esempio
R1: 0 2 3 5 0
R2: 0 1 4 6 0
Dopo Mossa Relocate sposta il nodo 2 dopo il nodo 4
R1: 0 3 5 0
R2: 0 1 4 2 6 0
Mossa Exchange : scambia un nodo con un altro nodo : esempio
R1: 0 2 3 5 0
R2: 0 1 4 6 0
Dopo Mossa Exchange scambia il nodo 2 co il nodo 4
R1: 0 4 3 5 0
R2: 0 1 2 6 0
Cosa vuol dire Best improvement?
Valutare tutte le mosse di tipo Relocate(per esempio) ed applicare la mossa Migliore(Best) e
continuare fino a quando non esiste più, una mossa migliorativa.
Esempio: Best del vicinato exchange.
R1: 0 4 3 5 0
R2: 0 1 2 6 0
Valutare tutte le mosse Possibili di tipo Exchange :
scambio il nodo:
4 con → 3 = c1, 4 con → 5=c2, 4 con → 1=c3, 4 con → 2=c4, 4 con → 6=c5
applicare la mossa con il costo(c..) migliore e continuare...
stessa cosa vale per la mossa di tipo relocate.
