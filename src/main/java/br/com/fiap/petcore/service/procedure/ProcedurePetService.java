
package br.com.fiap.petcore.service.procedure;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProcedurePetService {

    private final JdbcTemplate jdbcTemplate;

    private static final Pattern PADRAO_ID = Pattern.compile("\"idPet\"\\s*:\\s*(\\d+)");

    public ProcedurePetService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String buscarPetsDoTutor(Long idTutor) {

        return jdbcTemplate.execute((ConnectionCallback<String>) connection -> {

            // 1. Habilita a captura do DBMS_OUTPUT
            try (CallableStatement enable = connection.prepareCall(
                    "BEGIN DBMS_OUTPUT.ENABLE(?); END;")) {

                enable.setInt(1, 1000000);
                enable.execute();
            }

            // 2. Executa a procedure do pacote PKG_PETCORE
            try (CallableStatement procedure = connection.prepareCall(
                    "{call pkg_petcore.proc_pets_tutor_json(?)}")) {

                procedure.setLong(1, idTutor);
                procedure.execute();
            }

            // 3. Recupera as linhas produzidas pelo DBMS_OUTPUT
            StringBuilder resultado = new StringBuilder();

            try (CallableStatement getLine = connection.prepareCall(
                    "BEGIN DBMS_OUTPUT.GET_LINE(?, ?); END;")) {

                getLine.registerOutParameter(1, Types.VARCHAR);
                getLine.registerOutParameter(2, Types.INTEGER);

                while (true) {
                    getLine.execute();

                    int status = getLine.getInt(2);

                    if (status != 0) {
                        break;
                    }

                    String linha = getLine.getString(1);

                    if (linha != null) {
                        resultado.append(linha).append("\n");
                    }
                }
            }

            return resultado.toString();
        });
    }

    // Recupera os IDs dos pets retornados pela procedure
    public List<Long> buscarIdsPetsDoTutor(Long idTutor) {

        String resultado = buscarPetsDoTutor(idTutor);

        List<Long> ids = new ArrayList<>();

        Matcher matcher = PADRAO_ID.matcher(resultado);

        while (matcher.find()) {
            ids.add(Long.parseLong(matcher.group(1)));
        }

        return ids;
    }
}
