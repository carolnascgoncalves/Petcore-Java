-- Senha padrao dos usuarios de teste: Petcore123
-- Hash BCrypt: $2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS

insert into tutor_petcore(nome,data_nascimento,telefone,email,sexo,senha,url_img) values
('Ana Souza','1991-04-12','11981000001','ana.souza@petcore.com','F','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS',null),
('Bruno Lima','1988-11-23','11981000002','bruno.lima@petcore.com','M','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS',null),
('Camila Rocha','1995-02-18','11981000003','camila.rocha@petcore.com','F','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS',null),
('Diego Martins','1987-07-30','11981000004','diego.martins@petcore.com','M','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS',null),
('Fernanda Alves','1993-09-05','11981000005','fernanda.alves@petcore.com','F','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS',null);

insert into medico_petcore(nome,data_nascimento,telefone,email,sexo,senha,especialidade,url_img) values
('Mariana Costa','1984-03-14','11982000001','mariana.costa@petcore.com','F','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS','Clinica Geral',null),
('Beatriz Nunes','1989-06-27','11982000002','beatriz.nunes@petcore.com','F','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS','Dermatologia Veterinaria',null),
('Rafael Mendes','1981-12-09','11982000003','rafael.mendes@petcore.com','M','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS','Cardiologia Veterinaria',null),
('Lucas Ferreira','1986-08-16','11982000004','lucas.ferreira@petcore.com','M','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS','Ortopedia Veterinaria',null),
('Juliana Prado','1990-01-21','11982000005','juliana.prado@petcore.com','F','$2y$10$XZG0OV7wJTj2DRVbZy/.7eIPAG.97nD2X9DaNyri.vXf2R95ry7hS','Diagnostico por Imagem',null);

insert into historico_petcore(data,status) values
('2026-08-10 09:00:00',0),
('2026-08-11 10:00:00',0),
('2026-08-12 11:00:00',0),
('2026-08-13 09:30:00',0),
('2026-08-14 14:00:00',0),
('2026-08-15 15:30:00',0),
('2026-08-16 08:45:00',0),
('2026-08-17 13:15:00',0),
('2026-08-18 16:20:00',0);

insert into pet_petcore(nome,especie,raca,data_nascimento,pelagem,porte,sexo,status,url_img,"ID_hist_(FK)") values
('Thor','Cachorro','Golden Retriever','2021-03-10','Dourada','GRANDE','M',0,null,1),
('Mel','Cachorro','Shih Tzu','2022-07-18','Branca e marrom','PEQUENO','F',0,null,2),
('Nina','Gato','Siames','2020-01-25','Clara e escura','MEDIO','F',0,null,3),
('Bob','Cachorro','Beagle','2019-11-03','Tricolor','MEDIO','M',0,null,4),
('Luna','Gato','Persa','2023-02-11','Cinza longa','PEQUENO','F',0,null,5),
('Simba','Gato','Maine Coon','2018-06-22','Marrom longa','GRANDE','M',0,null,6),
('Theo','Cachorro','Bulldog Frances','2021-09-14','Caramelo','MEDIO','M',0,null,7),
('Amora','Cachorro','Border Collie','2020-05-30','Preta e branca','MEDIO','F',0,null,8),
('Bento','Cachorro','Labrador','2022-12-08','Preta curta','GRANDE','M',0,null,9);

insert into tut_pet_petcore("ID_tut_(FK)","ID_pet_(FK)") values
(1,1),(1,2),(1,3),(2,4),(2,5),(3,6),(4,7),(5,8),(5,9);

insert into endereco_petcore(cep,complemento) values
('04309010','Sala 12'),
('04037002','Bloco B'),
('01503001',null),
('05001000','Conjunto 51'),
('03310000','Sala terrea');

insert into clinica_petcore(cnpj,nome,"ID_end_(FK)") values
('12345678000101','PetCare Jabaquara',1),
('23456789000112','Vet Saude Paulista',2),
('34567890000123','Clinica Animal Vida',3),
('45678901000134','Centro Veterinario Oeste',4),
('56789012000145','Pet Mais Tatuape',5);

insert into medicamento_petcore(nome,dosagem) values
('Dipirona','500mg'),
('Amoxicilina','250mg'),
('Cefalexina','500mg'),
('Prednisona','20mg'),
('Meloxicam','2mg'),
('Omeprazol','20mg'),
('Doxiciclina','100mg'),
('Metronidazol','250mg'),
('Carprofeno','25mg'),
('Gabapentina','100mg');

insert into prontuario_petcore(data,descricao,"ID_med_(PK)","ID_hist_(PK)") values
('2026-08-25 10:30:00','Consulta clinica e avaliacao dos resultados do hemograma.',1,1),
('2026-08-27 12:00:00','Avaliacao dermatologica com orientacoes para tratamento de pele.',2,2),
('2026-08-29 15:00:00','Avaliacao cardiologica apos realizacao de ecocardiograma.',3,3),
('2026-09-01 11:30:00','Avaliacao ortopedica apos exame de imagem da pata traseira.',4,4),
('2026-09-02 17:00:00','Consulta de retorno com analise do exame de ultrassom abdominal.',5,5),
('2026-09-04 09:30:00','Consulta clinica para acompanhamento de alteracoes hematologicas.',1,6),
('2026-09-05 14:00:00','Avaliacao ortopedica por dificuldade leve de locomocao.',4,7),
('2026-09-07 16:00:00','Consulta preventiva e avaliacao clinica geral anual.',1,8);

