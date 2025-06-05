-- hibernate parses commands line by line
-- insert line break inside sql to greet one kilometer long stack trace!
-- (learned about it the hard way)

INSERT INTO membership_type (price, uuid, type, currency)
VALUES (21.37, '9d4e894f-30e4-488e-9689-ad0fa32a69d1', 'Standard', 'PLN');
INSERT INTO membership_type (price, uuid, type, currency)
VALUES (69.69, '38dc2c0f-566f-48c7-a147-95fd0f0632ee', 'Premium', 'PLN');

INSERT INTO membership (valid_until, membership_type_uuid, uuid)
VALUES ('2025-04-18 13:44:45.621014', '9d4e894f-30e4-488e-9689-ad0fa32a69d1', '06d25bae-8d09-4170-b566-fd17f2ee6a23');
INSERT INTO membership (valid_until, membership_type_uuid, uuid)
VALUES ('2025-04-18 13:45:04.293944', '38dc2c0f-566f-48c7-a147-95fd0f0632ee', 'bbac838e-4a2f-4ef8-b562-075d64d8493a');
INSERT INTO membership (valid_until, membership_type_uuid, uuid)
VALUES ('2025-04-18 13:45:04.393944', '38dc2c0f-566f-48c7-a147-95fd0f0632ee', 'ddd5f2a7-157e-4bf1-b11c-fa46e0d6bad1');

INSERT INTO payment_status (uuid, status)
VALUES ('dbc10aab-cce9-4813-b8b1-cb0ff763b0b1', 'Success');
INSERT INTO payment_status (uuid, status)
VALUES ('6cc7d83c-cfb3-46d5-a6cb-d16b5f1ef246', 'Pending');
INSERT INTO payment_status (uuid, status)
VALUES ('0ebfdd32-7faf-48f1-8921-d59155acb756', 'Error');

INSERT INTO country (uuid, country_name)
VALUES ('352ed7f1-8bb1-4baa-9ca7-88995ec58d8a', 'Poland');
INSERT INTO country (uuid, country_name)
VALUES ('15529918-2212-4451-ae0d-b9e0f2301bbd', 'USA');
INSERT INTO country (uuid, country_name)
VALUES ('55fbb9f2-22ae-4195-a390-92a9d740d7cb', 'Germany');

INSERT INTO card (date_of_birth, country_uuid, uuid, card_number, cvv, name_on_card, postal_code)
VALUES ('2025-04-23 19:34:43.34018', '352ed7f1-8bb1-4baa-9ca7-88995ec58d8a', '5bd05494-155e-4bd1-b14c-61421d0caaae',
        '1234123412341234', '069', 'John Pork', '21-000');
INSERT INTO card (date_of_birth, country_uuid, uuid, card_number, cvv, name_on_card, postal_code)
VALUES ('2025-04-23 19:35:20.328561', '15529918-2212-4451-ae0d-b9e0f2301bbd', 'ed36b15c-89d7-43cd-aa1b-354b2ec9067d',
        '2137213721372137', '213', 'Bob Beef', '21-370');

INSERT INTO payment (card_uuid, membership_uuid, status_uuid, uuid)
VALUES ('5bd05494-155e-4bd1-b14c-61421d0caaae', '06d25bae-8d09-4170-b566-fd17f2ee6a23',
        'dbc10aab-cce9-4813-b8b1-cb0ff763b0b1', 'a9e17e91-8792-42dd-badd-f2639e09ebc2');
INSERT INTO payment (card_uuid, membership_uuid, status_uuid, uuid)
VALUES ('ed36b15c-89d7-43cd-aa1b-354b2ec9067d', 'bbac838e-4a2f-4ef8-b562-075d64d8493a',
        '0ebfdd32-7faf-48f1-8921-d59155acb756', 'f36c3d20-00c6-4227-af89-ea8c04442ea5');

INSERT INTO user_role (uuid, role_name)
VALUES ('0608911b-f8fa-4a83-8f63-f314030a36ed', 'Client');
INSERT INTO user_role (uuid, role_name)
VALUES ('645195cd-6194-4d9c-a361-4882e146ccfc', 'Employee');
INSERT INTO user_role (uuid, role_name)
VALUES ('c5237de2-09ad-4106-8853-6ae0c04cdc9b', 'Coach');
INSERT INTO user_role (uuid, role_name)
VALUES ('f247de64-c7dd-4162-81d5-6496c224ac33', 'Manager');

