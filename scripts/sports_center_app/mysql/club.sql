insert into Club (name, district, postal_code, street_address, latitude, longitude)
select
    name
    ,district
    ,postal_code
    ,street_address
    ,cast(latitude as float)
    ,cast(longitude as float)
from
    raw_club;
