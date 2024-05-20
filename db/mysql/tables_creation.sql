-- Author: Luis Egui

use dunktomic_db;

-- Image table creation
create table if not exists
  Image (
    id serial primary key,
    path varchar(256) unique not null,
    mime_type enum('image/jpeg','image/jpg','image/png') not null
  );

-- User table creation
create table if not exists
  User (
    email varchar(30) unique primary key,
    name varchar(70),
    password varchar(70) not null,
    role enum ('admin', 'player') default 'player',
    user_image bigint unsigned,
    foreign key (user_image) references Image(id),
    check (
      email regexp '^[a-zA-Z0-9.!#$%&*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$'
    )
  );

-- Player table creation
create table if not exists
  Player (
    email varchar(30) unique not null,
    positions_on_court set ('PG', 'SG', 'SF', 'PF', 'C') not null,
    level float,
    foreign key (email) references User(email),
    check (
      level >= 0 and level <= 10
    )
  );

-- Admin table creation
create table if not exists
  Admin (
    email varchar(30) unique not null,
    foreign key (email) references User(email)
  );

-- Club table creation
create table if not exists
  Club (
    id serial primary key,
    name varchar(128) unique not null,
    district varchar(70),
    postal_code varchar(5),
    street_address varchar(128),
    club_image bigint unsigned,
    latitude float,
    longitude float,
    foreign key (club_image) references Image(id),
    check (
      postal_code regexp '^28[0-9]{3}$'
    )
  );

-- Court table creation
create table if not exists
  Court (
    club_id bigint unsigned not null,
    name varchar(70) not null,
    type enum ('indoor', 'outdoor', '3x3') not null,
    foreign key (club_id) references Club(id),
    primary key (club_id, name, type)
  );

-- Team table creation
create table if not exists
  Team (
    id serial primary key,
    name enum ('team_a', 'team_b') not null
  );

-- TeamPlayers table creation
create table if not exists
  TeamPlayers (
    team_id bigint unsigned not null,
    player varchar(30) not null,
    primary key (team_id, player),
    foreign key (team_id) references Team(id),
    foreign key (player) references Player(email)
  );

-- Game table creation
create table if not exists
  Game (
    id serial primary key,
    state enum ('pending', 'cancelled', 'in_progress', 'finished') not null default 'pending',
    type enum ('public', 'private') not null,
    club_id bigint unsigned not null,
    court_name varchar(70) not null,
    court_type enum ('indoor', 'outdoor', '3x3') not null,
    team_a bigint unsigned not null,
    team_b bigint unsigned not null,
    result enum ('win_a', 'win_b', 'draw', 'not_played') not null default 'not_played',
    ddate datetime not null,
    foreign key (club_id, court_name, court_type) references Court(club_id, name, type),
    foreign key (team_a) references Team(id),
    foreign key (team_b) references Team(id)
  );
