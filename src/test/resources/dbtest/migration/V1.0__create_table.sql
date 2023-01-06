-- Table: public.user_acc

-- DROP TABLE IF EXISTS public.user_acc;

CREATE TABLE IF NOT EXISTS public.user_acc
(
    id serial NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    mobile character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    token_password character varying(255) COLLATE pg_catalog."default",
    user_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_acc_pkey PRIMARY KEY (id),
    CONSTRAINT uk_fdxu0rmmrxvjtbjvux8ywnd9p UNIQUE (email),
    CONSTRAINT uk_tqt23y7h3b1wpo8yehwgsfcll UNIQUE (user_name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_acc
    OWNER to postgres;


-------------------------------------------

-- Table: public.role

-- DROP TABLE IF EXISTS public.role;

CREATE TABLE IF NOT EXISTS public.role
(
    id serial NOT NULL,
    role_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.role
    OWNER to postgres;
-------------------------------------------

-------------------------------------------

-- Table: public.user_role

-- DROP TABLE IF EXISTS public.user_role;

CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fknhy3ka6lpcxq3mnh99t69lif3 FOREIGN KEY (user_id)
        REFERENCES public.user_acc (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_role
    OWNER to postgres;
-- --------------------------------------------------------------
-- Table: Type Effectiveness
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.Type_Effectiveness
(
    type_id serial NOT NULL,
    type_name text COLLATE pg_catalog.default NOT NULL,
    bug double precision NOT NULL,
    dark double precision NOT NULL,
    dragon double precision NOT NULL,
    electric double precision NOT NULL,
    fairy double precision NOT NULL,
    fighting double precision NOT NULL,
    fire double precision NOT NULL,
    flying double precision NOT NULL,
    ghost double precision NOT NULL,
    grass double precision NOT NULL,
    ground double precision NOT NULL,
    ice double precision NOT NULL,
    normal double precision NOT NULL,
    poison double precision NOT NULL,
    psychic double precision NOT NULL,
    rock double precision NOT NULL,
    steel double precision NOT NULL,
    water double precision NOT NULL,
    CONSTRAINT pk_type_effectiveness PRIMARY KEY (type_id)
        INCLUDE(type_id),
    CONSTRAINT unique_name_type UNIQUE (type_name)
        INCLUDE(type_name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Type_Effectiveness
    OWNER to postgres;

-- --------------------------------------------------------------
-- Table: Pokemon
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.Pokemon
(
    poke_id integer NOT NULL,
    pokemon_name text COLLATE pg_catalog.default NOT NULL,
    base_stamina integer,
    base_defense integer,
    base_attack integer,
    first_type integer,
    second_type integer,
    CONSTRAINT pk_poke_id PRIMARY KEY (poke_id)
            INCLUDE(poke_id),
        CONSTRAINT unique_poke_id UNIQUE (poke_id)
            INCLUDE(poke_id),
        CONSTRAINT unique_poke_name UNIQUE (pokemon_name)
            INCLUDE(pokemon_name),
    CONSTRAINT fk_first_type FOREIGN KEY (first_type)
      REFERENCES public.Type_Effectiveness (type_id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
    CONSTRAINT fk_second_type FOREIGN KEY (second_type)
      REFERENCES public.Type_Effectiveness (type_id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Pokemon
    OWNER to postgres;

-- --------------------------------------------------------------
-- Table: Fast Move
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.FastMove
(
    move_id integer NOT NULL,
    move_name text COLLATE pg_catalog.default NOT NULL,
    power integer NOT NULL,
    stamina_loss_scaler double precision NOT NULL,
    type integer,
    CONSTRAINT pk_fast_move PRIMARY KEY (move_id)
        INCLUDE(move_id),
    CONSTRAINT fk_type FOREIGN KEY (type)
          REFERENCES public.Type_Effectiveness (type_id) MATCH SIMPLE
          ON UPDATE NO ACTION
          ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.FastMove
    OWNER to postgres;

-- --------------------------------------------------------------
-- Table: Charged Move
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.ChargedMove
(
    move_id integer NOT NULL,
    move_name text COLLATE pg_catalog.default NOT NULL,
    power integer NOT NULL,
    stamina_loss_scaler double precision NOT NULL,
    type integer,
    critical_chance double precision NOT NULL,
    CONSTRAINT pk_ch_move PRIMARY KEY (move_id)
        INCLUDE(move_id),
    CONSTRAINT fk_type FOREIGN KEY (type)
    REFERENCES public.Type_Effectiveness (type_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.ChargedMove
    OWNER to postgres;

-- --------------------------------------------------------------
-- Table: Catch
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.Catch
(
    id_caught_pokemon serial NOT NULL,
    user_id integer NOT NULL,
    poke_id integer NOT NULL,
    caught boolean,
    attack integer,
    defense integer,
    stamina integer,
    speed integer,
    fast_move integer,
    charged_move integer,
    CONSTRAINT pk_catch PRIMARY KEY (id_caught_pokemon)
        INCLUDE(id_caught_pokemon),
    CONSTRAINT fk_charged_move FOREIGN KEY (charged_move)
        REFERENCES public.ChargedMove (move_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_fast_move FOREIGN KEY (fast_move)
        REFERENCES public.FastMove (move_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_id_user FOREIGN KEY (user_id)
        REFERENCES public.user_acc (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_id_pogo FOREIGN KEY (poke_id)
        REFERENCES public.Pokemon (poke_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Catch
    OWNER to postgres;


