SELECT setval(
               'statement_id_seq',
               (SELECT MAX(id) FROM statement) + 1
       );
