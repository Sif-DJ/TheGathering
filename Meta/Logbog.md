># 2023-11-16
> Vi brugte denne dag på at opsætte vores projekt og GitHub repo.\
> Der blev downloadet alle relevante programmer og læst igennem alle filer.\
> Til næste gang skal vi have lavet "'Kom i gang'-guiden"
> individuelt uden nogen relation til vores Repository.
---

># Tema 1
># 2023-11-21
> Vi startede ud med at få helt styr på, hvordan vores gruppe ville fungere, ved at lave en gruppekontrakt. Her markerede vi vores målsætning, hvad vi gerne ville have ud af projektet, vores rollefordeling, mødestruktur, kommunikation og konflikthåndtering. \
> Da vi alle var tilfredse med gruppekontrakten, gik vi videre til at læse ugens tema. Her kom vi frem til en liste af classes vi ville skulle implementere, samt en liste af variabler og funktioner til disse classes: 
>
> abstract class Organism implements Actor \
> abstract class Animal implements Organism \
> class Rabbit extends Animal \
> abstract class Food implements Organism, Nonblocking \
> class Grass extends Food \
> class RabbitHole implements, Nonblocking 
>
> Da kaniner dør uden mad, valgte vi at implementere en variabel "energy", der symboliserer hvor meget energi mad eller et dyr indeholder. Hvis en kanins energy rammer 0 dør den af sult. Da både kaniner og mad indeholder energi, laver vi en abstract class kaldet "Organism", der bliver videre indelt i abstracts "Animal" og "Food". Grunden til vi gør dette, er for at undgå kodedublikering så meget som muligt. \
> Vi tænker også en kanin kommer til at kunne dø af andet end sult, så vi tilføjer også en "age" og en "health" variabel. Hvis kaninens helbred rammer 0 dør den også. Derudover vil en kanin også dø af alderdom over nok tid. 
>
> Da kaninhuller burde kunne indeholde flere forskellige kaniner, tilføjer vi en arraylist<Rabbit>, der holder styr på, hvilke kaniner der er inde i hvert kaninhul.
>
> Vi kom også frem til at flere af vores classes, skulle nedarve fra nogle af de indbyggede interfaces fra biblioteket. Vi valgte at gøre brug af interfacet "Actor" til alle de classes, der skulle gøre et eller andet under hvert iteration af programmet. Dette er f.eks. classet "Rabbit", der hvert iteration går rundt i verdenen og leder efter/spiser mad, eller classet "grass", der har en chance for at sprede sig til nye felter i verdenen. Vi gør også brug af interfacet "Nonblocking" til de classes, hvor det giver mening man kan stå ovenpå dem. F.eks. nedarver "grass" og "Rabbithole" fra "Nonblocking" eftersom kaninerne burde kunne gå over græsset og hullerne.
>
> For at gøre det mere overskueligt for os selv og andre, lavede vi samtidigt et UML diagram over vores classes, og hvordan vi tænkte, de ville interagere med hinanden. Dette var en stor hjælp, når det kom til at skrive koden senere, da vi hurtigt kunne tjekke, hvad hver class skulle kunne. \
> ![Image](https://github.com/Sif-DJ/TheGathering/blob/main/Meta/magicV1.drawio.png) 
>
># 2023-11-23
> Nu når vi har fået styr på, hvad der skal implementeres, kan vi begynde med at tilføje funktionaliteterne.
>
> Vi startede ud med at prøve at få plantet græsset ordentligt i verdenen. \
> Vores grass class har en konstruktør der instantiere dens variabler. Her nedarver den varibler fra vores abstracts, "Food" og "Organism", samt "act()" funktionen fra "Actor" interfacet.
>
> Den første funktion grass har er "eat(int amount)", der tager et input "amount", der symboliserer hvor meget græs et dyr vil spise af den. Dette trækker den fra sit sit total af "energy" græsset har tilgængelig og returner den mængde energy der blev spist. \
> Græsset har en "die()" funktion, den nedarver fra vores abstract "organism", der sletter græsset. Vi har valgt at gøre så græs kun kan dø, hvis det bliver spist. Grunden til dette er at græs i virkeligheden spreder sig selv så hurtigt, at græs aldrig rigtig forsvinder. Derfor er det kun objekter der nedarver fra "Animal" abstracten, der har en age variabel. \
> Da græsset skal kunne sprede sig selv, har vi en "spread()" funktion, der lader græsset gro til tomme felter rundt om sig selv. Her gør vi brug af "getEmptySorrundingTiles()" funktionen fra biblioteket. \
> Til sidst har vi dens "act()" funktion den implementerer fra "Actor". Her overrider vi funktionen, og går igennem alle de ting, et instans af grass skal tage stilling til hver iteration. Her tjekker græsset om den er løbet tør for energy. Hvis ja kalder den die(). Udover det increaser den sin energy (til et maksimum "maxEnergy"), der symboliserer at græsset gror. Til sidst vælger den et random tal, baseret på dens "spreadchance" variabel. Hvis tallet rammer dens spreadchance, kalder den "spread()" funktionen.
>
> "Rabbit" har også en konstruktør der nedarver fra forskellige absctracts. Kaniner nedarver fra vores abstracts "Organism", "Animal" og interfacet "Actor". \
> Da kaniner nedarver fra "Organism" har de også en "die()" funktion og energy. \
> Kaniner nedarver evnen til at bruge "move()", "age()", "reproduce()" og "eat()" funktionerne fra "Animal" abstracten, der lader den bevæge sig til nonblocking felter, blive ældre, hvilket reducere dyrets "maxEnergy", reproduce, hvilket skaber nye dyr, og til sidst spise mad, hvilket giver dyret energi. Vi har gjort så dyret mister energy af at bevæge sig rundt, hvilket giver den en grund til at søge efter mad, så den ikke dør af sult. 
> 
># 2023-11-25
> Vi tjekkede testfilerne ud, og kom frem til at vi havde brug for en funktion der kunne læse testfilen og konvertere den til et string array, som vi kan bruge til at læse inputne. Vi gør dette ved brug af en indbygget dropdown menu kaldet "JOptionPane", hvilket gør det super nemt at vælge, hvilken testfil man gerne vil simulere.
