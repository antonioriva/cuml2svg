# Nome Progetto: cUml2svg #
## Data Lezione: 02/04/2007 ##

## Attività svolta ##

### Compilatore ###
  * Aggiornamento della grammatica alla nuova soluzione model/template
  * Inizio raccolta dati dalle produzioni e primi controlli sulla semantica

### Generazione Svg ###
  * Creata versione base delle classi per la traduzione in SVG
  * Implementato motore di template Velocity

### Interfaccia grafica ###
  * Ricerca di guide per la creazione di Plugins
  * Esecuzione di plugins standard

## Problemi emersi e Soluzioni adottate (o ipotizzate) ##

### Compilatore ###
In questa settimana ho risolto il problema delle espressioni regolari con contesto

```
TOKEN:{
	<IMPORT: "import"> : IMPORT_STATE
}
<IMPORT_STATE>  TOKEN : { <FILE_NAME: (~[";"])* > : DEFAULT  }
```

così facendo quando si trova la parola chiave **import** si entra nello stato **IMPORT\_STATE** e tutto ciò che c'è fino al ";" è considerato **FILE\_NAME**; si ritorna poi allo stato **DEFAULT** che + lo stato standard.

### Interfaccia grafica ###
Per iniziare a creare un editor per Eclipse, che aiuti nella scrittura del codice per Svg2Uml, sono stati provati degli esempi di plugins standard (hello world). il problema che abbiamo incontrato è la mancanza di guide aggiornate alla versione 3.2 di eclipse. Le guide che interessano il nostro progetto, inoltre, risultano frammentate, come anche la guida ufficiale di eclipse 3.2, che riporta informazioni in grossa parte carenti..
Abbiamo ora a disposizione il libro “Professional Eclipse 3”, sul quale ci baseremo per sviluppare il nostro editor

## Stato del progetto in relazione al Gantt presentato ##
[Gantt Diagram](http://www.antonioriva.net/download/files/gantt.html)

### Compilatore ###
Sul fronte compilatore sono in ritardo di 1 o 2 settimane ma ora prevedo un boost nella programmazione per il fatto che sono stati risolti tutti i problemi relativi all'integrazione e non prevedo di dover risolvere altri particolari problemi.

### Generazione Svg ###
Riguardo la parte di traduzione in SVG possiamo dire di essere allineati con i tempi previsti. Siamo un po' in ritardo con la creazione dei modelli in SVG da utilizzare ma solo perchè si è scelto di crearli insieme alle classi. Nella creazione delle classi infatti siamo abbastanza in anticipo con i tempi.

### Interfaccia grafica ###
Riguardo alla parte dell'interfaccia grafica il progetto risulta in ritardo rispetto al tempo stabilito a causa della mancanza di supporto (guide e tutorial) allo sviluppo. Ora abbiamo a disposizione una guida per Eclipse su cui basarci.

## Note e Commenti ##

Nulla da segnalare

