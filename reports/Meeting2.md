# Minuta Meeting 2 - Definizione MVC e dominio di gioco

## Obiettivo del meeting

Definire il perimetro funzionale del progetto, chiarire la struttura MVC, fissare le interazioni principali tra gli elementi di gioco e segnare le scelte ancora aperte prima dell'implementazione.

## 1. Elementi del gioco

### 1.1 Stato globale del gioco

Lo stato globale del gioco contiene le informazioni necessarie a gestire la partita in corso:

- livello corrente
- stato del player
- stato dei nemici
- oggetti ancora presenti nella mappa
- bonus attivi
- vite residue
- stato di fine partita
- eventuale stato di pausa
- conteggio degli oggetti da raccogliere nel livello

Decisione: le vite non sono un dato del player in modo isolato, ma fanno parte dello stato di gioco complessivo, insieme alla condizione di vittoria o sconfitta.

### 1.2 Mappa

La mappa rappresenta il labirinto del livello ed e` caricata da file testuale ASCII.
Contiene la struttura del livello, le celle percorribili, i muri, gli oggetti, i bonus, i teletrasporti e gli eventuali spawn point.

### 1.3 Player

Il player e` l'entita` controllata dall'utente.
Gestisce il movimento, la raccolta degli oggetti, l'interazione con bonus e teletrasporti e il contatto con i nemici.

### 1.4 Nemici

I nemici sono controllati dall'AI e si muovono secondo strategie differenti.
Ogni nemico ha una posizione, una direzione, una velocita` e una strategia di comportamento.

### 1.5 Oggetti

Gli oggetti sono cio` che il player deve raccogliere per completare il livello.
Quando vengono raccolti, devono essere rimossi dalla mappa e non devono contare piu` come presenti.

### 1.6 Teletrasporti

I teletrasporti collegano due punti della mappa e permettono lo spostamento immediato da una cella all'altra.

### 1.7 Vite e fine partita

Le vite fanno parte dello stato globale del gioco.
La fine partita viene gestita dallo stato di gioco, che determina se la partita e` terminata in vittoria o in sconfitta.

## 2. Interazioni tra gli elementi

### 2.1 Movimento

Il movimento e` stato discusso come punto ancora da consolidare.
Decisione attuale: per ora si pensa a un movimento continuo, non discreto.

### 2.2 Collisioni

Sono state considerate le interazioni principali:

- player contro muro: il movimento viene bloccato
- player contro oggetto: l'oggetto viene raccolto
- player contro bonus: il bonus viene attivato
- player contro nemico: dipende dallo stato del player
- nemico contro muro: il nemico cambia comportamento o direzione
- nemico contro teletrasporto: segue le regole della mappa, se previsto

### 2.3 Player invulnerabile

Decisione presa: se il player e` invulnerabile e passa sopra al nemico, il nemico non lo danneggia.

### 2.4 Bonus

Decisione presa: il bonus e` associato allo stato del gioco, non al player in modo isolato.
Questo permette di gestire in modo centralizzato durata, attivazione e scadenza degli effetti.

### 2.5 Pausa e salvataggio

Decisione presa: la pausa verra` gestita insieme al salvataggio, come parte dello stesso flusso di gestione della partita.

## 3. MVC

### 3.1 Model

Il Model contiene lo stato e tutta la logica del gioco.

Nel Model rientrano:

- mappa
- player
- nemici
- oggetti
- teletrasporti
- vite
- bonus
- regole di collisione
- regole di fine partita
- validazione della mappa
- gestione dello stato globale
- gestione degli effetti temporanei

Il Model non si occupa di input e rendering.

### 3.2 View

La View mostra lo stato corrente del gioco.
Si occupa della rappresentazione grafica di mappa, player, nemici, oggetti, bonus, vite e schermate di fine partita.

### 3.3 Controller

Il Controller riceve gli input, li traduce in azioni di gioco e coordina l'aggiornamento del Model e della View.

## 4. Flusso di gioco

