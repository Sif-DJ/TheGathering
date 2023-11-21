># 2023-11-16
> Vi brugte denne dag på at opsætte vores projekt og GitHub repo.\
> Der blev downloadet alle relevante programmer og læst igennem alle filer.\
> Til næste gang skal vi have lavet "'Kom i gang'-guiden"
> individuelt uden nogen relation til vores Repository.
---
># 2023-11-21
> Vi startede ud med at få helt styr på, hvordan vores gruppe ville fungere, ved at lave en gruppekontrakt. Her markerede vi vores målsætning, hvad vi gerne ville have ud af projektet, vores rollefordeling, mødestruktur, kommunikation og konflikthåndtering. \
> Da vi alle var tilfredse med gruppekontrakten, gik vi videre til at læse ugens tema. Her kom vi frem til en liste af classes vi ville skulle implementere, samt en liste af variabler og funktioner til disse classes: 
>
> abstract class Organism implements Actor
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
> For at gøre det mere overskueligt for os selv og andre, lavede vi samtidigt et UML diagram over vores classes, og hvordan vi tænkte, de ville interagere med hinanden. Dette var en stor hjælp, når det kom til at skrive koden senere, da vi hurtigt kunne tjekke, hvad hver class skulle kunne. \
> ![Image](https://github.com/Sif-DJ/TheGathering/blob/main/mappe/magicV1.drawio.png)
>
