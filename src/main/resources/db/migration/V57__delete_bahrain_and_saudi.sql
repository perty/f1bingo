delete
from race_weekend
where name in ('Bahrain GP', 'Saudi Arabian GP');

delete
from session_schedule
where summary like '%BAHRAIN%2026%';

delete
from session_schedule
where summary like '%SAUDI ARABIAN%2026%';