Questo e` stato considerato un punto centrale del meeting.

Flusso previsto:

1. il Controller avvia il livello
2. il Model carica e valida la mappa
3. il Model inizializza player, nemici, oggetti, teletrasporti e stato globale
4. la View mostra lo stato iniziale
5. l'utente fornisce un input
6. il Controller interpreta l'input e lo inoltra al Model
7. il Model aggiorna il mondo di gioco:
   - muove il player
   - aggiorna i nemici
   - gestisce le collisioni
   - applica bonus ed effetti temporanei
   - verifica se il livello e` stato completato o perso
8. la View ridisegna la scena aggiornata
9. il ciclo continua fino a fine partita

## 5. Caricamento e validazione

La mappa viene letta da file ASCII.
La validazione deve controllare almeno:

- presenza del player
- correttezza della struttura del labirinto
- coerenza dei teletrasporti
- presenza dei muri e dei corridoi
- raggiungibilita` degli obiettivi, se richiesta dalla logica del livello

## 6. Proposta simboli ASCII

Proposta di legenda per la rappresentazione testuale della mappa:

| Simbolo | Significato |
|---|---|
| `#` | muro |
| ` ` | corridoio / cella vuota |
| `.` | oggetto raccoglibile |
| `P` | player |
| `E` | nemico |
| `B` | bonus |
| `T` | teletrasporto |
| `S` | spawn point |

Nota: la legenda e` una proposta di lavoro e puo` essere raffinata in base alle esigenze di implementazione.

## 7. Appunti tecnici emersi nel meeting

Decisioni prese o indirizzi gia` definiti:

- il bonus e` gestito nello stato del gioco
- gli oggetti raccolti vengono cancellati dalla mappa
- l'interazione che distingue il tipo di oggetto puo` essere gestita nel player
- si puo` introdurre una superclasse `MazeObject`, da cui derivano `Tile` e `Collectible`
- il conteggio degli oggetti da raccogliere viene mantenuto nel livello / stato di gioco
- il movimento, per ora, viene considerato continuo e non discreto

Punti ancora da risolvere:

- chi possiede lo spawn point: player o mappa
- il teletrasporto deve essere valido anche se l'uscita e` chiusa
- la struttura definitiva delle superclassi tra entita` mobili, entita` immobili e oggetti
- il dettaglio finale della gestione dei teletrasporti

## 8. Decisioni prese

- Il player, se invulnerabile, puo` passare sopra al nemico senza subire danni.
- Il movimento viene considerato continuo, almeno per la versione corrente di progettazione.
- La pausa viene trattata insieme al salvataggio.
- Il punteggio non entra nel core del progetto ed e` considerato opzionale.
- Le vite fanno parte dello stato globale di gioco.
- Lo spawn point e` un `Tile` della mappa, non solo un'informazione associata a player o nemici.
- Player e nemici vengono generalizzati in una superclasse o interfaccia comune, ad esempio `MovingEntity`.
- I `Collectible` devono avere una `Position`, cosi` da essere trattati come elementi collocati sulla mappa.
- `Tile` e `Collectible` vengono generalizzati in un tipo comune, ad esempio `MazeTile` o equivalente.
- Lo score viene calcolato usando tempo e bonus raccolti, quindi queste informazioni devono essere memorizzate nello stato di gioco.
- Gli elementi opzionali devono essere evidenziati con un colore diverso o con un simbolo dedicato.
- I teletrasporti devono disabilitarsi per un certo tempo dopo l'uso.

## 9. Punti da risolvere

- definizione definitiva dello spawn point
- regole definitive sui teletrasporti
- struttura finale delle classi del dominio
- eventuale revisione della legenda ASCII

## 10. Sintesi

La seconda minuta conferma che il progetto va costruito intorno a uno stato di gioco centrale, con mappa, player, nemici, oggetti, teletrasporti, vite e fine partita coordinati dal Model.
Il flusso di gioco e` stato considerato fondamentale, mentre il punteggio e` stato escluso dal core e rimane una funzionalita` opzionale.
