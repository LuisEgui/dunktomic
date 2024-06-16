-- Author: Luis Egui

use dunktomic_db;

-- set the delimiter to a different string to allow creating the trigger
delimiter //

-- drop the existing trigger if it exists
drop trigger if exists check_team_size;

-- create the trigger to enforce the constraint of a maximum of 5 players per team
create trigger check_team_size
before insert on TeamPlayers
for each row
begin
  declare team_size int;

  -- count the number of players already in the team
  select count(*) into team_size 
  from TeamPlayers
  where team_id = new.team_id;
  
  -- if the count is 5 or more, raise an error
  if team_size >= 5 then
    signal sqlstate '45000' set message_text = 'team is full';
  end if;
end //

-- reset the delimiter back to the default
delimiter ;