# Prima call – Ruoli, Scrum e Definition of Done

## 1. Persone e Ruoli
| Persona | Ruolo |
|---|---|
| Alex | Product Owner |
| Matilde | Cliente |
| Gaia | Membro del team |

## Obiettivo dell'incontro
Creare il product backlog, definirne gli item, e suddividere quelli più prioritari in task da poter iniziare nel primo sprint.

## 2. Strumenti
La gestione del backlog e dello sprint avviene tramite **GitHub** (GitHub Projects / Issues).

## 3. Regole di Code Review
- Le review vengono assegnate alla persona responsabile della parte di progetto interessata.
- Ogni Pull Request deve avere **almeno un altro reviewer** oltre all'autore.

## 4. Branching Strategy
| Branch | Descrizione |
|---|---|
| master | Branch principale, sempre stabile e rilasciabile. |
| dev | Branch di integrazione del lavoro corrente. |
| epica | Branch principale di una singola funzionalità/epica. |
| branch item | Branch dedicato a un singolo item all'interno di un'epica. |

## 5. Pull Request
- Ogni PR deve essere revisionata da almeno un'altra persona.
- Per essere mergiata su master, una PR deve **passare tutti i test**.
- Ogni PR deve includere i test relativi, quando applicabile.

## 6. Convenzioni sui Commit
- Si utilizzano i **Conventional Commits**, scritti in inglese, con il seguente formato:

  ```
  <tipo>(<ambito>): <descrizione>
  ```
  dove `<tipo>` è il tipo di modifica effettuata (es. `feat`, `fix`, `docs`, ...), `<ambito>` (tra parentesi) indica la parte specifica del progetto interessata, e dopo i due punti segue la descrizione. Esempio:
  ```
  feat(auth): add multi-factor authentication support
  ```

- Ogni commit deve contenere **una singola modifica logica**.
- Per scrivere i commit in modo semplice e coerente si può usare l'estensione VS Code: [Conventional Commits – vivaxy](https://marketplace.visualstudio.com/items?itemName=vivaxy.vscode-conventional-commits)

## 7. Definition of Done (DoD) e CI/CD

### Task
Un task è considerato **fatto** solo se sono soddisfatte tutte le seguenti condizioni:
- [ ] L'item è completato e rispetta tutti i criteri prefissati.
- [ ] Passa i test automatici.
- [ ] Passa la CI: il merge è bloccato finché i test non sono passati.
- [ ] La documentazione è stata aggiornata con le modifiche introdotte dall'item.

**CD:**
- La release è automatica via CD.
- Il pacchetto di produzione viene generato automaticamente al merge su master.

### Epica
Un'epica è considerata **fatta** quando tutti i task che la compongono sono a loro volta "done" e il relativo lavoro è stato correttamente mergiato.

## 8. Stati del Backlog
| Stato | Significato |
|---|---|
| backlog | Tutto ciò che non è nello sprint corrente. |
| ready | Nello sprint corrente, ma ancora da iniziare. |
| in progress | Attività in lavorazione. |
| in review | Attività in fase di revisione. |
| done | Attività completata. |

## 9. Sprint pianificati
Sono stati pianificati 4 sprint, ciascuno della durata di una settimana:

| Sprint | Settimana | Backlog refinement (venerdì pomeriggio) | Sprint review / retrospective / planning (domenica sera) |
|---|---|---|---|
| 1 | 3–9 agosto 2026 | 7 agosto | 9 agosto |
| 2 | 10–16 agosto 2026 | 14 agosto | 16 agosto |
| 3 | 24–30 agosto 2026 | 28 agosto | 30 agosto |
| 4 | 7–13 settembre 2026 | 11 settembre | 13 settembre |

## 10. Riunioni Scrum
- **Sprint Planning, Sprint Review e Sprint Retrospective**: la domenica sera, alla fine/inizio di ogni sprint (vedi tabella sopra).
- **Backlog Refinement**: a metà sprint, di norma il venerdì pomeriggio.
- **Daily Scrum**: tutti i giorni lavorativi del progetto.

## 11. Backlog Items (Epiche)
| # | Epica | Responsabile/i |
|---|---|---|
| 1 | Configurazione del progetto e CI/CD | Configurazione: Matilde, Gaia; CI/CD: Alex |
| 2 | Modello del dominio | Tutti |
| 3 | Caricamento, generazione e validazione mappe | Alex |
| 4 | Movimento e gestione dell'input | Gaia |
| 5 | Gameloop | Tutti |
| 6 | Raccolta oggetti e bonus | Matilde |
| 7 | Gestione delle vite, vittoria e sconfitte | da assegnare |
| 8 | Strategia dei nemici | da assegnare |
| 9 | UI | da assegnare |
| 10 | Documentazione finale | da assegnare |
| 11 | Opzionale: conteggio e classifica | da assegnare |
| 12 | Opzionale: strategie aggiuntive dei nemici | da assegnare |
| 13 | Opzionale: salvataggio | da assegnare |
| 14 | Opzionale: nuove modalità di gioco | da assegnare |

## Nota
Per verificare l'assegnazione dei singoli task ai membri del team, consultare la backlog view sulla repo GitHub del progetto.
