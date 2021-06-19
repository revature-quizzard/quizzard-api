INSERT INTO roles (name) VALUES ('BASIC_USER');
INSERT INTO subjects (name) VALUES ('test');

INSERT INTO users (first_name, last_name, email) VALUES ('Alice', 'Anderson', 'aanderson@mail.com');
INSERT INTO users (first_name, last_name, email) VALUES ('Billy', 'Bob', 'bbob@mail.com');
INSERT INTO users (first_name, last_name, email) VALUES ('Charlie', 'Cain', 'ccain@mail.com');
INSERT INTO users (first_name, last_name, email) VALUES ('Daniel', 'Davids', 'ddavids@mail.com');
INSERT INTO users (first_name, last_name, email) VALUES ('Eric', 'Erikson', 'eerikson@mail.com');

INSERT INTO accounts (user_id, username, password, points) VALUES (1, 'aanderson', 'password', 0);
INSERT INTO accounts (user_id, username, password, points) VALUES (2, 'bbob', 'password', 0);
INSERT INTO accounts (user_id, username, password, points) VALUES (3, 'ccain', 'password', 0);
INSERT INTO accounts (user_id, username, password, points) VALUES (4, 'ddavids', 'password', 0);
INSERT INTO accounts (user_id, username, password, points) VALUES (5, 'eerikson', 'password', 0);

INSERT INTO accounts_roles (account_id, role_id) VALUES (1, 1);
INSERT INTO accounts_roles (account_id, role_id) VALUES (2, 1);
INSERT INTO accounts_roles (account_id, role_id) VALUES (3, 1);
INSERT INTO accounts_roles (account_id, role_id) VALUES (4, 1);
INSERT INTO accounts_roles (account_id, role_id) VALUES (5, 1);

INSERT INTO cards (question, answer, reviewable, public, subject_id) VALUES ('question-1', 'ansawer-1', true, true, 1);
INSERT INTO cards (question, answer, reviewable, public, subject_id) VALUES ('question-2', 'ansawer-2', true, true, 1);
INSERT INTO cards (question, answer, reviewable, public, subject_id) VALUES ('question-3', 'ansawer-3', true, true, 1);
INSERT INTO cards (question, answer, reviewable, public, subject_id) VALUES ('question-4', 'ansawer-4', true, true, 1);
INSERT INTO cards (question, answer, reviewable, public, subject_id) VALUES ('question-5', 'ansawer-5', true, true, 1);