INSERT INTO users (card_uuid, membership_uuid, date_of_birth, email, first_name, image_url, last_name,
                   phone_number, uuid)
VALUES ('5bd05494-155e-4bd1-b14c-61421d0caaae', '06d25bae-8d09-4170-b566-fd17f2ee6a23',
        '2025-04-23 19:40:32.598028', 'email1@email.com', 'John',
        'https://picsum.photos/500/600', 'Pork', '213721372', '65f40335-135a-47ec-ad7d-72278c4be65c');
INSERT INTO users (card_uuid, membership_uuid, date_of_birth, email, first_name, image_url, last_name,
                   phone_number, uuid)
VALUES ('ed36b15c-89d7-43cd-aa1b-354b2ec9067d', 'bbac838e-4a2f-4ef8-b562-075d64d8493a',
        '2025-04-23 19:41:45.774591', 'email2@emial.com', 'Bob',
        'https://picsum.photos/500', 'Beef', '123123123', '4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56');
INSERT INTO users (card_uuid, membership_uuid, date_of_birth, email, first_name, image_url, last_name,
                   phone_number, uuid)
VALUES (NULL, NULL, '2025-04-23 19:42:23.424396', 'email3@email.com', 'Tim',
        'https://picsum.photos/500/700', 'Cheese', '420420420', '1953df3d-9015-4c2e-b314-e4a5ef771e62');
INSERT INTO users (card_uuid, membership_uuid, date_of_birth, email, first_name, image_url, last_name,
                   phone_number, uuid)
VALUES (NULL, NULL, '2025-04-23 19:43:07.35223', 'email4@example.com', 'Steven',
        'https://picsum.photos/400/600', 'Steak', '321321321', '55ad45d7-fb36-4090-81cb-dc062cfb619d');
INSERT INTO users (card_uuid, membership_uuid, date_of_birth, email, first_name, image_url, last_name,
                   phone_number, uuid)
VALUES (NULL, NULL, '2025-04-23 19:46:02.051935', 'email5@exmaple.com',
        'Tralalelo', 'https://tr.rbxcdn.com/180DAY-a8273bb70d4ded6359cd241f40ef543d/420/420/Hat/Webp/noFilter',
        'Tralalala', '111222333', 'f18d2783-77f6-4f3d-a58e-f72bb31600c6');

INSERT INTO hall_type (uuid, name)
VALUES ('cc5f2a2a-1248-4e4f-aed9-5aab7c3f577a', 'Yoga');
INSERT INTO hall_type (uuid, name)
VALUES ('2a2fa2ba-2381-4cb6-86f4-282bdbf18e81', 'Powerlifting');

INSERT INTO hall (hall_type_uuid, uuid, hall_description, hall_name)
VALUES ('2a2fa2ba-2381-4cb6-86f4-282bdbf18e81', 'ce5f8d01-6fa8-4226-97fc-51d3e9cd91e5', 'First hall for powerlifting.',
        '1A');
INSERT INTO hall (hall_type_uuid, uuid, hall_description, hall_name)
VALUES ('2a2fa2ba-2381-4cb6-86f4-282bdbf18e81', '9256b9cb-40a6-4d17-b987-180e4e6596e0', 'Second hall for powerlifting.',
        '2A');
INSERT INTO hall (hall_type_uuid, uuid, hall_description, hall_name)
VALUES ('cc5f2a2a-1248-4e4f-aed9-5aab7c3f577a', 'fc704e91-8e33-450f-9413-14ad12a444f9', 'Hall for yoga.', '3B');

INSERT INTO maintenance_task (planned_end_date, planned_start_date, maintainer_uuid, maintenance_hall_uuid, uuid,
                              description)
VALUES ('2025-04-23 20:07:43.095829', '2025-04-23 20:07:43.095829', '1953df3d-9015-4c2e-b314-e4a5ef771e62',
        'fc704e91-8e33-450f-9413-14ad12a444f9', '596706d3-6095-44b8-9f0d-8fd30ab9b847', 'Annual maintenance.');

INSERT INTO workout_session (date, coach_uuid, hall_uuid, uuid, description, title)
VALUES ('2025-06-01', '55ad45d7-fb36-4090-81cb-dc062cfb619d', 'ce5f8d01-6fa8-4226-97fc-51d3e9cd91e5',
        '8d64dca2-87dc-479f-bcb5-9f91b16d870c',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sapien urna, molestie in consequat lacinia, dictum blandit ex.',
        'Powerlifting session');
