#  Progetto Ricerca Operativa
## Pseudocodice
for i=1 to 10
*Calcola C&W considerando solo le rotte BH
*calcola C&W considerando solo le rotte LH, interrompendo l’unione delle rotte se n. rotte LH == k o n. rotte LH == n. rotte BHC
*Calcola i saving tra rotte LH e BW e unisci finché è possibile 
*Mosse di miglioramento best exchange (considerando le parti LH e BH di ogni rotta come due rotte distinte)
*Mosse di miglioramento best recolate (considerando le parti LH e BH di ogni rotta come due rotte distinte)
*Salva la soluzione in un file temp
end for
Scegli dai file la soluzione migliore
