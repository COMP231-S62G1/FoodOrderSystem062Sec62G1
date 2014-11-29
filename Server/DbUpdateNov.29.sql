--
-- change price type of menu
--
ALTER TABLE `menu` CHANGE `price` `price` DECIMAL(6,3) NULL DEFAULT '0.9900';

--
-- add role Type to UserTable
-- 0-admin ,1 -vender Manager, 2- user, 3- staff, 4-others
--

ALTER TABLE `user` ADD `roletype` INT(2) NOT NULL DEFAULT '2' ;