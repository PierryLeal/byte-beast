CREATE TABLE IF NOT EXISTS levels (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS card_types (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS forms (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS attributes (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS types (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS sets (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS cards (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    level_id INT REFERENCES levels(id),
    card_type_id INT REFERENCES card_types(id),
    form_id INT REFERENCES forms(id),
    attribute_id INT REFERENCES attributes(id),
    type_id INT REFERENCES types(id),
    dp INT,
    play_cost INT,
    digivolve_cost1 TEXT,
    digivolve_cost2 TEXT,
    effect TEXT,
    inherited_effect TEXT,
    security_effect TEXT,
    notes TEXT,
    set_id TEXT REFERENCES sets(id)
);

CREATE TABLE IF NOT EXISTS colors (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE card_colors (
    card_id TEXT REFERENCES cards(id) ON DELETE CASCADE,
    color_id INTEGER REFERENCES colors(id) ON DELETE CASCADE,
    PRIMARY KEY (card_id, color_id)
);

