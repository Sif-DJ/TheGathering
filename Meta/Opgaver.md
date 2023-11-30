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
>>- [ ] **K2-1c.** Ulve jager andre dyr og spiser dem for at opnå energi.
>
>> ### Pack tactics
>>- [ ] **K2-2a.** Ulve er et flokdyr. De søger konstant mod andre ulve i flokken, og derigennem
’jager’ sammen. Når inputfilen beskriver (på en enkelt linje) at der skal placeres flere
ulve, bør disse automatisk være i samme flok.
>>- [ ] **K2-3a.** Ulve og deres flok, tilhører en ulvehule, det er også her de formerer sig. Ulve
’bygger’ selv deres huler. Ulve kan ikke lide andre ulveflokke og deres huler. De prøver
således at undgå andre grupper. Møder en ulv en ulv fra en anden flok, kæmper de
mod hinanden.
>
>> ### Bunny fear
>>- [ ] **K2-4a.** Kaniner frygter ulve og forsøger så vidt muligt at løbe fra dem.
>>- [ ] **K2-5c.** Kaniner frygter bjørne og forsøger så vidt muligt at løbe fra dem.
>
>> ### Beware, Bear
>>- [x] **K2-5a.** Bjørne kan placeres på kortet når input filerne beskriver dette.
>>- [ ] **K2-5b.** Bjørne jager, ligesom ulve, og spiser også alt.
>>- [ ] **K2-6a.** Bjørnen er meget territoriel, og har som udgangspunkt ikke et bestemt sted den
’bor’. Den knytter sig derimod til et bestemt område og bevæger sig sjældent ud herfra.
Dette territories centrum bestemmes ud fra bjørnens startplacering på kortet.
>>- [ ] **K2-7a.** Dertil spiser bjørne også bær fra buske (såsom blåbær og hindbær) når de gror
i området. Bær er en god ekstra form for næring for bjørnen (om end det ikke giver
samme mængde energi som når de spiser kød), men som det er med buske går der tid
før bær gror tilbage. Bær skal indsættes på kortet når inputfilerne beskriver dette.
>>- [ ] **K2-8a.** Bjørnen er naturligvis vores øverste rovdyr i denne lille fødekæde, men det
hænder at en stor nok gruppe ulve kan angribe (og dræbe) en bjørn. Dette vil i praksis
være hvis flere ulve af samme flok er i nærheden af en bjørn.

## Tema 3:
## Tema 4: