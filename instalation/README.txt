BoxScore 0.1 - Beta verze programu

install - pøekopíruje soubory do sloky C:\Program Files\BoxScore a vytvoøí ikonu na pracovní ploše
uninstall - vymae všechny soubory z C:\Program Files\BoxScore vèetnì ikony
unistall_all - funkce jako uninstall + vymae i datové sloky (C:\boxscoreData)
-soubory spuštìt jako admin

-datová sloka (C:\boxscoreData) se vytvoøí pøi prvním spuštìní programu
-datová sloka obsahuje dvì podsloky
	-rosters - obsahuje uloené soupisky
	-boxscores - obsahuje uloené technické zápisy

-soupiska se musí nejdøíve v programu ruènì vytvoøit a uloit, pak mùe bıt znovu naèítána
-soupisky se implicitnì ukládají do sloky C:\boxscoreData\rosters
-pøi zapisování statistik se musí nejdøíve kliknout na èíslo hráèe na høišti a pak na statistiku nebo na hráèe na lavièce pro støídání
-základní pìtka je nastavena automaticky - pìt hráèù s nejnišími èísly, pøed zaèátkem utkání je nutno prostøídat
-tlaèítko generate table (pracovní název, bude vhodnì zmìnìno) generuje technickı zápis, implicitnì ukládá do sloky C:\boxscoreData\boxscores
-TO = time-out (není potøeba zvolení hráèe pøed kliknutím)

-nepodporuje chybová hlášení - v pøípadì chyby program pouze zamrzne (napøíklad vytvoøení tımu s ménì jak 6 hráèi)
-nepodporuje zápis pro dva tımy zaráz
-nepodporuje nastavení barev pøi vytváøení tımu (aktuálnì natvrdo nastavená èerno-modrá)
-nepodporuje tlaèítko Back
-tvorba vısledného technického zápisu stále probíhá (tabulka jako taková u je hotová, ale stále obsahje pár chyb)