INSERT INTO workout_session (date, coach_uuid, hall_uuid, uuid, description, title)
VALUES ('2025-06-03', '55ad45d7-fb36-4090-81cb-dc062cfb619d', '9256b9cb-40a6-4d17-b987-180e4e6596e0',
        '9a9c531a-e4a7-4c6b-b4f6-eea79009ddae',
        'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Integer sit amet nisl scelerisque, auctor dui quis, cursus massa.',
        'Second powerlifting session');

INSERT INTO workout_session_attendants (attendants_uuid, workout_session_uuid)
VALUES ('65f40335-135a-47ec-ad7d-72278c4be65c', '8d64dca2-87dc-479f-bcb5-9f91b16d870c');
INSERT INTO workout_session_attendants (attendants_uuid, workout_session_uuid)
VALUES ('4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56', '8d64dca2-87dc-479f-bcb5-9f91b16d870c');
INSERT INTO workout_session_attendants (attendants_uuid, workout_session_uuid)
VALUES ('65f40335-135a-47ec-ad7d-72278c4be65c', '9a9c531a-e4a7-4c6b-b4f6-eea79009ddae');
INSERT INTO workout_session_attendants (attendants_uuid, workout_session_uuid)
VALUES ('4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56', '9a9c531a-e4a7-4c6b-b4f6-eea79009ddae');

INSERT INTO exercise (uuid, name)
VALUES ('ee371adf-3ac7-4a0a-a6c2-254990c1c80f', 'Bench press');
INSERT INTO exercise (uuid, name)
VALUES ('c5c07bb9-424a-4b9c-866d-6240ae0732d5', 'Push up');
INSERT INTO exercise (uuid, name)
VALUES ('45053820-d688-46ec-b37d-899d26792a30', 'Squat');

INSERT INTO target_muscle (uuid, name)
VALUES ('fcdc736c-556e-48ef-a533-9f93f3b61538', 'Chest');
INSERT INTO target_muscle (uuid, name)
VALUES ('a62e1cb4-ba82-43bd-8af8-07f339310640', 'Bicep');
INSERT INTO target_muscle (uuid, name)
VALUES ('f9b481c4-31ce-43c0-93a3-0b57fe3e1e8a', 'Quads');
INSERT INTO target_muscle (uuid, name)
VALUES ('283ef0f5-0bc9-4201-a6ca-8496ae2bef06', 'Glutes');

INSERT INTO exercise_target_muscle (exercise_uuid, target_muscle_uuid)
VALUES ('ee371adf-3ac7-4a0a-a6c2-254990c1c80f', 'fcdc736c-556e-48ef-a533-9f93f3b61538');
INSERT INTO exercise_target_muscle (exercise_uuid, target_muscle_uuid)
VALUES ('c5c07bb9-424a-4b9c-866d-6240ae0732d5', 'fcdc736c-556e-48ef-a533-9f93f3b61538');
INSERT INTO exercise_target_muscle (exercise_uuid, target_muscle_uuid)
VALUES ('c5c07bb9-424a-4b9c-866d-6240ae0732d5', 'a62e1cb4-ba82-43bd-8af8-07f339310640');
INSERT INTO exercise_target_muscle (exercise_uuid, target_muscle_uuid)
VALUES ('45053820-d688-46ec-b37d-899d26792a30', 'f9b481c4-31ce-43c0-93a3-0b57fe3e1e8a');
INSERT INTO exercise_target_muscle (exercise_uuid, target_muscle_uuid)
VALUES ('45053820-d688-46ec-b37d-899d26792a30', '283ef0f5-0bc9-4201-a6ca-8496ae2bef06');

INSERT INTO workout_session_exercise (uuid, exercise_uuid, workout_session_uuid, reps, weight, exercise_order)
VALUES ('0713a057-a183-4f13-be9d-9fe3985db31e', 'ee371adf-3ac7-4a0a-a6c2-254990c1c80f',
        '8d64dca2-87dc-479f-bcb5-9f91b16d870c', 3, 30, 1);
INSERT INTO workout_session_exercise (uuid, exercise_uuid, workout_session_uuid, reps, weight, exercise_order)
VALUES ('70eb7aea-c056-42ab-bd08-f8e78caf4ea7', 'c5c07bb9-424a-4b9c-866d-6240ae0732d5',
        '8d64dca2-87dc-479f-bcb5-9f91b16d870c', 3, 37.5, 2);
