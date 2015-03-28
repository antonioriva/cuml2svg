# Nome Progetto: cUml2svg #
## Data Lezione: 21/05/2007 ##

## Attività svolta ##

### Compilatore ###
  * aggiunta controlli semantici
    1. non è possibile re-dichiarare una classe
    1. non è possibile re-dichiarare una metodo (stessa segnatura) in una classe
    1. i nomi degli attributi di un metodo non si possono ripetere nella stessa dichiarazione
    1. non è possibile re-dichiarare una relazione in una classe
    1. non è possibile re-dichiarare un attributo in una classe
    1. non è possibile inserire la stessa classe in un diagramma
  * progressiva integrazione degli elementi con la generazione dell'svg man mano gli oggetti diventano disponibili
  * aggiunta la modalità di solo check nella classe di compilazione: modalità utilizzata nel plugin-Eclipse per ottenere tutti gli errori da visualizzare
  * ottimizzati i medodi di errore per visulizzare più informazioni e più stutturate

### Generazione Svg ###
  * Nuova gestione del bounding box del diagramma per l'impaginazione
  * Nuove frecce
  * Aggiunta delle etichette di cardinalità
  * Ottimizzazione dell'algoritmo di calcolo delle frecce; ora non si sovrappongono
  * Aggiunta di un flag per visualizzare o meno la GUI per la generazione delle frecce
  * Ottimizzazione generale
  * Aggiunta di una console personalizzata per la compilazione

### Interfaccia grafica ###
  * correzione di alcuni bug
  * compilatore implementato
  * implementato auto-complete
  * l'editor parsa il log del compilatore e visualizza dei marker a lato della linea con l'errore con il messaggio associato (alla Eclipse)
  * text-highlight migliorato
  * incapsulamento del plugin in un file jar

## Problemi emersi e Soluzioni adottate (o ipotizzate) ##

### Compilatore ###
In taluni casi è problematico utilizzare riferimenti a token ormai utilizzati da tempo (è il caso delle relazione che vengono valutate come ultima cosa). Si è risolto con una class RelationRef che oltre a contenere la Relation stessa si fa carico di trasportare le informazioni necessarie fino alla radice.

### Generazione Svg ###
  * Sfasamento nel disegno delle relazioni rispetto al grafico

### Interfaccia grafica ###
  * ci sono alcuni problemi nella gestione delle risorse (template di velocity per la generazione dell'svg) all'interno del jar: si opterà per una soluzione esterna


## Stato del progetto in relazione al Gantt presentato ##
[Gantt Diagram](http://www.antonioriva.net/download/files/gantt.html)

### Compilatore ###
L'integrazione dei componenti è completata, tutti i controlli semantici previsti sono stati effettuati; è ora prevista un'attività di bugfix.

### Generazione Svg ###
  * Siamo in leggero ritardo rispetto alla scaletta iniziale ma contiamo di finire nei tempi stabiliti l'intero progetto

### Interfaccia grafica ###
  * manca solo la generazione dell'svg attraverso l'interfaccia di compilazione già esistente

## Note e Commenti ##

Nulla da segnalare