-- GENRE
INSERT INTO GENRE (NAME) VALUES
	 ('Аниме'),
	 ('Биографии'),
	 ('Боевики'),
	 ('Вестерны'),
	 ('Военные'),
	 ('Детективы'),
	 ('Детские'),
	 ('Документальные'),
	 ('Драмы'),
	 ('Игры');
INSERT INTO GENRE (NAME) VALUES
	 ('Исторические'),
	 ('Комедии'),
	 ('Короткометражки'),
	 ('Криминал'),
	 ('Мелодрамы'),
	 ('Музыкальные'),
	 ('Мультфильмы'),
	 ('Мюзиклы'),
	 ('Приключения'),
	 ('Реальное ТВ');
INSERT INTO GENRE (NAME) VALUES
	 ('Семейные'),
	 ('Спортивные'),
	 ('Ток-шоу'),
	 ('Триллеры'),
	 ('Ужасы'),
	 ('Фантастика'),
	 ('Фильмы-нуар'),
	 ('Фэнтези');

-- RATING
INSERT INTO RATING (NAME,DESCRIPTION) VALUES
	 ('G','Нет возрастных ограничений'),
	 ('PG','Рекомендуется присутствие родителей'),
	 ('PG-13','Детям до 13 лет просмотр не желателен'),
	 ('R','Лицам до 17 лет обязательно присутствие взрослого'),
	 ('NC-17','Лицам до 18 лет просмотр запрещен');

-- USER
INSERT INTO "USER" (NAME,LOGIN,EMAIL,BIRTHDAY) VALUES
	 ('Hayden','Hayden','cras.interdum.nunc@protonmail.com','1985-03-08'),
	 ('Mechelle','Mechelle','cursus.vestibulum.mauris@icloud.org','1982-05-22'),
	 ('Christian','Christian','sociis.natoque@aol.com','2016-04-13'),
	 ('Whilemina','Whilemina','orci.adipiscing@yahoo.org','2008-01-18'),
	 ('Gisela','Gisela','turpis.nec@outlook.ca','1996-08-22'),
	 ('Austin','Austin','placerat.velit@yahoo.org','2003-05-17'),
	 ('Maxine','Maxine','feugiat.placerat@outlook.edu','1983-07-21'),
	 ('Jasper','Jasper','nisl@protonmail.com','2007-03-16'),
	 ('Cyrus','Cyrus','malesuada.fames@hotmail.com','1992-10-29'),
	 ('Heidi','Heidi','sodales@yahoo.couk','2012-01-05');
INSERT INTO "USER" (NAME,LOGIN,EMAIL,BIRTHDAY) VALUES
	 ('Hilda','Hilda','ipsum.porta@icloud.org','1983-10-12'),
	 ('Rafael','Rafael','tellus.suspendisse.sed@yahoo.couk','1994-02-27'),
	 ('Eliana','Eliana','urna.nunc.quis@google.couk','2003-12-19'),
	 ('Laurel','Laurel','luctus.sit@outlook.net','1995-07-31'),
	 ('Idola','Idola','fusce.aliquet@icloud.org','1988-11-10'),
	 ('Ifeoma','Ifeoma','ante.blandit@google.edu','2012-05-31'),
	 ('Cedric','Cedric','et.malesuada@protonmail.net','1984-03-05'),
	 ('Maggy','Maggy','iaculis.quis@protonmail.org','2005-10-10'),
	 ('Wylie','Wylie','nulla@outlook.edu','1992-01-03'),
	 ('Joshua','Joshua','eu.ultrices.sit@google.com','2018-05-15');

--FILM
INSERT INTO FILM (RATING_ID,NAME,DESCRIPTION,RELEASE_DATE,DURATION) VALUES
	 (1,'По щучьему велению (2023)','Чудо-рыба помогает непутевому Емеле завоевать сердце царской дочери. Сказочный хит с Никитой Кологривым','2023-11-24',115),
	 (1,'Гарри Поттер и философский камень (2001)','Жизнь десятилетнего Гарри Поттера нельзя назвать сладкой: родители умерли, едва ему исполнился год, а от дяди и тёти, взявших сироту на воспитание, достаются лишь тычки да подзатыльники.','2001-03-04',NULL),
	 (3,'Властелин колец: Братство Кольца (2001)','Сказания о Средиземье — это хроника Великой войны за Кольцо, длившейся не одну тысячу лет. Тот, кто владел Кольцом, получал неограниченную власть, но был обязан служить злу.','2012-12-03',178);

--FILM_GENRE
INSERT INTO FILM_GENRE (FILM_ID,GENRE_ID) VALUES
	 (1,19),
	 (1,21),
	 (1,28),
	 (2,19),
	 (2,21),
	 (2,28);
-- FILM_LIKE
INSERT INTO FILM_LIKE (FILM_ID,USER_ID,CREATE_DATE) VALUES
	 (1,1,'2001-03-04 12:00:00'),
	 (1,2,'2002-03-04 13:00:00'),
	 (1,3,'2002-03-04 14:00:00'),
	 (3,5,'2002-03-04 11:00:00');
-- FRIENDSHIP
INSERT INTO FRIENDSHIP (USER_ID,FRIEND_ID,STATUS) VALUES
	 (1,2,true),
	 (5,6,true),
	 (15,11,false),
	 (13,6,true),
	 (7,2,true),
	 (5,16,false);
