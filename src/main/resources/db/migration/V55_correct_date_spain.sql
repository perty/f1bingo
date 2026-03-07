update race_weekend rw
set startdate = '20260911T113000Z',
    enddate   = '20260913T150000Z',
    track = 'Circuito de Madring'
where rw.name = 'Spain GP'
  and rw.country = 'Spain'
  and rw.track = 'Circuit de Barcelona-Catalunya';
