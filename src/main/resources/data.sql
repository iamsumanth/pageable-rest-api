INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (1, 'ABC', 'XYZ', 200);
INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (2, 'XYZ', 'MNP', 100);
INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (3, 'XYZ', 'MNP', 300);
INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (4, 'ABC', 'MNP', 1000);
INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (5, 'XYZ', 'ABC', 50);
INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (6, 'MNP', 'PQRS', 200);
INSERT INTO PARENT_TRANSACTION (id, sender, receiver, total_Amount) VALUES (7, 'ABC', 'PQRS', 200);


INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (1, 1, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (2, 1, 50);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (3, 1, 40);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (4, 2, 100);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (5, 3, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (6, 3, 150);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (7, 3, 100);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (8, 4, 300);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (9, 4, 300);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (10, 4, 300);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (11, 5, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (12, 5, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (13, 5, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (14, 5, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (15, 5, 10);
INSERT INTO CHILD_TRANSACTION (id, parent_transaction_id, paid_amount) VALUES (16, 6, 125);