INSERT INTO workout_session_exercise (uuid, exercise_uuid, workout_session_uuid, reps, weight, exercise_order)
VALUES ('4a287342-c608-446b-a8d3-575531edc12e', 'ee371adf-3ac7-4a0a-a6c2-254990c1c80f',
        '9a9c531a-e4a7-4c6b-b4f6-eea79009ddae', 3, 50, 1);
INSERT INTO workout_session_exercise (uuid, exercise_uuid, workout_session_uuid, reps, weight, exercise_order)
VALUES ('5cf75834-ffff-491a-aaa7-309e88ce2c7c', 'c5c07bb9-424a-4b9c-866d-6240ae0732d5',
        '9a9c531a-e4a7-4c6b-b4f6-eea79009ddae', 3, 60, 2);
INSERT INTO workout_session_exercise (uuid, exercise_uuid, workout_session_uuid, reps, weight, exercise_order)
VALUES ('747a511e-ec5c-431e-8135-1f89b629b83c', '45053820-d688-46ec-b37d-899d26792a30',
        '9a9c531a-e4a7-4c6b-b4f6-eea79009ddae', 3, 70, 3);

INSERT INTO chat (uuid)
VALUES ('a1ed2efc-59ac-4467-883b-89ee2ee6846d');
INSERT INTO chat (uuid)
VALUES ('bb895e43-d1e9-4bd6-aaf0-a5688c5d98d0');

INSERT INTO chat_participant (last_read_date_time, chat_uuid, user_uuid, uuid)
VALUES ('2025-04-23 20:51:01.490984', 'a1ed2efc-59ac-4467-883b-89ee2ee6846d', '65f40335-135a-47ec-ad7d-72278c4be65c',
        'df350066-f256-41cb-aba8-5d2d1fbc92b7');
INSERT INTO chat_participant (last_read_date_time, chat_uuid, user_uuid, uuid)
VALUES ('2025-04-23 20:51:41.237283', 'a1ed2efc-59ac-4467-883b-89ee2ee6846d', '4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56',
        'a8321c29-cf44-46ff-b402-f41f4ff8e9c9');
INSERT INTO chat_participant (last_read_date_time, chat_uuid, user_uuid, uuid)
VALUES (NULL, 'bb895e43-d1e9-4bd6-aaf0-a5688c5d98d0', '65f40335-135a-47ec-ad7d-72278c4be65c',
        'a45b248a-cb31-421b-b458-3bce9ec3e528');
INSERT INTO chat_participant (last_read_date_time, chat_uuid, user_uuid, uuid)
VALUES (NULL, 'bb895e43-d1e9-4bd6-aaf0-a5688c5d98d0', '1953df3d-9015-4c2e-b314-e4a5ef771e62',
        'c9ae3a0a-12a1-4e59-9d71-8be7458858d3');

INSERT INTO message (date_time, chat_uuid, sender_uuid, uuid, content)
VALUES ('2025-04-23 20:56:05.913229', 'a1ed2efc-59ac-4467-883b-89ee2ee6846d', '65f40335-135a-47ec-ad7d-72278c4be65c',
        '402a05a9-d5f6-49a8-bbf2-91daa1be013f', 'siema');
INSERT INTO message (date_time, chat_uuid, sender_uuid, uuid, content)
VALUES ('2025-04-23 20:56:27.37503', 'a1ed2efc-59ac-4467-883b-89ee2ee6846d', '4a3e70f9-bd65-45c4-a47a-eaae0a0d3d56',
        'df53ef89-efda-4994-8a5d-e22f2232a23e', 'elo');
INSERT INTO message (date_time, chat_uuid, sender_uuid, uuid, content)
VALUES ('2025-04-23 20:57:05.166963', 'bb895e43-d1e9-4bd6-aaf0-a5688c5d98d0', '1953df3d-9015-4c2e-b314-e4a5ef771e62',
        '2683f59e-3b3c-4eaf-8a8d-6a7d0d8e9b18', 'witam');
INSERT INTO message (date_time, chat_uuid, sender_uuid, uuid, content)
VALUES ('2025-04-23 20:57:23.357584', 'bb895e43-d1e9-4bd6-aaf0-a5688c5d98d0', '65f40335-135a-47ec-ad7d-72278c4be65c',
        'bb895e43-d1e9-4bd6-aaf0-a5688c5d98d0', 'dzień dobry');
