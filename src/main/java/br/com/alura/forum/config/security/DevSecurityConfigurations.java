package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // habilitando a parte do security na aplicação
@Configuration    // habilita a leitura das configurações de bin dentro da classe
@Profile("dev")   // carregue esse classe somente se for esse ambiente
public class DevSecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Override // Configurações de autorização
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // autorização
                .antMatchers("/**").permitAll()
                .and().csrf().disable();

    }


}



