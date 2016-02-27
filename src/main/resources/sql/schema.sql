CREATE TABLE authors (
  -- id SERIAL PRIMARY KEY
  id       INTEGER AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(100) NOT NULL,
  birthday DATE         NOT NULL,
  death    DATE,
  country  VARCHAR(20)  NOT NULL,
  notes    VARCHAR(1000)
);

CREATE TABLE books (
  -- id SERIAL PRIMARY KEY
  id                INTEGER AUTO_INCREMENT PRIMARY KEY,
  title             VARCHAR(100) NOT NULL,
  author_id         INTEGER,
  original_language VARCHAR(20),
  isbn              VARCHAR(20),
  published         DATE         NOT NULL,
  abstract          VARCHAR(1000),
  FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

INSERT INTO authors (id, name, birthday, death, country, notes) VALUES
  (1, 'George Orwell',
   '1903-06-23', '1950-01-21',
   'UK',
   'Eric Arthur Blair (25 June 1903 – 21 January 1950), who used the pen name George Orwell, was an English novelist, essayist, journalist, and critic. His work is marked by lucid prose, awareness of social injustice, opposition to totalitarianism, and outspoken support of democratic socialism.'),

  (2, 'Frank Herbert',
   '1920-10-08', '1986-02-11',
   'USA',
   'Frank Patrick Herbert, Jr. (October 8, 1920 – February 11, 1986) was an American science fiction writer best known for the novel Dune and its five sequels. Though he became famous for science fiction, he was also a newspaper journalist, photographer, short story writer, book reviewer, ecological consultant and lecturer.'),

  (3, 'John Steinbeck',
   '1902-02-27', '1968-12-20',
   'USA',
   'John Ernst Steinbeck, Jr. (February 27, 1902 – December 20, 1968) was an American author of twenty-seven books, including sixteen novels, six non-fiction books, and five collections of short stories. He is widely known for the comic novels Tortilla Flat (1935) and Cannery Row (1945), the multi-generation epic East of Eden (1952), and the novellas Of Mice and Men (1937) and The Red Pony (1937). The Pulitzer Prize-winning The Grapes of Wrath (1939), widely attributed to be part of the American literary canon, is considered Steinbeck''s masterpiece. In the first 75 years since it was published, it sold 14 million copies.'),

  (4, 'Nikolay Zabolotsky',
   '1903-05-07', '1958-10-14',
   'USSR',
   'Nikolay Alexeyevich Zabolotsky (May 7, 1903 - October 14, 1958) was a Russian poet, children''s writer and translator. He was a Modernist and one of the founders of the Russian avant-garde absurdist group Oberiu.'),

  (5, 'Lesya Ukrainka',
   '1871-02-25', '1913-08-01',
   'Ukraine',
   'Larysa Petrivna Kosach-Kvitka (February 25 1871 – August 1 1913) better known under her literary pseudonym Lesya Ukrainka, was one of Ukraine''s best-known poets and writers and the foremost woman writer in Ukrainian literature. She also was a political, civil, and feminist activist.'),

  (6, 'Vladimir Nabokov',
   '1899-04-22', '1977-07-02',
   'USA',
   'Vladimir Nabokov, also known by the pen name Vladimir Sirin; 22 April 1899 – 2 July 1977) was a Russian-American novelist. His first nine novels were in Russian, and he achieved international prominence after he began writing English prose.'),

  (7, 'John Ronald Reuel Tolkien',
   '1892-01-03', '1973-09-02',
   'UK',
   'John Ronald Reuel Tolkien (3 January 1892 – 2 September 1973) was an English writer, poet, philologist, and university professor who is best known as the author of the classic high-fantasy works The Hobbit, The Lord of the Rings, and The Silmarillion.');

-- This will work for postgres:
-- ALTER SEQUENCE authors_id_seq RESTART WITH 8;

ALTER TABLE authors ALTER COLUMN id RESTART WITH 8;

INSERT INTO books (title, author_id, original_language, isbn, published, abstract) VALUES
  ('Nineteen Eighty-Four', 1,
   'English', '978-0-547-24964-3', '1949-06-08',
   'Nineteen Eighty-Four, often published as 1984, is a dystopian novel by English author George Orwell published in 1949. The novel is set in Airstrip One (formerly known as Great Britain), a province of the superstate Oceania in a world of perpetual war, omnipresent government surveillance and public manipulation, dictated by a political system euphemistically named English Socialism (or Ingsoc in the government''s invented language, Newspeak) under the control of a privileged elite of the Inner Party, that persecutes individualism and independent thinking as "thoughtcrime."'),

  ('Animal Farm', 1,
   'English', '978-0-452-28424-1', '1945-08-17',
   'Animal Farm is an allegorical and dystopian novella by George Orwell, first published in England on 17 August 1945. According to Orwell, the book reflects events leading up to the Russian Revolution of 1917 and then on into the Stalinist era of the Soviet Union. Orwell, a democratic socialist, was a critic of Joseph Stalin and hostile to Moscow-directed Stalinism, an attitude that was critically shaped by his experiences during the Spanish Civil War.'),

  ('Dune', 2,
   'English', NULL, '1965-08-01',
   'Dune is a 1965 epic science fiction novel by Frank Herbert. It tied with Roger Zelazny''s This Immortal for the Hugo Award in 1966, and it won the inaugural Nebula Award for Best Novel. It is the first installment of the Dune saga, and in 2003 was cited as the world''s best-selling science fiction novel.'),

  ('The Grapes of Wrath', 3,
   'English', NULL, '1939-04-14',
   'The Grapes of Wrath is an American realist novel written by John Steinbeck and published in 1939. The book won the National Book Award and Pulitzer Prize for fiction, and it was cited prominently when Steinbeck was awarded the Nobel Prize in 1962.'),

  ('Of Mice and Men', 3,
   'English', NULL, '1937-01-01',
   'Of Mice and Men is a novella written by author John Steinbeck. Published in 1937, it tells the story of George Milton and Lennie Small, two displaced migrant ranch workers, who move from place to place in California in search of new job opportunities during the Great Depression in the United States.'),

  ('The Forest Song', 5,
   'Ukrainian', NULL, '1914-01-01',
   'Féerie by famous Ukrainian poet Lesya Ukrainka, which treats the conflict between lofty idealism and the prosaic details of everyday life.'),

  ('Mashen''ka', 6,
   'Russian', NULL, '1926-01-01',
   'Mashen''ka is the debut novel by Vladimir Nabokov, first published under pen name V. Sirin in 1926 by the Russian language publisher "Slovo".'),

  ('Lolita', 6,
   'English', NULL, '1955-01-01',
   'Lolita is a novel by Vladimir Nabokov, written in English and published in 1955 in Paris, in 1958 in New York City, and in 1959 in London. It was later translated by its Russian-native author into Russian.'),

  ('Pnin', 6,
   'English', NULL, '1957-01-01',
   'Pnin is Vladimir Nabokov''s 13th novel and his fourth written in English; it was published in 1957. The success of Pnin in the United States would launch Nabokov''s career into literary prominence.'),

  ('The Hobbit', 7,
   'English', NULL, '1937-02-21',
   'The Hobbit, or There and Back Again is a fantasy novel and children''s book by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction. The book remains popular and is recognized as a classic in children''s literature.');