insert into exame_petcore(nome_ex,data,tp_ex,removido,"ID_med_(PK)","ID_pront_(PK)","ID_pet_(FK)") values
('Hemograma completo','2026-08-25 09:00:00','Laboratorial',false,1,1,1),
('Avaliacao dermatologica','2026-08-27 11:00:00','Dermatologico',false,2,2,2),
('Ecocardiograma','2026-08-29 14:00:00','Cardiologico',false,3,3,3),
('Radiografia da pata','2026-09-01 10:30:00','Imagem',false,4,4,4),
('Ultrassom abdominal','2026-09-02 16:00:00','Imagem',false,5,5,5),
('Hemograma de controle','2026-09-04 08:30:00','Laboratorial',false,1,6,6),
('Avaliacao ortopedica','2026-09-05 13:00:00','Ortopedico',false,4,7,7),
('Avaliacao clinica anual','2026-09-07 15:00:00','Clinico Geral',false,1,8,8),
('Retorno dermatologico','2026-09-15 10:00:00','Dermatologico',false,2,null,9),
('Ecocardiograma de retorno','2026-09-20 09:00:00','Cardiologico',false,3,null,1);

insert into receita_petcore(nome,descricao,instrucao,data,validade,removida,"ID_med_(PK)","ID_pront_(PK)","ID_pet_(FK)") values
('Tratamento pos-exame','Medicacao para controle de dor e prevencao de infeccao.','Administrar apos alimentacao.','2026-08-25 11:00:00','2026-10-25',false,1,1,1),
('Tratamento dermatologico','Tratamento para irritacao e infeccao superficial da pele.','Administrar conforme orientacao medica.','2026-08-27 13:00:00','2026-10-27',false,2,2,2),
('Suporte cardiologico','Medicacao de suporte durante acompanhamento cardiologico.','Administrar nos horarios prescritos.','2026-08-29 16:00:00','2026-11-29',false,3,3,3),
('Controle de dor ortopedica','Tratamento para dor e inflamacao decorrente de lesao leve.','Administrar junto com alimento.','2026-09-01 12:00:00','2026-10-01',false,4,4,4),
('Protecao gastrica','Medicacao para protecao gastrica durante o tratamento.','Administrar antes da primeira refeicao.','2026-09-02 17:30:00','2026-10-02',false,5,5,5),
('Tratamento de controle','Medicacao complementar para acompanhamento hematologico.','Administrar uma vez ao dia.','2026-09-04 10:00:00','2026-10-04',false,1,6,6),
('Tratamento ortopedico','Medicacao para controle de dor durante a recuperacao.','Administrar a cada doze horas.','2026-09-05 14:30:00','2026-10-05',false,4,7,7),
('Prevencao anual','Medicacao preventiva apos avaliacao clinica de rotina.','Administrar conforme prescricao.','2026-09-07 16:30:00','2026-11-07',false,1,8,8);

insert into rec_medic_petcore("ID_rec_(FK)","ID_medic_(FK)") values
(1,1),(1,2),
(2,3),(2,4),
(3,6),(3,10),
(4,5),(4,9),
(5,6),(5,8),
(6,1),(6,7),
(7,5),(7,10),
(8,1),(8,6);

insert into relatorio_petcore(observacao,data,"ID_hist_(PK)","ID_med_(PK)") values
('Animal apresentou boa resposta ao tratamento e deve manter acompanhamento de rotina.','2026-08-25 11:30:00',1,1),
('Lesao dermatologica em melhora, mantendo tratamento e retorno programado.','2026-08-27 13:30:00',2,2),
('Exame cardiologico sem alteracoes graves, recomendado acompanhamento periodico.','2026-08-29 16:30:00',3,3),
('Quadro ortopedico leve e com boa evolucao durante o tratamento.','2026-09-01 12:30:00',4,4),
('Ultrassom sem alteracoes importantes, manter acompanhamento preventivo.','2026-09-02 18:00:00',5,5),
('Resultados laboratoriais estaveis e dentro do esperado para o acompanhamento.','2026-09-04 10:30:00',6,1),
('Mobilidade apresentou melhora apos tratamento e periodo de repouso.','2026-09-05 15:00:00',7,4),
('Consulta preventiva sem alteracoes clinicas relevantes no momento.','2026-09-07 17:00:00',8,1);

insert into cli_rel_petcore("ID_cli_(FK)","ID_rel_(FK)") values
(1,1),(1,6),(2,2),(2,8),(3,3),(4,4),(4,7),(5,5);
