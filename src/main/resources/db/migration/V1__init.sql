--CREATE DATABASE UsersAndRewards;
--USE UsersAndRewards;

DROP TABLE IF EXISTS rewardees_list;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS rewards;

----GO

CREATE TABLE Users (
Id serial PRIMARY KEY,
FirstName varchar(100) NOT NULL,
LastName varchar(100) NOT NULL,
Birthday date NOT NULL,
CONSTRAINT Users_birthday_borders CHECK(120 >= date_part('year', age(Birthday)) AND 0 <= date_part('year', age(Birthday))),
CONSTRAINT Users_unique UNIQUE(FirstName, LastName, Birthday)
);


-- также check в идеале вынести в constraint

CREATE TABLE Rewards (
Title varchar(100) Primary KEY,
Description varchar(500)
);

CREATE TABLE Rewardings (
UserId serial,
RewardTitle varchar(100),
RewardDate date,
PRIMARY KEY (UserId, RewardTitle, RewardDate),
FOREIGN KEY (UserId) REFERENCES Users(Id),
FOREIGN KEY (RewardTitle) REFERENCES Rewards(Title)
);

----------------------------------------------INSERT----------------------------------------------------------------
--GO

CREATE PROCEDURE user_insert (first_name varchar(100), last_name varchar(100), birth_date date)
LANGUAGE plpgsql 
AS
$$
BEGIN
	IF (first_name IS NULL OR first_name = '') THEN 
		RAISE EXCEPTION 'FIRST NAME CAN`T BE NULL OR EMPTY' USING ERRCODE = '50000';
	END IF;
		
	IF (last_name IS NULL OR last_name = '') THEN 
		RAISE EXCEPTION 'LAST NAME CAN`T BE NULL OR EMPTY' USING ERRCODE = '50001';
	END IF;
	
	IF NOT (120 >= date_part('year', age(birth_date)) AND 0 <= date_part('year', age(birth_date))) THEN  
		RAISE EXCEPTION 'INVALID BIRTHDAY VALUE: %', birth_date USING ERRCODE = '50002';
	END IF;

	IF EXISTS (SELECT * FROM Users WHERE FirstName = first_name AND LastName = last_name AND Birthday = birth_date) THEN
    		RAISE EXCEPTION 'USER WITH SAME PARAMS HAS ALREADY EXISTS' USING ERRCODE = '50003';
    END IF;
	
	INSERT INTO Users(FirstName, LastName, Birthday) VALUES(first_name, last_name, birth_date);
END
$$;

--GO

CREATE PROCEDURE reward_insert (title varchar(100), description varchar(500))
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF (title IS NULL OR title = '') THEN 
		RAISE EXCEPTION 'TITLE CAN`T BE NULL OR EMPTY' USING ERRCODE = '50010';
	END IF;
	
	IF EXISTS(SELECT * FROM Rewards WHERE Title = title) THEN 
		RAISE EXCEPTION 'THE REWARD WITH THIS TITLE HAS ALREADY EXIST' USING ERRCODE = '50011';
	END IF;
	
	INSERT INTO Rewards VALUES(title, description);
END
$$;
--GO

CREATE PROCEDURE rewarding_insert (user_id bigint, reward_title varchar(100), reward_date date)
LANGUAGE plpgsql 
AS
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN  
		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50020';
	END IF;
	
	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = reward_title) THEN 
		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50021';
	END IF;
		
	IF reward_title IS NULL THEN 
		RAISE EXCEPTION 'A REWARD TITLE HAS CANT`T BE NULL' USING ERRCODE = '50022';
	END IF;
		
	INSERT INTO Rewardings VALUES(user_id, reward_title, reward_date);
END
$$;

--GO

--DROP PROCEDURE IF EXISTS user_insert;
--DROP PROCEDURE IF EXISTS reward_insert;
--DROP PROCEDURE IF EXISTS rewardee_insert;

----------------------------------------------UPDATE-----------------------------------------------------------------
--GO

CREATE PROCEDURE user_update (user_id bigint, new_first_name varchar(100), new_last_name varchar(100), new_birth_date date)
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN 
		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50100';
	END IF;
	
	IF new_first_name IS NULL OR new_first_name = '' THEN
		RAISE EXCEPTION 'A NEW FIRST NAME CAN`T BE NULL OR EMPTY' USING ERRCODE = '50101';
	END IF;

	IF new_last_name IS NULL OR new_last_name = '' THEN
    		RAISE EXCEPTION 'A NEW LAST NAME CAN`T BE NULL OR EMPTY' USING ERRCODE = '50102';
    END IF;

    IF new_birth_date IS NULL THEN
    		RAISE EXCEPTION 'A NEW BIRTHDAY CAN`T BE NULL' USING ERRCODE = '50104';
    END IF;
		
	UPDATE Users SET FirstName = new_first_name, LastName = new_last_name, Birthday = new_birth_date WHERE Id = user_id;
END
$$;

--GO

--CREATE PROCEDURE user_last_name_update (user_id bigint, new_last_name varchar(100))
--LANGUAGE plpgsql
--AS
--$$
--BEGIN
--	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN
--		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50103';
--	END IF;
--
--
--
--	UPDATE Users SET LastName = new_last_name WHERE Id = user_id;
--END
--$$;
--
----GO
--
--CREATE PROCEDURE user_birthday_update (user_id bigint, new_birth_date date)
--LANGUAGE plpgsql
--AS
--$$
--BEGIN
--	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN
--		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50105';
--	END IF;
--
--
--
--	UPDATE Users SET Birthday = new_birth_date WHERE Id = user_id;
--END
--$$;

--GO

-----------------------------------------

--GO

