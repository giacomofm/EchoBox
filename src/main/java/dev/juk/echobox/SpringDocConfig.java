package dev.juk.echobox;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Documentation available at:
 * <ul>
 * <li>/swagger-ui.html</li>
 * <li>/v3/api-docs</li>
 * </ul>
 */
@Configuration
class SpringDocConfig {

	static final String[] DOCS_ANTPATTERNS = {
			// @formatter:off
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/v3/api-docs/**",
			"/v3/api-docs.yaml"
			// @formatter:on
	};

	@Bean
	OpenAPI docsOpenAPI() {
		return new OpenAPI().info(new Info().title("EchoBox API")
				.description(
						"A lightweight REST service that returns custom HTTP responses based on user-provided parameters")
				.version("v1"))
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//				.components(new Components()
//						.addSecuritySchemes("bearer-jwt", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
//						.addSecuritySchemes("basic-auth", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
//				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
//				.addSecurityItem(new SecurityRequirement().addList("basic-auth"))
				;
	}

}