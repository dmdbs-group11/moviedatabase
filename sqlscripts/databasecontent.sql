insert into FilmPerson values(1,'Hermann',1999,'Norge',False,True,False);
insert into Serie values(1, 'Paradise Hotel', 'Triana Iglesias åpner igjen dørene til Paradise Hotel, et sted der alt handler om å finne seg en partner å stole på for å komme seg til finalen.');
insert into Sesong values(1,'Sesong 12' ,'Hermann sjekker inn på hotellet og skaper kaos',1);
insert into Selskap values(1, 'Viaplay', 'https://viaplay.no/serier/paradise-hotel', 'osloveien 1', 'Norge');
insert into Film values(1, 'Episode 1', 42, 2020, 1211, 'Hermann får ingen venner på hotellet',true,1,1);
insert into RolleIFilm values(1,1,'Hermann venneløs');
insert into Film values(2, 'Episode 2', 42, 2020, 1311, 'Hermann får fortsatt ingen venner på hotellet',true,1,1);



insert into Selskap values(10, 'Disney', 'disney.no', 'Disneyveien 33', 'USA');
insert into Film values(101, 'Frost', 213, 2013, 1011,'Filmen handler om den unge jenta Anna som legger ut på en reise sammen med issageren Kristoffer, snømannen Olaf og reinsdyret Svein for å finne den legendariske Snødronningen Elsa, som også er hennes fremmedgjorte søster.', true,null,10);


insert into Selskap values(20, 'dplay', 'dplay.no/programmer/ex-on-the-beach-norge', 'Osloveien 2', 'Norge');
insert into Serie values(2, 'Ex on the Beach', 'Stramme kropper, krasse kommentarer og dramatiske følelser.');
insert into Sesong values(101,'Sesong 3' ,'Stramme kropper, krasse kommentarer og dramatiske følelser. Og Hermann.',2);
insert into Film values(51, 'Episode 1', '37', 2020, 1803, 'Julie har det hyggelig på Ex on the Beach helt til exen hennes Hermann kommer inn. Han prøver å bortforklare hvorfor han slettet henne på facebook, men alle deltagerne er enige om at det var en teit ting å gjøre. Hermann er venneløs', true, 101,20);

insert into RolleIFilm values(1,51,'Hermann venneløs');

insert into Kategori values(1,'Reality');
insert into Kategori values(2,'Animasjon');
insert into FilmKategori values(1,1);
insert into FilmKategori values(1,2);
insert into FilmKategori values(1,51);
insert into FilmKategori values(2,101);