package br.com.fiap.nutriai.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.nutriai.models.AvaliacaoNutricional;
import br.com.fiap.nutriai.models.ConsultaNutricional;
import br.com.fiap.nutriai.models.NutricionistaNutricao;
import br.com.fiap.nutriai.models.PlanoNutricao;
import br.com.fiap.nutriai.models.UsuarioNutricao;
import br.com.fiap.nutriai.repository.AvaliacaoNutricionalRepository;
import br.com.fiap.nutriai.repository.ConsultaNutricionalRepository;
import br.com.fiap.nutriai.repository.NutricionistaNutricaoRepository;
import br.com.fiap.nutriai.repository.PlanoNutricaoRepository;
import br.com.fiap.nutriai.repository.UsuarioNutricaoRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    AvaliacaoNutricionalRepository avaliacaoNutricionalRepository;

    @Autowired
    ConsultaNutricionalRepository consultaNutricionalRepository;

    @Autowired
    NutricionistaNutricaoRepository nutricionistaNutricaoRepository;

    @Autowired
    PlanoNutricaoRepository planoNutricaoRepository;

    @Autowired
    UsuarioNutricaoRepository usuarioNutricaoRepository;

    @Override
    public void run(String... args) throws Exception {
        PlanoNutricao planoBasico = new PlanoNutricao(1L, "Plano Básico", new BigDecimal(20), null);
        PlanoNutricao planoPremium = new PlanoNutricao(2L, "Plano Premium", new BigDecimal(50), new BigDecimal(480));
        planoNutricaoRepository.saveAll(List.of(planoBasico, planoPremium));

        usuarioNutricaoRepository.saveAll(List.of(
            UsuarioNutricao.builder()
                .nome("João")
                .email("joao@gmail.com")
                .senha("senhaJoao123")
                .dataNascimento(LocalDate.of(1990, 5, 15))
                .telefone("(11) 1234-5678")
                .planoNutricao(planoBasico)
                .build(),
            UsuarioNutricao.builder()
                .nome("Maria")
                .email("maria@gmail.com")
                .senha("senhaMaria456")
                .dataNascimento(LocalDate.of(1985, 8, 22))
                .telefone("(11) 9876-5432")
                .planoNutricao(planoPremium)
                .build(),
            UsuarioNutricao.builder()
                .nome("Carlos")
                .email("carlos@gmail.com")
                .senha("senhaCarlos789")
                .dataNascimento(LocalDate.of(1993, 3, 10))
                .telefone("(11) 5555-8888")
                .planoNutricao(planoBasico)
                .build()
        ));

        nutricionistaNutricaoRepository.saveAll(List.of(
            NutricionistaNutricao.builder()
                .nome("Nutricionista 1")
                .email("nutricionista1@gmail.com")
                .senha("senhaNutri123")
                .build(),
            NutricionistaNutricao.builder()
                .nome("Nutricionista 2")
                .email("nutricionista2@gmail.com")
                .senha("senhaNutri456")
                .build()
        ));

        consultaNutricionalRepository.saveAll(List.of(
            ConsultaNutricional.builder()
                .dataConsulta(LocalDate.of(2023, 5, 31))
                .nutricionistaNutricao(nutricionistaNutricaoRepository.findById(1L).get())
                .usuarioNutricao(usuarioNutricaoRepository.findById(1L).get())
                .build(),
            ConsultaNutricional.builder()
                .dataConsulta(LocalDate.of(2024, 8, 1))
                .nutricionistaNutricao(nutricionistaNutricaoRepository.findById(2L).get())
                .usuarioNutricao(usuarioNutricaoRepository.findById(2L).get())
                .build(),
            ConsultaNutricional.builder()
                .dataConsulta(LocalDate.of(2025, 2, 1))
                .nutricionistaNutricao(nutricionistaNutricaoRepository.findById(1L).get())
                .usuarioNutricao(usuarioNutricaoRepository.findById(3L).get())
                .build()
        ));

        avaliacaoNutricionalRepository.saveAll(List.of(
            AvaliacaoNutricional.builder()
                .peso(70.5)
                .altura(1.75)
                .pressaoArterial("120/80")
                .consultaNutricional(consultaNutricionalRepository.findById(1L).get())
                .build(),
            AvaliacaoNutricional.builder()
                .peso(60.2)
                .altura(1.68)
                .pressaoArterial("110/75")
                .consultaNutricional(consultaNutricionalRepository.findById(2L).get())
                .build(),
            AvaliacaoNutricional.builder()
                .peso(80.0)
                .altura(1.80)
                .pressaoArterial("130/85")
                .consultaNutricional(consultaNutricionalRepository.findById(3L).get())
                .build()
        ));
    }
}
