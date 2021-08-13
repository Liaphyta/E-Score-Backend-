package esportapplication.code.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 Created by Alek on 19.09.19.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new SimpleCorsFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/lol/previousMatches").permitAll()
                .antMatchers("/lol/upcomingMatches").permitAll()
                .antMatchers("/lol/tournaments").permitAll()
                .antMatchers("/dota2/previousMatches").permitAll()
                .antMatchers("/dota2/upcomingMatches").permitAll()
                .antMatchers("/dota2/tournaments").permitAll()
                .antMatchers("/csgo/previousMatches").permitAll()
                .antMatchers("/csgo/upcomingMatches").permitAll()
                .antMatchers("/csgo/tournaments").permitAll()
                .antMatchers("/teams").permitAll()
                .antMatchers("/users/create").permitAll()
                .antMatchers("/**").authenticated();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("**"));
        configuration.setAllowedMethods(Arrays.asList("**"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
