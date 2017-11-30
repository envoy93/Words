CREATE TABLE `categories` (
	`id`	INTEGER NOT NULL UNIQUE,
	`parent_id`	INTEGER NOT NULL,
	`title`	TEXT NOT NULL,
	`lvl`	INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);

CREATE TABLE `words` (
	`id`	INTEGER NOT NULL UNIQUE,
	`category_id`	INTEGER NOT NULL,
	`position`	INTEGER NOT NULL,
	`is_base`	INTEGER NOT NULL,
	`title`	TEXT NOT NULL,
	`translate`	TEXT NOT NULL,
	`transcription`	TEXT NOT NULL,
	`example`	TEXT NOT NULL,
	PRIMARY KEY(`id`)
);