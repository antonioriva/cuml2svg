# Nome Progetto: cUml2svg #
## Data Lezione: 02/04/2007 ##

## Attività svolta ##

### Compilatore ###
  * aggiunta la possibilità di gestire un margine nel gruppo
  * eliminati alcuni elementi considerati deprecati

### Generazione Svg ###
  * corretti dei bug nella gestione dei layout
  * aggiunta la gestione dei margini per i gruppi
  * implementazione del primo algoritmo automatico per la definizione dei percorsi delle linee delle relazione

### Interfaccia grafica ###
  * risolto il problema di gestione delle parole che iniziano con caratteri speciali
  * corretti alcuni problemi

## Problemi emersi e Soluzioni adottate (o ipotizzate) ##

### Compilatore ###
é sorto un problema su come gestire in un contesto dello scanner un riconoscimento di n token dello stesso tipo e capire quando è il momento di ritornare allo stato di default;

si è risolto cambiando stato ad ogni passaggio numerando i nuovi stati con un suffiso "_n" solo dopo lo stato con suffisso n desiderato si ritorna allo stato di default
### Generazione Svg ###
nulla da segnalare_

### Interfaccia grafica ###
la risoluzione del problema con le parole che iniziano con caratteri speciali è stata trovata modificando la classe WordDetector;

## Stato del progetto in relazione al Gantt presentato ##
[Gantt Diagram](http://www.antonioriva.net/download/files/gantt.html)

### Compilatore ###
il compilatore può considerarsi per lo più finito e collegato con la generazione dell'interfaccia grafica; man mano vengono effettuate piccole modifiche di usabilità alla grammatica e alcune correzzioni di bug che mano a mano vengono scoperti durante il testing delle applicazioni.

### Generazione Svg ###
pienamente nei tempi

### Interfaccia grafica ###
lo sviluppo dell'interfaccia grafica risulta in ritardo rispetto ai tempi previsti
## Note e Commenti ##

Nulla da segnalare