CREATE PROCEDURE reward_update (title varchar(100), new_title varchar(100), new_description varchar(500))
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = title) THEN 
		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50110';
	END IF;
	
	IF new_title IS NULL THEN 
		RAISE EXCEPTION 'A NEW TITLE CAN`T BE NULL' USING ERRCODE = '50111';
	END IF;
		
	UPDATE Rewards SET Title = new_title, Description = new_description WHERE Title = title;
END
$$;

--GO

--CREATE PROCEDURE reward_description_update (title varchar(100), new_description varchar(500))
--LANGUAGE plpgsql
--AS
--$$
--BEGIN
--	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = title) THEN
--		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50112';
--	END IF;
--
--	UPDATE Rewards SET Description = new_description WHERE Title = title;
--END
--$$;

--GO
-----------------------------------------
--GO

CREATE PROCEDURE rewarding_update (user_id bigint, reward_title varchar(100), reward_date date, new_user_id bigint,
new_reward_title varchar(100), new_reward_date date)
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN 
		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50120';
	END IF;
	
	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = reward_title) THEN 
		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50121';
	END IF;
	
	IF NOT EXISTS(SELECT * FROM Rewardings WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date) THEN
		RAISE EXCEPTION 'THIS REWARDEE DOESN`T EXIST' USING ERRCODE = '50122';
	END IF;
	
	IF NOT EXISTS(SELECT * FROM Users WHERE Id = new_user_id) THEN 
		RAISE EXCEPTION 'THIS NEW REWARDEE USER DOESN`T EXIST' USING ERRCODE = '50123';
	END IF;

	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = new_reward_title) THEN
    		RAISE EXCEPTION 'THIS NEW REWARD DOESN`T EXIST' USING ERRCODE = '50124';
    END IF;

    IF new_reward_date IS NULL THEN
    		RAISE EXCEPTION 'A REWARD DATE CAN`T BE NULL' USING ERRCODE = '50125';
    END IF;
		
	UPDATE Rewardings SET UserId = new_user_id, RewardTitle = new_reward_title, RewardDate = new_reward_date
	 WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date;
END
$$;

--GO

--CREATE PROCEDURE rewarding_reward_update (user_id bigint, reward_title varchar(100), reward_date date, new_reward_title varchar(100))
--LANGUAGE plpgsql
--AS
--$$
--BEGIN
--	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN
--		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50124';
--	END IF;
--
--	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = reward_title) THEN
--		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50125';
--	END IF;
--
--	IF NOT EXISTS(SELECT * FROM Rewardings WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date) THEN
--		RAISE EXCEPTION 'THIS REWARDEE DOESN`T EXIST' USING ERRCODE = '50126';
--	END IF;
--
--	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = new_reward_title) THEN
--		RAISE EXCEPTION 'THIS NEW REWARD DOESN`T EXIST' USING ERRCODE = '50127';
--	END IF;
--
--	UPDATE Rewardings SET RewardTitle = new_reward_title WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date;
--END
--$$;
--
----GO
--
--CREATE PROCEDURE rewarding_date_update (user_id bigint, reward_title varchar(100), reward_date date, new_reward_date date)
--LANGUAGE plpgsql
--AS
--$$
--BEGIN
--	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN
--		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50128';
--	END IF;
--
--	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = reward_title) THEN
--		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50129';
--	END IF;
--
--	IF NOT EXISTS(SELECT * FROM Rewardings WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date) THEN
--		RAISE EXCEPTION 'THIS REWARDEE DOESN`T EXIST' USING ERRCODE = '50130';
--	END IF;
--
--	IF new_reward_date IS NULL THEN
--		RAISE EXCEPTION 'A REWARD DATE CAN`T BE NULL' USING ERRCODE = '50131';
--	END IF;
--
--	UPDATE Rewardings SET RewardDate = new_reward_date WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date;
--END
--$$;

--GO

----------------------------------------------DELETE-----------------------------------------------------------------

--GO

CREATE PROCEDURE user_delete (user_id bigint)
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN  
		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50200';
	END IF;
		
	IF NOT EXISTS(SELECT * FROM Rewardings WHERE UserId = user_id) THEN
		RAISE EXCEPTION 'THIS USER HAS ANY Rewardings' USING ERRCODE = '50201';
	END IF;
		
	DELETE FROM Users WHERE Id = user_id;
END
$$;

--GO

CREATE PROCEDURE reward_delete (title varchar(100)) 
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = title) THEN  
		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50210';
	END IF;
		
	IF NOT EXISTS(SELECT * FROM Rewardings WHERE RewardTitle = title) THEN
		RAISE EXCEPTION 'ANY USERS HAS THIS REWARD' USING ERRCODE = '50211';
	END IF;
		
	DELETE FROM Rewards WHERE Title = title;
END
$$;

--GO

CREATE PROCEDURE rewarding_delete (user_id bigint, reward_title varchar(100), reward_date date)
LANGUAGE plpgsql
AS 
$$
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE Id = user_id) THEN  
		RAISE EXCEPTION 'THIS USER DOESN`T EXIST' USING ERRCODE = '50220';
	END IF;
	
	IF NOT EXISTS(SELECT * FROM Rewards WHERE Title = reward_title) THEN  
		RAISE EXCEPTION 'THIS REWARD DOESN`T EXIST' USING ERRCODE = '50221';
	END IF;
		
	IF NOT EXISTS(SELECT * FROM Rewardings WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date) THEN
		RAISE EXCEPTION 'THIS REWARDEE DOESN`T EXIST' USING ERRCODE = '50222';
	END IF;
		
	DELETE FROM Rewardings WHERE UserId = user_id AND RewardTitle = reward_title AND RewardDate = reward_date;
END
$$;

--GO

-----------------------------------------------------------------------------------------------------------------------