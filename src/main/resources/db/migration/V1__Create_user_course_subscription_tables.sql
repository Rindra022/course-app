CREATE TABLE course
(
    id         UUID         NOT NULL,
    title      VARCHAR(255) NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id         UUID         NOT NULL,
    first_name VARCHAR(200),
    last_name  VARCHAR(200) NOT NULL,
    user_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE subscription
(
    id         UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255),
    course_id  UUID,
    user_id    UUID,
    CONSTRAINT pk_subscription PRIMARY KEY (id)
);

ALTER TABLE course ADD CONSTRAINT uc_course_title UNIQUE (title);
ALTER TABLE "user" ADD CONSTRAINT uc_user_email UNIQUE (email);
ALTER TABLE "user" ADD CONSTRAINT uc_user_username UNIQUE (user_name);

ALTER TABLE subscription
    ADD CONSTRAINT fk_subscription_on_course FOREIGN KEY (course_id) REFERENCES course (id);
ALTER TABLE subscription
    ADD CONSTRAINT fk_subscription_on_user FOREIGN KEY (user_id) REFERENCES "user" (id);