# Formelle krav:
- Opfylde de enkelte krav der stilles i hvert tema
  - Det er dog op til os at bestemme præcis hvad de betyder
- Vores implementation skal være dokumenteret ved brug af Java dokumentation
- Skal kunne indlæse en input-fil fra et vilkårligt tema og stadig fungere
- Modulært design
- Skal kunne kompileres og køres på andre computere
- En projektrapport
- Logbog
  - 2 sider per uge
- Kildekode
  - Dokumenteret
  - Skal kunne kompileres
- Video demonstartion

# Arbejdskrav
- Send ind hver søndag:
  - Logbog
  - Kildekode
  - Til vores TA
  - Titel: "GRPRO-PROJEKT-The Gathering"
- Hav en gruppekontrakt
- Forventes ~22 timers arbejdstid per studerende om ugen

# Temaer's kravoversigt:

>## Tema 1: Herbivore og Planter
>> ### Græs
>>- [x] **K1-1a.** Græs kan blive plantet når input filerne beskriver dette.
Græs skal blot tilfældigt placeres.
>>- [x] **K1-1b.** Græs kan nedbrydes og forsvinde.
>>- [x] **K1-1c.** Græs kan sprede sig.
>>- [x] **k1-1d.** Dyr kan stå på græs uden der sker noget med græsset
(Her kan interfacet NonBlocking udnyttes).
>
>>### Kaniner
>>- [x] **K1-2a.** Kaniner kan placeres på kortet når input filerne beskriver dette.
Kaniner skal blot tilfældigt placeres.
>>- [x] **k1-2b.** Kaniner kan dø, hvilket resulterer I at de fjernes fra verdenen.
>>- [x] **K1-2c.** Kaniner lever af græs som de spiser i løbet af dagen,
uden mad dør en kanin.
>>- [x] **k1-2d.** Kaniners alder bestemmer hvor meget energi de har.
>>- [x] **K1-2e.** Kaniner kan reproducere.
>>- [x] **k1-2f.** Kaniner kan grave huller, eller dele eksisterende huller
med andre kaniner. Kaniner kan kun være knyttet til et hul.
>>- [x] **K1-2g.** Kaniner søger mod deres huller når det bliver aften, hvor de sover.
>
>>### Kaninhuller
>>- [x] **K1-3a.** Huller kan enten blive indsat når input filerne beskriver dette,
eller graves af kaniner. Huller skal blot blive tillfældigt placeret
når de indgår i en input fil.
>>- [x] **K1-3b.** Dyr kan stå på et kaninhul uden der sker noget.
>
>>### Frivillige krav
>>- [ ] **KF1-1.** Huller består altid minimum af en indgang,
der kan dog være flere indgange som sammen former én kanin tunnel.
Kaniner kan kun grave nye udgange mens de er i deres huller.

> ## Tema 2: Prædatorer, flokdyr, og territorier
>> ### Ulve Basis
>>- [x] **K2-1a.** Ulve kan placeres på kortet når input filerne beskriver dette.
>>- [x] **K2-1b.** Ulve kan dø, hvilket resulterer I at de fjernes fra verdenen.
>>- [x] **K2-1c.** Ulve jager andre dyr og spiser dem for at opnå energi.
>
>> ### Pack tactics
>>- [x] **K2-2a.** Ulve er et flokdyr. De søger konstant mod andre ulve i flokken, og derigennem
’jager’ sammen. Når inputfilen beskriver (på en enkelt linje) at der skal placeres flere
ulve, bør disse automatisk være i samme flok.
>>- [x] **K2-3a.** Ulve og deres flok, tilhører en ulvehule, det er også her de formerer sig. Ulve
’bygger’ selv deres huler. Ulve kan ikke lide andre ulveflokke og deres huler. De prøver
således at undgå andre grupper. Møder en ulv en ulv fra en anden flok, kæmper de
mod hinanden.
>
>> ### Bunny fear
>>- [x] **K2-4a.** Kaniner frygter ulve og forsøger så vidt muligt at løbe fra dem.
>>- [x] **K2-5c.** Kaniner frygter bjørne og forsøger så vidt muligt at løbe fra dem.
>
>> ### Beware, Bear
>>- [x] **K2-5a.** Bjørne kan placeres på kortet når input filerne beskriver dette.
>>- [x] **K2-5b.** Bjørne jager, ligesom ulve, og spiser også alt.
>>- [x] **K2-6a.** Bjørnen er meget territoriel, og har som udgangspunkt ikke et bestemt sted den
’bor’. Den knytter sig derimod til et bestemt område og bevæger sig sjældent ud herfra.
Dette territories centrum bestemmes ud fra bjørnens startplacering på kortet.
>>- [x] **K2-7a.** Dertil spiser bjørne også bær fra buske (såsom blåbær og hindbær) når de gror
i området. Bær er en god ekstra form for næring for bjørnen (om end det ikke giver
samme mængde energi som når de spiser kød), men som det er med buske går der tid
før bær gror tilbage. Bær skal indsættes på kortet når inputfilerne beskriver dette.
>>- [x] **K2-8a.** Bjørnen er naturligvis vores øverste rovdyr i denne lille fødekæde, men det
hænder at en stor nok gruppe ulve kan angribe (og dræbe) en bjørn. Dette vil i praksis
være hvis flere ulve af samme flok er i nærheden af en bjørn.

