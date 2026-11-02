alter table tutor_petcore
    add constraint uk_tutor_email unique (email);

alter table tutor_petcore
    add constraint uk_tutor_telefone unique (telefone);

alter table medico_petcore
    add constraint uk_medico_email unique (email);

alter table medico_petcore
    add constraint uk_medico_telefone unique (telefone);

alter table clinica_petcore
    add constraint uk_clinica_cnpj unique (cnpj);

alter table pet_petcore
    add constraint uk_pet_historico unique ("ID_hist_(FK)");

alter table clinica_petcore
    add constraint uk_clinica_endereco unique ("ID_end_(FK)");

alter table pet_petcore
    add constraint fk_pet_historico
    foreign key ("ID_hist_(FK)")
    references historico_petcore;

alter table clinica_petcore
    add constraint fk_clinica_endereco
    foreign key ("ID_end_(FK)")
    references endereco_petcore;

alter table prontuario_petcore
    add constraint fk_prontuario_medico
    foreign key ("ID_med_(PK)")
    references medico_petcore;

alter table prontuario_petcore
    add constraint fk_prontuario_historico
    foreign key ("ID_hist_(PK)")
    references historico_petcore;

alter table exame_petcore
    add constraint fk_exame_medico
    foreign key ("ID_med_(PK)")
    references medico_petcore;

alter table exame_petcore
    add constraint fk_exame_prontuario
    foreign key ("ID_pront_(PK)")
    references prontuario_petcore;

alter table exame_petcore
    add constraint fk_exame_pet
    foreign key ("ID_pet_(FK)")
    references pet_petcore;

alter table receita_petcore
    add constraint fk_receita_medico
    foreign key ("ID_med_(PK)")
    references medico_petcore;

alter table receita_petcore
    add constraint fk_receita_prontuario
    foreign key ("ID_pront_(PK)")
    references prontuario_petcore;

alter table receita_petcore
    add constraint fk_receita_pet
    foreign key ("ID_pet_(FK)")
    references pet_petcore;

alter table relatorio_petcore
    add constraint fk_relatorio_historico
    foreign key ("ID_hist_(PK)")
    references historico_petcore;

alter table relatorio_petcore
    add constraint fk_relatorio_medico
    foreign key ("ID_med_(PK)")
    references medico_petcore;

alter table tut_pet_petcore
    add constraint fk_tut_pet_tutor
    foreign key ("ID_tut_(FK)")
    references tutor_petcore;

alter table tut_pet_petcore
    add constraint fk_tut_pet_pet
    foreign key ("ID_pet_(FK)")
    references pet_petcore;

alter table rec_medic_petcore
    add constraint fk_rec_medic_receita
    foreign key ("ID_rec_(FK)")
    references receita_petcore;

alter table rec_medic_petcore
    add constraint fk_rec_medic_medicamento
    foreign key ("ID_medic_(FK)")
    references medicamento_petcore;

alter table cli_rel_petcore
    add constraint fk_cli_rel_clinica
    foreign key ("ID_cli_(FK)")
    references clinica_petcore;

alter table cli_rel_petcore
    add constraint fk_cli_rel_relatorio
    foreign key ("ID_rel_(FK)")
    references relatorio_petcore;

alter table tutor_petcore
    add constraint ck_tutor_sexo check (sexo in ('F','M'));

alter table medico_petcore
    add constraint ck_medico_sexo check (sexo in ('F','M'));

alter table pet_petcore
    add constraint ck_pet_sexo check (sexo in ('F','M'));

alter table pet_petcore
    add constraint ck_pet_status check (status in (0,1));

alter table historico_petcore
    add constraint ck_historico_status check (status in (0,1));
