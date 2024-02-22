SELECT setval(
               'fan_id_seq',
               (SELECT MAX(id) FROM fan) + 1
       );
