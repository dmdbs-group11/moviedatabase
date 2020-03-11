
create table Bruker(
	BID	integer not null,
    constraint Bruker_PK primary key (BID));
create table Anmeldelse(
	AnmeldelsesID	integer not null,
    Tekst	varchar(250),
    Rating	integer,
    BID	integer not null,
    constraint Anmeldelse_PK primary key (AnmeldelsesID),
    constraint Anmeldelse_FK foreign key (BID) references Bruker(BID));
create table Serie(
	SerieID	integer not null,
    Tittel	varchar(30),
    Storyline	varchar(250),
    constraint Serie_PK primary key (SerieID));
create table SerieAnmeldelse(
	AnmeldelsesID	integer not null,
    SerieID	integer,
    constraint SerieAnmeldelse_PK primary key (AnmeldelsesID),
    constraint SerieAnmeldelse_FK1 foreign key (AnmeldelsesID) references Anmeldelse(AnmeldelsesID),
    constraint SerieAnmeldelse_FK2 foreign key (SerieID) references Serie(SerieID));
create table Sesong(
	SesongID	integer not null,
    Tittel varchar(30),
    Storyline	varchar(250),
    SerieID	integer not null,
    constraint Sesong_PK primary key (SesongID),
    constraint Sesong_FK foreign key (SerieID) references Serie(SerieID));
create table SesongAnmeldelse(
	AnmeldelsesID	integer not null,
    SesongID	integer,
    constraint SesongAnmeldelse_PK primary key (AnmeldelsesID),
    constraint SesongAnmeldelse_FK1 foreign key (AnmeldelsesID) references Anmeldelse(AnmeldelsesID),
    constraint SesongAnmeldelse_FK2 foreign key (SesongID) references Sesong(SesongID));
create table Selskap(
	SelskapsID	integer not null,
    Selskapsnavn varchar(30),
    URL	varchar(100),
    adresse	varchar(30),
    land	varchar(30),
    constraint Selskap_PK primary key (SelskapsID));
create table Film(
	FilmID	integer not null,
    Tittel	varchar(30),
    Lengde	integer,
    Utgivelsesaar	integer,
    Lanseringsdato	integer,
    Storyline	varchar(250),
    Paavideo	boolean,
    SesongID	integer,
    SelskapsID	integer not null,
    constraint Film_PK primary key (FilmID),
    constraint Film_FK1 foreign key (SesongID) references Sesong(SesongID),
    constraint Film_FK2 foreign key (SelskapsID) references Selskap(SelskapsID));
create table FilmAnmeldelse(
	AnmeldelsesID	integer not null,
    FilmID	integer,
    constraint FilmAnmeldelse_PK primary key (AnmeldelsesID),
    constraint FilmAnmeldelse_FK1 foreign key (AnmeldelsesID) references Anmeldelse(AnmeldelsesID),
    constraint FilmAnmeldelse_FK2 foreign key (FilmID) references Film(FilmID));
create table FilmPerson(
	PID	integer not null,
    Navn	varchar(30),
    Fodselsaar	integer,
    Fodselsland	varchar(30),
    Regissor	boolean,
    Skuespiller	boolean,
    Manusforfatter	boolean,
    constraint FilmPerson_PK primary key (PID));
create table RegissorTilFilm(
	PID integer not null,
    FilmID integer not null,
    constraint RegissorTilFilm_PK primary key (PID, FilmID),
    constraint RegissorTilFilm_FK1 foreign key (PID) references FilmPerson(PID),
    constraint RegissorTilFilm_FK2 foreign key (FilmID) references Film(FilmID));
create table RolleIFilm(
	PID	integer not null,
    FilmID integer not null,
    Rollenavn	varchar(30),
    constraint RolleIFilm_PK primary key (PID, FilmID),
    constraint RolleIFilm_FK1 foreign key (PID) references FilmPerson(PID),
    constraint RolleIFilm_FK2 foreign key (FilmID) references Film(FilmID));
create table ForfatterIFilm(
	PID integer not null,
    FilmID integer not null,
    constraint ForfatterIFilm_PK primary key (PID, FilmID),
    constraint ForfatterIFilm_FK1 foreign key (PID) references FilmPerson(PID),
    constraint ForfatterIFilm_FK2 foreign key (FilmID) references Film(FilmID));
create table Musikk(
	MusikkID	integer not null,
    Komponist	varchar(30),
    Framforer	varchar(30),
    constraint Musikk_PK primary key (MusikkID));
create table MusikkIFilm(
	MusikkID	integer not null,
    FilmID	integer not null,
    constraint MusikkIFilm_PK primary key (MusikkID, FilmID),
    constraint MusikkIFilm_FK1 foreign key (MusikkID) references Musikk(MusikkID),
    constraint MusikkIFilm_FK2 foreign key (FilmID) references Film(FilmID));
create table Media(
	MediaID	integer not null,
    Beskrivelse	varchar(100),
    constraint Media_PK primary key (MediaID));
create table FilmMedia(
	MediaID integer not null,
    FilmID integer not null,
    constraint FilmMedia_PK primary key (MediaID, FilmID),
    constraint FilmMedia_FK1 foreign key (MediaID) references Media(MediaID),
    constraint FilmMedia_FK2 foreign key (FilmID) references Film(FilmID));
create table Kategori(
	KategoriID	integer not null,
    KategoriNavn	varchar(30),
    constraint Kategori_PK primary key (KategoriID));
create table FilmKategori(
	KategoriID integer not null,
    FilmID	integer not null,
    constraint FilmKategori_PK primary key (KategoriID, FilmID),
    constraint FilmKategori_FK1 foreign key (KategoriID) references Kategori(KategoriID),
    constraint FilmKategori_FK2 foreign key (FilmID) references Film(FilmID));