>## Tema 3:
>>### Carcasssssss
>>- [ ] **K3-1a.** Opret ådsler, som placeres på kortet når input filerne beskriver dette.
>>- [x] **K3-1b.** Når dyr dør nu, skal de efterlade et ådsel. Ådsler kan spises ligesom dyr kunne
tidligere, dog afhænger mængden af ’kød’ af hvor stort dyret der døde er. Således
spises dyr ikke direkte længere når det slås ihjel, i stedet spises ådslet. Alle dyr som er
kødædende spiser ådsler.
>>- [x] **K3-1c.** Ådsler bliver dårligere med tiden og nedbrydes helt – selvom det ikke er spist 
op (altså forsvinder det)! Det forsvinder naturligvis også hvis det hele er spist.
>
>>### Mushrooms (Cordyceps)
>>- [x] **K3-2a.** Udover at ådsler nedbrydes, så hjælper svampene til. Således kan der opstå
svampe I et ådsel. Dette kan ikke ses på selve kortet, men svampen lever I selve ådslet.
Når ådslet er nedbrudt (og forsvinder), og hvis svampen er stor nok, kan den ses som
en svamp placeret på kortet, der hvor ådslet lå.
>>- [x] **K3-2b.** Svampe kan kun overleve, hvis der er andre ådsler den kan sprede sig til i
nærheden. Er dette ikke tilfældet, vil svampen også dø efter lidt tid. Desto større ådslet 
er, desto længere vil svampen leve efter ådslet er væk. Da svampen kan udsende
sporer, kan den række lidt længere end kun de omkringliggende pladser
>
>>### Frivilige Krav
>>- [ ] **KF3-1a.** Når en svamp dør, er jorden ekstra gunstig. Derfor opstår græs på sådanne
felter, når svampen dør.
>>- [ ] **KF3-2a.** Ikke alle typer svampe lever på døde dyr. Der eksisterer også en anden type
svamp (Cordyceps). Denne svamp spreder sig til, og styrer, (kun) levende dyr. Deres
livscyklus er den samme som de tidligere svampe; de nedbryder langsomt dyret, og er
der ikke mere tilbage af dyret, dør svampen snart efter. Når svampen har tæret nok på 
dyret, dør dyret. Da denne svamp nedbryder dyret mens det lever, er der ikke noget
ådsel efter døden. Svampen dør også når dyret dør, med undtagelsen af krav
>>- [ ] **KF3-3a.** Når Cordyceps’ vært dør, forsøger den at sprede sig til levende dyr i nærheden,
og kun på dette tidspunkt. Igen kan denne svamp sprede sig lidt længere end de
omkringliggende pladser da den sender sporer ud.
>>- [ ] **KF3-3b.** Når et dyr er inficeret med Cordyceps svampen, overtager svampen dyrets
handlinger. Dyret gør derfor som svampen bestemmer, hvilket er at søge mod andre
dyr af samme art.

>## Tema 4:
>>### Checkliste:
>>- Nyt dyr: Fox
>>> #### Ideer:
>>>- Bier
>>>- Fisk
>>>- Mennesker
>>>- Ræve!
>>
>>> #### Adfærd:
>>>- [ ] Sover om natten, jager om dagen.
>>>- [ ] Finder kaninhuller og står oven på dem for at angribe kaniner nede i dem.
>>>- [ ] Graver deres egne huller som de kan sove i.
>>>- [ ] Ræve gemmer kanin lig og ligger dem ved sin egen hule.
>>>- [ ] Jager kun kaniner.
> 
>>### Rigtige krav:
>>- [ ] **K4-1.** Vælg et valgfrit dyr og implementer dets karakteristika og adfærd i økosystemet.
Dyret skal have mindst et unikt adfærd.
>>- [ ] **K4-2.** Dyret skal kunne interagere med eksisterende elementer i økosystemet, herunder ådsler, planter og andre dyr.
>>- [ ] **K4-3.** Simuler dyrets livscyklus, herunder fødsel, vækst, reproduktion og død.
>>- [ ] **K4-4.** Implementer dyrets fødekæde og prædator-bytte forhold.
>>- [ ] **K4-5.** Dyrets tilstedeværelse og adfærd skal kunne påvirke økosystemets balance og dynamik.