package com.bigdatan.framework.swagger.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springbootcamp.swagger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerConfigurationProperties {

  String title = "APPLICATION.NAME";

  String version = "APPLICATION.VERSION";

  boolean redirect = false;

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getVersion() {
	return version;
}

public void setVersion(String version) {
	this.version = version;
}

public boolean isRedirect() {
	return redirect;
}

public void setRedirect(boolean redirect) {
	this.redirect = redirect;
}
  

}
