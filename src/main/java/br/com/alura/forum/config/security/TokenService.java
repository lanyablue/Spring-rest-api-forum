package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")  // propriedade do aplication properties para ser ejetada
    private String expiration;

    @Value("${forum.jwt.secret}")  // propriedade do aplication properties para ser ejetada
    private String secret;

    public String gerarToken(Authentication authentication){
        Usuario logado = (Usuario) authentication.getPrincipal(); // recupera o usuario que esta logado
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration)); // somando para criar nova data

        return Jwts.builder()  //api do jjwt
                .setIssuer("API do forum da alura") // quem ta gerando o token
                .setSubject(logado.getId().toString()) // Quem é o dono deste token
                .setIssuedAt(hoje)   // Data de geração deste token
                .setExpiration(dataExpiracao)   // Tempo de expiração do token
                .signWith(SignatureAlgorithm.HS256, secret)  //token é criptografado
                .compact();  // compacta td e transforma em uma string
    }


    public boolean isTokenValido(String token) {
        try {
            Jwts.parser() // método para fazer a logica do parse de um token, descriptografar e verificar
                    .setSigningKey(this.secret) // chave para criptografar e descriptografar
                    .parseClaimsJws(token); // objeto para recuperar o token e as informações setadas
            return true;
        } catch (Exception e) {
            return false;
        }


    }
}
