-- Author: Luis Egui

use dunktomic_db;

-- Image table creation
create table if not exists
  Image (
    id char(36) primary key,
    path varchar(256) unique not null,
    mime_type enum('image/jpeg','image/jpg','image/png') not null
  );

-- User table creation
create table if not exists
  User (
    id char(36) primary key,
    email varchar(30) unique,
    name varchar(70),
    role enum ('admin', 'player') default 'player',
    user_image char(36),
    foreign key (user_image) references Image(id) on update cascade,
    check (
      email regexp '^[a-zA-Z0-9.!#$%&*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$'
    )
  );

-- Player table creation
create table if not exists
  Player (
    email varchar(30) unique not null,
    positions_on_court set ('PG', 'SG', 'SF', 'PF', 'C') not null,
    level float default 1,
    foreign key (email) references User(email) on update cascade,
    check (
      level >= 0 and level <= 10
    )
  );

-- Follows table creation
create table if not exists
  Follows (
    follower varchar(30) not null,
    followed varchar(30) not null,
    primary key (follower, followed),
    foreign key (follower) references Player(email) on update cascade,
    foreign key (followed) references Player(email) on update cascade
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
    id char(36) primary key,
    name varchar(128) unique not null,
    district varchar(70),
    postal_code varchar(5),
    street_address varchar(128),
    club_image char(36),
    latitude float,
    longitude float,
    foreign key (club_image) references Image(id) on update cascade,
    check (
      postal_code regexp '^28[0-9]{3}$'
    )
  );

alter table Club drop index name;
create fulltext index name on Club(name);

-- Court table creation
create table if not exists
  Court (
    club_id char(36) not null,
    name varchar(70) not null,
    type enum ('indoor', 'outdoor', '3x3') not null,
    foreign key (club_id) references Club(id),
    primary key (club_id, name, type)
  );

-- Available hours table creation
create table if not exists
  AvailableHours (
    start_time time not null,
    end_time time not null,
    check (
      start_time < end_time
      and start_time >= '09:00:00' and start_time < '22:00:00'
      and end_time <= '22:00:00'
    )
  );

-- Weekday table creation
create table if not exists
  Weekday (
    select 'monday' as weekday union
    select 'tuesday' as weekday union
    select 'wednesday' as weekday union
    select 'thursday' as weekday union
    select 'friday' as weekday union
    select 'saturday' as weekday union
    select 'sunday' as weekday
  );

-- Create a procedure to populate the AvailableHours table
delimiter //

create procedure PopulateAvailableHours()
begin
  declare start_time time;
  declare end_time time;

  set start_time = '09:00:00';
  set end_time = '09:50:00';

  while start_time < '22:00:00' do
    insert into AvailableHours (start_time, end_time) values (start_time, end_time);

    set start_time = addtime(start_time, '01:00:00'); -- Incrementa la hora de inicio en 1 hora
    set end_time = addtime(end_time, '01:00:00');     -- Incrementa la hora de finalización en 1 hora
  end while;
end //

delimiter ;

call PopulateAvailableHours();

-- Slots table creation
create table if not exists
  Slots (
    id serial primary key,
    weekday enum ('monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday') not null,
    start_time time not null,
    end_time time not null
  );

insert into Slots (weekday, start_time, end_time)
  select
    w.weekday
    ,a.start_time
    ,a.end_time
  from
    Weekday w
  cross join
    AvailableHours a
  order by
    field(w.weekday, 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday')
    ,a.start_time
  ;

-- AvailableCourtSlots table creation
create table if not exists
  AvailableCourtSlots (
    club_id varchar(36) not null,
    court_name varchar(70) not null,
    court_type enum ('indoor', 'outdoor', '3x3') not null,
    slot_id bigint unsigned not null,
    state enum ('available', 'booked') not null default 'available',
    primary key (club_id, court_name, court_type, slot_id),
    foreign key (club_id, court_name, court_type) references Court(club_id, name, type),
    foreign key (slot_id) references Slots(id)
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
    id char(36) primary key,
    state enum ('pending', 'cancelled', 'in_progress', 'finished') not null default 'pending',
    type enum ('public', 'private') not null,
    club_id char(36) not null,
    court_name varchar(70) not null,
    court_type enum ('indoor', 'outdoor', '3x3') not null,
    team_a bigint unsigned not null,
    team_b bigint unsigned not null,
    result enum ('win_a', 'win_b', 'draw', 'not_played') not null default 'not_played',
    start_time time not null,
    foreign key (club_id, court_name, court_type) references Court(club_id, name, type),
    foreign key (team_a) references Team(id),
    foreign key (team_b) references Team(id)
  );

-- Chat table creation
create table if not exists
  Chat (
    id char(36) primary key,
    foreign key (id) references Game(id) on delete cascade
  );

-- Message table creation
create table if not exists
  ChatMessage (
    id char(36) primary key,
    chat_id char(36) not null,
    sender_email varchar(30) not null,
    message varchar(256) not null,
    sent_at timestamp not null,
    foreign key (chat_id) references Chat(id),
    foreign key (sender_email) references Player(email)
  );
