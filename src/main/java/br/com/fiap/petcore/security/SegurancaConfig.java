package br.com.fiap.petcore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {
	
	@Bean
	public SecurityFilterChain filtrar(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((req) -> req
				.requestMatchers("/img/**","/css/**","/h2-console/**").permitAll()
				.requestMatchers("/tutor/novo","/tutor/cadastrar","/medico/novo","/medico/cadastrar").permitAll()
				.requestMatchers("/tutor/minha-conta","/tutor/editar","/tutor/atualizar","/tutor/apagar","/pet/novo","/pet/cadastrar","/pet/editar/**","/pet/atualizar/**").hasAuthority("ROLE_TUTOR")
				.requestMatchers("/medico/minha-conta","/medico/editar","/medico/atualizar","/medico/apagar","/clinica/**","/endereco/**","/medicamento/**","/prontuario/**","/relatorio/**").hasAuthority("ROLE_MEDICO")
				.requestMatchers("/exame/novo","/exame/cadastrar","/exame/editar/**","/exame/atualizar/**","/exame/remover/**").hasAuthority("ROLE_MEDICO")
				.requestMatchers("/receita/nova","/receita/cadastrar","/receita/remover/**","/historico/remover/**").hasAuthority("ROLE_MEDICO")
				.requestMatchers("/pet/listar","/pet/detalhes/**","/historico/listar","/historico/detalhes/**","/exame/listar","/exame/detalhes/**","/receita/listar","/receita/detalhes/**").hasAnyAuthority("ROLE_TUTOR","ROLE_MEDICO")
				.anyRequest().authenticated())
				
		//Configurações necessárias para acesso ao console do Banco H2 em qualquer navegador
				.csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
				.headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin()))
		//*******************************************************************************//
				
		.formLogin((login) -> login.loginPage("/login")
				.defaultSuccessUrl("/home", true).failureUrl("/login?falha=true").permitAll())
		.logout((logout) -> logout.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true").permitAll())
		.exceptionHandling((exception)  -> 
		exception.accessDeniedHandler((request,response,AccessDeniedException)
		-> {response.sendRedirect("/acesso_negado");}));
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
