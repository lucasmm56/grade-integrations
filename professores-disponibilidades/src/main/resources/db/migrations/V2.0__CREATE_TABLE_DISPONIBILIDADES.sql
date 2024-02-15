CREATE TABLE IF NOT EXISTS disponibilidades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dia_semana VARCHAR(255),
    horario VARCHAR(255),
    professor_id BIGINT,
    FOREIGN KEY (professor_id) REFERENCES professores(id)
);